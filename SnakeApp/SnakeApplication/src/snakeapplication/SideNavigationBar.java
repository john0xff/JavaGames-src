/*
 * This class contains the GUI elements and functionality of the
 * right side navigation bar.
 */
package snakeapplication;

import javax.swing.*; 
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class SideNavigationBar extends JPanel 
{
    private NavButton newGame;
    private NavButton pause;
    private NavButton highScores;
    private NavButton settings;
    private NavButton quit;

    private NavButton[] buttons;

    SideNavigationBar()
    {
        setLayout(new GridLayout(5, 1));
        setBackground(Color.gray);
        
        newGame    = new NavButton("New Game");
        highScores = new NavButton("High Scores");
        settings   = new NavButton("Game Settings");
        pause      = new NavButton("Pause");
        quit       = new NavButton("Quit");

        add(newGame);
        add(highScores);
        add(settings);
        add(pause);
        add(quit);

        buttons = new NavButton[5];
        buttons[0] = newGame;
        buttons[1] = highScores;
        buttons[2] = pause;
        buttons[3] = settings;
        buttons[4] = quit;
    }

    //;private Shortcut class for the navButtons in the side Navigation
    private class NavButton extends JButton
    {
        public NavButton(String text)
        {
            super(text);
            addActionListener(new ButtonHandler());
        }
    }

    //Handles buttons in the side navigation
    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent event)
        {
            if ( (event.getSource() == newGame) )
            {
                if(SnakeApplication.gamePanel.getStatus()==GamePanel.Status.PAUSED)
                {
                    SnakeApplication.gamePanel.resetGameValues();
                }

                if ( (SnakeApplication.gamePanel.getActive()==false) )
                {
                    SnakeApplication.setOthersInactive(SnakeApplication.gamePanel);
                    SnakeApplication.gamePanel.resetGameValues();
                }
            }

            if ( (event.getSource() == pause ) )
            {
                if (SnakeApplication.gamePanel.getActive()==true)
                {
                    if(SnakeApplication.gamePanel.getStatus()==GamePanel.Status.PAUSED)
                    {
                        SnakeApplication.gamePanel.setStatus(GamePanel.Status.PLAY);
                    }
                    else
                    {
                        SnakeApplication.gamePanel.setStatus(GamePanel.Status.PAUSED);
                    }
                }
            }

            if ( (event.getSource() == highScores) )
            {
                SnakeApplication.setOthersInactive(SnakeApplication.scoresPanel);
            }

            if ( (event.getSource() == settings) )
            {
                SnakeApplication.setOthersInactive(SnakeApplication.settingsPanel);
            }

            if ( (event.getSource() == quit) )
            {
                Main.game.dispose();
                System.exit(0); 
            }

            repaint();
            setFocusNavigationBar(false);
        }
    }

    //Set the navigation bar's focus to focusable or not
    //to avoid focus problems between panels/elements
    public void setFocusNavigationBar(Boolean b)
    {
        for(int i=0; i<buttons.length; i++)
        {
            buttons[i].setFocusable(b);
        }
    }
}
