const imageElement = document.querySelector("#imageElement");
const screenshotButton = document.querySelector("#screenshotButton");
const darkModeButton = document.querySelector("#darkModeButton");
const galleryContainer = document.querySelector("#galleryContainer");

// Refresh the image every 1 second
function refreshImage() {
    const timestamp = new Date().getTime();
    imageElement.src = `./images/burner.jpg?t=${timestamp}`;
}
setInterval(refreshImage, 1000);

// Screenshot functionality
function takeScreenshot() {
    const galleryImg = document.createElement("img");
    galleryImg.src = imageElement.src;
    galleryImg.style.width = "150px";  // Thumbnail size
    galleryImg.style.margin = "5px";   // Space between thumbnails

    // Append the image to the gallery container
    galleryContainer.appendChild(galleryImg);

    // Automatically download the image
    const link = document.createElement("a");
    link.href = imageElement.src;
    link.download = "screenshot.jpg";
    link.click();
}

// Toggle dark mode
function toggleDarkMode() {
    const body = document.body;

    // Toggle dark mode class
    body.classList.toggle("dark-mode");

    // Save preference to local storage
    const darkModeEnabled = body.classList.contains("dark-mode");
    localStorage.setItem("darkMode", darkModeEnabled ? "enabled" : "disabled");

    // Apply dark mode to other elements
    document.querySelectorAll("header, footer, nav").forEach(element => {
        element.classList.toggle("dark-mode");
    });
}

// Apply dark mode based on stored preference
document.addEventListener("DOMContentLoaded", () => {
    const darkMode = localStorage.getItem("darkMode");
    if (darkMode === "enabled") {
        document.body.classList.add("dark-mode");
        document.querySelectorAll("header, footer, nav").forEach(element => {
            element.classList.add("dark-mode");
        });
    }
});

// Bind event listeners
screenshotButton.addEventListener("click", takeScreenshot);
darkModeButton.addEventListener("click", toggleDarkMode);
