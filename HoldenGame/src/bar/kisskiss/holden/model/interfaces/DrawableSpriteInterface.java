package bar.kisskiss.holden.model.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface DrawableSpriteInterface {
	
	public TextureRegion getTextureRegion();
	public void setTextureRegion(TextureRegion textureRegion);
	public float getAngle();
	public void setAngle(float angle);
	public void draw(SpriteBatch sb);
}
