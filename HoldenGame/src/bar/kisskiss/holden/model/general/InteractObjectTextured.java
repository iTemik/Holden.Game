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
	public void draw(SpriteBatch sb) {
		// TODO Auto-generated method stub
		if(sb != null && textureRegion != null) {
			/*AK TODO: improve? make sprite a member*/
			Sprite sprite = new Sprite(textureRegion);
			sprite.rotate(angle);
			sprite.setBounds( rectOnScreen.x , rectOnScreen.y, rectOnScreen.width, rectOnScreen.height);		
			sprite.draw(sb);
		}	
	}

	public InteractObjectTextured() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InteractObjectTextured(Rectangle bounds) {
		super(bounds);
		// TODO Auto-generated constructor stub
	}

	public InteractObjectTextured(Vector2 position, float size) {
		super(position, size);
		// TODO Auto-generated constructor stub
	}
	
	
}
