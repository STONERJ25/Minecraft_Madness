const express = require('express');
const WebSocket = require('ws');
const http = require('http');
const path = require('path');

const app = express();
const server = http.createServer(app);

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

// Create a WebSocket server that listens on the same HTTP server
const wss = new WebSocket.Server({ server });

// Handle WebSocket connections from clients
wss.on('connection', (ws) => {
    console.log("New WebSocket connection.");
    // Listen for video frames (Base64-encoded JPEGs) from Python client
    ws.on('message', (message) => {
        console.log("Received a frame.");
        // Broadcast the frame data to all connected clients
        wss.clients.forEach(client => {
            if (client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });
    ws.on('close', () => {
        console.log("WebSocket connection closed.");
    });
});

// Start the server
server.listen(8765, () => {
    console.log('Server running on http://localhost:8765');
});