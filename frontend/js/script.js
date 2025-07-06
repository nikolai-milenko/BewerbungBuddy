function analyzeCV(cvText) {
  if (!cvText || cvText.trim() === "") {
    return "Bitte gib einen Lebenslauf-Text ein.";
  }

  const softSkills = [
    "Teamarbeit",
    "Kommunikationsfähigkeit",
    "Eigeninitiative",
    "Belastbarkeit",
    "Problemlösungsfähigkeit",
    "Zuverlässigkeit"
  ];

  const foundSkills = softSkills.filter(skill =>
    cvText.toLowerCase().includes(skill.toLowerCase())
  );

  if (foundSkills.length > 0) {
    return `✅ Erkannte Soft Skills: ${foundSkills.join(", ")}`;
  } else {
    return "⚠️ Keine bekannten Soft Skills gefunden.";
  }
}

// Für Unit-Tests exportieren
module.exports = { analyzeCV };
