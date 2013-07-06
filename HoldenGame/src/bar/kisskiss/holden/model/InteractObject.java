package bar.kisskiss.holden.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InteractObject {
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public enum State {
		DEFAULT, STOPPER, SLOWER
	}
	private State state = State.DEFAULT;
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	/*Add sprites to this*/
	TextureRegion textureRegion;
	float angle = 0;

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
			/*AK TODO: check*/
			sprite.rotate(angle);
			sprite.setBounds((position.x - shiftX) * ppuX, (position.y - shiftY )* ppuY, bounds.width * ppuX, bounds.height * ppuY);
			
			sprite.draw(sb);
		}			
	}
	
	public void update(float delta) {
		
	}
		
	InteractObject() {
		position = new Vector2(0,0);
		bounds = new Rectangle( position.x, position.y, 1, 1);
	}
	
	InteractObject(Rectangle bounds) {
		setBounds(bounds);
	}
}
