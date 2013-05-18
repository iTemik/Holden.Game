package bar.kisskiss.holden.model;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** Our player controlled hero **/
	Holden holden;
	Friend friend;
	
	public Friend getFriend() {
		return friend;
	}

	public void setBlocks(Array<Block> blocks) {
		this.blocks = blocks;
	}

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
		holden = new Holden(new Vector2(75, 25), 5);
		friend = new Friend(new Vector2(10, 10), 10);
		
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			for(int j = 0; j<7; j++) {
				int randomNum = rand.nextInt(10);
				if(randomNum<3)
					blocks.add(new Block(new Vector2(i*10, j*10)));
			}			
		}	
	}
}
