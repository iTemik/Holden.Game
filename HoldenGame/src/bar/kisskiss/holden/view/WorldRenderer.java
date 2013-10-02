package bar.kisskiss.holden.view;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;


import bar.kisskiss.holden.model.actors.AccelerationPad;
import bar.kisskiss.holden.model.actors.Friend;
import bar.kisskiss.holden.model.actors.Holden;
import bar.kisskiss.holden.model.general.InteractObject;

import bar.kisskiss.holden.model.Target;
import bar.kisskiss.holden.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {

	//private static final float CAMERA_WIDTH = 100f;
	//private static final float CAMERA_HEIGHT = 70f;

	private World world;
	//private OrthographicCamera cam;

	private static final float WALKING_FRAME_DURATION = 0.053f;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private TreeMap<Integer, Array<InteractObject>> drawByLevelMap;	
	private TextureAtlas atlas;
	
	private BitmapFont font;
	    
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void setAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
	}
	
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public WorldRenderer(World world, boolean debug) {
		drawByLevelMap = new TreeMap<Integer, Array<InteractObject>>();
		this.world = world;		

		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
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

	private void loadTextures() {
		
		atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		world.getHolden().setHoldenIdle(atlas.findRegion("holden-00"));
	
		TextureRegion[] walkFrames = new TextureRegion[16];
		for (int i = 0; i < walkFrames.length; i++) {			
			walkFrames[i] = atlas.findRegion("holden-" +(i/10)+""+(i%10));
		}
		world.getHolden().setHoldenAnimation(new Animation(WALKING_FRAME_DURATION, walkFrames));
		
		TextureRegion[] friendFrames = new TextureRegion[4];		
		for (int i = 0; i < friendFrames.length; i++) {	
			friendFrames[i] = atlas.findRegion("friend-" +(i/10)+""+(i%10));
		}
		world.getFriend().setAnimation(new Animation(0.1f, friendFrames));
		font = new BitmapFont();
		
	}
	
	static public Animation createAnimationFromAtlas(TextureAtlas textureAtlas,
			String prefix, int framesNumXX, float frameDuration) {
		TextureRegion[] animationFrames = new TextureRegion[framesNumXX];
		for (int i = 0; i < animationFrames.length; i++) {
			animationFrames[i] = textureAtlas.findRegion(prefix + (i / 10) + ""
					+ (i % 10));
		}
		return new Animation(frameDuration, animationFrames);
	}

	public void render() {
		if(world == null) return;
		spriteBatch.begin();
		updateCam();
		updateTreeMap(world.getDrawableObjects());
		drawSprites(spriteBatch, drawByLevelMap);

		if (debug) {
		
			font.draw(spriteBatch, String.format("target  x: %.2f   y: %.2f",
					world.getTarget().getPosition().x, world.getTarget()
							.getPosition().y), 10, 20);
			font.draw(spriteBatch, String.format("holden  x: %.2f   y: %.2f",
					world.getHolden().getPosition().x, world.getHolden()
							.getPosition().y), 10, 32);
			font.draw(spriteBatch, String.format("drawable  r.x: %.2f   r.w: %.2f  cam pos x:  %.2f",
					world.getDrawableArea().getX(), world.getDrawableArea().getWidth(), world.getCamera().getPosition().x), 10, 44);
/*			
			font.draw(spriteBatch, String.format("cam velocity  x: %.2f   y: %.2f    free: %.0f",
					world.getCamera().getVelocity().x, world.getCamera().getVelocity().y, world.getCamera().isFree() ? 1f : 0f), 10, 20);
			font.draw(spriteBatch, String.format("cam accel  x: %.2f   y: %.2f",
					world.getCamera().getAcceleration().x, world.getCamera().getAcceleration().y), 10, 32);
	*/	
			/*
			font.draw(spriteBatch, String.format("Holden world x: %.2f   y: %.2f",
					world.getHolden().getPosition().x, world.getHolden().getPosition().y), 10, 44);
		
			font.draw(spriteBatch, String.format(
					"Holden screen x: %.2f   y: %.2f   w: %.2f   h: %.2f",
					world.getHolden().getRectOnScreen().x, world.getHolden()
							.getRectOnScreen().y, world.getHolden()
							.getRectOnScreen().width, world.getHolden()
							.getRectOnScreen().height), 10, 56);
*/
		}
		spriteBatch.end();
		if (debug) {
			drawDebug();			
		}
	}

	private void drawSprites(SpriteBatch sb, TreeMap<Integer, Array<InteractObject>> map) {
		// TODO Auto-generated method stub
		/*
		for (InteractObject io : drawMap) {
			//io.draw(sb, x, y, width, height)
			io.draw(sb);
		}
		*/
		Iterator<Entry<Integer, Array<InteractObject>>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();			
			Array<InteractObject> valueList = (Array<InteractObject>) thisEntry.getValue();
			if (valueList != null) {
				for (InteractObject io : valueList) {
					if(io == null)
						continue;
					Rectangle rectOnScreen = new Rectangle();
					rectOnScreen.x = (io.getPosition().x
							- world.getCamera().getPosition().x + world.getCamera()
							.getWidth() / 2) * world.getCamera().getPpuX();
					rectOnScreen.y = (io.getPosition().y
							- world.getCamera().getPosition().y + world.getCamera()
							.getHeight() / 2) * world.getCamera().getPpuY();
					rectOnScreen.width = io.getBounds().width
							* world.getCamera().getPpuX();
					rectOnScreen.height = io.getBounds().height
							* world.getCamera().getPpuY();

					io.setRectOnScreen(rectOnScreen);
					io.draw(sb);
				}
			}
		}
	}

	private void updateTreeMap(Array<InteractObject> objects) {
		// TODO Auto-generated method stub
		drawByLevelMap.clear();
		/*AK TODO: improve*/
		for (InteractObject io : objects) {			
			
			Array<InteractObject> ioArray = (Array<InteractObject>) drawByLevelMap.get(io.getDepth());
			if(ioArray == null)
				ioArray = new Array<InteractObject>();
			ioArray.add(io);
			drawByLevelMap.put(io.getDepth(), ioArray);
			
		}
		{
			Array<InteractObject> ioArray = (Array<InteractObject>) drawByLevelMap.get(world.getHolden().getDepth());
			if(ioArray == null)
				ioArray = new Array<InteractObject>();
			ioArray.add( world.getHolden());
			drawByLevelMap.put(world.getHolden().getDepth(),ioArray);
		}
		{
			Array<InteractObject> ioArray = (Array<InteractObject>) drawByLevelMap.get(world.getFriend().getDepth());
			if(ioArray == null)
				ioArray = new Array<InteractObject>();
			ioArray.add( world.getFriend());
			drawByLevelMap.put(world.getFriend().getDepth(),ioArray);
		}
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean getDebug() {
		return debug;
	}

	private void drawDebug() {
		// render collidable
		debugRenderer.setProjectionMatrix(world.getCamera().getCam().combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(0, 0, 1, 0.2f));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x-world.getCamera().getPosition().x, rect.y-world.getCamera().getPosition().y, rect.width, rect.height);
		}
		debugRenderer.end();
		// render blocks
		debugRenderer.begin(ShapeType.Line);

		for (InteractObject interactObject : world.getDrawableObjects()) {
			Rectangle rect = interactObject.getBounds();
			if (interactObject.getClass() == AccelerationPad.class) {
				debugRenderer.setColor(new Color(1, 1, 0, 1));
			} else {
				debugRenderer.setColor(new Color(1, 0, 0, 1));
			}
			debugRenderer.rect(interactObject.getPosition().x-world.getCamera().getPosition().x,
					interactObject.getPosition().y-world.getCamera().getPosition().y, rect.width, rect.height);
		}

		// render holden
		{
			Holden holden = world.getHolden();
			Rectangle rect = holden.getBounds();
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			float x = rect.x-world.getCamera().getPosition().x;
			float y = rect.y-world.getCamera().getPosition().y;
			float w = rect.width;
			float h = rect.height;
			debugRenderer.rect(x, y, w, h);
			//debugRenderer.rect(holden.getPosition().x, holden.getPosition().y, rect.width, rect.height);
		}
		// render friend
		{
			Friend friend = world.getFriend();
			Rectangle rect = friend.getBounds();
			debugRenderer.setColor(new Color(0, 0, 1, 1));
			float x = rect.x-world.getCamera().getPosition().x;
			float y = rect.y-world.getCamera().getPosition().y;
			float w = rect.width;
			float h = rect.height;
			debugRenderer.rect(x, y, w, h);
			//debugRenderer.rect(friend.getPosition().x, friend.getPosition().y, rect.width, rect.height);
		}
		{
			/*Target*/
			Target target = world.getTarget();
			Rectangle rect = target.getBounds();
			debugRenderer.setColor(new Color(1, 1, 0, 1));
			float x = rect.x-world.getCamera().getPosition().x;
			float y = rect.y-world.getCamera().getPosition().y;
			float w = rect.width;
			float h = rect.height;
			debugRenderer.rect(x, y, w, h);
			//debugRenderer.rect(target.getPosition().x, target.getPosition().y, rect.width, rect.height);
			
		}

		{
			/*Draw drawable rect*/
			Rectangle rect = world.getDrawableArea();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			float x = rect.x-world.getCamera().getPosition().x;
			float y = rect.y-world.getCamera().getPosition().y;
			float w = rect.width;
			float h = rect.height;
			debugRenderer.rect(x, y, w, h);
		}

		debugRenderer.end();
	}

	public void updateCam() {
		// TODO Auto-generated method stub
		//cam.position.x = camera.getPosition().x;
		//cam.position.y = camera.getPosition().x;
		float x = world.getCamera().getPosition().x - world.getCamera().getWidth() / 2;
		float y = world.getCamera().getPosition().y - world.getCamera().getHeight() / 2;
		//world.setDrawableArea(new Rectangle(world.getCamera().getCam().position.x - world.getCamera().getWidth() / 2,
		//		world.getCamera().getCam().position.y - world.getCamera().getHeight() / 2, world.getCamera().getWidth()*world.getCamera().getPpuX(), world.getCamera().getHeight()*world.getCamera().getPpuY()));
		world.setDrawableArea(new Rectangle(x, y, world.getCamera().getWidth(),
				world.getCamera().getHeight()));
		world.getCamera().update();
		//cam.update();
	}

}
