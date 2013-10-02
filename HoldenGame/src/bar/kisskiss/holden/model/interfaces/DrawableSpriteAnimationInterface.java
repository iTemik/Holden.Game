package bar.kisskiss.holden.model.interfaces;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface DrawableSpriteAnimationInterface extends DrawableSpriteInterface {

	public Animation getAnimation();
	public void setAnimation(Animation animation);

	public void update(float delta);
	public void draw(SpriteBatch sb);
	public float getStateTime(); 
	public void setStateTime(float stateTime);
}
