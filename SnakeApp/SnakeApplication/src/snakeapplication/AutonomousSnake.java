/*
 * Concrete subclass of abstract class snake that
 * controls the behavior of the autonomous enemy snake
 */
package snakeapplication;

import java.awt.*; 

public class AutonomousSnake extends Snake 
{
    private int osfp;

    public AutonomousSnake(Color clr, int shift, int length)
    {
        super(clr, shift, length);

        osfp  = 0;
        setDirection('a');
    }

    //Override abstract update method to update
    //the Autonomous Snake appropriately
    @Override
    public void update(PickUp[] pickups)
    {
      osfp = findNearestPickUp( pickups );

      setDirection( getRobotDirection(pickups) );

      move(); 
    }
    
    //Get the direction for the snake to autonomously travel
    public char getRobotDirection(PickUp[] pickups)
    {
        PickUp pickUp = pickups[osfp];

        Point tempP = pickUp.getCoors();

        Point coor = (Point) getFrontCoor();

        if ( (tempP.x > coor.x) && (getDirection() != 'a'))
            return 'd';

        if ( (tempP.x < coor.x) && (getDirection() != 'd'))
            return 'a';

        if ( (tempP.y > coor.y) && (getDirection() != 'w'))
            return 's';

        if ( (tempP.y < coor.y) && (getDirection() != 's'))
            return 'w';

        if (( tempP.y == coor.y) && (getDirection() != 'w') && (getDirection() != 's'))
            return tempP.x>coor.x ? 'd':'a';

        if ( (tempP.x == coor.x) && (getDirection() != 'w') && (getDirection() != 's'))
            return tempP.y>coor.y ? 's':'w';

        else return getDirection();
    }

    //Find the nearest pick up,
    //returns the index of the nearest pickup in the pickups list
    public int findNearestPickUp( PickUp[] pickups)
    {
        Point headCoors = pickups[pickups.length-1].getCoors();

        float[] dists  = new float[pickups.length];

        for(int i=0; i< pickups.length; i++)
        {
            PickUp tempP = pickups[i];
            Point temp = tempP.getCoors();

            dists[i] = (float) Math.sqrt(Math.pow((temp.x-headCoors.x), 2)+
                               Math.pow((temp.y-headCoors.y), 2));
        }

        for(int i=1; i<dists.length; i++)
        {
            if ( (dists[i-1] > dists[i]) && (dists[i] < dists[osfp]) )
                osfp = i;
        }

        return osfp;
    }
}
