
// Install required npm packages

// npm install ws express



const express = require('express');

const WebSocket = require('ws');

const http = require('http');



// Create an Express app

const app = express();

const server = http.createServer(app);



// Create a WebSocket server that listens on the same HTTP server

const wss = new WebSocket.Server({ server });



// Serve the HTML page with the video stream

app.get('/', (req, res) => {

  res.sendFile(__dirname + '/index.html');

});



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

  console.log("WebSocket server running on ws://localhost:8765");

});
