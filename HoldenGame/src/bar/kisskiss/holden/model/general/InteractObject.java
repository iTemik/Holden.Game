package bar.kisskiss.holden.model.general;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InteractObject {
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	protected Integer depth = 0;
	protected Rectangle rectOnScreen;
	
	public Rectangle getRectOnScreen() {
		return rectOnScreen;
	}
	public void setRectOnScreen(Rectangle rectOnScreen) {
		this.rectOnScreen = rectOnScreen;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	/*

*/
	/*Add sprites to this*/
	//protected TextureRegion textureRegion;
	//protected float angle = 0;

	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
		bounds.setX(position.x);
		bounds.setY(position.y);
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		position.x = bounds.x;
		position.y = bounds.y;
	}
	

	/*
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

	public void draw(SpriteBatch sb, float shiftX, float shiftY, float ppuX, float ppuY) {
		if(sb != null && textureRegion != null) {
			Sprite sprite = new Sprite(textureRegion);
			sprite.rotate(angle);
			sprite.setBounds((position.x - shiftX) * ppuX, (position.y - shiftY )* ppuY, bounds.width * ppuX, bounds.height * ppuY);
			
			sprite.draw(sb);
		}			
	}
	*/
	
	public void draw(SpriteBatch sb) {}
	
	public void update(float delta) {		
	}
		
	public InteractObject() {
		position = new Vector2(0,0);
		bounds = new Rectangle( position.x, position.y, 1, 1);
		rectOnScreen = new Rectangle(bounds);
	}
	
	public InteractObject(Rectangle bounds) {
		setBounds(bounds);
		rectOnScreen = new Rectangle(bounds);
	}
	
	public InteractObject(Vector2 position, float size) {
		//this.position = position;
		setBounds (new Rectangle(position.x, position.y, size, size));
		rectOnScreen = new Rectangle(bounds);
	}
}
