/*
 * Class for all pick up-able items
 */
package snakeapplication;

import java.awt.*;
import java.util.Random;

public class PickUp
{
    private Point coors; 
    private Color color;
    
    static enum Status {NORMAL, P_HIT, E_HIT};
    private Status status; 
    
    PickUp(Color clr)
    {
        color = clr; 
        status = Status.NORMAL;

        int[] tempR = { new Random().nextInt(GamePanel.width -GamePanel.dim*2),
                        new Random().nextInt(GamePanel.height-GamePanel.dim*2)};

        coors = new Point(tempR[0]-(tempR[0]%(GamePanel.dim)),
                          tempR[1]- tempR[1]%(GamePanel.dim));
    }

    //Get and set methods for PickUp instance variables
    public Point getCoors()
    {
        return coors; 
    }

    public Status getStatus()
    {
       return status;
    }

    public void setStatus(Status status)
    {
        this.status = status; 
    }

    public Color getColor()
    {
        return color; 
    }
}