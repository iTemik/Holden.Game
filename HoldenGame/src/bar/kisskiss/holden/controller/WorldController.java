package bar.kisskiss.holden.controller;

import bar.kisskiss.holden.model.InteractObject;
import bar.kisskiss.holden.model.World;

public class WorldController {

	private World world;
	
	public void update(float delta) {
		for (InteractObject object : world.getDrawableObjects()) {
			object.update(delta);
		}
	}
	
	public WorldController(World world) {
		this.world = world;
	}
	
}
