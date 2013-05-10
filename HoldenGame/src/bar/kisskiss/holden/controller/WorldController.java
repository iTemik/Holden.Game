package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.Holden.Facing;
import bar.kisskiss.holden.model.Holden.State;
import bar.kisskiss.holden.model.World;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, UP, DOWN, FIRE
	}

	private World world;
	private Holden holden;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);

		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.holden = world.getHolden();
	}

	// ** Key presses and touches **************** //

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

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
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

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	/** The main update method **/
	public void update(float delta) {
		processInput();
		holden.update(delta);
	}

	/** Change Holden's state and parameters based on input controls **/

	private void processInput() {

		if (keys.get(Keys.DOWN)) {
			// DOWN is pressed
			holden.setFacing(Facing.BOTTOM);
			holden.setState(State.WALKING);
			holden.getVelocity().y = -Holden.SPEED;
		}

		if (keys.get(Keys.UP)) {
			holden.setFacing(Facing.TOP);
			holden.setState(State.WALKING);
			holden.getVelocity().y = Holden.SPEED;
		}
		
		if (keys.get(Keys.LEFT)) {
			// left is pressed
			if (keys.get(Keys.DOWN)) {
				holden.setFacing(Facing.BOTTOM_LEFT);
			} else if (keys.get(Keys.UP)) {
				holden.setFacing(Facing.TOP_LEFT);
			} else
				holden.setFacing(Facing.LEFT);
			holden.setState(State.WALKING);
			holden.getVelocity().x = -Holden.SPEED;
		}

		if (keys.get(Keys.RIGHT)) {
			if (keys.get(Keys.DOWN)) {
				holden.setFacing(Facing.BOTTOM_RIGHT);
			} else if (keys.get(Keys.UP)) {
				holden.setFacing(Facing.TOP_RIGHT);
			} else
				holden.setFacing(Facing.RIGHT);
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
				&& !keys.get(Keys.RIGHT)) {
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
