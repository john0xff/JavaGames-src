package pr.dawe.game.level.tiles;

import pr.dawe.game.gfx.Colours;
import pr.dawe.game.gfx.Screen;
import pr.dawe.game.level.Level;

public abstract class Tile {
	
	//BASIC TILES
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(222, 333, 444, 333), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0x0FF00FF00);
	public static final Tile TREE = new BasicSolidTile(4, 4, 0, Colours.get(-1, 170, 320, 170), 0x0FF523000);
	public static final Tile TREETOP = new BasicTile(5, 5, 0, Colours.get(-1, 170, 320, 170), 0x0FF423000);
	public static final Tile SAND = new BasicTile(6, 3, 0, Colours.get(-1, 542, 555, -1), 0x0FFFFF000);
	public static final Tile STONEWALL = new BasicSolidTile(8, 6, 0, Colours.get(333, 222, 333, 444), 0xFFAAAAAA);
	public static final Tile FLOWERS_1 = new BasicTile(10, 7, 0, Colours.get(131, 131, 510, 450), 0x0FFFF9000);
	public static final Tile TREE_LEAVES = new BasicTile(11, 8, 0, Colours.get(121, 121, 141, 522), 0x0FF00A600);
	
	//ANIMATED TILES
	public static final Tile WATER = new AnimatedTile(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colours.get(-1, 004, 115, -1), 0x0FF0000FF, 900);
	public static final Tile WATERFALL = new AnimatedTile(7, new int[][] {{3, 5}, {4, 5}, {5, 5}, {4, 5}}, Colours.get(004, 335, 115, 225), 0x0FF009696, 100);
	public static final Tile WATERFALLSPLASH = new AnimatedTile(9, new int[][] {{6, 5}, {7, 5}, {8, 5}, {7, 5}}, Colours.get(004, 335, 115, 225), 0x0FF00FFFF, 900);
	public static final Tile C_AND_D = new AnimatedTile(20, new int[][] {{12, 5}, {13, 5}, {14, 5}}, Colours.get(004, 005, 555, 111), 0x0FF85F8FF, 900);
	public static final Tile KEY_FLOWER = new AnimatedTile(21, new int[][] {{15, 5}, {16, 5}}, Colours.get(131, 005, 151, 500), 0x0FFBAFF00, 900);
	
	//TRIGGER TILES
	public static final Tile CHEST = new BasicSolidTile(12, 9, 0, Colours.get(321, 170, 444, 542), 0x0FFFF0000);
	public static final Tile DOOR_ENTER = new BasicTile(13, 10, 0, Colours.get(321, 000, 444, 542), 0x0FF666666);
	public static final Tile DOOR_LEAVE = new BasicTile(14, 11, 0, Colours.get(-1, 000, 510, -1), 0x0FF820000);
	
	//BUILDINGS
	public static final Tile BRICKS = new BasicSolidTile(15, 12, 0, Colours.get(321, 170, 444, 542), 0x0FF948550);
	public static final Tile WOODEN_FLOOR = new BasicTile(16, 12, 0, Colours.get(-1, 320, 542, -1), 0x0FFFFEEEE);
	public static final Tile ROOF = new BasicSolidTile(17, 13, 0, Colours.get(-1, 300, 222, -1), 0x0FFCB0000);
	public static final Tile WARDROBE = new BasicSolidTile(19, 14, 0, Colours.get(321, 170, 444, 542), 0x0FFFF4747);
	
	//DAMAGE TILES
	public static final Tile LAVA = new AnimatedTile(18, new int[][] {{9, 5}, {10, 5}, {11, 5}, {10, 5}}, Colours.get(300, 410, 400, 542), 0x0FF960088, 900);
	
	//PORTALS
	public static final Tile FOREST_ENTER = new BasicTile(22, 10, 0, Colours.get(321, 000, 444, 542), 0x0FF666666);
	
	
	
	protected byte id;
    protected boolean solid;
    protected boolean emitter;
    protected boolean trigger;
    private int levelColour;
  
    
    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
    	this.id = (byte) id;
    	if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
    	this.solid = isSolid;
    	this.emitter = isEmitter;
    	this.levelColour = levelColour;
    	tiles[id] = this;
    }
    
    public byte getId() {
    	return id;
    }
    
    public boolean isSolid() {
    	return solid;
    }
    
    public boolean isEmitter() {
    	return emitter;
    }
    
    public int getLevelColour() {
    	return levelColour;
    }
    
    public abstract void tick();
    
	public abstract void render(Screen screen, Level level, int x, int y);
		
	

}

