import bar.kisskiss.holden.GameHolden;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class HoldenGameDesktop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LwjglApplication(new GameHolden(), "Star Assault", 480, 320, true);
	}

}
