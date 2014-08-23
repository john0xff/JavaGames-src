import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The game paddle. It is keyboard controlled (left, right, space). It also 
 * generates a new ball when it is created itself.
 * 
 * @author mik
 * @version 1.1
 */
public class Paddle extends Actor
{
    private Ball myBall;  // used before ball gets released

    public Paddle()
    {
    }
    
    /**
     * When the paddle gets created, create a ball as well.
     */
    public void addedToWorld(World world)
    {
        newBall();
    }
    
    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.isKeyDown ("left")) {
            moveSideways(-9);
        }
        if (Greenfoot.isKeyDown ("right")) {
            moveSideways(9);
        }
        if (haveBall() && Greenfoot.isKeyDown ("space")) {
            releaseBall();
        }
        
    }
    
    private void moveSideways(int dist)
    {
        setLocation (getX() + dist, getY());
        if (myBall != null) {
            myBall.move (dist);
        }
    }
    
    public void newBall()
    {
        myBall = new Ball();
        getWorld().addObject (myBall, getX(), getY()-20);
    }
        
    public boolean haveBall()
    {
        return myBall != null;
    }
        
    public void releaseBall()
    {
        myBall.release();
        myBall = null;
    }
        
}
