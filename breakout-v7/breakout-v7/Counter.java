import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * Write a description of class Counter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Counter extends Actor
{
    private int score;
    
    public Counter()
    {
        score = 0;
        setImage (new GreenfootImage(200, 30));
        update();
    }
    
    public void addScore()
    {
        score++;
        update();
    }
    
    private void update()
    {
        GreenfootImage img = getImage();
        img.clear();
        img.setColor(Color.BLACK);
        img.setFont(img.getFont().deriveFont(Font.BOLD));
        img.setFont(img.getFont().deriveFont(16.0F));
        img.drawString("Score: " + score, 4, 20);
    }
}
