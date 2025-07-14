let currentStep = 1;

  function updateSteps() {
    for (let i = 1; i <= 4; i++) {
      document.getElementById("step" + i).classList.add("hidden");
      document.getElementById("stepLabel" + i).classList.remove("active");
    }
    document.getElementById("step" + currentStep).classList.remove("hidden");
    document.getElementById("stepLabel" + currentStep).classList.add("active");
  }

  function nextStep() {
    if (currentStep < 4) {
      currentStep++;
      updateSteps();
    }
  }

  function prevStep() {
    if (currentStep > 1) {
      currentStep--;
      updateSteps();
    }
  }


  updateSteps();