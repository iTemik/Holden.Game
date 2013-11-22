package bar.kisskiss.holden.model.general;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import bar.kisskiss.holden.model.interfaces.DrawableSpriteInterface;

public class InteractObjectTextured extends InteractObject implements DrawableSpriteInterface {

	protected float angle = 0; 
	protected TextureRegion textureRegion;
	protected Integer depth = 0;
	protected Rectangle rectOnScreen;
	
	public InteractObjectTextured(String name, Rectangle rectangle) {
		super(name, rectangle);
	}
	
	public InteractObjectTextured(String name, Vector2 position, float size) {
		super(name, position, size);
	}
	
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
		return angle;
	}

	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/*
	public InteractObjectTextured() {
		super();
	}

	public InteractObjectTextured(Rectangle bounds) {
		super(bounds);
	}

	public InteractObjectTextured(Vector2 position, float size) {
		super(position, size);
		// TODO Auto-generated constructor stub
	}
*/
	@Override
	public void draw() {
		if(spriteBatch != null && textureRegion != null) {
			Sprite sprite = new Sprite(textureRegion);
			sprite.rotate(angle);
			sprite.setBounds( rectOnScreen.x , rectOnScreen.y, rectOnScreen.width, rectOnScreen.height);		
			sprite.draw(spriteBatch);
		}
		
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
	public InteractObjectTextured create(String name, Rectangle bounds) {
		return new InteractObjectTextured(name, bounds);
	}
	
	public Rectangle getRectOnScreen() {
		return rectOnScreen;
	}

	public void setRectOnScreen(Rectangle rectOnScreen) {
		this.rectOnScreen = rectOnScreen;
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
	public Integer getDepth() {
		return depth;
	}

	@Override
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	
}
