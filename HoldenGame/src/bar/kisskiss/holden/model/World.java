package bar.kisskiss.holden.model;

import bar.kisskiss.holden.view.WorldRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	/** The blocks making up the world **/
	Array<InteractObject> interactObject = new Array<InteractObject>();
	/** Our player controlled hero **/
	Holden holden;
	Friend friend;
	Target target;

	Rectangle drawableArea = new Rectangle();
	
	 public Rectangle getDrawableArea() {
		return drawableArea;
	}

	public void setDrawableArea(Rectangle drawableArea) {
		this.drawableArea = drawableArea;		
		level.getBlocksInRect( drawableArea, drawableObjects );
	}
	
	Array<InteractObject> drawableObjects = new Array<InteractObject>();

	public void setDrawableObjects(Array<InteractObject> drawableObjects) {
		this.drawableObjects = drawableObjects;
	}

	/** A world has a level through which Bob needs to go through **/
	Level level;
	
	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}


	public Array<InteractObject> getInteractObject() {
		return interactObject;
	}


	public Level getLevel() {
		return level;
	}

	Array<Rectangle> collisionRects = new Array<Rectangle>();

	 /** Return only the blocks that need to be drawn **/
	public Array<InteractObject> getDrawableObjects() {		
		return drawableObjects;
	}
	
	
	public Target getTarget() {
		return target;
	}

	public Friend getFriend() {
		return friend;
	}

	public Holden getHolden() {
		return holden;
	}

	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		holden = new Holden(new Vector2(55, 25), 5);
		friend = new Friend(new Vector2(20, 40), 10);
		target = new Target(new Vector2(50, 35));		
		level = new Level(1000, 500);	

	}

}