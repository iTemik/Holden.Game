package bar.kisskiss.holden.controller;

import bar.kisskiss.holden.model.Camera;

import com.badlogic.gdx.math.Vector2;


public class CameraController {

	private Camera cam;
	
	private static final float DAMP 	= 0.9f;
	private static final float MAX_VEL = 500f;
	private static final float MAX_ACC = 5000f;
	
	
	public CameraController(Camera cam) {
		this.cam = cam;
	}
	
	public void update(float delta) {
		/*AK TODO: set new Velocity, aCccel and pos*/
		//cam.getAcceleration().add(new Vector2(cam.getDeltaPosition()).scl(60f));
		/*
		if(cam.getDeltaPosition().x == 0f && cam.getDeltaPosition().y == 0f ) {
			cam.getVelocity().scl(DAMP);
			cam.getAcceleration().scl(DAMP);
		}
		*/
		
		//cam.getVelocity().add(cam.getAcceleration()).scl(delta);
		cam.getVelocity().add(new Vector2(cam.getDeltaPosition().scl(3f)));
		
		
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
	}
}
