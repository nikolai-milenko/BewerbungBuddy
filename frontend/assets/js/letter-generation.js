
  // File Upload Logic - Display the file name after selection
  document.getElementById('letterUpload').addEventListener('change', function(event) {
    const fileName = event.target.files[0]?.name || 'Kein Datei ausgewählt';
    document.getElementById('letterFileNameDisplay').textContent = `Ausgewählte Datei: ${fileName}`;
  });

  // Generate Cover Letter Function
  function generateCoverLetter() {
    const jobDescription = document.getElementById('letterJobDesc').value;
    const previewContainer = document.getElementById('letterPreviewContainer');
    
    if (jobDescription.trim() === "") {
      previewContainer.innerHTML = "<p class='text-danger'>Bitte geben Sie eine Stellenbeschreibung ein.</p>";
    } else {
      previewContainer.innerHTML = `
        <h4>Vorschau des Motivationsschreibens:</h4>
        <p>Sehr geehrte Damen und Herren,</p>
        <p>Mit großem Interesse habe ich Ihre Stellenanzeige für die Position gelesen. Die beschriebenen Anforderungen und Aufgaben entsprechen exakt meinen Fähigkeiten und Erfahrungen...</p>
        <p><strong>Stellenbeschreibung:</strong> ${jobDescription}</p>
        <p>Ich freue mich auf die Möglichkeit, mich persönlich bei Ihnen vorzustellen.</p>
        <p>Mit freundlichen Grüßen, <br> Ihr Bewerber</p>
      `;
    }
  }

  // Dark Mode Toggle Logic
  const darkModeToggle = document.getElementById('darkModeToggle');
  const body = document.getElementById('body');

  darkModeToggle.addEventListener('click', () => {
    body.classList.toggle('dark-mode');
    if (body.classList.contains('dark-mode')) {
      darkModeToggle.textContent = "🌙"; // Toggle text to "light mode" when dark mode is on
    } else {
      darkModeToggle.textContent = "🌞"; // Toggle text to "dark mode" when dark mode is off
    }
  });
