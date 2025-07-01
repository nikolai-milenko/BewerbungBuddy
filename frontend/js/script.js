<script src="js/script.js"></script>
function analyzeCV(cvText) {
  if (!cvText || cvText.trim() === "") {
    return "Bitte gib einen Lebenslauf-Text ein.";
  }

  if (cvText.toLowerCase().includes("teamarbeit")) {
    return "Top Soft Skill erkannt: Teamarbeit";
  }

  return "Kein Hinweis auf Teamarbeit gefunden.";
}

// Test in der Konsole
console.log(analyzeCV("Ich habe Erfahrung mit Teamarbeit."));
