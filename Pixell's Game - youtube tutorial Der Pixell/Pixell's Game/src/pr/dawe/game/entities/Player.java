package pr.dawe.game.entities;

import pr.dawe.game.Game;
import pr.dawe.game.GameEvents;
import pr.dawe.game.InputHandler;
import pr.dawe.game.gfx.Colours;
import pr.dawe.game.gfx.Font;
import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;
import pr.dawe.game.level.tiles.Tile;
import pr.dawe.game.entities.Entity.*;

public class Player extends Mob {
	
	private InputHandler input;
	public static int colour = Colours.get(-1, 000, 500, 543);
	private int scale = 1;
	protected boolean isSwimming = false;
	public static boolean gettingDamage;
	private int tickCount;
	public static boolean triggeredCHEST = false;
	public static boolean triggeredDOOR = false;
	public static boolean triggeredDOOR_LEAVE = false;
	public static boolean triggeredLAVA = false;
	public static boolean triggeredKEY = false;
	public static boolean wardrobe = false;
	public static int xPos;
	public static int yPos;
	
	public Player(Level level, int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
	}

	public void tick() {
        int xa = 0;
    	int ya = 0;
    	
        if(input.up.isPressed()) { 
        	ya--;
        }
            if (!hasCollided(xa, ya)) 
        
		
		if(input.down.isPressed()) { 
			ya++;
		}
			if (!hasCollided(xa, ya)) 
		
		
		if(input.left.isPressed()) { 
			xa--; 
		}
			if(!hasCollided(xa, ya))
		
		
		if(input.right.isPressed()) { 
			xa++; 
		}
			if (!hasCollided(xa, ya))
				
				//System.out.println(x+" "+y);
		
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		
		}
		
		//SWIMMING
		if(level.getTile(this.x >> 3, this.y >> 3 ).getId() == 3) {
			isSwimming = true;	
		}
		if (isSwimming && level.getTile(this.x >> 3, this.y >> 3 ).getId() != 3) {
			isSwimming = false;
		}
		
	    //TRIGGERED CHEST
		if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 12) {
			triggeredCHEST = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 12) {
			triggeredCHEST = false;
		}
		
		//TRIGGERED DOOR_ENTER
		if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 13) {
			triggeredDOOR = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 13) {
			triggeredDOOR = false;
		}
		
		//TRIGGERED DOOR_LEAVE
		if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 14) {
			triggeredDOOR_LEAVE = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 14) {
			triggeredDOOR_LEAVE = false;
		}
		
		//LAVA DAMAGE
	    if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 18) {
			triggeredLAVA = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 18) {
			triggeredLAVA = false;
		}
		
		//KEY FLOWER
	    if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 21) {
			triggeredKEY = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 21) {
			triggeredKEY = false;
		}
		
		if(level.getTile(this.x >> 3,  this.y >> 3).getId() == 19) {
			wardrobe = true;
		}
		if (level.getTile(this.x >> 3, this.y >> 3 ).getId() != 19) {
			wardrobe = false;
		}
		tickCount++;
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
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
	
	public static void checkBullet() {
		if (Weapon.moving == false) {
			level.removeEntity(Game.FireBall);
			GameEvents.shotbullet = 0;
			}
		}
	
}
