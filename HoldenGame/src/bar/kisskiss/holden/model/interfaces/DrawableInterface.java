package bar.kisskiss.holden.model.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface DrawableInterface {
	public void draw();
	public String getName();
	public void setName(String name);
	public DrawableInterface create(String name, Rectangle rectangle);
	public Integer getDepth();
	public void setDepth(Integer depth);
	public Rectangle getBounds();
	public void update(float delta);
	public void setRectOnScreen(Rectangle rectOnScreen);
}
