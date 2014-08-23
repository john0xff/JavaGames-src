/*
 * This class creates the application's menu bar and its functionality.
 */
package snakeapplication;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class TopMenuBarPanel extends JMenuBar
{
    JMenu file;
        JMenuItem quit;
        JMenuItem newGame;
        JMenuItem highScores;

    JMenu preferences;
        JMenu difficulty;
        JMenu mapType;
        JMenu setColors;
            JMenuItem[] colorEdits;
        JMenuItem more;

    JMenu help;
        JMenuItem howToPlay;

    JRadioButtonMenuItem[] difficulties;
    ButtonGroup difButtonGroup;

    JRadioButtonMenuItem[] mapOptions;
    ButtonGroup mapButtons; 

    String[] difs = {"Easy", "Normal", "Hard", "Extreme"};
    String[] maps = {"Closed", "Open"                   };
    String[] clrs = {"Player", "Enemy", "Pick-ups"};
    
    TopMenuBarPanel()
    {
        ItemHandler itemHandler = new ItemHandler();

        file = new JMenu("File");
        help = new JMenu("Help");
        preferences = new JMenu("Preferences");

        newGame = new JMenuItem("New Game");
        highScores = new JMenuItem("High Scores");
        quit = new JMenuItem("Quit");

        newGame.addActionListener(itemHandler);
        highScores.addActionListener(itemHandler);
        quit.addActionListener(itemHandler);

        file.add(newGame);
        file.add(highScores);
        file.addSeparator();
        file.add(quit);

        mapType     = new JMenu("Map Type");
        setColors   = new JMenu("Change Colors");
        more        = new JMenuItem("More...");
        difficulty  = new JMenu("Difficulty");
        
        difButtonGroup = new ButtonGroup();

        difficulties = new JRadioButtonMenuItem[difs.length];

        for(int i=0; i<difs.length; i++)
        {
            difficulties[i] = new JRadioButtonMenuItem( difs[i] );
            difficulty.add(difficulties[i]);
            difButtonGroup.add(difficulties[i]);
            difficulties[i].addActionListener(itemHandler);
        }

        colorEdits = new JMenuItem[clrs.length];

        for(int i=0; i<clrs.length; i++)
        {
            colorEdits[i] = new JMenuItem( clrs[i] );
            setColors.add(colorEdits[i]);
            colorEdits[i].addActionListener(itemHandler);
        }

        mapOptions = new JRadioButtonMenuItem[maps.length];
        mapButtons = new ButtonGroup();

        for(int i=0; i<maps.length; i++)
        {
            mapOptions[i] = new JRadioButtonMenuItem( maps[i] );
            mapType.add(mapOptions[i]);
            mapButtons.add(mapOptions[i]);
            mapOptions[i].addActionListener(itemHandler);
        }

        preferences.add(difficulty);
        preferences.add(mapType);
        preferences.add(setColors);
        preferences.addSeparator();
        preferences.add(more);
        more.addActionListener(itemHandler);

        howToPlay = new JMenuItem("How to Play...");
        howToPlay.addActionListener(itemHandler);

        help.add(howToPlay); 

        add(file);
        add(preferences);
        add(help);
    }

    //Handle the menu bar item actions
    private class ItemHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if ( event.getSource() == mapOptions[0] )
            {
                SnakeApplication.gamePanel.setOpenMap(false);
                SnakeApplication.settingsPanel.closed.setSelected(true);
                SnakeApplication.gamePanel.resetGameValues();
            }
            if ( event.getSource() == mapOptions[1] )
            {
                SnakeApplication.gamePanel.setOpenMap(true);
                SnakeApplication.settingsPanel.open.setSelected(true);
                SnakeApplication.gamePanel.resetGameValues();
            }
            if (event.getSource() == colorEdits[0] )
            {
                SnakeApplication.settingsPanel.setPlayerColor( JColorChooser.showDialog(Main.game, "Choose a color", SnakeApplication.settingsPanel.getPlayerColor()));
                SnakeApplication.gamePanel.player.setColor(SnakeApplication.settingsPanel.getPlayerColor());
                SnakeApplication.settingsPanel.showPlayerColor.setBackground(SnakeApplication.settingsPanel.getPlayerColor());
                SnakeApplication.gamePanel.resetGameValues();
            }

            if (event.getSource() == colorEdits[1] )
            {
                SnakeApplication.settingsPanel.setEnemyColor( JColorChooser.showDialog(Main.game, "Choose a color", SnakeApplication.settingsPanel.getEnemyColor()));
                SnakeApplication.gamePanel.computer.setColor(SnakeApplication.settingsPanel.getEnemyColor());
                SnakeApplication.settingsPanel.showEnemyColor.setBackground(SnakeApplication.settingsPanel.getEnemyColor());
                SnakeApplication.gamePanel.resetGameValues();
            }

            if (event.getSource() == colorEdits[2] )
            {
                SnakeApplication.settingsPanel.setPickUpColor(JColorChooser.showDialog(Main.game, "Choose a color", SnakeApplication.settingsPanel.getPickUpColor()));
                SnakeApplication.settingsPanel.showPickUpColor.setBackground(SnakeApplication.settingsPanel.getPickUpClr());
                SnakeApplication.gamePanel.resetGameValues();            
            }

            for(int i=0; i<difficulties.length; i++)
            {
                if (event.getSource() == difficulties[i] )
                {
                    SnakeApplication.settingsPanel.setDifficultyToGamePanel(i);
                    SnakeApplication.settingsPanel.difficultySetter.setSelectedIndex(i);
                    SnakeApplication.gamePanel.resetGameValues();
                }
            }

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

            if ( event.getSource() == howToPlay )
            {
                JOptionPane.showMessageDialog(SnakeApplication.gamePanel, "Controls:"+
                            "Use the Arrow Keys to Guide your Snake\n\nObjective: Gather pick-ups" +
                            " to defend against the enemy snake." );
            }

            if (event.getSource() ==  more )
            {
                 SnakeApplication.setOthersInactive(SnakeApplication.settingsPanel);
            }

            if (event.getSource() ==  highScores )
            {
                SnakeApplication.setOthersInactive(SnakeApplication.scoresPanel);
            }

            if ( (event.getSource() == quit) )
            {
                Main.game.dispose();
                System.exit(0);
            }
            
            repaint();
            setFocusable(false);
        }
    }
}