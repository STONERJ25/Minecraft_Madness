import asyncio
import websockets
import cv2
import base64
import numpy as np
from flask import Flask, render_template, Response

# WebSocket server address
WS_SERVER = "ws://localhost:8765"  # Address of the WebSocket server

# OpenCV video capture (default webcam)
cap = cv2.VideoCapture(0)

app = Flask(__name__)

async def send_video():
    async with websockets.connect(WS_SERVER) as websocket:
        while True:
            # Capture frame from webcam
            ret, frame = cap.read()
            if not ret:
                break
            # Encode frame as JPEG
            _, buffer = cv2.imencode('.jpg', frame)
            # Convert to base64 to send over WebSocket
            frame_data = base64.b64encode(buffer).decode('utf-8')
            # Send the frame to the WebSocket server
            await websocket.send(frame_data)

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

if __name__ == '__main__':
    asyncio.run(main())
    app.run(host='0.0.0.0', port=5000, debug=True)