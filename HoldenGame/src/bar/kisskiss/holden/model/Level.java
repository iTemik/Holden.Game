package bar.kisskiss.holden.model;

import java.util.Random;

import bar.kisskiss.holden.view.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Level {

	private int width;
	private int height;
	private Array<InteractObject> interactObjects = new Array<InteractObject>();

	
	public Array<InteractObject> getInteractObjects() {
		return interactObjects;
	}

	public void setInteractObjects(Array<InteractObject> interactObjects) {
		this.interactObjects = interactObjects;
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	public void getBlocksInRect(Rectangle rect, Array<InteractObject> resultblocks) {
		resultblocks.clear();
		for (InteractObject interactObject : interactObjects) {			
			if(rect.overlaps(interactObject.getBounds())) {
				resultblocks.add(interactObject);
			}				
		}
	}	

	public Level(int width, int height) {
		this.width=width;
		this.height=height;
		
		loadDemoLevel();
	}	
	
	private void loadDemoLevel() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		
		Random rand = new Random();
		for (int i = 0; i < width/10; i++) {
			for(int j = 0; j<height/10; j++) {
				int randomNum = rand.nextInt(10);
				
				if(randomNum<1) {
					int randomSize = rand.nextInt(6)+5;
					InteractObject object = new InteractObject(new Rectangle(i*10, j*10, randomSize, randomSize));
					
					/* AK TODO: refactor. use some pattern to get renderer.*/
					if(randomSize%2==0) {
						object.setState(InteractObject.State.SLOWER);
						object.setTextureRegion(atlas.findRegion("tree"));
					}
					else {
						object.setState(InteractObject.State.STOPPER);
						object.setTextureRegion(atlas.findRegion("crate"));
					}
					interactObjects.add(object);
				}
				
			}						
		}
		
		{
			AccelerationPad object = new AccelerationPad(new Rectangle(15,15,7,7), new Vector2(-1, -1).scl(50));
			object.setAnimation(WorldRenderer.createAnimationFromAtlas(atlas, "accelerator-", 4, 0.12f));			
			interactObjects.add(object);
		}
		
	}
}