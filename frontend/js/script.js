// frontend/js/script.js

// Logik importieren (nur im Browser eingebunden, nicht in Jest)
import('./logic.js').then(({ analyzeCV, generateMotivationsschreiben }) => {
  document.addEventListener("DOMContentLoaded", () => {
    const cvButton = document.getElementById("cvButton");
    const letterButton = document.getElementById("generateLetterBtn");

    if (cvButton) {
      cvButton.addEventListener("click", () => {
        const input = document.getElementById("cvInput").value;
        const result = analyzeCV(input);
        document.getElementById("cvResult").textContent = result;
      });
    }

    if (letterButton) {
      letterButton.addEventListener("click", () => {
        const cvText = document.getElementById("cvInput").value;
        const jobText = document.getElementById("jobInput").value;
        const result = generateMotivationsschreiben(cvText, jobText);
        document.getElementById("letterOutput").textContent = result;
      });
    }
  });
});

