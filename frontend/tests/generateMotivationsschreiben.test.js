// frontend/tests/generateMotivationsschreiben.test.js
const { generateMotivationsschreiben } = require("../js/logic");

describe("generateMotivationsschreiben", () => {
  test("gibt Hinweis bei fehlendem Input", () => {
    expect(generateMotivationsschreiben("", "")).toMatch(/bitte/i);
  });

  test("erzeugt Schreiben mit Teamarbeit", () => {
    const result = generateMotivationsschreiben("Ich habe viel Teamarbeit gemacht", "Teamplayer gesucht");
    expect(result).toMatch(/Teamarbeit/);
  });

  test("ohne Schlüsselwörter", () => {
    const result = generateMotivationsschreiben("Ich analysiere Daten", "Wir suchen Analysten");
    expect(result).toMatch(/\.\.\./);
  });
});
