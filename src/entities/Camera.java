package entities;

import org.lwjgl.input.Mouse;
//import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	public float mouseWheelSensitivity = 0.1f;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 200; //high&low
	private float yaw; //left&right
	private float roll; //tilt inside
	
	private PlayerShip player;
	
	public Camera(PlayerShip player) {
		this.player = player;
	}
	
	public void move() {
		/* przeniesiono do PlayerShip
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=0.5f;
		}else 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+=0.5f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=0.5f;
		}else 
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=0.5f;
		}
		*/		
		
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horiDist, float vertDist) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horiDist * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horiDist * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + vertDist;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * mouseWheelSensitivity;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
