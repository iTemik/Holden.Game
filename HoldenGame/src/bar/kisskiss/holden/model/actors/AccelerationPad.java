package bar.kisskiss.holden.model.actors;

import bar.kisskiss.holden.model.general.InteractObjectAnimation;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AccelerationPad extends InteractObjectAnimation  {

	Vector2 force = new Vector2();
	
	public AccelerationPad(String name, Rectangle bounds, Vector2 force) {
		super (name, bounds);
		setBounds(bounds);
		this.force = force;
		setAngle(force.angle());
	}
	
	public Vector2 getForce() {
		return force;
	}

	public void setForce(Vector2 force) {
		this.force = force;
		setAngle(force.angle());
	}

}
