/*
 * Main class of full-fledged Snake Game Application, contains all elements.
 */
package snakeapplication;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class SnakeApplication extends JFrame implements KeyListener
{
    static GamePanel          gamePanel;
    static HighScoresPanel    scoresPanel;
    static SettingsPanel      settingsPanel;

    static JPanel             mainPanel;

    static ApplicationPanel[] contentPanels;
    static String[]           contentNames;

    static SideNavigationBar  sideNavigation;
    static TopMenuBarPanel    menuBar;

  
    public SnakeApplication()
    {
        scoresPanel    = new HighScoresPanel(Color.darkGray);
        settingsPanel  = new SettingsPanel(Color.white);
        gamePanel      = new GamePanel(Color.gray);
        
        contentPanels = new ApplicationPanel[3];
        contentNames  = new String[3];

        contentPanels[0] = gamePanel; 
        contentPanels[1] = scoresPanel; 
        contentPanels[2] = settingsPanel;

        contentNames[0] = "1";
        contentNames[1] = "2";
        contentNames[2] = "3";

        mainPanel = new JPanel();
        CardLayout cards = new CardLayout();
        mainPanel.setLayout(cards);

        for(int i=0; i<3; i++)
            mainPanel.add(contentPanels[i], contentNames[i]);

        sideNavigation = new SideNavigationBar();
        menuBar        = new TopMenuBarPanel();

        setLayout(new BorderLayout(0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(902, 572);
        
        add(mainPanel, BorderLayout.CENTER);
        add(sideNavigation, BorderLayout.WEST);
        setJMenuBar(menuBar);

        setOthersInactive(gamePanel);
        gamePanel.resetGameValues();

        setFocusable(true);
        addKeyListener(this); 

        setVisible(true); 
    }

    //Set all the other panels inactive and activate the parameter panel
    //Used to handle the cardlayout between separate panels
    //Probably wasn't the best idea, but that's all I knew to do when 
    //I'd first written the program. 
    public static void setOthersInactive(ApplicationPanel panel)
    {
        for (int i=0; i<contentPanels.length; i++)
        {
            contentPanels[i].setActive (false);
            contentPanels[i].setVisible(false);
        }
        panel.setActive(true);
        panel.setVisible(true);
    }

    //Handle keypresses in application
    @Override
    public void keyPressed (KeyEvent e)
    {
        if (gamePanel.getCheckFrame() != gamePanel.getFrameCount())
        {
            if (e.getKeyCode()==KeyEvent.VK_UP)
            {
                if (gamePanel.getPlayer().getDirection() != 's')
                    gamePanel.getPlayer().setDirection('w');
            }

            if (e.getKeyCode()==KeyEvent.VK_LEFT)
            {
                if (gamePanel.getPlayer().getDirection() != 'd')
                    gamePanel.getPlayer().setDirection('a');
            }

            if (e.getKeyCode()==KeyEvent.VK_DOWN)
            {
                if (gamePanel.getPlayer().getDirection() != 'w')
                    gamePanel.getPlayer().setDirection('s');
            }

            if (e.getKeyCode()==KeyEvent.VK_RIGHT)
            {
                if (gamePanel.getPlayer().getDirection() != 'a')
                    gamePanel.getPlayer().setDirection('d');
            }

            System.out.println("dir: "+gamePanel.getPlayer().getDirection());
        }
        
        if ( (gamePanel.getActive() == true) &&
             (e.getKeyCode()==KeyEvent.VK_P) )
        {
            if (gamePanel.getStatus() == GamePanel.Status.PAUSED)
            {
                gamePanel.setStatus(GamePanel.Status.PLAY);
            }
            else
            {
                gamePanel.setStatus(GamePanel.Status.PAUSED);
            }
        }

        if (gamePanel.getStatus() == GamePanel.Status.START){
            gamePanel.setStatus(     GamePanel.Status.PLAY);
        }
        
        gamePanel.setCheckFrame(gamePanel.getFrameCount());
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e)    { }
}