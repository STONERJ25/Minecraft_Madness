const express = require('express');
const app = express();
const path = require('path');

// Serve static files (CSS, JS, images) from the "static" directory
app.use(express.static(path.join(__dirname, 'static')));

// Route for the main page
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'templates/index.html')); // Serve HTML file
});

// Route for the front page if it's separate
app.get('/frontpage', (req, res) => {
    res.sendFile(path.join(__dirname, 'templates/frontpage.html'));
});

// Start the server
app.listen(3000, () => {
    console.log('Server running on http://localhost:3000');
});
