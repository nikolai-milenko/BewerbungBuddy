// js/letterGeneration.js

let currentStep = 1;

function showStep(stepNumber) {
  // Hide all steps
  const steps = document.querySelectorAll(".form-box");
  steps.forEach((step) => step.classList.add("hidden"));

  // Show current step
  const current = document.getElementById(`step${stepNumber}`);
  if (current) current.classList.remove("hidden");

  // Highlight step indicator
  const indicators = document.querySelectorAll(".step");
  indicators.forEach((el, index) => {
    if (index === stepNumber - 1) {
      el.classList.add("active-step");
    } else {
      el.classList.remove("active-step");
    }
  });

  currentStep = stepNumber;
}

function nextStep() {
  if (currentStep < 4) {
    showStep(currentStep + 1);
  }
}

function prevStep() {
  if (currentStep > 1) {
    showStep(currentStep - 1);
  }
}

// Initialize first step on page load
document.addEventListener("DOMContentLoaded", () => {
  showStep(1);
});
