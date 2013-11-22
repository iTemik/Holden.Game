package bar.kisskiss.holden.model.actors;

import bar.kisskiss.holden.model.general.InteractObjectTextured;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block extends InteractObjectTextured {
	/*
	public Block(Rectangle rect) {
		super(rect);
	}
	*/
	public Block(String name, Rectangle rectangle) {
		super(name, rectangle);
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
	/*
	@Override
	public Block create(String name, Rectangle rectangle) {
		return new Block(name, rectangle);
	}*/
	
	public Block(String name, Vector2 position, float size) {
		super(name, position, size);
	}
	
	
	
}
