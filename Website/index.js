// Required packages: ws, express, fs, helmet, and cors
const express = require('express');
const WebSocket = require('ws');
const http = require('http');
const fs = require('fs');
const path = require('path');
const helmet = require('helmet');
const cors = require('cors');

// Create an Express app
const app = express();
const server = http.createServer(app);

// Use Helmet for security headers, including a permissive Content Security Policy
app.use(
  helmet({
    contentSecurityPolicy: {
      directives: {
        defaultSrc: ["'self'"],
        imgSrc: ["'self'", "data:", "http:"],
      },
    },
  })
);

// Enable CORS to allow cross-origin requests
app.use(cors());

// Serve static files for direct image access
app.use('/images', express.static(path.join(__dirname, 'images')));

app.use(
  helmet.contentSecurityPolicy({
    directives: {
      defaultSrc: ["'self'"], // Allow resources from your own domain
      imgSrc: ["'self'", "https: data:"], // Allow images from your domain and external sources
      scriptSrc: ["'self'"], // Allow scripts from your domain
      styleSrc: ["'self'", "'unsafe-inline'"], // Allow styles from your domain and inline styles
      objectSrc: ["'none'"], // Disallow <object>, <embed>, and <applet> tags
      connectSrc: ["'self'"], // Allow connections to your domain
      frameSrc: ["'self'"], // Allow iframes from your domain
    },
  })
);
// Serve a favicon to prevent 404 errors for favicon requests
app.get('/favicon.ico', (req, res) => {
  res.sendFile(path.join(__dirname, 'favicon.ico'));
});

app.use(helmet({ contentSecurityPolicy: false }));
// Serve the HTML page with the video stream
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'index.html'));
});

// Create a WebSocket server that listens on the same HTTP server
const wss = new WebSocket.Server({ server });

// Handle WebSocket connections from clients
wss.on('connection', (ws, req) => {
  console.log("New WebSocket connection.");

  // Listen for video frames (Base64-encoded JPEGs) from the client
  ws.on('message', (message) => {
    console.log("Received a frame.");

    // Decode the Base64-encoded image
    const buffer = Buffer.from(message, 'base64');
    const filePath = path.join(__dirname, 'images', 'burner.jpg'); // Always save as burner.jpg

    // Save the frame as burner.jpg, overwriting the previous image
    fs.writeFile(filePath, buffer, (err) => {
      if (err) {
        console.error('Error saving image:', err);
      } else {
        // Dynamically generate the URL to the saved image
        const imageUrl = `http://${req.headers.host}/images/burner.jpg`;
        ws.send(imageUrl); // Send the image URL back to the client
      }
    });
  });

  ws.on('close', () => {
    console.log("WebSocket connection closed.");
  });
});

// Ensure the 'images' directory exists
const imagesDir = path.join(__dirname, 'images');
if (!fs.existsSync(imagesDir)) {
  fs.mkdirSync(imagesDir);
}

// Start the server
const PORT = 8765;
server.listen(PORT, () => {
  console.log(`WebSocket server running on ws://localhost:${PORT}`);
});
