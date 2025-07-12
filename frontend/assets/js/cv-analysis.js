const fileInput = document.getElementById("cvUpload");
const step1Next = document.getElementById("step1-next");
const pdfCanvas = document.getElementById("pdfPreviewCanvas");
const imagePreview = document.getElementById("imagePreview");

pdfjsLib.GlobalWorkerOptions.workerSrc = "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js";

fileInput.addEventListener("change", () => {
  const file = fileInput.files[0];
  step1Next.disabled = true;

  // Reset preview
  pdfCanvas.classList.add("d-none");
  imagePreview.classList.add("d-none");

  if (!file) return;

  const fileType = file.type;

  if (fileType === "application/pdf") {
    const reader = new FileReader();
    reader.onload = async function () {
      const pdfData = new Uint8Array(reader.result);
      const pdf = await pdfjsLib.getDocument({ data: pdfData }).promise;
      const page = await pdf.getPage(1);
      const viewport = page.getViewport({ scale: 1.2 });

      const context = pdfCanvas.getContext("2d");
      pdfCanvas.width = viewport.width;
      pdfCanvas.height = viewport.height;

      await page.render({ canvasContext: context, viewport: viewport }).promise;
      pdfCanvas.classList.remove("d-none");

      sessionStorage.setItem("cvFileType", "pdf");
      sessionStorage.setItem("cvPreview", reader.result); // for later if needed
      step1Next.disabled = false;
    };
    reader.readAsArrayBuffer(file);

  } else if (fileType.startsWith("image/")) {
    const reader = new FileReader();
    reader.onload = function () {
      imagePreview.src = reader.result;
      imagePreview.classList.remove("d-none");

      sessionStorage.setItem("cvFileType", "image");
      sessionStorage.setItem("cvPreview", reader.result);
      step1Next.disabled = false;
    };
    reader.readAsDataURL(file);

  } else {
    alert("Bitte laden Sie eine PDF- oder Bilddatei hoch.");
  }
});

step1Next.addEventListener("click", () => {
  // Navigate to the job description step (next page)
  window.location.href = "cv-description.html";
});
