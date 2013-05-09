import bar.kisskiss.holden.GameHolden;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class HoldenGameDesktop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		new LwjglApplication(new GameHolden(), "Holden Game", 480, 320, true);
	}

}
