import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * The game board. The board had a paddle that can move.
 * 
 * @author mik
 * @version 1.0
 */
public class Board extends World
{
    private static final int GAP = 12;

    private Paddle paddle;
    private Counter counter;

    /**
     * Constructor for objects of class Board.
     * 
     */
    public Board()
    {    
        super(460, 460, 1);
        setPaintOrder ( Ball.class, Smoke.class );

        counter = new Counter();
        addObject (counter, 113, 440);

        paddle = new Paddle();
        addObject ( paddle, getWidth() / 2, getHeight() - 40);

        createBlocks();
        drawRandomCircles(20);
    }

    public void drawRandomCircles(int howMany)
    {
        int i = 0;
        while (i < howMany) 
        {
            drawCircle();
            i = i+1;
        }
    }
    
    /**
     * Draw a circle with random size and colour.
     */
    public void drawCircle()    
    {
        GreenfootImage image = getBackground();
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());
        int r = Greenfoot.getRandomNumber(256);
        int g = Greenfoot.getRandomNumber(256);
        int b = Greenfoot.getRandomNumber(256);
        Color color = new Color (r, g, b, 20);
        int size = Greenfoot.getRandomNumber(100) + 50;
        image.setColor(color);
        image.fillOval(x, y, size, size);
    }
    
    /**
     * Create the blocks at the top of the world.
     */
    private void createBlocks()
    {
        int y = 30;

        while ( y <= 94 ) {
            createRow(y);
            y = y + 20 + GAP;
        }
    }

    private void createRow(int y)
    {
        int x = 50;
        while ( x < 460 ) 
        {
            addObject( new Block(), x, y);
            x = x + 60 + GAP;
        }
    }

    public void ballIsOut()
    {
        paddle.newBall();
    }

    public void score()
    {
        counter.addScore();
    }
}
