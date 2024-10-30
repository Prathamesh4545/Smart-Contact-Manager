document.addEventListener("DOMContentLoaded", () => {
  const changeThemeBtn = document.querySelector("#theme_change_button");
  const themeText = changeThemeBtn.querySelector("span");
  const htmlElement = document.querySelector("html");
  
  let currentTheme = getTheme();
  
  // Initialize theme
  updateTheme(currentTheme);
  
  // Attach event listener once
  changeThemeBtn.addEventListener("click", () => {
      currentTheme = currentTheme === "dark" ? "light" : "dark";
      updateTheme(currentTheme);
  });

  function updateTheme(theme) {
      htmlElement.classList.remove("light", "dark");
      htmlElement.classList.add(theme);
      themeText.textContent = theme === "light" ? "Dark" : "Light";
      setTheme(theme);
  }

  function setTheme(theme) {
      localStorage.setItem("theme", theme);
  }

  function getTheme() {
      return localStorage.getItem("theme") || "light";
  }
});
