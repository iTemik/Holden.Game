package bar.kisskiss.holden.model.actors;

import bar.kisskiss.holden.model.general.MovingObejectAnim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Holden extends MovingObejectAnim {

	public enum State {
		IDLE, WALKING, DOING_SOMETHING
	}
	public static final float SPEED = 20f; // unit per second	
	//public static final float SIZE = 5f;  // 10 units = 1 meter.

	State state = State.IDLE;
	//float stateTime = 0;
	Vector2 facing;
	Animation holdenAnimation;
	TextureRegion holdenIdle;
	
	public void setHoldenAnimation(Animation holdenAnimation) {
		this.holdenAnimation = holdenAnimation;
	}

	public void setHoldenIdle(TextureRegion holdenIdle) {
		this.holdenIdle = holdenIdle;
	}

	public Holden(String name, Rectangle rectangle) {
		super(name, rectangle);
		depth = 1;
//		this.position = position;
//		this.bounds.height = size;
//		this.bounds.width = size;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Vector2 getFacing() {
		return facing;
	}

	public void setFacing(Vector2 facing) {
		this.facing = facing;
	}

	@Override
	public void draw() {
		
		if(state == State.IDLE) {
			textureRegion = holdenIdle;			
		}			 
		if(state == State.WALKING && holdenAnimation != null) {
			textureRegion = holdenAnimation.getKeyFrame(getStateTime(), true);
			//super.draw(sb, screenX, screenY, screenWidth, screenHeight);
		}
		if(spriteBatch != null && textureRegion != null) {
			Sprite sprite = new Sprite(textureRegion);
			sprite.rotate(facing.angle() - 90);
			sprite.setBounds( rectOnScreen.x , rectOnScreen.y, rectOnScreen.width, rectOnScreen.height);			
			sprite.draw(spriteBatch);	
		}
		
	}
}
