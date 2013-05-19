package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import bar.kisskiss.holden.model.Friend;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.World;


public class FriendController {

	enum Keys {
		PUSH
	}
	static Map<Keys, Boolean> keys = new HashMap<FriendController.Keys, Boolean>();
	static {
		keys.put(Keys.PUSH, false);		
	};
	
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

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	private World world;
	private Holden holden;
	private Friend friend;
	
	private static final float GRAVITY 	= 5f; // 10 unit per second per second
	private static final float DAMP 	= 0.9f;
	private static final float DAMP1 	= 0.97f;
	private static final float MAX_VEL = 500f;
	
	
	
	public FriendController(World world) {
		this.world = world;
		this.holden = world.getHolden();
		this.friend = world.getFriend();
	}
	
	public void pushPressed() {
		keys.get(keys.put(Keys.PUSH, true));
	}
	
	public void pushReleased() {
		keys.get(keys.put(Keys.PUSH, false));
	}
	
	public void update(float delta) {
		processInput();
		
		Vector2 resultForce = new Vector2(holden.getPosition());
		resultForce.sub(friend.getPosition());
		float R = resultForce.len();		
		resultForce.nor().scl(GRAVITY-friend.getVelocity().len2()/(R+1));
		friend.getAcceleration().add(resultForce);
		
		friend.getVelocity().add(friend.getAcceleration().x, friend.getAcceleration().y);
		if(holden.getState() == Holden.State.IDLE)
			friend.getAcceleration().scl(DAMP1);
		
		if (friend.getVelocity().x > MAX_VEL) {
			friend.getVelocity().x = MAX_VEL;
		}
		if (friend.getVelocity().x < -MAX_VEL) {
			friend.getVelocity().x = -MAX_VEL;
		}
		
		if (friend.getVelocity().y > MAX_VEL) {
			friend.getVelocity().y = MAX_VEL;
		}
		if (friend.getVelocity().y < -MAX_VEL) {
			friend.getVelocity().y = -MAX_VEL;
		}
		
		friend.update(delta);
		
//		if (friend.getPosition().y < 0) {
//			friend.getPosition().y = 0f;
//			friend.setPosition(friend.getPosition());
//			if (friend.getState().equals(Friend.State.MOVING)) {
//				friend.setState(Friend.State.IDLE);
//			}
//		}
//		if (friend.getPosition().x < 0) {
//			friend.getPosition().x = 0;
//			friend.setPosition(friend.getPosition());
//			if (!friend.getState().equals(Friend.State.MOVING)) {
//				friend.setState(Friend.State.IDLE);
//			}
//		}
//		if (friend.getPosition().x > WIDTH - friend.getBounds().width ) {
//			friend.getPosition().x = WIDTH - friend.getBounds().width;
//			friend.setPosition(friend.getPosition());
//			if (!friend.getState().equals(Friend.State.MOVING)) {
//				friend.setState(Friend.State.IDLE);
//			}
//		}
//		
//		if (friend.getPosition().y > HEIGHT - friend.getBounds().height ) {
//			friend.getPosition().y = HEIGHT - friend.getBounds().height;
//			friend.setPosition(friend.getPosition());
//			if (!friend.getState().equals(Friend.State.MOVING)) {
//				friend.setState(Friend.State.IDLE);
//			}
//		}
	}

	private void processInput() {
		if (keys.get(Keys.PUSH)) {
			friend.getAcceleration().scl(DAMP);			
		}
				
	}
	
}
