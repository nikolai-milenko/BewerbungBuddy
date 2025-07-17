pdfjsLib.GlobalWorkerOptions.workerSrc =
  'https://unpkg.com/pdfjs-dist@3.10.111/build/pdf.worker.min.js';

const BACKEND_SERVER='http://localhost:8080';
//const BACKEND_SERVER='https://bewerbungbuddy.de';

const totalSteps = 4;

const pdfUpload = document.getElementById('pdfUpload');
const pdfPlaceholder = document.getElementById('pdfPlaceholder');
const pdfPreviewContainer = document.getElementById('pdfPreviewContainer');

function goToStep(step) {
  for (let i = 1; i <= totalSteps; i++) {
    document.getElementById(`wizardStep${i}`)
      .classList.toggle('d-none', i !== step);
  }

  document.querySelectorAll('.wizard-progress .circle').forEach((circle, idx) => {
    if (idx < step) circle.classList.add('filled');
    else circle.classList.remove('filled');
  });

  const percent = ((step - 1) / (totalSteps - 1)) * 100;
  const fillBar = document.getElementById('wizardFillBar');
  if (fillBar) fillBar.style.width = `${percent}%`;

  if (step === 3) {
    const msg = document.querySelector('#wizardStep3 p');

    if (!window.uploadedCvId || !window.jobAdId) {
      msg.innerText = "❌ Fehler: fehlende IDs";
      console.error("❌ cvDocumentId oder jobAdId fehlt:", {
        cvDocumentId: window.uploadedCvId,
        jobAdvertisementId: window.jobAdId
      });
      return;
    }

    msg.innerText = "Analyse läuft...";

    fetch(BACKEND_SERVER + '/api/cv-analysis', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        cvDocumentId: window.uploadedCvId,
        jobAdvertisementId: window.jobAdId
      })
    })
      .then(res => res.json())
      .then(data => {
        window.analysisResult = data;

        setTimeout(() => {
          goToStep(4);
          renderStep4(data);
        }, 1500);
      })
      .catch(err => {
        console.error(err);
        msg.innerText = "❌ Analyse fehlgeschlagen.";
      });
  }
}

function renderStep4(data) {
  const score = parseInt(data.matchScore ?? 0);
  const clampedScore = Math.min(Math.max(score, 0), 100);
  const circumference = 2 * Math.PI * 90;
  const offset = circumference - (circumference * clampedScore) / 100;

  const circle = document.getElementById("matchScoreCircle");
  const scoreText = document.getElementById("matchScoreText");

  let color = "#dc3545";
  if (clampedScore >= 80) {
    color = "#006400";
  } else if (clampedScore >= 60) {
    color = "#28a745";
  } else if (clampedScore >= 40) {
    color = "#ffc107";
  }
  if (circle) {
    circle.setAttribute("stroke", color);
    circle.setAttribute("stroke-dashoffset", offset);
  }
  if (scoreText) {
    scoreText.textContent = clampedScore + "%";
  }

  const strengths = Array.isArray(data.strengths) ? data.strengths : [];
  const weaknesses = Array.isArray(data.weaknesses) ? data.weaknesses : [];
  const maxRows = Math.max(strengths.length, weaknesses.length);

  let rowsHtml = "";
  for (let i = 0; i < maxRows; i++) {
    const s = strengths[i]
      ? `<td><i class="bi bi-plus-circle text-success me-2"></i>${strengths[i]}</td>`
      : `<td></td>`;
    const w = weaknesses[i]
      ? `<td><i class="bi bi-dash-circle text-danger me-2"></i>${weaknesses[i]}</td>`
      : `<td></td>`;
    rowsHtml += `<tr>${s}${w}</tr>`;
  }
  document.getElementById("analysisTableBody").innerHTML = rowsHtml;
}

