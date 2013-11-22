package bar.kisskiss.holden.controller;

import bar.kisskiss.holden.model.World;
import bar.kisskiss.holden.model.general.InteractObject;
import bar.kisskiss.holden.model.interfaces.DrawableInterface;

public class WorldController {

	private World world;
	
	
	
	public void update(float delta) {
		//for (DrawableInterface drawable : world.getLevel().getScreenObjects()) {
		for (DrawableInterface drawable : world.getWorldObjects()) {		
			drawable.update(delta);
		}
	}
	
	public WorldController(World world) {
		this.world = world;
	}
	
}
