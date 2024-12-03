import asyncio
import websockets
import cv2
import base64
import numpy as np
from flask import Flask, render_template, Response
import time
import traceback

# WebSocket server address
#WS_SERVER = "ws://localhost:8765"  # Address of the WebSocket server
WS_SERVER = "wss://node-production-617e.up.railway.app"  # Address of the WebSocket server

# OpenCV video capture (default webcam)
cap = cv2.VideoCapture(0)

app = Flask(__name__)

# Don't use base64 encoding; send raw JPEG bytes
async def send_video():
    async with websockets.connect(WS_SERVER, ping_interval=None) as websocket:
        while True:
            # Capture frame from webcam
            ret, frame = cap.read()
            if not ret:
                break
            # Encode frame as JPEG
            _, buffer = cv2.imencode('.jpg', frame)
            # Send the raw JPEG bytes to the WebSocket server
            await websocket.send(buffer.tobytes())

def gen():
    while True:
        success, frame = cap.read()
        if not success:
            break
        else:
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/')
def index():
    return render_template('index.html')

async def main():
    try:
        await send_video()
    finally:
        cap.release()  # Release the video capture when done

def start_app():
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
