import socket
import threading
from gpiozero import OutputDevice, PWMOutputDevice
from time import sleep
from threading import Timer

# Pin Definitions for each motor
# Front Left Motor
FL_IN1 = OutputDevice(27)
FL_IN2 = OutputDevice(17)
FL_EN = PWMOutputDevice(22)

# Back Left Motor
BL_IN1 = OutputDevice(26)
BL_IN2 = OutputDevice(19)
BL_EN = PWMOutputDevice(13)

# Front Right Motor
FR_IN1 = OutputDevice(20)
FR_IN2 = OutputDevice(21)
FR_EN = PWMOutputDevice(16)

# Back Right Motor
BR_IN1 = OutputDevice(24)
BR_IN2 = OutputDevice(23)
BR_EN = PWMOutputDevice(25)

stop_timer = None  # Global variable to keep track of the stop timer


def set_all_motors(direction):
    """Sets the direction for all motors."""
    if direction == 1:  # Forward
        FL_IN1.value, FL_IN2.value = 1, 0
        BL_IN1.value, BL_IN2.value = 1, 0
        FR_IN1.value, FR_IN2.value = 1, 0
        BR_IN1.value, BR_IN2.value = 1, 0
    elif direction == -1:  # Backward
        FL_IN1.value, FL_IN2.value = 0, 1
        BL_IN1.value, BL_IN2.value = 0, 1
        FR_IN1.value, FR_IN2.value = 0, 1
        BR_IN1.value, BR_IN2.value = 0, 1


def set_left_turn():
    """Sets motors for turning left."""
    FL_IN1.value, FL_IN2.value = 0, 0
    BL_IN1.value, BL_IN2.value = 0, 0
    FR_IN1.value, FR_IN2.value = 1, 0
    BR_IN1.value, BR_IN2.value = 1, 0


def set_right_turn():
    """Sets motors for turning right."""
    FL_IN1.value, FL_IN2.value = 1, 0
    BL_IN1.value, BL_IN2.value = 1, 0
    FR_IN1.value, FR_IN2.value = 0, 0
    BR_IN1.value, BR_IN2.value = 0, 0


def enable_all_motors(state):
    """Enables or disables all motors."""
    FL_EN.value = BL_EN.value = FR_EN.value = BR_EN.value = state


def stop_motors():
    """Stops all motors."""
    enable_all_motors(0)


def handle_command(command):
    """Handles the commands received from the socket."""
    global stop_timer

    # Cancel any existing stop timer
    if stop_timer:
        stop_timer.cancel()

    if command == "forward":
        set_all_motors(1)
        enable_all_motors(1)
    elif command == "backward":
        set_all_motors(-1)
        enable_all_motors(1)
    elif command == "left":
        set_left_turn()
        enable_all_motors(1)
    elif command == "right":
        set_right_turn()
        enable_all_motors(1)
    elif command == "stop":
        stop_motors()
        return
    else:
        print(f"Unknown command: {command}")
        return

    # Start a new timer to stop the motors after 2 seconds
    stop_timer = Timer(2.0, stop_motors)
    stop_timer.start()


def socket_server():
    """Socket server to receive commands."""
    HOST = "0.0.0.0"  # Listen on all interfaces
    PORT = 12346      # Match this port with the Java client
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
        server.bind((HOST, PORT))
        server.listen(1)
        print(f"Listening on {HOST}:{PORT}")

        conn, addr = server.accept()
        print(f"Connected by {addr}")
        with conn:
            while True:
                data = conn.recv(1024).decode('utf-8').strip()
                if not data:
                    break
                print(f"Received: {data}")
                handle_command(data)


if __name__ == "__main__":
    try:
        server_thread = threading.Thread(target=socket_server, daemon=True)
        server_thread.start()
        print("Socket server started. Waiting for commands...")
        while True:
            sleep(1)  # Keep the main thread alive
    except KeyboardInterrupt:
        print("Shutting down...")
        enable_all_motors(0)
