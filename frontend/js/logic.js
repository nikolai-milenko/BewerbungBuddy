// frontend/js/logic.js

function analyzeCV(cvText) {
  if (!cvText || cvText.trim() === "") {
    return "Bitte gib einen Lebenslauf-Text ein.";
  }

  if (cvText.toLowerCase().includes("teamarbeit")) {
    return "✅ Erkanntes Soft Skill: Teamarbeit";
  }

  return "⚠️ Kein Hinweis auf Teamarbeit gefunden.";
}

function generateMotivationsschreiben(cvText, jobText) {
  if (!cvText.trim() || !jobText.trim()) {
    return "Bitte Lebenslauf und Stellenanzeige eingeben.";
  }

  return `Sehr geehrte Damen und Herren,

mit großem Interesse habe ich Ihre Stellenanzeige gelesen. Aufgrund meiner Erfahrung im Bereich ${cvText.includes("Teamarbeit") ? "Teamarbeit" : "..."} bin ich überzeugt, dass ich gut zu Ihrem Team passe.

Ich freue mich über die Möglichkeit, meine Motivation in einem persönlichen Gespräch zu erläutern.

Mit freundlichen Grüßen,
Max Mustermann`;
}

module.exports = { analyzeCV, generateMotivationsschreiben };
