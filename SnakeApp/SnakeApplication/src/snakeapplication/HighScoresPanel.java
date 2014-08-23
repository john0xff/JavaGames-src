/*
 * This class describes the contents and behavior of the high scores screen
 */

package snakeapplication;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class HighScoresPanel extends ApplicationPanel
{
    ArrayList scoreEntries;

    JPanel titlePanel;
    
    JPanel listContainer;
    JPanel nameContainer;
    JPanel scoreContainer;
    JPanel levelContainer;
    JPanel diffContainer;
    JPanel borderContainer;

    JPanel sortPanel;

    public HighScoresPanel(Color clr)
    {
        super(clr);

        setLayout(new BorderLayout());

        scoreEntries = new ArrayList();

        titlePanel     = new JPanel();
        nameContainer  = new JPanel();
        scoreContainer = new JPanel();
        levelContainer = new JPanel();
        diffContainer  = new JPanel();
        borderContainer= new JPanel();


        nameContainer. setBackground(Color.white);
        scoreContainer.setBackground(Color.white);
        levelContainer.setBackground(Color.white);
        diffContainer.setBackground(Color.white);
        borderContainer.setBackground(Color.white);

        listContainer = new JPanel();
        listContainer.setLayout(new GridLayout(1,4, 5, 5));

        sortPanel = new JPanel();

        JLabel titleLabel = new JLabel("HIGH SCORES");
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        sortPanel.add(new JList(), BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(sortPanel, BorderLayout.EAST);
    }

    //Overridden paintComponent to draw the panel appropriately
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, 768, 512);
    }

    //This returns a column to be used in the high scores panel, 
    //Such as score, name, et cetera
    public JPanel getFlowPanel(String text, int size)
    {
        JPanel tempFlow = new JPanel();
        if (size<18)
            tempFlow.setBackground(Color.white);
        tempFlow.setLayout(new FlowLayout());

        JLabel tempLab = new JLabel();
        tempLab.setFont(new Font("Helvetica", Font.PLAIN, size));

        tempLab.setText(text); 

        tempFlow.add(tempLab);

        return tempFlow;
    }

    //Write scores to the panel in the correct order
    public void updateScore()
    {

        remove(listContainer);
        
        listContainer.removeAll();
        nameContainer.removeAll();
        scoreContainer.removeAll();
        levelContainer.removeAll();
        diffContainer.removeAll();
        borderContainer.removeAll();

        nameContainer .setLayout(new GridLayout(11, 1));
        scoreContainer.setLayout(new GridLayout(11, 1));
        levelContainer.setLayout(new GridLayout(11, 1));
        diffContainer .setLayout(new GridLayout(11, 1));
        borderContainer.setLayout(new GridLayout(11,1));

        nameContainer.add(getFlowPanel("Name:", 18));
        scoreContainer.add(getFlowPanel("Name:", 18));
        levelContainer.add(getFlowPanel("Level:", 18));
        diffContainer .add(getFlowPanel("Difficulty:", 18));
        borderContainer.add(getFlowPanel("Map Type:", 18));

        int count=0;

        Collections.sort(scoreEntries, new ScoreComparator());
        
        for(int i=scoreEntries.size()-1; i>=0; i--)
        {
            ScoreEntry tempE = (ScoreEntry) scoreEntries.get(i);
            nameContainer .add(getFlowPanel(tempE.name, 16));
            scoreContainer.add(getFlowPanel(tempE.score+"", 16));
            levelContainer.add(getFlowPanel(tempE.level+"", 16));
            diffContainer .add(getFlowPanel(tempE.difficulty, 16));
            borderContainer.add(getFlowPanel(tempE.openOrClosed, 15));
            count++;
            if (count>10)
                break;
        }

        listContainer.add(nameContainer );
        listContainer.add(scoreContainer);
        listContainer.add(levelContainer);
        listContainer.add(diffContainer );
        listContainer.add(borderContainer); 
        add(listContainer, BorderLayout.CENTER);
    }
    
    // Comparator class to sort scores correctly
    private class ScoreComparator implements Comparator<ScoreEntry>
    {
        public int compare(ScoreEntry entry1, ScoreEntry entry2)
        {
            int scoreCompare = entry1.score - entry2.score; 
            
            if (scoreCompare != 0)
            {
                return scoreCompare; 
            }
            return -1; 
        }
    }
}