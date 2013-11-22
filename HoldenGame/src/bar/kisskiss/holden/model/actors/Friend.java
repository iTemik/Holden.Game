package bar.kisskiss.holden.model.actors;

import java.util.Calendar;

import bar.kisskiss.holden.model.general.MovingObejectAnim;

import com.badlogic.gdx.math.Rectangle;

public class Friend extends MovingObejectAnim {

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

	public Friend(String name, Rectangle rectangle) {
		
		super(name, rectangle);
		setDepth(3);
		// TODO Auto-generated constructor stub
	}
	/*
	@Override
	public void update(float delta) {		
		stateTime += delta;
		position.add(velocity.scl(delta));
	}
	*/
}
