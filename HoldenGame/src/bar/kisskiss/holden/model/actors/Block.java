package bar.kisskiss.holden.model.actors;

import bar.kisskiss.holden.model.general.InteractObjectTextured;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block extends InteractObjectTextured {
	
	public Block(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}
	public enum State {
		DEFAULT, STOPPER, SLOWER
	}
	private State state = State.DEFAULT;
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
	//Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();

	
	
	public Block(Vector2 pos, float size) {
		super(pos, size);
	}
/*
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	*/
	
	
}
