package bar.kisskiss.holden.model.general;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import bar.kisskiss.holden.model.interfaces.DrawableSpriteAnimationInterface;

public class InteractObjectAnimation extends InteractObject implements DrawableSpriteAnimationInterface {

	protected float angle = 0; 
	protected TextureRegion textureRegion;
	protected float stateTime = 0;
	protected Animation animation;
	protected SpriteBatch spriteBatch = null;
	protected Integer depth = 0;
	protected Rectangle rectOnScreen;
	
	
	public InteractObjectAnimation(String name, Rectangle rectangle) {
		super(name, rectangle);
		rectOnScreen = getBounds();
	}

	public Rectangle getRectOnScreen() {
		return rectOnScreen;
	}

	public void setRectOnScreen(Rectangle rectOnScreen) {
		this.rectOnScreen = rectOnScreen;
	}

	@Override
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public void draw() {
		if(animation != null) {
			textureRegion = animation.getKeyFrame(getStateTime(), true);
		}
		if (spriteBatch != null && textureRegion != null) {
			Sprite sprite = new Sprite(textureRegion);
			sprite.setBounds(rectOnScreen.x, rectOnScreen.y,
					rectOnScreen.width, rectOnScreen.height);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.rotate(angle);
			sprite.draw(spriteBatch);
		}
	}

	@Override
	public Animation getAnimation() {
		return animation;
	}

	@Override
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	@Override
	public void update(float delta) {
		stateTime+=delta;
	}
	
	@Override
	public float getStateTime() {
		return stateTime;
	}

	@Override
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	@Override
	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public InteractObjectAnimation create(String name, Rectangle bounds) {
		return new InteractObjectAnimation(name, bounds);
	}

	@Override
	public Integer getDepth() {
		return depth;
	}

	@Override
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	

}
