function showSpinner() {
    document.getElementById('loading-spinner').style.display = 'block';
    // Simulate a time-consuming process like a form submission or API request
    setTimeout(function() {
      document.getElementById('loading-spinner').style.display = 'none';
      alert("Process Complete!");
    }, 3000); // Replace with actual API call or action
  }
  