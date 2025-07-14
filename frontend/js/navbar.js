document.addEventListener("DOMContentLoaded", function () {
  const toolsLink = document.getElementById('toolsLink');
  const toolsMenu = document.getElementById('toolsMenu');

  toolsLink.addEventListener('click', function (e) {
    e.preventDefault();
    toolsMenu.classList.toggle('show');
    toolsLink.classList.toggle('active');
  });

  document.addEventListener('click', function (e) {
    if (!toolsLink.contains(e.target) && !toolsMenu.contains(e.target)) {
      toolsMenu.classList.remove('show');
      toolsLink.classList.remove('active');
    }
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const burgerBtn = document.getElementById('burgerBtn');
  const navLinks = document.getElementById('navLinks');

  burgerBtn.addEventListener('click', function () {
    navLinks.classList.toggle('show');
  });
});
