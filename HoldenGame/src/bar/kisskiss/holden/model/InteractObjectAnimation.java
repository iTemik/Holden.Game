package bar.kisskiss.holden.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class InteractObjectAnimation extends InteractObject {

	float stateTime = 0;
	private Animation animation;
	
	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void update(float delta) {		
		stateTime += delta;
	}
	
	public void draw(SpriteBatch sb, float shiftX, float shiftY, float ppuX, float ppuY) {
		if(animation != null) {
			textureRegion = animation.getKeyFrame(getStateTime(), true);
			super.draw(sb, shiftX, shiftY, ppuX, ppuY);
		}
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
