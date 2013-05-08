package bar.kisskiss.holden.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	Holden holden;

	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}

	public Holden getHolden() {
		return holden;
	}

	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		holden = new Holden(new Vector2(75, 25));

		for (int i = 0; i < 10; i++) {
			blocks.add(new Block(new Vector2(i*10, 0)));
			blocks.add(new Block(new Vector2(i*10, 60)));
			if (i > 2)
				blocks.add(new Block(new Vector2(i*10, 10)));
		}
		blocks.add(new Block(new Vector2(90, 20)));
		blocks.add(new Block(new Vector2(90, 30)));
		blocks.add(new Block(new Vector2(90, 40)));
		blocks.add(new Block(new Vector2(90, 50)));

		blocks.add(new Block(new Vector2(60, 30)));
		blocks.add(new Block(new Vector2(60, 40)));
		blocks.add(new Block(new Vector2(60, 50)));
	}
}
