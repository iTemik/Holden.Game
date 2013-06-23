package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import bar.kisskiss.holden.model.AccelerationPad;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.InteractObject;
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
	
	private Array<InteractObject> collidable = new Array<InteractObject>();

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
		
		checkCollisionWithBlocks(delta);		
		
		if ((Math.abs(holden.getPosition().x - target.getPosition().x) < 3)
				&& (Math.abs(holden.getPosition().y - target.getPosition().y) < 3)) {
			target.setReached(true);
			holden.setState(State.IDLE);
		}

		if(!target.isReached()) {
			holden.setState(State.WALKING);
			holden.setVelocity(new Vector2(holden.getFacing()).nor().scl(Holden.SPEED));
		}
		//holden.getPosition().add(holden.getVelocity().scl(delta));
		
	}
	
	private void checkCollisionWithBlocks(float delta) {
		// TODO Auto-generated method stub

		holden.getVelocity().scl(delta);

		// Rectangle friendRect = rectPool.obtain(); // ?????
		Rectangle holdenRect = new Rectangle();

		holdenRect.set(holden.getBounds().x, holden.getBounds().y,
				holden.getBounds().width, holden.getBounds().height);

		int startX = (int) holden.getBounds().x;
		int endX = (int) (holden.getBounds().x + holden.getBounds().width);
		if (holden.getVelocity().x < 0) {
			endX = startX;
			startX = (int) Math.floor(holden.getBounds().x
					+ holden.getVelocity().x);
		} else {
			startX = endX;
			endX = (int) Math.floor(holden.getBounds().x
					+ holden.getBounds().width + holden.getVelocity().x);
		}

		int startY = (int) holden.getBounds().y;
		int endY = (int) (holden.getBounds().y + holden.getBounds().height);
		if (holden.getVelocity().y < 0) {
			endY = startY;
			startY = (int) Math.floor(holden.getBounds().y
					+ holden.getVelocity().y);
		} else {
			startY = endY;
			endY = (int) Math.floor(holden.getBounds().y
					+ holden.getBounds().width + holden.getVelocity().y);
		}

		// populateCollidableBlocks(startX, startY, endX, endY);
		populateCollidableBlocks(startX, startY, endX, endY);
		holdenRect.x += holden.getVelocity().x;
		holdenRect.y += holden.getVelocity().y;
		world.getCollisionRects().clear();

		for (InteractObject interactObject : collidable) {
			if (interactObject == null)
				continue;
			if (holdenRect.overlaps(interactObject.getBounds())) {
				world.getCollisionRects().add(interactObject.getBounds());

				// friend.getVelocity().scl(DAMP);
				if (interactObject.getState() == InteractObject.State.STOPPER) {
					holden.getAcceleration().x = 0;
					holden.getAcceleration().y = 0;
					holden.getVelocity().x = 0;
					holden.getVelocity().y = 0;
					target.setReached(true);
				}
				// break;
			}
		}
		if (collidable.size == 0) {
			holden.getPosition().add(holden.getVelocity());
			holden.getBounds().x = holden.getPosition().x;
			holden.getBounds().y = holden.getPosition().y;
			// friend.getVelocity().scl(1 / delta);
		}
	}

	private void populateCollidableBlocks(int startX, int startY, int endX,
			int endY) {
		// TODO Auto-generated method stub
		collidable.clear();
		world.getLevel().getBlocksInRect(
				new Rectangle(startX, startY, endX - startX, endY - startY),
				collidable);
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
