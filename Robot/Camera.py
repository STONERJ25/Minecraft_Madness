import asyncio
import websockets
import time
from flask import Flask, render_template, Response
import numpy as np
from picamera2 import Picamera2, Preview
from PIL import Image
import io
import traceback

# WebSocket server address
#WS_SERVER = "ws://minecraftmadness-production.up.railway.app:8765"  # Address of the WebSocket server
WS_SERVER = "wss://node-production-617e.up.railway.app"

# Initialize Flask app
app = Flask(__name__)

# Initialize Picamera2
picam2 = Picamera2()

# Create preview configuration (optional: you can change size if needed)
preview_config = picam2.create_preview_configuration(main={"size": (800, 600)})
picam2.configure(preview_config)

# Start preview (needed to adjust settings like exposure, focus, etc.)
picam2.start_preview(Preview.QTGL)
picam2.start()
time.sleep(2)  # Allow time for camera initialization

async def send_video():
    """Capture frames from Picamera2 and send via WebSocket."""
    async with websockets.connect(WS_SERVER, ping_interval=None) as websocket:
        while True:
            # Capture frame from the Pi camera as a numpy array
            frame = picam2.capture_array()

            # Convert numpy array to Pillow Image
            pil_image = Image.fromarray(frame)

            # Ensure image is in RGB mode before saving as JPEG
            pil_image = pil_image.convert('RGB')

            # Convert the image to JPEG in memory
            img_byte_arr = io.BytesIO()
            pil_image.save(img_byte_arr, format='JPEG')
            img_byte_arr = img_byte_arr.getvalue()

            # Send the raw JPEG bytes to the WebSocket server
            await websocket.send(img_byte_arr)
            time.sleep(1/30)

def gen():
    """Generate frames to be used for the Flask video feed."""
    while True:
        # Capture frame from the Pi camera as a numpy array
        frame = picam2.capture_array()

        # Convert numpy array to Pillow Image
        pil_image = Image.fromarray(frame)

        # Ensure image is in RGB mode before saving as JPEG
        pil_image = pil_image.convert('RGB')

        # Convert the image to JPEG in memory
        img_byte_arr = io.BytesIO()
        pil_image.save(img_byte_arr, format='JPEG')
        img_byte_arr = img_byte_arr.getvalue()

        # Yield the frame as raw JPEG bytes for streaming via Flask
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + img_byte_arr + b'\r\n')

@app.route('/video_feed')
def video_feed():
    """Flask route to serve video feed via MJPEG stream."""
    return Response(gen(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/')
def index():
    """Render the index page."""
    return render_template('index.html')

async def main():
    """Main async function to send video over WebSocket."""
    try:
        await send_video()
    finally:
        picam2.close()  # Release the camera when done

def start_app():
    """Start the Flask app and WebSocket sender concurrently."""
    while True:
        try:
            asyncio.run(main())
            app.run(host='0.0.0.0', port=5000, debug=True)
        except Exception as e:
            print("An error occurred:", e)
            traceback.print_exc()
            time.sleep(1)  # Small delay before restarting

if __name__ == '__main__':
    start_app()