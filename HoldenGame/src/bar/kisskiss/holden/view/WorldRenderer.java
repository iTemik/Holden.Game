package bar.kisskiss.holden.view;

import bar.kisskiss.holden.model.Block;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.World;

import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(100, 70);
		this.cam.position.set(50, 35f, 0);
		this.cam.update();
	}

	public void render() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Block block : world.getBlocks()) {
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		
		// render Holden
		Holden holden = world.getHolden();
		Rectangle rect = holden.getBounds();
		float x1 = holden.getPosition().x + rect.x;
		float y1 = holden.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		//debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.circle(x1, y1, rect.width/4+ rect.height/4);
		debugRenderer.end();
	}
}
