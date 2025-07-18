// js/sign-up.js

document.addEventListener('DOMContentLoaded', () => {
    // Select elements
    const usernameInput = document.querySelector('input[type="text"]');
    const passwordInput = document.querySelector('input[type="password"]');
    const submitButton = document.querySelector('input[type="submit"]');
    const forgotLink  = document.querySelector('.group a:first-child');
    const signupLink  = document.querySelector('.group a:last-child');
  
    // Handle login click
    submitButton.addEventListener('click', (e) => {
      e.preventDefault(); // prevent form submission if wrapped in a form
  
      const username = usernameInput.value.trim();
      const password = passwordInput.value;
  
      // Basic validation
      if (!username) {
        alert('Bitte Benutzername eingeben.');
        usernameInput.focus();
        return;
      }
      if (!password) {
        alert('Bitte Passwort eingeben.');
        passwordInput.focus();
        return;
      }
  
      // TODO: replace with real API call
      console.log('Logging in as', username);
      // Example:
      // fetch('/api/login', {
      //   method: 'POST',
      //   headers: { 'Content-Type': 'application/json' },
      //   body: JSON.stringify({ username, password })
      // })
      // .then(res => res.json())
      // .then(data => {
      //   if (data.success) window.location.href = '/dashboard.html';
      //   else alert('Login fehlgeschlagen: ' + data.message);
      // })
      // .catch(err => alert('Ein Fehler ist aufgetreten: ' + err.message));
    });
  
    // Optional: hooks for the other links
    forgotLink.addEventListener('click', (e) => {
      e.preventDefault();
      // TODO: navigate to your "forgot password" page
      alert('Leite weiter zur Passwort‑vergessen‑Seite…');
    });
  
    signupLink.addEventListener('click', (e) => {
      e.preventDefault();
      // TODO: navigate to your "registration" page
      alert('Leite weiter zur Registrierungsseite…');
    });
  });
  