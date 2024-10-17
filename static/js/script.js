document.addEventListener("DOMContentLoaded", () => {
  let currentTheme = getTheme();
  const changeThemeBtn = document.querySelector("#theme_change_button");
  const themeText = changeThemeBtn.querySelector("span");

  // Attach event listener only once
  changeThemeBtn.addEventListener("click", () => {
    currentTheme = currentTheme === "dark" ? "light" : "dark";
    updateTheme(currentTheme);
  });

  // Initialize theme
  updateTheme(currentTheme);

  function updateTheme(theme) {
    setTheme(theme);
    const htmlElement = document.querySelector("html");
    htmlElement.classList.remove("light", "dark");
    htmlElement.classList.add(theme);
    themeText.textContent = theme === "light" ? "Dark" : "Light";
  }

  function setTheme(theme) {
    localStorage.setItem("theme", theme);
  }

  function getTheme() {
    return localStorage.getItem("theme") || "light";
  }
});
