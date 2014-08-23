import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Smoke here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Smoke  extends Actor
{
   private GreenfootImage image;   // the original image
    private int fade;               // the rate of fading

    public Smoke()
    {
        image = getImage();
        fade = Greenfoot.getRandomNumber(4) + 1;  // 1 to 3
        if (fade > 3) {
          fade = fade - 2;
        }
    }
    
    /**
     * In every step, get smaller until we disappear.
     */
    public void act() 
    {
        shrink();
    }    
    
    private void shrink()
    {
        if(getImage().getWidth() < 10) {
            getWorld().removeObject(this);
        }
        else {
            GreenfootImage img = new GreenfootImage(image);
            img.scale ( getImage().getWidth()-fade, getImage().getHeight()-fade );
            setImage (img);
        }
    }
}
