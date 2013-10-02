package bar.kisskiss.holden.model.general;

import com.badlogic.gdx.math.Vector2;

public class MovingObject extends InteractObject {
	
	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	
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

	public MovingObject(Vector2 position, float size) {
		this.position = position;
		this.bounds.height = size;
		this.bounds.width = size;
	}
	
	public MovingObject() {
		this.position = new Vector2(0, 0);
		this.bounds.width = 0;
		this.bounds.height = 0;
	}
}
