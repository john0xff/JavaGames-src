/*
 * Abstract snake class that describes the behavior of both the player
 * snake and the autonomous snake.
 */
package snakeapplication;

import java.util.*; 
import java.awt.*;
import javax.swing.*; 

public abstract class Snake
{
    private ArrayList<Point>coors;

    static int dim = 12;
    static int width = 768;
    static int height = 528;

    private char direc;
    private int score;
    private Boolean grow;

    private Color color;

    public Snake(Color clr, int shift, int length)
    {
       	coors = new ArrayList<Point>();
    	grow =  false;
        direc = 'd';

        color = clr;
        score = 0;
        
        coors.add(new Point(width/2-dim,
                             height/2+dim + shift*dim));
        for(int i=0; i<length-1; i++)
        {
        coors.add(new Point(width/2+i*dim,
                            height/2+ dim + shift*dim));
        }
    }
    
    //Move snake, used by both types of snake
    public void move()
    {
        Point[] shifted = new Point[coors.size()];
        Point[] pts = new Point[coors.size()];

        for(int i=0; i<pts.length; i++)
        {
            pts[i] = shifted[i] = (Point) coors.get(i);
        }

        if ( (coors.size()>1) )
        {
            for(int i=0; i<pts.length-2; i++)
            {
                shifted[i] = pts[i+1];
                coors.set(i, shifted[i]);
            }

            shifted[pts.length-2] = pts[pts.length-1];
            coors.set(pts.length-2, shifted[pts.length-2]);
        }

        Point tempP = pts[pts.length-1];


        addLastCoorToFront(tempP);

        endOfScreenAdjust();


        if (grow)
        {
            coors.add(0,pts[0]);
            grow=false;
        }
    }

    //Put the last coordinate to the front of the snake
    private void addLastCoorToFront(Point tempP)
    {
        switch(direc)
        {
            case 'a':
            {
                coors.set(coors.size()-1, new Point(tempP.x-dim, tempP.y));
                break;
            }
            case 'w':
            {
                coors.set(coors.size()-1, new Point(tempP.x, tempP.y-dim));
                break;
            }
            case 'd':
            {
                coors.set(coors.size()-1, new Point(tempP.x+dim, tempP.y));
                break;
            }
            case 's':
            {
                coors.set(coors.size() - 1,new Point(tempP.x, tempP.y+dim));
                break;
            }
            default:{ break; }
        }
    }

    //Determines what to do when the snake hits the endo of the screen; 
    //Either lose game or loop around, depending on user preference
    public void endOfScreenAdjust()
    {
        Point tempP;

        if (SnakeApplication.gamePanel.getOpenMap())
        {
            for(int i=0; i<coors.size(); i++)
            {
                tempP = (Point) coors.get(i);

                if (tempP.x + dim  > width){
                    tempP.x=0;
                }
                if (tempP.x < 0) {
                    tempP.x=width;
                }
                if (tempP.y < 0){
                    tempP.y = height;
                }
                if((tempP.y + dim > height) && (direc != 'w')){
                    tempP.y = 0;
                }
                coors.set(i, tempP);
            }
        }

        else
        {
            for(int i=0; i<coors.size(); i++)
            {
                tempP = (Point) coors.get(i);

                if (tempP.x + dim > width){
                    SnakeApplication.gamePanel.setStatus(GamePanel.Status.LOSE);
                }
                if (tempP.x < 0) {
                    SnakeApplication.gamePanel.setStatus(GamePanel.Status.LOSE);
                }
                if (tempP.y < 0){
                    SnakeApplication.gamePanel.setStatus(GamePanel.Status.LOSE);
                }
                if((tempP.y + dim > height) && (direc != 'w')){
                    SnakeApplication.gamePanel.setStatus(GamePanel.Status.LOSE);
                }
            }
        }
    }

    //Snake get and set methods for Snake variables
    public int getLength()
    {
        return coors.size(); 
    }

    public void setGrow(Boolean grow)
    {
        this.grow = grow; 
    }
    
    public char getDirection()
    {
    	return direc; 
    }
    
    public void setDirection(char d)
    {
    	direc = d; 
    }

    public ArrayList getCoorsList()
    {
        return coors;
    }

    public Point getCoor(int i)
    {
        return (Point) coors.get(i);
    }

    public Point getFrontCoor()
    {
        return coors.get(coors.size()-1);
    }

    public Color getColor()
    {
        return color; 
    }

    public void setColor(Color clr)
    {
        color = clr; 
    }

    abstract void update( PickUp[] pickups );
    
}