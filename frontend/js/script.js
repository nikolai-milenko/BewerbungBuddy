// script.js
function analyzeCV(cvText) {
  if (!cvText || cvText.trim() === "") {
    return "Bitte gib einen Lebenslauf-Text ein.";
  }

  if (cvText.toLowerCase().includes("teamarbeit")) {
    return "Top Soft Skills: Teamarbeit";
  }

  return "Keine bekannten Soft Skills gefunden.";
}

// Für Unit-Tests exportieren
module.exports = { analyzeCV };

// CV-Analyse Funktion
function analyzeCV(cvText) {
  if (!cvText || cvText.trim() === "") {
    return "Bitte gib einen Lebenslauf-Text ein.";
  }

  if (cvText.toLowerCase().includes("teamarbeit")) {
    return "✅ Erkanntes Soft Skill: Teamarbeit";
  }

  return "⚠️ Kein Hinweis auf Teamarbeit gefunden.";
}

// Klick-Event für den Button
document.addEventListener("DOMContentLoaded", () => {
  const cvButton = document.getElementById("cvButton");

  if (cvButton) {
    cvButton.addEventListener("click", () => {
      const input = document.getElementById("cvInput").value;
      const result = analyzeCV(input);
      document.getElementById("cvResult").textContent = result;
    });
  }
});
