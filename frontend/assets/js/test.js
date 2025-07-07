function runMockTests() {
  const fileInput = document.getElementById('cvUpload');
  const jobDesc = document.getElementById('jobDescription');
  const preview = document.getElementById('previewContainer');
  const analyzeBtn = document.getElementById('analyzeButton');
  const resultBox = document.getElementById('analysisResult');

  let testPassed = 0;
  let testFailed = 0;

  function assert(condition, message) {
    if (condition) {
      console.log(`✅ ${message}`);
      testPassed++;
    } else {
      console.error(`❌ ${message}`);
      testFailed++;
    }
  }

  assert(fileInput !== null, "File input is present");
  assert(jobDesc !== null, "Job description textarea is present");
  assert(preview !== null, "Preview container exists");
  assert(analyzeBtn !== null, "Analyze button is present");

  // Simulate button click
  if (analyzeBtn) {
    jobDesc.value = "Sample Job Description";
    fileInput.value = "dummy.pdf"; // Can't set real file, but mock
    analyzeBtn.click();

    setTimeout(() => {
      assert(resultBox.classList.contains('d-block') || !resultBox.classList.contains('d-none'), "Analysis result is shown");
      console.log(`\nTests complete: ${testPassed} passed, ${testFailed} failed`);
    }, 500);
  }
}

runMockTests();

