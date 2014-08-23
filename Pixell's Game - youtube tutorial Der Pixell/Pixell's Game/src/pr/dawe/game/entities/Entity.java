package pr.dawe.game.entities;

import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;

public abstract class Entity {
	
	public int x;
	public int y;
	protected static Level level;
	
	public Entity(Level level) {
		init(level);
		
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);

}
