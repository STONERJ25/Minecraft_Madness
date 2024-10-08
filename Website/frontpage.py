import cv2
from flask import Flask, render_template, Response

app = Flask(__name__)

def gen_frames():
    camera = cv2.VideoCapture(0)  # Use 0 for the default webcam
    while True:
        success, frame = camera.read()  # Read frame from the webcam
        if not success:
            break
        else:
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/')
def index():
    return render_template('frontpage.html')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
