package bar.kisskiss.holden.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Camera {

	private Vector2 position = new Vector2(0,0);
	private Vector2 acceleration = new Vector2(0,0);
	private Vector2 velocity = new Vector2(0,0);
	private OrthographicCamera cam;
	private float ppuX = 6;
	private float ppuY = 6;
	
	private boolean free = true;
	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public float getPpuX() {
		return ppuX;
	}

	public void setPpuX(float ppuX) {
		this.ppuX = ppuX;
	}

	public float getPpuY() {
		return ppuY;
	}

	public void setPpuY(float ppuY) {
		this.ppuY = ppuY;
	}

	private Vector2 deltaPosition = new Vector2(0,0);
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

	public Camera(OrthographicCamera cam) {
		this.cam = cam;	
		setPosition( new Vector2(0, 0));
	}
	
	public void resize(float width, float height) {
		/*AK TODO: Improve*/
		position.x += width/ppuX/4;
		position.y += height/ppuY/4;
		
		cam.viewportWidth = width/ppuX;
		cam.viewportHeight = height/ppuY;
		cam.update();
		
	}

	public void setShift(float dx, float dy) {
		// TODO Auto-generated method stub
		deltaPosition.set(dx, dy);
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public Vector2 getDeltaPosition() {
		return deltaPosition;
	}

	public void setDeltaPosition(Vector2 deltaPosition) {
		this.deltaPosition = deltaPosition;
	}

	public void update() {
		cam.lookAt(position.x, position.y, 0);	
	}	
	
	public float getWidth() {
		return cam.viewportWidth;
	}
	
	public float getHeight() {
		return cam.viewportHeight;	
	}
	
}
