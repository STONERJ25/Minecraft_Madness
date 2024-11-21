// Required packages: ws, express, and fs (file system module is built-in)

const express = require('express');
const WebSocket = require('ws');
const http = require('http');
const fs = require('fs');
const path = require('path');

// Create an Express app
const app = express();
const server = http.createServer(app);

// Create a WebSocket server that listens on the same HTTP server
const wss = new WebSocket.Server({ server });

// Serve static files for direct image access
app.use('/Website/images', express.static(path.join(__dirname, 'images')));

// Serve the HTML page with the video stream
app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

// Handle WebSocket connections from clients
wss.on('connection', (ws) => {
  console.log("New WebSocket connection.");

  // Listen for video frames (Base64-encoded JPEGs) from the client
  ws.on('message', (message) => {
    console.log("Received a frame.");

    // Decode the Base64-encoded image
    const buffer = Buffer.from(message, 'base64');
    const filePath = path.join(__dirname, 'Website/images', 'burner.jpg'); // Always save as burner.jpg

    // Save the frame as burner.jpg, overwriting the previous image
    fs.writeFile(filePath, buffer, (err) => {
      if (err) {
        console.error('Error saving image:', err);
      } else {
        // Send the direct URL to the saved image back to the client
        const imageUrl = `http://localhost:8765/Website/images/burner.jpg`;
        ws.send(imageUrl);
      }
    });
  });

  ws.on('close', () => {
    console.log("WebSocket connection closed.");
  });
});

// Start the server
server.listen(8765, () => {
  console.log("WebSocket server running on ws://localhost:8765");
});
