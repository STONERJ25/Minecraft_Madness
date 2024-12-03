const image = document.querySelector("#imageElement");
const screenshotButton = document.querySelector("#screenshotButton");
const darkModeButton = document.querySelector("#darkModeButton");

// Function to refresh the image every second
// Function to refresh the image every second
function refreshImage() {
    const img = document.getElementById("imageElement");

    // Append a timestamp to the URL to bypass cache
    const timestamp = new Date().getTime();
    img.src = `images/burner.jpg?t=${timestamp}`;
}

// Refresh the image every 1 second
setInterval(refreshImage, 1000);


// Screenshot functionality
function takeScreenshot() {
    const img = document.getElementById("imageElement");

    // Use the current `src` of the displayed image as a screenshot URL
    const galleryImg = document.createElement("img");
    galleryImg.src = img.src;
    galleryImg.style.width = "150px"; // Set thumbnail size
    galleryImg.style.margin = "5px"; // Add some space around each thumbnail

    // Append the image to the gallery container
    document.getElementById("galleryContainer").appendChild(galleryImg);

    // Optionally, download the image
    const link = document.createElement('a');
    link.href = img.src;
    link.download = 'screenshot.jpg';
    link.click();
}

// Toggle dark mode
function toggleDarkMode() {
    const body = document.body;

    // Toggle the dark mode class
    body.classList.toggle("dark-mode");

    // Save the preference to local storage
    if (body.classList.contains("dark-mode")) {
        localStorage.setItem("darkMode", "enabled");
    } else {
        localStorage.setItem("darkMode", "disabled");
    }

    // Apply dark mode to other shared elements (e.g., header, footer)
    document.querySelectorAll("header, footer, nav, h1").forEach(element => {
        element.classList.toggle("dark-mode");
    });
}

// Apply the dark mode on page load if it was enabled previously
document.addEventListener("DOMContentLoaded", () => {
    const darkMode = localStorage.getItem("darkMode");
    if (darkMode === "enabled") {
        document.body.classList.add("dark-mode");

        // Apply dark mode to other shared elements (e.g., header, footer)
        document.querySelectorAll("header, footer, nav").forEach(element => {
            element.classList.add("dark-mode");
        });
    }

    // Bind event listeners
    screenshotButton.addEventListener("click", takeScreenshot);
    darkModeButton.addEventListener("click", toggleDarkMode);
});
