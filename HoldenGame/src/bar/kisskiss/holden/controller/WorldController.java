package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.Position;

import com.badlogic.gdx.math.Vector2;

import bar.kisskiss.holden.model.Friend;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.Holden.Facing;
import bar.kisskiss.holden.model.Holden.State;
import bar.kisskiss.holden.model.World;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, UP, DOWN, JUMP, PUSH
	}

	private World world;
	private Holden holden;
	private Friend friend;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		
		keys.put(Keys.PUSH, false);
	};

	private long	pushPressedTime;
	private boolean pushPressed;

	private static final long LONG_PUSH_PRESS 	= 150l;
	//private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_PUSH_SPEED	= 70f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 40f;
	
	// AK TODO: Temp
	private static final float WIDTH = 100f;
	private static final float HEIGHT = 70f;
	
	public WorldController(World world) {
		this.world = world;
		this.holden = world.getHolden();
		this.friend = world.getFriend();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
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

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}

	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}

	public void pushPressed() {
		keys.get(keys.put(Keys.PUSH, true));
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

	public void pushReleased() {
		keys.get(keys.put(Keys.PUSH, false));
		pushPressed = false;
	}
	
	
	
	/** The main update method **/
	public void update(float delta) {
		processInput();
		holden.update(delta);
		//friend.update(delta);
		Vector2 gravityVector = new Vector2(x, y).getPosition().add(friend.getPosition());
		gravityVector = gravityVector.nor().scl(-GRAVITY);
		friend.setAcceleration(friend.getAcceleration().add(gravityVector));  
		
		friend.setVelocity(friend.getVelocity().add(friend.getAcceleration().scl(delta)));
		//friend.getVelocity().add(friend.getAcceleration().x, friend.getAcceleration().y);
		/*
		if (friend.getAcceleration().x == 0) friend.getVelocity().x *= DAMP;
		
		if (friend.getVelocity().x > MAX_VEL) {
			friend.getVelocity().x = MAX_VEL;
		}
		if (friend.getVelocity().x < -MAX_VEL) {
			friend.getVelocity().x = -MAX_VEL;
		}
		*/
		friend.update(delta);
		/*
		if (friend.getPosition().y < 0) {
			friend.getPosition().y = 0f;
			friend.setPosition(friend.getPosition());
			if (friend.getState().equals(Friend.State.MOVING)) {
				friend.setState(Friend.State.IDLE);
			}
		}
		if (friend.getPosition().x < 0) {
			friend.getPosition().x = 0;
			friend.setPosition(friend.getPosition());
			if (!friend.getState().equals(Friend.State.MOVING)) {
				friend.setState(Friend.State.IDLE);
			}
		}
		if (friend.getPosition().x > WIDTH - friend.getBounds().width ) {
			friend.getPosition().x = WIDTH - friend.getBounds().width;
			friend.setPosition(friend.getPosition());
			if (!friend.getState().equals(Friend.State.MOVING)) {
				friend.setState(Friend.State.IDLE);
			}
		}
		*/
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
		
		if (keys.get(Keys.PUSH)) {
			if (!friend.getState().equals(Friend.State.MOVING)) {
				pushPressed = true;
				pushPressedTime = System.currentTimeMillis();
				friend.setState(Friend.State.MOVING);
				friend.getVelocity().y = (float) (MAX_PUSH_SPEED *(holden
						.getPosition().y - friend.getPosition().y)/HEIGHT);
				friend.getVelocity().x = (float) (MAX_PUSH_SPEED *(holden
						.getPosition().x - friend.getPosition().x)/WIDTH);
		} else {
				if (pushPressed && ((System.currentTimeMillis() - pushPressedTime) >= LONG_PUSH_PRESS)) {
					pushPressed = false;
				} else {
					if (pushPressed) {
						friend.getVelocity().y = MAX_PUSH_SPEED;
						friend.getVelocity().x = MAX_PUSH_SPEED;
					}
				}
			}
		}
		
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}
 
}
