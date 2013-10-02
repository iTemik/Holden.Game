package bar.kisskiss.holden.model.general;
import bar.kisskiss.holden.model.interfaces.DrawableSpriteAnimationInterface;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;



public class MovingObejectAnim extends MovingObject implements DrawableSpriteAnimationInterface {

	protected float angle = 0; 
	protected TextureRegion textureRegion;
	protected float stateTime = 0;
	protected Animation animation;
	

	@Override
	public TextureRegion getTextureRegion() {
		// TODO Auto-generated method stub
		return textureRegion;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		this.textureRegion = textureRegion;
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		// TODO Auto-generated method stub
		this.angle = angle;
	}

	@Override
	public Animation getAnimation() {
		// TODO Auto-generated method stub
		return animation;
	}

	@Override
	public void setAnimation(Animation animation) {
		// TODO Auto-generated method stub
		this.animation = animation;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		stateTime += delta;
	}

	@Override
	public void draw(SpriteBatch sb) {
		if(animation != null) {
			textureRegion = animation.getKeyFrame(getStateTime(), true);
			//super.draw(sb, screenX, screenY, screenWidth, screenHeight);
			//super.draw(sb);
		}
		Sprite sprite = new Sprite(textureRegion);
		sprite.rotate(angle);
		sprite.setBounds( rectOnScreen.x , rectOnScreen.y, rectOnScreen.width, rectOnScreen.height);			
		sprite.draw(sb);
	}
	
	@Override
	public float getStateTime() {
		// TODO Auto-generated method stub
		return stateTime;
	}

	@Override
	public void setStateTime(float stateTime) {
		// TODO Auto-generated method stub
		this.stateTime = stateTime;
	}

	
	public MovingObejectAnim(Vector2 position, float size) {
		super.setPosition(position);
		super.setBounds(new Rectangle(position.x, position.y, size, size));		
	}
	
}
