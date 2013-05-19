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
	Target target;
	
	public Target getTarget() {
		return target;
	}

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
		holden = new Holden(new Vector2(55, 25), 5);
		friend = new Friend(new Vector2(20, 40), 10);
		target = new Target(new Vector2(0,0));
		
		Random rand = new Random();
		for (int i = 1; i < 9; i++) {
			for(int j = 1; j<6; j++) {
				int randomNum = rand.nextInt(10);
				if(randomNum<3)
					blocks.add(new Block(new Vector2(i*10, j*10)));
			}			
		}	
	}
}
