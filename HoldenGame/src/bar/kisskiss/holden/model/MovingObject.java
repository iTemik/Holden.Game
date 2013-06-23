package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovingObject {
	
	float weight;
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		this.bounds.setX(x);
		this.bounds.setY(y);
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
		setPosition(bounds.x, bounds.y);
	}

	public MovingObject(Vector2 position, float size) {
		this.position = position;
		this.bounds.height = size;
		this.bounds.width = size;
		setWeight(1);
	}
	
}
