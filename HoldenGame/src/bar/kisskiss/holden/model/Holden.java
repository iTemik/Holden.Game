package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Vector2;

public class Holden extends MovingObject {

	public enum State {
		IDLE, WALKING, DOING_SOMETHING
	}
	public enum Facing {
		TOP_LEFT,		TOP,		TOP_RIGHT,
		LEFT,			CENTER,		RIGHT,
		BOTTOM_LEFT, 	BOTTOM, 	BOTTOM_RIGHT  
	}

	public static final float SPEED = 20f; // unit per second	
	//public static final float SIZE = 5f;  // 10 units = 1 meter.

	State state = State.IDLE;
	Facing facing = Facing.LEFT;

	float stateTime = 0;

	
	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}

	public Holden(Vector2 position, float size) {
		super(position, size);
//		this.position = position;
//		this.bounds.height = size;
//		this.bounds.width = size;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void update(float delta) {		
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
