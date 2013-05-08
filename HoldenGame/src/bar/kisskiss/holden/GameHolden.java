package bar.kisskiss.holden;

import bar.kisskiss.holden.screens.GameScreen;

import com.badlogic.gdx.Game;

public class GameHolden extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
