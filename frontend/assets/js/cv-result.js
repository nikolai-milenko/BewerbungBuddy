const resultBox = document.getElementById("resultBox");

// Get stored data
const jobText = sessionStorage.getItem("jobDescription");
const fileType = sessionStorage.getItem("cvFileType");

// You could later use cvPreview for sending to backend
// const cvData = sessionStorage.getItem("cvPreview");

// Simulate result generation
function generateMockResult(description, type) {
  return `
    ✅ <strong>Match Score:</strong> 82%<br/>
    🔑 <strong>Gefundene Keywords:</strong> Java, REST, Spring Boot<br/>
    ⚠️ <strong>Fehlende:</strong> Docker, CI/CD<br/><br/>
    📝 <strong>Stellenanzeige (Kurzfassung):</strong><br/>
    <pre style="white-space: pre-wrap;">${description.slice(0, 300)}${description.length > 300 ? '...' : ''}</pre>
  `;
}

// Display result
if (jobText && fileType) {
  resultBox.innerHTML = generateMockResult(jobText, fileType);
} else {
  resultBox.innerHTML = "<p class='text-danger'>❌ Es fehlen Eingaben. Bitte erneut versuchen.</p>";
}
