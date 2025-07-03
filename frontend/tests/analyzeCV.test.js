// frontend/tests/analyzeCV.test.js
const { analyzeCV } = require("../js/logic");

describe("analyzeCV", () => {
  test("gibt Hinweis bei leerem Text", () => {
    expect(analyzeCV("")).toMatch(/bitte/i);
  });

  test("erkennt Teamarbeit", () => {
    expect(analyzeCV("Ich arbeite gerne im Team und liebe Teamarbeit.")).toMatch(/teamarbeit/i);
  });

  test("kein Hinweis gefunden", () => {
    expect(analyzeCV("Ich liebe Zahlen und Statistiken.")).toMatch(/kein/i);
  });
});
