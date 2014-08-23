import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ball of the game. It moves and bounces off the walls and the paddle.
 * 
 * @author mik
 * @version 1.0
 */
public class Ball extends Actor
{
    private int deltaX;         // x movement speed
    private int deltaY;         // y movement speed
    private int count = 2;
    
    private boolean stuck = true;   // stuck to paddle
    
    public Ball()
    {
    }
    
    /**
     * Act. Move if we're not stuck.
     */
    public void act() 
    {
        if (!stuck) 
        {
            move();
            makeSmoke();
            checkOut();
        }
    }
    
    /**
     * Move the ball. Then check what we've hit.
     */
    public void move()
    {
        setLocation (getX() + deltaX, getY() + deltaY);
        checkPaddle();
        checkWalls();
    }
    
    /**
     * Check whether we've hit one of the three walls. Reverse direction if necessary.
     */
    private void checkWalls()
    {
        if (getX() == 0 || getX() == getWorld().getWidth()-1) {
            deltaX = -deltaX;
        }
        if (getY() == 0) {
            deltaY = -deltaY;
        }
    }
    
    /**
     * Check whether we're out (bottom of screen).
     */
    private void checkOut()
    {
        if (getY() == getWorld().getHeight()-1) {
            ((Board) getWorld()).ballIsOut();
            getWorld().removeObject(this);
        }
    }
    
    private void checkPaddle()
    {
        Actor paddle = getOneIntersectingObject(Paddle.class);
        if (paddle != null) {
            deltaY = -deltaY;
            int offset = getX() - paddle.getX();
            deltaX = deltaX + (offset/10);
            if (deltaX > 7) {
                deltaX = 7;
            }
            if (deltaX < -7) {
                deltaX = -7;
            }
            Board myBoard = (Board) getWorld();
            myBoard.score();
        }            
    }
    
    /**
     * Move the ball a given distance sideways.
     */
    public void move(int dist)
    {
        setLocation (getX() + dist, getY());
    }

    /**
     * Put out a puff of smoke (only on every second call).
     */
    private void makeSmoke()
    {
        count--;
        if (count == 0) {
            getWorld().addObject ( new Smoke(), getX(), getY());
            count = 2;
        }
    }
    
    /**
     * Release the ball from the paddle.
     */
    public void release()
    {
        deltaX = Greenfoot.getRandomNumber(11) - 5;
        deltaY = -5;
        stuck = false;
    }
}
