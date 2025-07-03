document.addEventListener('DOMContentLoaded', function () {
  const cvUpload = document.getElementById('cvUpload');
  const fileNameDisplay = document.getElementById('fileNameDisplay');
  const previewContainer = document.getElementById('previewContainer');

  cvUpload.addEventListener('change', function () {
    const file = cvUpload.files[0];

    if (!file) return;

    if (
      file.type === 'application/pdf' ||
      file.type === 'image/jpeg' ||
      file.type === 'image/png'
    ) {
      fileNameDisplay.textContent = `📎 Datei ausgewählt: ${file.name}`;
    } else {
      fileNameDisplay.textContent = '❌ Nur PDF oder Bilddateien (JPEG, PNG) werden unterstützt.';
      previewContainer.innerHTML = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
      const fileURL = e.target.result;

      if (file.type === 'application/pdf') {
        previewContainer.innerHTML = `<iframe src="${fileURL}" width="100%" height="600px" style="border:none;"></iframe>`;
      } else if (file.type.startsWith('image/')) {
        previewContainer.innerHTML = `<img src="${fileURL}" class="img-fluid rounded border" alt="Vorschau">`;
      }
    };

    reader.readAsDataURL(file);
  });
});
