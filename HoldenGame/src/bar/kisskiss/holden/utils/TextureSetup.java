package bar.kisskiss.holden.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class TextureSetup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TexturePacker2.process(
				"../HoldenGame-android/assets/images/",
				"../HoldenGame-android/assets/images/textures/",
				"textures.pack");
	}

}
