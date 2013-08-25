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

	//private static final float CAMERA_WIDTH = 100f;
	//private static final float CAMERA_HEIGHT = 70f;

	private World world;
	//private OrthographicCamera cam;

	private static final float WALKING_FRAME_DURATION = 0.053f;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
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

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;		

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
		holdenIdle = atlas.findRegion("holden-00");

	
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
		drawInteractObjects(spriteBatch);
		drawHolden();				
		drawFriend();
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
			font.draw(spriteBatch, String.format("cam delta position  x: %.2f   y: %.2f",
					world.getCamera().getDeltaPosition().x, world.getCamera().getDeltaPosition().y), 10, 44);
			
			font.draw(spriteBatch, String.format(
					"Cam shift  x: %.2f   y: %.2f", world.getCamera().getPosition().x,
					world.getCamera().getPosition().y), 10, 56);
*/
		}
		spriteBatch.end();
		if (debug) {
			drawDebug();			
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

	private void drawInteractObjects(SpriteBatch sb) {
		for (InteractObject interactObject : world.getDrawableObjects()) {
			interactObject.draw(sb, world.getCamera().getPosition().x - world.getCamera().getWidth() / 2, world.getCamera().getPosition().y
					- world.getCamera().getHeight() / 2, world.getCamera().getPpuX(), world.getCamera().getPpuY());
		}
	}

	private void drawHolden() {
		Holden holden = world.getHolden();
		holdenFrame = holdenIdle;

		if (holden.getState().equals(State.WALKING)) {
			holdenFrame = walkAnimation
					.getKeyFrame(holden.getStateTime(), true);
		}
		Sprite sprite = new Sprite(holdenFrame);
		sprite.rotate(world.getHolden().getFacing().angle() - 90);

		sprite.setX((holden.getPosition().x - world.getCamera().getPosition().x + world.getCamera().getWidth() / 2)
				* world.getCamera().getPpuX());
		sprite.setY((holden.getPosition().y - world.getCamera().getPosition().y + world.getCamera().getHeight() / 2)
				* world.getCamera().getPpuY());
		sprite.draw(spriteBatch);

	}

	private void drawFriend() {
		Friend friend = world.getFriend();
		friendFrame = friendAnimation.getKeyFrame(friend.getStateTime(), true);

		spriteBatch
				.draw(friendFrame,
						(friend.getPosition().x - world.getCamera().getPosition().x + world.getCamera().getWidth() / 2)
								* world.getCamera().getPpuX(),
						(friend.getPosition().y - world.getCamera().getPosition().y + world.getCamera().getHeight() / 2)
								* world.getCamera().getPpuY(), friend.getBounds().width * world.getCamera().getPpuX(),
						friend.getBounds().height * world.getCamera().getPpuY());

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
