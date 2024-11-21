import asyncio
import websockets
import cv2
import numpy as np

# WebSocket server address
WS_SERVER = "ws://localhost:8765"  # Address of the WebSocket server

# OpenCV video capture (default webcam)
cap = cv2.VideoCapture(0)

async def send_video():
    async with websockets.connect(WS_SERVER) as websocket:
        while True:
            # Capture frame from webcam
            ret, frame = cap.read()

            if not ret:
                break

            # Encode frame as JPEG (binary format)
            _, buffer = cv2.imencode('.jpg', frame)
            
            # Convert to binary data (do not encode to base64)
            frame_data = buffer.tobytes()  # Get binary data directly from the buffer

            # Send the frame as binary data
            await websocket.send(frame_data)

# Start sending video frames
asyncio.get_event_loop().run_until_complete(send_video())




# Start the WebSocket client and stream video

async def main():

    try:

        await send_video()

    finally:

        cap.release()  # Release the video capture when done



if __name__ == '__main__':

    asyncio.run(main())
