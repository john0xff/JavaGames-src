/*
 * Controls the behavior of the player's snake, distinct from the 
 * autonomous one. 
 */
package snakeapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

/**
 *
 * @author 14svitali
 */
public class PlayerSnake extends Snake
{
    private int score;

    public PlayerSnake(Color clr, int shift, int length)
    {
        super(clr, shift, length);

        score = 0;
    }
    
    //overridden abstract method update(PickUp) to update player
    @Override
    public void update( PickUp[] pickups)
    {
        move();
    }

    //Get and set score methods
    public void setScore(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }
}
