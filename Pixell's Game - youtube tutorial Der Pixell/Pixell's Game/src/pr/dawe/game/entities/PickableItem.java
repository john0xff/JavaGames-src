package pr.dawe.game.entities;

import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;
import pr.dawe.game.Game;
import pr.dawe.game.GameEvents;
import pr.dawe.game.InputHandler;


public class PickableItem extends Entity{
	
	public static PickableItem pickUp = null;
	public String name;
	public int ID; //0 = instant pickUp; 1 = press to pickUp;
	public int colour;
	public int xTile;
	public int yTile;
	public static boolean overItem = false;
	public static boolean overCoin = false;
	
	public PickableItem(Level level, String pname, int pcolour, int id, int pxTile, int pyTile, int x, int y) {
		super(level);
		this.x = x;
		this.y = y;
		colour = pcolour;
		name = pname;
	    ID = id;
		xTile = pxTile;
		yTile = pyTile;
	}

	private int scale = 1;
	
    public void tick() {
    	checkEnvironment(Game.input);
    }
        	   
    public void render(Screen screen) {
		int xOffset = x;
		int yOffset = y;
		screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 1, scale);
	}

    public void checkEnvironment(InputHandler input) {
    	if (input.investigate.isPressed() == true) {
        if (Game.player.x >= x && Game.player.x <= x + 5 && Game.player.y >= y && Game.player.y <= y + 5) {
    		pickUp = this;
    		if (pickUp.getID() == 0) {
    			GameEvents.overCoin = true;
    		
    		} else {
    			
    		if (pickUp.getID() == 1) {
    			GameEvents.overItem = true;
    			System.out.println("Item");
       		} else {	
    		pickUp = null;
    		GameEvents.overCoin = false;
    		GameEvents.overItem = false; 
    	    }
          }
        }
      }
    }
    
    public int getID() {
    	return ID;
    }
    
    }
