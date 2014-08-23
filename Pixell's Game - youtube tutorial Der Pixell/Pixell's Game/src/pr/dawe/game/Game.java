package pr.dawe.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;

import javax.swing.JFrame;

import pr.dawe.game.entities.NPC;
import pr.dawe.game.entities.PickableItem;
import pr.dawe.game.entities.Player;
import pr.dawe.game.entities.Weapon;
import pr.dawe.game.gfx.Colours;
import pr.dawe.game.gfx.Menu;
import pr.dawe.game.gfx.Screen;
import pr.dawe.game.gfx.SpriteSheet;
import pr.dawe.game.level.Level;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 4;
	public static final String NAME = "Game";
	
	JFrame frame;
	Random generator = new Random();
	
	public boolean running = false;
	public int tickCount = 0;
	long lastTime;
	public static int xOffset;
	public static int yOffset;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6*6*6];
	
	private Screen screen;
	public static InputHandler input;
	public static Level level;
	public static GameEvents gameEvents;
	
	//ENTITIES
	public static Player player;
	public static NPC npc;
	public static Weapon FireBall;

	public List<PickableItem> pickableItems = new ArrayList<PickableItem>();
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void init() {
		int index = 0;
		for (int r = 0; r<6; r++) {
			for (int g = 0; g<6; g++) {
				for (int b = 0; b<6; b++) {
					int rr = (r*255/5);
					int gg = (g*255/5);
					int bb = (b*255/5);
					
					colours[index++] = rr << 16 | gg << 8 | bb;
			}
		}
	}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));  // MAP; PLAYER; SHEEP
	    input = new InputHandler(this);
	    startLevel("/levels/level_1.png", 390, 390);
	    addEntities();
    }
	
	public static void startLevel(String levelPath, int x, int y) {
		level = new Level(levelPath);    
		player = new Player(level, x, y, input);
		npc = new NPC(level, 270, 270);
		level.addEntity(player);                                   
		level.addEntity(npc);
		gameEvents = new GameEvents();
		                                                        // ADD ENTITIES
	}
	
	public void addEntities() {
		PickableItem gun = new PickableItem(level, "Gun", Colours.get(-1, 111, 170, 222),1, 9, 29, 400, 400);
		PickableItem gun_mun = new PickableItem(level, "gun_mun", Colours.get(-1, 111, 540, 321),0, 8, 29, 380, 430);
		PickableItem gun_mun2 = new PickableItem(level, "gun_mun", Colours.get(-1, 111, 540, 321),0, 8, 29, 380, 410);
		PickableItem shotgun_mun = new PickableItem(level, "shotgun_mun", Colours.get(-1, 111, 300, 540),1, 10, 29, 380, 450);
		pickableItems.add(gun_mun);
		pickableItems.add(gun_mun2); 
		pickableItems.add(gun);
		pickableItems.add(shotgun_mun);
		level.addPickableItems(pickableItems);
		
	}
	
	public static void startIndoorLevel(String levelPath, int x, int y){
		level = new Level(levelPath);    
		Player player = new Player(level, x, y, input);
		level.addEntity(player);
	}
	
	public synchronized void start() {
    	running = true;
    	Menu.running = true;
    	new Thread(this).start();
    }
    
    public synchronized void stop() {
    	running = false;
    	Menu.running = true;
	}
    
    public void close() {
    	frame.dispose();
    }
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
			ticks++;
			tick();
			delta -=1;
			shouldRender = true;
		}
          try {
	        Thread.sleep(2);
        } 
          catch (InterruptedException e) {
	
	        e.printStackTrace();
        }
			if (shouldRender) {
			frames++;
			render();
			}
			
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(""+ticks+ " ticks, "+frames+ " frames");
				frames = 0;
			    ticks = 0;
			}
		}
	}
	
	
	public void tick(){
		tickCount++;
		level.tick();
	}
	
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		xOffset = player.x - (screen.width/2);
		yOffset = player.y - (screen.height/2);
		
		level.renderTiles(screen, xOffset, yOffset);
		
		for(int x = 0; x <level.width; x++) {
			int colour = Colours.get(-1, -1, -1, 000);
			if (x % 10 == 0 && x != 0) {
			colour = Colours.get(-1, -1, -1, 500);
			}
			
		}
		
		level.renderEntities(screen); //ENTITIES
		Player.checkBullet();
		
		gameEvents.renderInterface(screen, xOffset, yOffset);
	    gameEvents.renderPlayerEvents(screen, xOffset, yOffset, input, player, level);
	 

		
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x+y * screen.width];
				if (colourCode < 255) pixels [x + y * WIDTH] = colours[colourCode]; 
				
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
		
	}
	
	
	
	
	public static void main(String[] args) {
		new Game().start();
	}

	

}
