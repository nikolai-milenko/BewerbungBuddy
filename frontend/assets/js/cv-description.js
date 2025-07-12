const jobInput = document.getElementById("jobDescription");
const showResultBtn = document.getElementById("showResultBtn");

jobInput.addEventListener("input", () => {
  showResultBtn.disabled = jobInput.value.trim().length === 0;
});

showResultBtn.addEventListener("click", () => {
  const jobText = jobInput.value.trim();

  // Optional: Save to sessionStorage
  sessionStorage.setItem("jobDescription", jobText);

  // Redirect to result page
  window.location.href = "cv-result.html";
});
