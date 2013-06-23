package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();

	public Block(Vector2 pos, float size) {
		this.position = pos;
		this.bounds.width = size;
		this.bounds.height = size;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
	}

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
	
}
