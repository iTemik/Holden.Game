package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Target {

	private Vector2 position;
	private Rectangle bounds;
	public boolean reached = false;

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		reached = false;
	}

	public Target(Vector2 position) {
		super();
		this.position = position;
		bounds = new Rectangle(position.x, position.y, 2, 2);
		
		reached = false;
	}

	public boolean isReached() {
		return reached;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}
	
}
