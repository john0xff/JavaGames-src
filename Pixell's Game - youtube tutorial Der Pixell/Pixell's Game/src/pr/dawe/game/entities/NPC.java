package pr.dawe.game.entities;

import java.util.Random;

import pr.dawe.game.gfx.Colours;
import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;

public class NPC extends Mob {
	
	private int colour = Colours.get(-1, 000, 230, 543);
	private int scale = 1;
	protected boolean isSwimming = false;
	private int tickCount;
	Random generator = new Random();
	private int move;

	public NPC(Level level, int x, int y) {
		super(level, "Zombie", x, y, 1);
	}

	
    public void tick() {
    	int xa = 0;
    	int ya = 0;
    	move = generator.nextInt(50) + 1;
    	
    	if(move == 50) 
        	ya--;
        	if (!hasCollided(xa, ya)) 
        
		
		if(move == 40) 
			ya++;
			if (!hasCollided(xa, ya)) 
		
		
		if(move == 30) 
			xa--;
			if (!hasCollided(xa, ya))
		
		
		if(move == 20) 
			xa++;
			if (!hasCollided(xa, ya)) 
    	
    	
    		
    	
    	
    	if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
		if(level.getTile(this.x >> 3, this.y >> 3 ).getId() == 3) {
			isSwimming = true;
			
		}
		if (isSwimming && level.getTile(this.x >> 3, this.y >> 3 ).getId() != 3) {
			isSwimming = false;
		}
		tickCount++;
		
		
	}
	
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 25;
		int walkingSpeed = 4;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if (movingDir == 1) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier /2 - 4;
		if (isSwimming) {
			int waterColour = 0;
			yOffset += 4;
			if (tickCount % 60 < 15) {
				waterColour = Colours.get(-1, -1, 225, -1);
			} else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			} else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
				waterColour = Colours.get(-1, 115, -1, 225);
			} else {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			}
			screen.render(xOffset, yOffset + 3, 0 + 27 * 32, waterColour, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 27 * 32, waterColour, 0x01, 1);
		}
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, xTile + 1 + yTile * 32, colour, flipTop, scale);	
		
		if (!isSwimming) {
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile 
				+ (yTile + 1) * 32, colour, flipBottom, scale);	
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom, scale);	
		
	}
}
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		for (int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		
		return false;
	}
}

