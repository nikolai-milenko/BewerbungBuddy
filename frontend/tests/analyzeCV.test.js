const { analyzeCV } = require("../js/script.js");

describe("analyzeCV", () => {
  test("gibt Hinweis bei leerem Text", () => {
    expect(analyzeCV("")).toBe("Bitte gib einen Lebenslauf-Text ein.");
  });

  test("erkennt ein Soft Skill", () => {
    const input = "Ich habe viel Teamarbeit geleistet.";
    const result = analyzeCV(input);
    expect(result).toContain("Teamarbeit");
  });

  test("erkennt mehrere Soft Skills", () => {
    const input = "Ich bin sehr zuverlässig und arbeite gerne im Team. Kommunikationsfähigkeit ist meine Stärke.";
    const result = analyzeCV(input);
    expect(result).toContain("Team");
    expect(result).toContain("Kommunikationsfähigkeit");
  });

  test("erkennt keine Soft Skills, wenn keine vorhanden sind", () => {
  const result = analyzeCV("Ich liebe es zu reisen und Kuchen zu backen.");
  expect(result).toBe("⚠️ Keine bekannten Soft oder Hard Skills gefunden.");
});

test("erkennt Hard Skills wie Python und Projektmanagement", () => {
  const input = "Ich habe Erfahrung in Python und Projektmanagement.";
  const result = analyzeCV(input);
  expect(result).toContain("Python");
  expect(result).toContain("Projektmanagement");
});

});
