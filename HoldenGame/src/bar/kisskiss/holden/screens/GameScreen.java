package bar.kisskiss.holden.screens;

import bar.kisskiss.holden.controller.FriendController;
import bar.kisskiss.holden.controller.HoldenController;
import bar.kisskiss.holden.controller.WorldController;
import bar.kisskiss.holden.model.World;
import bar.kisskiss.holden.view.WorldRenderer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private FriendController friendController;
	private HoldenController holdenController;
	private WorldController worldController;
	
	
	private int width, height;

	@Override
	public void show() {
		
		world = new World();
		renderer = new WorldRenderer(world, false);		

		friendController = new FriendController(world);
		holdenController = new HoldenController(world);
		worldController = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		holdenController.update(delta);
		friendController.update(delta);		
		worldController.update(delta);
		
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT)
			holdenController.leftPressed();
		if (keycode == Keys.RIGHT)
			holdenController.rightPressed();
		if (keycode == Keys.UP)
			holdenController.upPressed();
		if (keycode == Keys.DOWN)
			holdenController.downPressed();
		
		if (keycode == Keys.X)
			friendController.pushPressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			holdenController.leftReleased();
		if (keycode == Keys.RIGHT)
			holdenController.rightReleased();
		if (keycode == Keys.UP)
		holdenController.upReleased();
		if (keycode == Keys.DOWN)
			holdenController.downReleased();
		
		if (keycode == Keys.X)
			friendController.pushReleased();
		
		if(keycode == Keys.D)
			renderer.setDebug(!renderer.getDebug());
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// if (!Gdx.app.getType().equals(ApplicationType.Android))
		// return false;

		float leftConer = renderer.getCamShiftX() - renderer.getCam().viewportWidth/2;
		float bottomConer = renderer.getCamShiftY() - renderer.getCam().viewportHeight/2;
		
		float x = leftConer + screenX * renderer.getCam().viewportWidth/width;
		float y = bottomConer + (height - screenY) * renderer.getCam().viewportHeight/height;
		world.getTarget().setPosition(new Vector2(x, y));
		renderer.setCamShiftX(x);
		renderer.setCamShiftY(y);
		renderer.updateCam();
		
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// if (!Gdx.app.getType().equals(ApplicationType.Android))
		// return false;

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
