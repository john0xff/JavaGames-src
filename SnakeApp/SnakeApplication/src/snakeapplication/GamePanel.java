/*
 * This panel contains the game animation and all the game elements
 * it extends ApplicationPanel because it is a main content panel
 */
package snakeapplication;

import javax.swing.*; 
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends ApplicationPanel implements Runnable
{
    public ScoreEntry userScoreEntry;

    public static int dim = 12;

    private boolean isOpenMap;

    public static String[] difficulties = {"Easy", "Normal", "Hard", "Extreme"};
    
    public enum Difficulty {EASY, NORM, HARD, EXTREME};
    private Difficulty difficulty;


    public static PlayerSnake     player;
    public static AutonomousSnake computer;

    private static int level = 1;

    public enum Status {PLAY, LOSE, PAUSED, START};
    private Status status;

    private int checkFrame = 0;
    private int frameCount = 0;

    private Thread animator;
    private int delay;

    private ArrayList<PickUp> pickUps; 
    private PickUp[] pickups;
	
    
    public GamePanel(Color clr)
    {
        super(clr);
        
        setLayout(null);
        
        animator = null;

        isOpenMap = true;

        difficulty = Difficulty.NORM;

        resetGameValues();
    }

    //This method does the job of the constructor,
    //But it ensures that more than one animation
    //thread is not created
    public void resetGameValues()
    {
        if (difficulty == Difficulty.EASY)
            delay = 90;
        if (difficulty == Difficulty.NORM)
            delay = 60;
        if (difficulty == Difficulty.HARD)
            delay = 40;
        if (difficulty == Difficulty.EXTREME)
            delay = 20;

        if (animator != null)
            animator.interrupt();
        
        status = Status.START;
        
        level = 1;         
        
        player =   new PlayerSnake(SnakeApplication.settingsPanel.getPlayerClr(), 0, 5);
        computer = new AutonomousSnake(SnakeApplication.settingsPanel.getEnemyClr(), 4, this.level);

        pickUps = new ArrayList();

        for(int i=0; i<10; i++)
        {
            pickUps.add(new PickUp(SnakeApplication.settingsPanel.getPickUpClr()));
        }

        startThread();
    }

    //Start animator thread
    public void startThread()
    {
        animator = new Thread(this);
        animator.start();
    }

    //Main animation thread of the animator thread,
    //Perfoerms the actual game play
    @Override
    public void run()
    {
        while (status != Status.LOSE)
        {
            if( (status != Status.START) && (status != Status.PAUSED) )
            {
                player.update( getPickUps(pickUps) );

                if (frameCount % 1 == 0)
                    computer.update( getPickUps(pickUps) );

                updatePickUpList( getPickUps (pickUps) );

                checkForSnakeCollisions();
            }

            repaint();

            try {
            Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                break;
            }
            frameCount++;
        }

        if (status == status.LOSE)
        {
            String name = JOptionPane.showInputDialog(this, "    Game Over\nScore:  " +
                                      player.getScore() + "\nLevel:  "+level);
            
            userScoreEntry = new ScoreEntry(    player.getScore(),
                                                level,
                                                name,
                                                isOpenMap,
              difficulties[SnakeApplication.settingsPanel.difficultySetter.getSelectedIndex()]
            );

            SnakeApplication.scoresPanel.scoreEntries.add(userScoreEntry);

            SnakeApplication.scoresPanel.updateScore();

            SnakeApplication.setOthersInactive(SnakeApplication.scoresPanel);
        }
    }

    //Check collisions between the player and the snake's head, 
    //Check collisions between the player's head and the snake, 
    //Check collisions between the player's head and himself
    public void checkForSnakeCollisions()
    {
        Point tempP1 = player.getFrontCoor();
        Point tempA1 = computer.getFrontCoor();

        for(int j = player.getLength()-1; j>=0; j--)
        {
            Point tempP2 = player.getCoor(j);

            for (int i = computer.getLength()-1; i>=0; i--)
            {
                Point tempPA = computer.getCoor(i);

                if ((tempP1.x == tempPA.x) && (tempP1.y == tempPA.y))
                {
                    status = Status.LOSE;
                    break;
                }
            }
            if ( (tempA1.x == tempP2.x) && (tempA1.y == tempP2.y) )
            {
                for(int k=0; k<player.getCoorsList().size()-j; k++)
                {
                    player.getCoorsList().remove(k);
                }
                repaint();
            }
            if (j < player.getLength() - 2)
            {
                if ((tempP2.x == tempP1.x) && (tempP2.y == tempP1.y))
                {
                    status = Status.LOSE; 
                }
            }
        }
    }


    //Call private method to check for snake-pickup collisions,
    //then update the snakes appropriately;
    //It also adds pickups where appropriate
    private void updatePickUpList(PickUp[] pickups)
    {
        checkPickUpCollisions(pickups);

        for(int i=0; i<pickups.length; i++)
        {
            if (pickups[i].getStatus() == PickUp.Status.P_HIT)
            {
                player.setGrow(true);

                player.setScore(player.getScore()+75);

                if (computer.getCoorsList().size()>1)
                {
                    computer.getCoorsList().remove(0);
                }
                else
                {
                    level++;
                    computer = new AutonomousSnake(SnakeApplication.settingsPanel.getEnemyClr(), 4, this.level);
                }
            }

            if (pickups[i].getStatus() == PickUp.Status.E_HIT)
            {
                computer.setGrow(true);

                if (player.getCoorsList().size()>1)
                {
                    player.getCoorsList().remove(0);
                }
                else
                {
                    status = Status.LOSE;
                }
            }

            if ((pickups[i].getStatus() == PickUp.Status.E_HIT) ||
                (pickups[i].getStatus() == PickUp.Status.P_HIT) )
                pickUps.remove(i);
            
            if (pickUps.size()<10)
            {
                addPickUp();
            }
        }
    }
    
    //Check for collisions between snakes and pick ups
    private void checkPickUpCollisions(PickUp[] pickups)
    {
        Point tempP;

        for(int i=0; i<pickups.length; i++)
        {
            tempP = player.getFrontCoor();

            Point pCoors = pickups[i].getCoors();

            if ( isColliding(tempP, pCoors) )
            {
                pickups[i].setStatus(PickUp.Status.P_HIT);
            }

            tempP = computer.getFrontCoor();

            if ( isColliding(tempP, pCoors) )
            {
                pickups[i].setStatus(PickUp.Status.E_HIT);
            }
        }
    }

    //General hit detection for two points in the game grid
    //Used to check collisions between pick ups and snakes in the
    //checkPickUpCollisions method, which is then used in the 
    //updatePickUpList method
    private Boolean isColliding(Point tempP, Point pCoors)
    {
        if (((tempP.x +dim >= pCoors.x) &&
             (tempP.x <= pCoors.x+dim)  &&
             (tempP.y + dim >= pCoors.y)&&
             (tempP.y <= pCoors.y+dim)) &&
           (((tempP.x == pCoors.x) &&
             (tempP.y == pCoors.y)) ||
            ((tempP.x == pCoors.x) &&
             (tempP.y == pCoors.y +dim-1))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Add pick up in a place where there is not one already
    private void addPickUp()
    {
        pickUps.add(0, new PickUp(SnakeApplication.settingsPanel.getPickUpClr()));
        PickUp tempP = (PickUp) pickUps.get(0);

        while ( coorIsFull(tempP.getCoors()) )
        {
            pickUps.set(0, new PickUp(SnakeApplication.settingsPanel.getPickUpClr()));
            tempP = pickUps.get(0);
        }
    }


    //Determine if there is a pick or a snake in a point on the grid,
    //used to make sure no pick up is added where there is something already
    private Boolean coorIsFull(Point v)
    {
        for(int i=0; i<player.getCoorsList().size(); i++)
        {
            Point tempJ = (Point) player.getCoorsList().get(i);
            if ( (tempJ.x == v.x) && (tempJ.y == v.y))
            {
                return true;
            }
        }
        for(int i=1; i<pickUps.size(); i++)
        {
            PickUp tempJ = (PickUp) pickUps.get(i);
            if ( (tempJ.getCoors().x == v.x) && (tempJ.getCoors().y == v.y) )
            {
                return true;
            }
        }
        return false;
    }

    //Performs the same function as the Collections.toArray() method, 
    //But I decided to just use this instead. 
    public PickUp[] getPickUps (ArrayList list)
    {
    	PickUp[] pickups = new PickUp[list.size()];

    	for(int i=0; i<list.size(); i++)
    		pickups[i] = (PickUp) list.get(i);

    	return pickups;
    }
    
    //Overridden paintComponent method for the gamePanel, 
    //Draws all the graphics, updates everything that is drawn, 
    //and does so for whichever status the game has
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.black);
        g2d.drawString("Score: "+player.getScore(), dim, dim*2);
        g2d.drawString("Level: "+level, dim, dim*4);

        Snake[] snakes = new Snake[2];
        snakes[0] = player;
        snakes[1] = computer; 

        for(int j=0; j<snakes.length; j++)
        {
             for(int i=0; i<snakes[j].getCoorsList().size(); i++)
             {
                Point tempP = (Point) snakes[j].getCoor(i);

                g2d.setColor(snakes[j].getColor());
                g2d.fillRect(tempP.x, tempP.y, Snake.dim-1, Snake.dim-1);

                g2d.setColor(Color.black);
                g2d.drawRect(tempP.x, tempP.y, Snake.dim, Snake.dim);
             }
        }

        pickups = getPickUps(pickUps);

        for(int i=0; i<pickups.length; i++)
        {
            if (pickups[i].getStatus() == PickUp.Status.NORMAL)
            {
                g2d.setColor(pickups[i].getColor());
                g2d.fillRect(pickups[i].getCoors().x, pickups[i].getCoors().y, Snake.dim-1, Snake.dim-1);

                g2d.setColor(Color.black);
                g2d.drawRect(pickups[i].getCoors().x, pickups[i].getCoors().y, Snake.dim-1, Snake.dim-1);
            }
        }

        if (status != Status.PLAY)
        {
           g2d.setColor(Color.black);
           g2d.drawRect(width/4, height/4,
                        width/2+1, height/8+1);
           g2d.drawRect(width/4+1, height/4-1,
                        width/2+1, height/8-1);

           g2d.setColor(Color.lightGray);
           g2d.fillRect(width/4+1, height/4+1,
                        width/2, height/8);

           g2d.setColor(Color.black);

           if (status == Status.START)
           {
           g2d.drawString("Press Any Key to Begin ", width/2-65, width/4-20);
            }

           if (status == Status.PAUSED)
           {
                g2d.drawString("PAUSED", width/2-2*dim, width/4-2*dim);
           }

           if (status == Status.LOSE)
           {
               g2d.drawString("GAME OVER", width/2-2*dim-dim/2, width/4-2*dim);
           }
        }
    }

    //Get and set Methods for GamePanel variables
    
    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public int getCheckFrame()
    {
        return checkFrame; 
    }

    public void setCheckFrame(int frame)
    {
        checkFrame = frame;
    }

    public int getFrameCount()
    {
        return frameCount; 
    }

    public PlayerSnake getPlayer()
    {
        return player; 
    }

    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    public void setOpenMap(boolean b)
    {
        isOpenMap = b; 
    }

    public boolean getOpenMap()
    {
        return isOpenMap; 
    }
}
