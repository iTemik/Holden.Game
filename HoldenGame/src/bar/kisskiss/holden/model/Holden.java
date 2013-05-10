package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Holden {

	public enum State {
		IDLE, WALKING, DOING_SOMETHING
	}
	public enum Facing {
		TOP_LEFT,		TOP,		TOP_RIGHT,
		LEFT,			CENTER,		RIGHT,
		BOTTOM_LEFT, 	BOTTOM, 	BOTTOM_RIGHT  
	}

	public static final float SPEED = 20f; // unit per second	
	public static final float SIZE = 5f;  // 10 units = 1 meter.

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	Facing facing = Facing.LEFT;

	float stateTime = 0;

	
	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	public Holden(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void update(float delta) {		
		//position.add(velocity.tmp().mul(delta));
		stateTime += delta;
		position.add(velocity.scl(delta));
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

}
