package bar.kisskiss.holden.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import bar.kisskiss.holden.model.World;
import bar.kisskiss.holden.model.actors.AccelerationPad;
import bar.kisskiss.holden.model.actors.Block;
import bar.kisskiss.holden.model.actors.Friend;
import bar.kisskiss.holden.model.actors.Holden;
import bar.kisskiss.holden.model.general.InteractObject;


public class FriendController {

	enum Keys {
		PUSH
	}
	static Map<Keys, Boolean> keys = new HashMap<FriendController.Keys, Boolean>();
	static {
		keys.put(Keys.PUSH, false);		
	};
	
	private World world;
	private Holden holden;
	private Friend friend;
	
	private static final float GRAVITY 	= 5f; // 10 unit per second per second
	private static final float DAMP 	= 0.9f;
	//private static final float DAMP1 	= 0.998f;
	private static final float MAX_VEL = 500f;
	
	private Array<InteractObject> collidable = new Array<InteractObject>();
	
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
		//float R = resultForce.len();
		float R2 = resultForce.len2();
		resultForce.nor().scl(GRAVITY-/*friend.getVelocity().len2()*/1000/(R2+1));
		friend.getAcceleration().add(resultForce);
		
		friend.getVelocity().add(friend.getAcceleration().x, friend.getAcceleration().y);
//		if(holden.getState() == Holden.State.IDLE)
//			friend.getAcceleration().scl(DAMP1);
		checkCollisionWithBlocks(delta);

		
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
		
		if (friend.getPosition().y < 0 ) {
			friend.getAcceleration().y *= -1; 
			friend.getPosition().y = 0f;			
			
		}
		if (friend.getPosition().y > world.getLevelHeight() ) {
			friend.getAcceleration().y *= -1; 
			friend.getPosition().y = world.getLevelHeight();
			
		}
		
		if (friend.getPosition().x < 0 ) {
			friend.getAcceleration().x *= -1; 
			friend.getPosition().x = 0f;
		}
		if (friend.getPosition().x > world.getLevelWidth() ) {
			friend.getAcceleration().x *= -1; 
			friend.getPosition().x = world.getLevelWidth();			
		}

	}

	private void processInput() {
		if (keys.get(Keys.PUSH)) {
			friend.getAcceleration().scl(DAMP);			
		}
				
	}
	
	
	private void checkCollisionWithBlocks(float delta) {
		friend.getVelocity().scl(delta);

		Rectangle friendRect = new Rectangle();
		
		friendRect.set(friend.getBounds().x, friend.getBounds().y,
				friend.getBounds().width, friend.getBounds().height);
		
		
		int startX = (int) friend.getBounds().x;
		int endX = (int) (friend.getBounds().x + friend.getBounds().width);
		if (friend.getVelocity().x < 0) {
			endX = startX;
			startX = (int) Math.floor(friend.getBounds().x
					+ friend.getVelocity().x);
		} else {
			startX = endX;
			endX = (int) Math.floor(friend.getBounds().x
					+ friend.getBounds().width + friend.getVelocity().x);
		}
		
		int startY = (int) friend.getBounds().y;
		int endY = (int) (friend.getBounds().y + friend.getBounds().height);
		if (friend.getVelocity().y < 0) {
			endY = startY;
			startY = (int) Math.floor(friend.getBounds().y
					+ friend.getVelocity().y);
		} else {
			startY = endY;
			endY = (int) Math.floor(friend.getBounds().y
					+ friend.getBounds().width + friend.getVelocity().y);
		}
		
		//populateCollidableBlocks(startX, startY, endX, endY);
		friendRect.x += friend.getVelocity().x;
		friendRect.y += friend.getVelocity().y;
		
		//world.getCollisionObjects().clear();
		Array<InteractObject> collidable = world.getInteractObjectsInRect(new Rectangle(startX, startY, endX, endY));
		for (InteractObject interactObject : collidable) {
			if (interactObject == null) continue;			
			if (friendRect.overlaps(interactObject.getBounds())) {
				//world.getCollisionObjects().add(interactObject.getBounds());
				
				//friend.getVelocity().scl(DAMP);
				if(interactObject.getClass() == AccelerationPad.class) {
					friend.getAcceleration().add(((AccelerationPad)interactObject).getForce());	
				} else if (interactObject.getClass() == Block.class
						&& ((Block) interactObject).getState() == Block.State.SLOWER) {
					friend.getAcceleration().scl(DAMP);				
				}
					//break;
			}
		}

		friend.getPosition().add(friend.getVelocity());
		friend.getBounds().x = friend.getPosition().x;
		friend.getBounds().y = friend.getPosition().y;
	}
	
}
