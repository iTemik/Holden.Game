package bar.kisskiss.holden.controller;

import bar.kisskiss.holden.model.Camera;
import bar.kisskiss.holden.model.World;

import com.badlogic.gdx.math.Vector2;


public class CameraController {

	private Camera cam;
	
	//private static final float DAMP 	= 0.9f;
	private static final float MAX_VEL = 500f;
	private static final float MAX_ACC = 5000f;
	
	World world = null;
	
	public CameraController(Camera cam) {
		this.cam = cam;
	}
	
	public void update(float delta) {

		cam.getVelocity().add(new Vector2(cam.getDeltaPosition().scl(1.5f)));
		
		if(cam.getVelocity().x >= MAX_VEL)
			cam.getVelocity().x = MAX_VEL;
		if(cam.getVelocity().x <= -MAX_VEL)
			cam.getVelocity().x=-MAX_VEL;
		
		if(cam.getVelocity().y >= MAX_VEL)
			cam.getVelocity().y = MAX_VEL;
		if(cam.getVelocity().y <= -MAX_VEL)
			cam.getVelocity().y=-MAX_VEL;
		
		if(cam.getAcceleration().x >= MAX_ACC)
			cam.getAcceleration().x = MAX_ACC;
		if(cam.getAcceleration().x <= -MAX_ACC)
			cam.getAcceleration().x=-MAX_ACC;
		
		if(cam.getAcceleration().y >= MAX_ACC)
			cam.getAcceleration().y = MAX_ACC;
		if(cam.getAcceleration().y <= -MAX_ACC)
			cam.getAcceleration().y=-MAX_ACC;
		
		cam.getPosition().add(new Vector2(cam.getVelocity()).scl(delta));
		cam.update();
		
		if(!cam.isFree()) {
			cam.getVelocity().scl(0.9f);
			//cam.getAcceleration().scl(0.9f);
			cam.setShift(0, 0);			
		}
		else {
			/*AK TODO: 
			 * Add boost to velocity!!!! and acceleration*/
			cam.getVelocity().scl(0.95f);
			//cam.getAcceleration().scl(0.95f);
		}
		
		if ((Math.abs(cam.getVelocity().x) <= 0.02f)
				&& (Math.abs(cam.getVelocity().y) <= 0.02f) 
				&& cam.isFree()) {
			//cam.getVelocity().x = 0f;
			//cam.getVelocity().y = 0f;
			
			/*AK TODO: BUG?*/
			if(world != null && world.getTarget().isReached()) {
				world.getTarget().setPosition(cam.getPosition());
			}
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
