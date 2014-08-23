package pr.dawe.game;

import java.util.Random;

import pr.dawe.game.entities.Mob;
import pr.dawe.game.entities.PickableItem;
import pr.dawe.game.entities.Player;
import pr.dawe.game.entities.Weapon;
import pr.dawe.game.gfx.Colours;
import pr.dawe.game.gfx.Font;
import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;


public class GameEvents {
	static Random generator = new Random();
	static long lastTime;
	static boolean playerIsIndoor = false;
	private long lastShot;
	private long Time;
	public static boolean overItem = false;
	public static boolean overCoin = false;
	
	private int green = Colours.get(-1, 555, 141, 400);
	private int blue = Colours.get(-1, 555, 115, 400);
	private int orange = Colours.get(-1, 555, 542, 400);
	private int red = Colours.get(-1, 555, 500, 400);
	private int black = Colours.get(-1, 555, 000, 400);

	private int playerHealth = 3;
	static int bullets = 0;
	public static int shotbullet = 0;
	private int water = 100;
	private int food = 100;
	
	public GameEvents() {
		
	}
	

	public void renderInterface(Screen screen, int x, int y) {
	if (!playerIsIndoor)
	if (playerHealth == 3) // HEALTH
	    Font.render("ccc", screen, x+1, y, Colours.get(-1, 555, -1, 400), 1);
	if (playerHealth == 2) 
		Font.render("cc", screen, x+1, y, Colours.get(-1, 555, -1, 400), 1);
	if (playerHealth == 1) 
		Font.render("c", screen, x+1, y, Colours.get(-1, 555, -1, 400), 1);
	
	if (water <= 100 && water > 50) // WATER
		Font.render("w", screen, x+24, y, blue, 1);
	if (water <= 50 && water > 15)
		Font.render("w", screen, x+24, y, orange, 1);
	if (water <= 15 && water > 0)
		Font.render("w", screen, x+24, y, red, 1);
	if (water <= 0)
		Font.render("w", screen, x+24, y, black, 1);
	

	if (food <= 100 && food > 50) // FOOD
		Font.render("f", screen, x+31, y, green, 1);
	if (food <= 50 && food > 15)
		Font.render("f", screen, x+31, y, orange, 1);
	if (food <= 15 && food > 0)
		Font.render("f", screen, x+31, y, red, 1);
	if (food <= 0)
		Font.render("f", screen, x+31, y, black, 1);
		
		
	
	Font.render("m"+bullets, screen, x, y+7, Colours.get(-1, 111, 540, 111), 1);
	}
	
		
	public void renderPlayerEvents(Screen screen, int x, int y, InputHandler input, Player player, Level level) {
		
		
		
	if (Player.triggeredCHEST == true) {   // CHEST TRIGGER
		Font.render("a", screen, x + 72, y + 44, Colours.get(-1, 135, -1, 000), 1);
		if (input.chest.isPressed()) {
			Font.render("INVENTORY", screen, x + 55, y + 37, Colours.get(-1, -1, -1, 000), 1);
	}
	}
	
	if (Player.triggeredKEY == true) {   // INVESTIGATE FLOWER
		Font.render("b", screen, x + 72, y + 44, Colours.get(-1, 135, -1, 000), 1);
		if(input.investigate.isPressed()) {
			Font.render("FLOWER!", screen, x + 65, y + 37, Colours.get(-1, 135, -1, 000), 1);
	}
	}
	
	
	if (input.shoot.isPressed() == true && bullets > 0 && shotbullet == 0) {   // SHOOT
		Game.FireBall = new Weapon(level, Screen.xOffset + 75, Screen.yOffset + 55);
		level.addEntity(Game.FireBall);
		lastShot = System.currentTimeMillis();
		shotbullet++;
		bullets--;
	}
	
	if (overItem == true) {  //PICK UP ITEMS
		level.removeEntity(PickableItem.pickUp);
		overItem = false;
		}
	
	
	if (overCoin == true) {  //PICK UP ITEMS
		level.removeEntity(PickableItem.pickUp);
		overCoin = false;	
		bullets+=10;
	}
	
	if (Player.wardrobe == true) {  // WARDROBE
		int randomcolour1 = generator.nextInt(5) + 1;
		int randomcolour2 = generator.nextInt(5) + 1;
		int randomcolour3 = generator.nextInt(5) + 1;
		level.removeEntity(Game.npc);
		
		Font.render("b", screen, x + 72, y + 44, Colours.get(-1, 135, -1, 000), 1);
		if (input.investigate.isPressed()) {
			level.removeEntity(Game.FireBall);
			Player.colour = Colours.get(-1, 000, randomcolour1 * 100 + randomcolour2 * 10 + randomcolour3, 543);
			//Player.wardrobe = false;
		}
	}
	
	if (Player.triggeredDOOR == true) {  
		Font.render("ENTER", screen, x + 65, y + 37, Colours.get(-1, 135, -1, 000), 1);
		if (input.enter.isPressed()) {
			
			Game.startIndoorLevel("/levels/houses_house1.png", 75, 85);
			playerIsIndoor = true;
		}
	}
	
	if (Player.triggeredDOOR_LEAVE == true) {  // FOREST/HOUSE_LEAVE 
		Font.render("LEAVE", screen, x + 65, y + 37, Colours.get(-1, 135, -1, 000), 1);
		if (input.enter.isPressed()) {
			Game.startLevel("/levels/level_1.png", 505, 475);
			playerIsIndoor = false;
		}
	}
	
	if (Player.triggeredLAVA == true) {  // LAVA DAMAGE
		
		Player.gettingDamage = true;
		if (System.currentTimeMillis() >= lastTime ) {
		lastTime = System.currentTimeMillis() + 1000;
		playerHealth--;
		}
		else {
			Player.gettingDamage = false;
		}
	}
	
	if (Player.gettingDamage == false && playerHealth < 3) { // MEDIC
		if (System.currentTimeMillis() >= lastTime ) {
		lastTime = System.currentTimeMillis() + 3000;
		playerHealth++;
		}
	}
		
	if (playerHealth <= 0) {       // PLAYER DEAD
		Game.level = new Level("/levels/you_are_dead.png");
		Font.render("Y O U  A R E", screen, 28, 30, Colours.get(-1, 135, -1, 555), 2);
	}
	
}
	
}