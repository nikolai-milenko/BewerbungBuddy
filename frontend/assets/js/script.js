// Dark Mode Toggle Logic
const darkModeToggle = document.getElementById('darkModeToggle');
const body = document.body;

darkModeToggle.addEventListener('click', () => {
  body.classList.toggle('dark-mode');
  // Change button text based on current mode
  if (body.classList.contains('dark-mode')) {
    darkModeToggle.textContent = "🌞"; // Change text to light mode when dark mode is on
  } else {
    darkModeToggle.textContent = "🌙"; // Change text to dark mode when light mode is on
  }
});
