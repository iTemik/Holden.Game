package bar.kisskiss.holden.view;

import java.util.Map;

import bar.kisskiss.holden.model.AccelerationPad;
import bar.kisskiss.holden.model.Friend;
import bar.kisskiss.holden.model.Holden;
import bar.kisskiss.holden.model.Holden.State;
import bar.kisskiss.holden.model.InteractObject;
import bar.kisskiss.holden.model.Target;
import bar.kisskiss.holden.model.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 70f;

	private World world;
	private OrthographicCamera cam;

	private static final float WALKING_FRAME_DURATION = 0.053f;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	//private Texture holdenTexture;
	
	private TextureRegion holdenIdle;
	private TextureRegion holdenFrame;	
	private TextureRegion friendFrame;

	
	private TextureAtlas atlas;
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void setAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
	}

	private BitmapFont font;
	
	/** Animations **/
    private Animation walkAnimation;
    private Animation friendAnimation;
    
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	private float camShiftX = CAMERA_WIDTH/2;
	private float camShiftY = CAMERA_HEIGHT/2;
	
	public float getCamShiftX() {
		return camShiftX;
	}

	public void setCamShiftX(float camShiftX) {
		this.camShiftX = camShiftX;
	}

	public float getCamShiftY() {
		return camShiftY;
	}

	public void setCamShiftY(float camShiftY) {
		this.camShiftY = camShiftY;
	}

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
		world.setDrawableArea(new Rectangle(cam.position.x - CAMERA_WIDTH / 2,
				cam.position.y - CAMERA_HEIGHT / 2, CAMERA_WIDTH, CAMERA_HEIGHT));
		
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
/*AK TODO: refactor*/
	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
		world.setDrawableArea(new Rectangle(cam.position.x - CAMERA_WIDTH / 2,
				cam.position.y - CAMERA_HEIGHT / 2, CAMERA_WIDTH, CAMERA_HEIGHT));
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

	public static float getCameraWidth() {
		return CAMERA_WIDTH;
	}

	private void loadTextures() {
		
		atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
		holdenIdle = atlas.findRegion("holden-00");

		//blockTexture = atlas.findRegion("block");
		
		TextureRegion[] walkFrames = new TextureRegion[16];
		for (int i = 0; i < walkFrames.length; i++) {			
			walkFrames[i] = atlas.findRegion("holden-" +(i/10)+""+(i%10));
		}
		walkAnimation = new Animation(WALKING_FRAME_DURATION, walkFrames);
		
		TextureRegion[] friendFrames = new TextureRegion[4];		
		for (int i = 0; i < friendFrames.length; i++) {	
			friendFrames[i] = atlas.findRegion("friend-" +(i/10)+""+(i%10));
		}
		friendAnimation = new Animation(0.1f, friendFrames);
		font = new BitmapFont();
/*
		TextureRegion[] accelerationPadFrames = new TextureRegion[2];
		for (int i = 0; i < accelerationPadFrames.length; i++) {			
			accelerationPadFrames[i] = atlas.findRegion("accelerator-" +(i/10)+""+(i%10));
		}
		accelereationPadAnimation = new Animation(0.12f, accelerationPadFrames);
	*/	
		
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
		drawInteractObjects(spriteBatch);
		drawHolden();				
		drawFriend();
		font.draw(spriteBatch, String
				.format("target  x: %.2f   y: %.2f", world.getTarget()
						.getPosition().x, world.getTarget().getPosition().y),
				20, 30);
		spriteBatch.end();
		if (debug)
			drawDebug();
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
	

	
	private void drawInteractObjects(SpriteBatch sb) {
		/*AK TODO: Re do?*/
		for (InteractObject interactObject : world.getDrawableObjects()) {
			interactObject.draw(sb, ppuX, ppuY);
		}
	}

	private void drawHolden() {
		Holden holden = world.getHolden();
		holdenFrame = holdenIdle;

		if(holden.getState().equals(State.WALKING)) {
			holdenFrame = walkAnimation.getKeyFrame(holden.getStateTime(), true);
		}
		Sprite sprite = new Sprite(holdenFrame);
		sprite.rotate(world.getHolden().getFacing().angle()-90); 

		sprite.setX(holden.getPosition().x * ppuX);
		sprite.setY(holden.getPosition().y * ppuY);
		sprite.draw(spriteBatch);		
		
	}
	
	private void drawFriend() {
		Friend friend = world.getFriend();
		friendFrame = friendAnimation.getKeyFrame(friend.getStateTime(), true);
				
		spriteBatch.draw(friendFrame, friend.getPosition().x * ppuX,
				friend.getPosition().y * ppuY, friend.getBounds().width * ppuX,
				friend.getBounds().height * ppuY);

	}
		
	private void drawDebug() {
		// render collidable
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(0, 0, 1, 0.2f));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();
		// render blocks
		debugRenderer.begin(ShapeType.Line);

		for (InteractObject interactObject : world.getDrawableObjects()) {
			Rectangle rect = interactObject.getBounds();
			if(interactObject.getClass() == AccelerationPad.class){
				debugRenderer.setColor(new Color(1, 1, 0, 1));
			}
			else {
				debugRenderer.setColor(new Color(1, 0, 0, 1));
			}
			debugRenderer.rect(interactObject.getPosition().x, interactObject.getPosition().y,
					rect.width, rect.height);
		}	
		
		// render holden
		{
			Holden holden = world.getHolden();
			Rectangle rect = holden.getBounds();
			float x1 = holden.getPosition().x/* + rect.x*/;
			float y1 = holden.getPosition().y/* + rect.y*/;
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		//render friend
		{
			Friend friend = world.getFriend();
			Rectangle rect = friend.getBounds();
			float x1 = friend.getPosition().x/* + rect.x*/;
			float y1 = friend.getPosition().y/* + rect.y*/;
			debugRenderer.setColor(new Color(0, 0, 1, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		{
			Target target = world.getTarget();
			Rectangle rect = target.getBounds();
			float x1 = /*rect.x + */target.getPosition().x;
			float y1 = /*rect.y + */target.getPosition().y;
			debugRenderer.setColor(new Color(1, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}

		debugRenderer.end();
	}

	public void updateCam() {
		// TODO Auto-generated method stub
		cam.position.x = camShiftX + CAMERA_WIDTH/2;
		cam.position.y = camShiftY + CAMERA_HEIGHT/2;
		cam.update();
		world.setDrawableArea(new Rectangle(cam.position.x - CAMERA_WIDTH / 2,
				cam.position.y - CAMERA_HEIGHT / 2, CAMERA_WIDTH, CAMERA_HEIGHT));
	}

}
