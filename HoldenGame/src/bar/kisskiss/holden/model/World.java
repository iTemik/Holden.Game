package bar.kisskiss.holden.model;

import java.util.HashSet;
import java.util.Random;

import bar.kisskiss.holden.model.actors.AccelerationPad;
import bar.kisskiss.holden.model.actors.Block;
import bar.kisskiss.holden.model.actors.Friend;
import bar.kisskiss.holden.model.actors.Holden;
import bar.kisskiss.holden.model.general.InteractObject;
import bar.kisskiss.holden.model.interfaces.DrawableInterface;
import bar.kisskiss.holden.view.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	public HashSet<DrawableInterface> getWorldObjects() {
		return worldObjects;
	}


	public void setWorldObjects(HashSet<DrawableInterface> worldObjects) {
		this.worldObjects = worldObjects;
	}

	/** Our player controlled hero **/
	private Holden holden;
	private Friend friend;
	private Target target;
	private Camera camera;
	
	public int getLevelWidth() {
		return levelWidth;
	}


	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}


	public int getLevelHeight() {
		return levelHeight;
	}


	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	private int levelWidth;
	private int levelHeight;
	
	HashSet<InteractObject> levelInteractObjects = new HashSet<InteractObject>();
	// AK TODO: HUD objects map
	HashSet<DrawableInterface> hudObjects = new HashSet<DrawableInterface>();
	
	// AK TODO: map all objects on lvl by name and interact objects
	HashSet<DrawableInterface> worldObjects = new HashSet<DrawableInterface>();
	// AK TODO: map drawable objects on screen 
	HashSet<DrawableInterface> screenObjects = new HashSet<DrawableInterface>();
	
	public HashSet<DrawableInterface> getScreenObjects() {
		return screenObjects;
	}


	public void setScreenObjects(HashSet<DrawableInterface> screenObjects) {
		this.screenObjects = screenObjects;
	}


	public Camera getCamera() {
		return camera;
	}


	public void fillScreenObjects(Rectangle rectangle) {
		screenObjects.clear();
		for (DrawableInterface drawable : worldObjects) {
			if(rectangle.overlaps(drawable.getBounds())) {
				screenObjects.add(drawable);
			}
		}
	}



	 /** Return only the blocks that need to be drawn **/
	/*
	public Array<InteractObject> getDrawableObjects() {		
		return drawableObjects;
	}
	*/
	
	
	public Target getTarget() {
		return target;
	}

	public Friend getFriend() {
		return friend;
	}

	public Holden getHolden() {
		return holden;
	}
	public Array<InteractObject> getInteractObjectsInRect(Rectangle rectangle) {
		Array<InteractObject> collidable = new Array<InteractObject>();
		for (InteractObject object : levelInteractObjects) {
			if (rectangle.overlaps(object.getBounds())) {
				collidable.add(object);
			}
		}
		return collidable;
	}

	public HashSet<InteractObject> getLevelInteractObjects() {
		return levelInteractObjects;
	}

	public void setLevelInteractObjects(HashSet<InteractObject> levelInteractObjects) {
		this.levelInteractObjects = levelInteractObjects;
	}

	public HashSet<DrawableInterface> getHudObjects() {
		return hudObjects;
	}

	public void setHudObjects(HashSet<DrawableInterface> hudObjects) {
		this.hudObjects = hudObjects;
	}
	
	private void loadDemoLevel() {
	
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		
		Random rand = new Random();
		for (int i = 0; i < levelWidth/10; i++) {
			for(int j = 0; j<levelHeight/10; j++) {
				int randomNum = rand.nextInt(8);
				
				String name = String.format("object-%02d-%02d", i,j);
				
				if(randomNum<1) {
					int randomSize = rand.nextInt(6)+5;
					Block object = new Block(name, new Rectangle(i*10, j*10, randomSize, randomSize));
					
					if(randomSize%2==0) {
						object.setState(Block.State.SLOWER);
						object.setTextureRegion(atlas.findRegion("tree"));
						object.setDepth(2);
					}
					else {
						object.setState(Block.State.STOPPER);
						object.setTextureRegion(atlas.findRegion("crate"));
						object.setDepth(1);
					}
					levelInteractObjects.add(object);
					worldObjects.add(object);
				}
				
				if( i%10==0 && j%10==0) {
					Vector2 force = new Vector2(1,1);					
					force.setAngle(360/8*(randomNum+1));
					//force.setAngle(45*i/10);
					force.nor().scl(200);
					AccelerationPad object = new AccelerationPad(name, new Rectangle(15+i*10,15+j*10,8,8), force);
					object.setAnimation(WorldRenderer.createAnimationFromAtlas(atlas, "accelerator-", 4, 0.12f));			
					levelInteractObjects.add(object);
					worldObjects.add(object);
				}
			}
		}	
	}
	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		holden = new Holden("Holden", new Rectangle(55, 25, 5, 5));
		worldObjects.add(holden);
		friend = new Friend("Friend", new Rectangle(20, 40, 10, 10));
		worldObjects.add(friend);
		target = new Target(new Vector2(50, 35));		
		levelWidth = 1000;
		levelHeight = 500;
		loadDemoLevel();
		
		camera = new Camera(new OrthographicCamera(400f, 300f));
		
	}

}