if (pdfUpload) {
  pdfUpload.addEventListener('change', async function () {
    const file = pdfUpload.files[0];
    if (!file) return;

    if (file.type !== 'application/pdf') {
      pdfPlaceholder.innerHTML = '<p class="text-danger">❌ Nur PDF-Dateien werden unterstützt.</p>';
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
      const res = await fetch(BACKEND_SERVER + '/api/cv/upload', {
        method: 'POST',
        body: formData
      });

      const text = await res.text();
      console.log("📦 Antwort vom Server:", text);

      if (!res.ok) {
        throw new Error('Upload fehlgeschlagen: ' + text);
      }

      let result;
      try {
        result = JSON.parse(text);
      } catch (e) {
        throw new Error("❌ Antwort ist kein gültiger JSON");
      }

      if (!result.id) {
        throw new Error("❌ response.id fehlt");
      }

      window.uploadedCvId = result.id;

      const fileURL = URL.createObjectURL(file);
      pdfPreviewContainer.innerHTML = `
        <iframe src="${fileURL}" width="100%" height="600px" style="border: none;" class="rounded"></iframe>
      `;
      document.getElementById('step1NextBtn').disabled = false;
    } catch (err) {
      console.error(err);
      pdfPlaceholder.innerHTML = '<p class="text-danger">❌ Fehler beim Upload.</p>';
    }
  });
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('step1NextBtn').disabled = true;
  const jobInput = document.getElementById('jobDescriptionInput');
  const step2NextBtn = document.getElementById('step2NextBtn');

  if (jobInput && step2NextBtn) {
    jobInput.addEventListener('input', () => {
      step2NextBtn.disabled = jobInput.value.trim() === '';
    });
    step2NextBtn.disabled = true;
  }

  const fillBar = document.getElementById('wizardFillBar');
  if (fillBar) fillBar.style.width = '0%';

  requestAnimationFrame(() => requestAnimationFrame(() => goToStep(1)));

  document.getElementById('step2NextBtn').addEventListener('click', async function () {
    const jobText = document.getElementById('jobDescriptionInput').value.trim();
    if (!jobText || !window.uploadedCvId) {
      alert("Bitte Lebenslauf und Stellenanzeige hochladen.");
      return;
    }

    try {
      const jobRes = await fetch(BACKEND_SERVER + '/api/job-advertisements', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ rawText: jobText })
      });

      const jobTextResult = await jobRes.text();
      console.log("📄 Antwort von JobAd:", jobTextResult);

      let jobData;
      try {
        jobData = JSON.parse(jobTextResult);
      } catch (e) {
        alert("❌ Ungültige Antwort vom JobAd-Endpoint");
        return;
      }

      if (!jobData.id) {
        alert("❌ Keine Job-ID in der Antwort");
        return;
      }

      window.jobAdId = jobData.id;
      goToStep(3);
    } catch (err) {
      console.error(err);
      alert("Fehler beim Speichern der Stellenanzeige.");
    }
  });

  // Drag & Drop
  document.querySelectorAll('.upload-box').forEach(box => {
    const input = box.querySelector('input[type="file"]');

    box.addEventListener('click', () => {
      if (input) input.click();
    });

    box.addEventListener('dragover', e => {
      e.preventDefault();
      box.classList.add('dragover');
    });

    box.addEventListener('dragleave', () => {
      box.classList.remove('dragover');
    });

    box.addEventListener('drop', async e => {
      e.preventDefault();
      box.classList.remove('dragover');

      const file = e.dataTransfer.files[0];
      if (!file || file.type !== 'application/pdf') {
        pdfPlaceholder.innerHTML = '<p class="text-danger">❌ Nur PDF-Dateien werden unterstützt.</p>';
        return;
      }

      const formData = new FormData();
      formData.append('file', file);

      try {
        const res = await fetch(BACKEND_SERVER + '/api/cv/upload', {
          method: 'POST',
          body: formData
        });

        const text = await res.text();
        console.log("📦 Antwort vom Server (drop):", text);

        if (!res.ok) throw new Error('Upload fehlgeschlagen: ' + text);

        const result = JSON.parse(text);
        if (!result.id) throw new Error("❌ response.id fehlt");

        window.uploadedCvId = result.id;

        const fileURL = URL.createObjectURL(file);
        pdfPreviewContainer.innerHTML = `
          <iframe src="${fileURL}" width="100%" height="600px" style="border: none;" class="rounded"></iframe>
        `;
        document.getElementById('step1NextBtn').disabled = false;
      } catch (err) {
        console.error(err);
        pdfPlaceholder.innerHTML = '<p class="text-danger">❌ Fehler beim Upload.</p>';
      }
    });
  });
});