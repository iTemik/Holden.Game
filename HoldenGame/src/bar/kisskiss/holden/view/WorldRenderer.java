package bar.kisskiss.holden.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;


import bar.kisskiss.holden.model.actors.AccelerationPad;
import bar.kisskiss.holden.model.actors.Friend;
import bar.kisskiss.holden.model.actors.Holden;
import bar.kisskiss.holden.model.general.InteractObject;
import bar.kisskiss.holden.model.interfaces.DrawableInterface;
import bar.kisskiss.holden.model.interfaces.DrawableSpriteInterface;

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

	// 5 km /h = 1.4 m/s = 6 unit/s
	// walking cadence = 100 (100 steps per minute) = 100/60 steps per sec 1.66667 steps
	// 1 step = 8 frames. => 100/60*8= 13.4 frames per sec = 0.075f
	private static final float WALKING_FRAME_DURATION = 0.075f;
	//private static final float WALKING_FRAME_DURATION = 0.053f;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private TreeMap<Integer, Array<DrawableInterface>> drawByLevelMap;	
	private TextureAtlas atlas;
	
	private BitmapFont font;
	    
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	
	private static final int MAXSAMPLES=100;
	int tickindex=0;
	long ticksum=0;
	long ticklist[] = new long[MAXSAMPLES];
	long lastTime;
	long deltaTime = 0;

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
		drawByLevelMap = new TreeMap<Integer, Array<DrawableInterface>>();
		this.world = world;		

		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
		lastTime = System.currentTimeMillis();
		
		updateSpriteBatch();
	}

	private void updateSpriteBatch() {
		// TODO Auto-generated method stub
		HashSet<DrawableInterface> objects = world.getWorldObjects();
		for (DrawableInterface obj : objects) {
			DrawableSpriteInterface drawableSprite = (DrawableSpriteInterface) obj;
			if ( drawableSprite != null) {
				drawableSprite.setSpriteBatch(spriteBatch);
			}
				
		}
		
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
		world.getFriend().setAnimation(new Animation(0.25f, friendFrames));
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
		long end_time = System.currentTimeMillis();
		deltaTime = end_time-lastTime;
		lastTime = end_time;
		
		// get screen objects
		updateCam();
		updateTreeMap(world.getWorldObjects());

		spriteBatch.begin();
		// draw screen sprite objects
		drawSprites();

		// draw HUD
		//drawHUD(spriteBatch, drawByLevelMap);
		
	
		if(lastTime != 0)
			font.draw(spriteBatch, String.format("%.2f   %d", 1000/CalcAverageTick(deltaTime), deltaTime), 10, 56);
		if (debug) {
		
			font.draw(spriteBatch, String.format("target  x: %.2f   y: %.2f",
					world.getTarget().getPosition().x, world.getTarget()
							.getPosition().y), 10, 20);
			font.draw(spriteBatch, String.format("holden  x: %.2f   y: %.2f",
					world.getHolden().getPosition().x, world.getHolden()
							.getPosition().y), 10, 32);	
		}
		spriteBatch.end();
		if (debug) {
			drawDebug();			
		}
		
	}
	
	private void drawSprites() {
		HashSet<DrawableInterface> drawables = world.getScreenObjects();
		for (DrawableInterface dr : drawables) {
			Rectangle rectOnScreen = new Rectangle();
			rectOnScreen.x = (dr.getBounds().x
					- world.getCamera().getPosition().x + world.getCamera()
					.getWidth() / 2) * world.getCamera().getPpuX();
			rectOnScreen.y = (dr.getBounds().y
					- world.getCamera().getPosition().y + world.getCamera()
					.getHeight() / 2) * world.getCamera().getPpuY();
			rectOnScreen.width = dr.getBounds().width
					* world.getCamera().getPpuX();
			rectOnScreen.height = dr.getBounds().height
					* world.getCamera().getPpuY();
			
			dr.setRectOnScreen(rectOnScreen);
			dr.draw();
		}
	}

	private void updateTreeMap(HashSet<DrawableInterface> screenObjects) {
		drawByLevelMap.clear();
		for (DrawableInterface drawable : screenObjects) {

			Array<DrawableInterface> drawables = (Array<DrawableInterface>) drawByLevelMap
					.get(drawable.getDepth());
			if (drawables == null)
				drawables = new Array<DrawableInterface>();
			drawables.add(drawable);
			drawByLevelMap.put(drawable.getDepth(), drawables);

		}
		{
			Array<DrawableInterface> drawables = (Array<DrawableInterface>) drawByLevelMap
					.get(world.getHolden().getDepth());
			if (drawables == null)
				drawables = new Array<DrawableInterface>();
			drawables.add(world.getHolden());
			drawByLevelMap.put(world.getHolden().getDepth(), drawables);
		}
		{
			Array<DrawableInterface> drawables = (Array<DrawableInterface>) drawByLevelMap
					.get(world.getFriend().getDepth());
			if (drawables == null)
				drawables = new Array<DrawableInterface>();
			drawables.add(world.getFriend());
			drawByLevelMap.put(world.getFriend().getDepth(), drawables);
		}

	}

	float CalcAverageTick(long newtick)
	{
	    ticksum-=ticklist[tickindex];  /* subtract value falling off */
	    ticksum+=newtick;              /* add new value */
	    ticklist[tickindex]=newtick;   /* save new value so it can be subtracted later */
	    if(++tickindex==MAXSAMPLES)    /* inc buffer index */
	        tickindex=0;

	    /* return average */
	    return((float)ticksum/MAXSAMPLES);
	}
/*
	private void drawSprites(SpriteBatch sb, TreeMap<Integer, Array<InteractObject>> map) {
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
*/
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
//		for (Rectangle rect : world.getCollisionRects()) {
//			debugRenderer.rect(rect.x-world.getCamera().getPosition().x, rect.y-world.getCamera().getPosition().y, rect.width, rect.height);
//		}
		debugRenderer.end();
		// render blocks
		debugRenderer.begin(ShapeType.Line);

		for (DrawableInterface drawable : world.getScreenObjects()) {
			Rectangle rect = drawable.getBounds();
			if (drawable.getClass() == AccelerationPad.class) {
				debugRenderer.setColor(new Color(1, 1, 0, 1));
			} else {
				debugRenderer.setColor(new Color(1, 0, 0, 1));
			}
			debugRenderer.rect(drawable.getBounds().x-world.getCamera().getPosition().x,
					drawable.getBounds().y-world.getCamera().getPosition().y, rect.width, rect.height);
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
			/*
			Rectangle rect = world.getDrawableArea();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			float x = rect.x-world.getCamera().getPosition().x;
			float y = rect.y-world.getCamera().getPosition().y;
			float w = rect.width;
			float h = rect.height;
			debugRenderer.rect(x, y, w, h);
		*/
		}
		debugRenderer.end();
	}

	public void updateCam() {
		
		float x = world.getCamera().getPosition().x - world.getCamera().getWidth() / 2;
		float y = world.getCamera().getPosition().y - world.getCamera().getHeight() / 2;
		//world.setDrawableArea(new Rectangle(world.getCamera().getCam().position.x - world.getCamera().getWidth() / 2,
		//		world.getCamera().getCam().position.y - world.getCamera().getHeight() / 2, world.getCamera().getWidth()*world.getCamera().getPpuX(), world.getCamera().getHeight()*world.getCamera().getPpuY()));
		world.fillScreenObjects(new Rectangle(x, y, world.getCamera().getWidth(),
				world.getCamera().getHeight()));
		world.getCamera().update();
		//cam.update();
	}

}
