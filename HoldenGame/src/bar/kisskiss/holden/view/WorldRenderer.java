package bar.kisskiss.holden.view;

import bar.kisskiss.holden.model.Block;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.World;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 70f;

	private World world;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Texture holdenTexture;
	private Texture blockTexture;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		holdenTexture = new Texture(Gdx.files.internal("images/holden_01.png"));
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
	}

	public void render() {
		spriteBatch.begin();		
		drawHolden();
		drawBlocks();
		spriteBatch.end();
		if (debug)
			drawDebug();
	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX,
					block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE
							* ppuY);
		}
	}

	private void drawHolden() {
		Holden holden = world.getHolden();
		spriteBatch.draw(holdenTexture, holden.getPosition().x * ppuX,
				holden.getPosition().y * ppuY, Holden.SIZE * ppuX, Holden.SIZE
						* ppuY);
	}

	private void drawDebug() {
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
		// render holden
		Holden holden = world.getHolden();
		Rectangle rect = holden.getBounds();
		float x1 = holden.getPosition().x + rect.x;
		float y1 = holden.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		
//		if (Gdx.app.getType().equals(ApplicationType.Android)) {
//			debugRenderer.setColor(new Color(0, 0, 1, 1));
//			debugRenderer.rect(0, 0, width*ppuY, height/4*ppuY);
//			debugRenderer.rect(0, height * 3/4*ppuY, width*ppuY, height/4*ppuY);
//			debugRenderer.rect(0, 0, width/4*ppuY, height*ppuY);
//			debugRenderer.rect(width * 3/4*ppuY, 0, width/4*ppuY, height*ppuY);
//		}
		debugRenderer.end();
	}

}
