package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.Target;
import bar.kisskiss.holden.model.World;
import bar.kisskiss.holden.model.Holden.State;

public class HoldenController {

	enum Keys {
		LEFT, RIGHT, UP, DOWN, JUMP,

	}

	static Map<Keys, Boolean> keys = new HashMap<HoldenController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
	};

	private World world;
	private Holden holden;
	private Target target;

	public HoldenController(World world) {
		this.world = world;
		this.holden = world.getHolden();
		this.target = world.getTarget();
	}

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}

	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Holden getHolden() {
		return holden;
	}

	public void setHolden(Holden holden) {
		this.holden = holden;
	}

	public void update(float delta) {
		processInput();
		holden.update(delta);
		Vector2 v = new Vector2(target.getPosition());
		holden.setFacing(v.sub(holden.getPosition()));
		if ((Math.abs(holden.getPosition().x - target.getPosition().x) < 3)
				&& (Math.abs(holden.getPosition().y - target.getPosition().y) < 3)) {
			target.setReached(true);
			holden.setState(State.IDLE);
		}

		if(!target.isReached()) {
			holden.setState(State.WALKING);
			holden.setVelocity(new Vector2(holden.getFacing()).nor().scl(Holden.SPEED));
		}
	}
	
	private void processInput() {

		if (keys.get(Keys.DOWN)) {
			// DOWN is pressed
			holden.setState(State.WALKING);
			holden.getVelocity().y = -Holden.SPEED;
		}

		if (keys.get(Keys.UP)) {
			holden.setState(State.WALKING);
			holden.getVelocity().y = Holden.SPEED;
		}
		
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			holden.setState(State.WALKING);
			holden.getVelocity().x = -Holden.SPEED;
		}

		if (keys.get(Keys.RIGHT)) {
			
			holden.setState(State.WALKING);
			holden.getVelocity().x = Holden.SPEED;
		}

		// need to check if both or none direction are pressed, then Bob is idle
		if (keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) {
			holden.setState(State.IDLE);
			// acceleration is 0 on the x
			holden.getAcceleration().x = 0;
			// horizontal speed is 0
			holden.getVelocity().x = 0;
		}

		if (keys.get(Keys.UP) && keys.get(Keys.DOWN)) {
			holden.setState(State.IDLE);
			// acceleration is 0 on the y
			holden.getAcceleration().y = 0;
			// vertical speed is 0
			holden.getVelocity().y = 0;
		}
		
		if (!keys.get(Keys.UP) && !keys.get(Keys.DOWN) && !keys.get(Keys.LEFT)
				&& !keys.get(Keys.RIGHT) && target.isReached()) {
			holden.setState(State.IDLE);
			// acceleration is 0 on the y
			holden.getAcceleration().y = 0;
			// vertical speed is 0
			holden.getVelocity().y = 0;
			// acceleration is 0 on the x
			holden.getAcceleration().x = 0;
			// horizontal speed is 0
			holden.getVelocity().x = 0;
		}
	}
	
}
