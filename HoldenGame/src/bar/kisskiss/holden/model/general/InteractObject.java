package bar.kisskiss.holden.model.general;

import bar.kisskiss.holden.model.interfaces.DrawableInterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InteractObject {
	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	protected Integer depth = 0;
	protected SpriteBatch spriteBatch;
	protected String name;
	
	public InteractObject(String name, Rectangle rectangle) {
		this.name = name;
		setBounds(rectangle);
	}
	
	public InteractObject(String name, Vector2 position, float size) {
		this.name = name;
		setPosition(position);
		bounds.width = size;
		bounds.height = size;
	}


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
	public void draw(SpriteBatch sb) {}
	*/
	public void update(float delta) {		
	}
	
	public InteractObject() {
		position = new Vector2(0,0);
		bounds = new Rectangle( position.x, position.y, 1, 1);
	}
	
	public InteractObject(Rectangle bounds) {
		setBounds(bounds);
	}
	
	public InteractObject(Vector2 position, float size) {
		//this.position = position;
		setBounds (new Rectangle(position.x, position.y, size, size));
	}
	
	
}
