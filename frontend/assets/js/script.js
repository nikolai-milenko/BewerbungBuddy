// Dark Mode Toggle Logic
const darkModeToggle = document.getElementById('darkModeToggle');
const body = document.body;

darkModeToggle.addEventListener('click', () => {
  body.classList.toggle('dark-mode');
  // Change button text based on current mode
  if (body.classList.contains('dark-mode')) {
    darkModeToggle.textContent = "🌞"; // Change text to light mode when dark mode is on
  } else {
    darkModeToggle.textContent = "🌙"; // Change text to dark mode when light mode is on
  }
});
document.getElementById('letterUpload').addEventListener('change', function(event) {
  const file = event.target.files[0];
  const fileNameDisplay = document.getElementById('letterFileNameDisplay');
  const imagePreview = document.getElementById('letterImagePreview');

  if (file) {
    fileNameDisplay.textContent = `Ausgewählte Datei: ${file.name}`;

    if (file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onload = function(e) {
        imagePreview.src = e.target.result;
        imagePreview.classList.remove('d-none');
      };
      reader.readAsDataURL(file);
    } else {
      // Hide image preview if the file is not an image
      imagePreview.classList.add('d-none');
      imagePreview.src = '';
    }
  } else {
    fileNameDisplay.textContent = 'Kein Datei ausgewählt';
    imagePreview.classList.add('d-none');
    imagePreview.src = '';
  }
});
