package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TextureModel;
import renderengine.DisplayManager;

public class PlayerShip extends Entity {

	private static final float SHIP_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float SPIN_SPEED = 120;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float currentSpinSpeed = 0;

	public PlayerShip(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move() {
		checkInputs();
		super.increaseRotation(0/*currentSpinSpeed * DisplayManager.getFrameTimeSeconds()*/, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		
		/*float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dy = 0;// (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, dy, dz);*/
		
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dy = 0;//(float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, dy, dz);
	}

	private void checkInputs() {

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			this.currentSpeed = -SHIP_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_S))
			this.currentSpeed = SHIP_SPEED / 2;
		else
			this.currentSpeed = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			this.currentTurnSpeed = -TURN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			this.currentTurnSpeed = TURN_SPEED;
		else
			this.currentTurnSpeed = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			this.currentSpinSpeed = -SPIN_SPEED;
		else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			this.currentSpinSpeed = SPIN_SPEED;
		else
			this.currentSpinSpeed = 0;
	}
}
