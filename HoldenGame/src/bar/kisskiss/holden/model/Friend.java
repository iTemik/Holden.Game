package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Vector2;

public class Friend extends MovingObject {

	float stateTime = 0;
	
	public enum State {
		IDLE, MOVING
	}
	
	State state = State.IDLE;
	

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Friend(Vector2 position, float size) {
		super(position, size);
		// TODO Auto-generated constructor stub
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
