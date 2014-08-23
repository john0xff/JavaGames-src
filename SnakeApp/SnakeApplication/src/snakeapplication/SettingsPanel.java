/*
 * This panel sends and receieves data from the GamePanel panel
 * to set gamePanel's settings. 
 */

package snakeapplication;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class SettingsPanel extends ApplicationPanel
{
    private Color backgroundClr;

    private Color playerClr;
    private Color enemyClr;
    private Color pickUpClr;

    private JButton playerButton;
    private JButton enemyButton;
    private JButton pickUpButton;

    public ContentPanel showPlayerColor;
    public ContentPanel showEnemyColor;
    public ContentPanel showPickUpColor;

    private ContentPanel difficultyPanel;
    private ContentPanel borderSetPanel;

    private ContentPanel colorSettings;

    public static JComboBox difficultySetter;

    public static ButtonGroup borders;
    public static JRadioButton closed;
    public static JRadioButton open;

    private JPanel content;


    public SettingsPanel(Color clr)
    {
        super(clr);
        this.setLayout(new BorderLayout());

        content = new JPanel();
        content.setLayout(new GridLayout(0,1,10,10));

        playerClr = Color.darkGray;
        enemyClr = Color.blue;
        pickUpClr = Color.red;

        playerButton = new JButton("Change Player 1 Color");
        enemyButton  = new JButton("Change Enemy Color");
        pickUpButton = new JButton("Change Pick-Up Color");

        ButtonHandler handler = new ButtonHandler();
        playerButton.addActionListener(handler);
        enemyButton.addActionListener(handler);
        pickUpButton.addActionListener(handler);

        showPlayerColor =  new ContentPanel(playerClr);
        showEnemyColor  = new ContentPanel(enemyClr);
        showPickUpColor = new ContentPanel(pickUpClr);

        colorSettings   = new ContentPanel(Color.lightGray);

        colorSettings.setLayout(new GridLayout(3, 2, 4, 4));

        colorSettings.add(playerButton);
        colorSettings.add(showPlayerColor);

        colorSettings.add(enemyButton);
        colorSettings.add(showEnemyColor);

        colorSettings.add(pickUpButton);
        colorSettings.add(showPickUpColor);

        content.add(colorSettings);


        difficultyPanel = new ContentPanel(Color.lightGray);
        difficultyPanel.setLayout(new FlowLayout());

        JTextField difficultyText = new JTextField("Change Game Difficulty: ");
        difficultyText.setFont(new Font("Helvetica",Font.PLAIN, 20));
        difficultyText.setEditable(false);
        
        difficultyPanel.add(difficultyText);

        difficultySetter = new JComboBox(SnakeApplication.gamePanel.difficulties);
        difficultySetter.setFont( new Font("Helvetica", Font.PLAIN, 20) );
        difficultySetter.setSelectedIndex(1);
        difficultySetter.setSize(256, 256);

        difficultySetter.addItemListener (
            new ItemListener()
            {
                public void itemStateChanged(ItemEvent event)
                {
                    if (event.getStateChange() == ItemEvent.SELECTED)
                    {
                        setDifficultyToGamePanel(difficultySetter.getSelectedIndex());
                    }
                }
            }
        );

        difficultyPanel.add(difficultySetter);
        difficultyPanel.add(new JLabel());
        difficultyPanel.add(new JLabel());

        borderSetPanel  = new ContentPanel(Color.lightGray);
        borderSetPanel.setLayout(new FlowLayout());

        JTextField bordersText = new JTextField("Closed or Open borders: ");
        bordersText.setFont(new Font("Helvetica", Font.PLAIN, 20));
        bordersText.setEditable(false);


        closed = new JRadioButton("Closed", false);
        open   = new JRadioButton("Open"  , true );

        closed.setFont(new Font("Helvetica", Font.PLAIN, 20));
        open.setFont(new Font("Helvetica", Font.PLAIN, 20));

        RadioButtonHandler radioHandler = new RadioButtonHandler();
        closed.addItemListener(radioHandler);
        open  .addItemListener(radioHandler);

        borders = new ButtonGroup();
        borders.add(open);
        borders.add(closed);

        borderSetPanel.add(bordersText);
        borderSetPanel.add(closed);
        borderSetPanel.add(open);


        content.add(difficultyPanel);
        content.add(borderSetPanel );

        add(content, BorderLayout.CENTER);
    }

    //Get and set methods for Settings variables
    public Color getPlayerClr()
    {
        return playerClr;
    }

    public Color getEnemyClr()
    {
        return enemyClr; 
    }

    public Color getPickUpClr()
    {
        return pickUpClr;
    }

    public static void setDifficultyToGamePanel(int i)
    {
        SnakeApplication.gamePanel.setDifficulty( getDifficulty(i) );
    }

    public static GamePanel.Difficulty getDifficulty(int i)
    {
        if (i==0)
            return GamePanel.Difficulty.EASY;
        if (i==1)
            return GamePanel.Difficulty.NORM;
        if (i==2)
            return GamePanel.Difficulty.HARD;
        if (i==3)
            return GamePanel.Difficulty.EXTREME;
        return null;
    }

    private String getGameDifficultyDescription(int i)
    {
        return ""; 
    }

    public void setPlayerColor(Color clr)
    {
        playerClr = clr;
    }

    public void setEnemyColor(Color clr)
    {
        enemyClr = clr;
    }

    public void setPickUpColor(Color clr)
    {
        pickUpClr = clr;
    }

    public Color getPlayerColor()
    {
        return playerClr;
    }

    public Color getEnemyColor()
    {
        return enemyClr;
    }

    public Color getPickUpColor()
    {
        return pickUpClr;
    }
    
    //Overridden paintComponent to draw the settings panel
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 768, 512);
    }

    //Button handler for settings buttons
    private class ButtonHandler implements ActionListener
    {
        //Action performed to describe button behaviors
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == playerButton)
            {
                playerClr = JColorChooser.showDialog(colorSettings, "Choose a color", playerClr);
                SnakeApplication.gamePanel.player.setColor(playerClr);
                showPlayerColor.setBackground(playerClr);
            }

            if (event.getSource() == enemyButton)
            {
                enemyClr = JColorChooser.showDialog(colorSettings, "Choose a color", enemyClr);
                SnakeApplication.gamePanel.computer.setColor(enemyClr);
                showEnemyColor.setBackground(enemyClr);
            }

            if (event.getSource() == pickUpButton)
            {
                pickUpClr = JColorChooser.showDialog(colorSettings, "Choose a color", pickUpClr);
                showPickUpColor.setBackground(pickUpClr);
            }
            
            repaint();
        }
    }

    //Handler for the radio buttons in Settings
    private class RadioButtonHandler implements ItemListener
    {
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getSource() == closed)
            {
                SnakeApplication.gamePanel.setOpenMap(false);
            }
            if (event.getSource() == open)
            {
                SnakeApplication.gamePanel.setOpenMap(true);
            }
        }
    }
}