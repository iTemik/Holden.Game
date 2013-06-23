package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AccelerationPad extends InteractObjectAnimation {

	Vector2 force = new Vector2();
	
	AccelerationPad(Rectangle bounds, Vector2 force) {
		setBounds(bounds);
		this.force = force;
		angle = force.angle();
	}
	
	public Vector2 getForce() {
		return force;
	}

	public void setForce(Vector2 force) {
		this.force = force;
		angle = force.angle();
	}

}
