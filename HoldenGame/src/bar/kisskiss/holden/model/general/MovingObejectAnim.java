package bar.kisskiss.holden.model.general;
import bar.kisskiss.holden.model.interfaces.DrawableInterface;
import bar.kisskiss.holden.model.interfaces.DrawableSpriteAnimationInterface;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;




public class MovingObejectAnim extends MovingObject implements DrawableSpriteAnimationInterface {

	protected float angle = 0; 
	protected TextureRegion textureRegion;
	protected float stateTime = 0;
	protected Animation animation;
	protected SpriteBatch spriteBatch = null;
	protected Integer depth = 0;
	protected Rectangle rectOnScreen;

	
	public MovingObejectAnim(String name, Rectangle bounds) {
		super(name, bounds);
		rectOnScreen = getBounds();
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
	public Animation getAnimation() {
		return animation;
	}

	@Override
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
	}

	@Override
	public void draw() {
		if(animation != null) {
			textureRegion = animation.getKeyFrame(getStateTime(), true);
		}
		if (spriteBatch != null && textureRegion != null) {
			Sprite sprite = new Sprite(textureRegion);
			sprite.rotate(angle);			
			sprite.setBounds(rectOnScreen.x, rectOnScreen.y,
					rectOnScreen.width, rectOnScreen.height);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.draw(spriteBatch);
		}
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
	public MovingObejectAnim create(String name, Rectangle rectangle) {
		return new MovingObejectAnim(name, rectangle);
	}
	
	@Override
	public Integer getDepth() {
		return depth;
	}

	@Override
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public Rectangle getRectOnScreen() {
		return rectOnScreen;
	}
	public void setRectOnScreen(Rectangle rectOnScreen) {
		this.rectOnScreen = rectOnScreen;
	}
	
}
