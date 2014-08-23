/*
 * This class allows me to simplify storing high scores entries. 
 */

package snakeapplication;

public class ScoreEntry
{
    int score;
    int level;
    String name;
    String difficulty;
    String openOrClosed;

    ScoreEntry(int score, int level, String name, Boolean isOpen, String difficulty)
    {
        this.score = score;
        this.level = level;
        this.name = name;
        this.difficulty = difficulty;
        if (isOpen)
            openOrClosed = "Open";
        else
            openOrClosed = "Closed";
    }

    //Overridden toString to write score appropriately as a string
    @Override
    public String toString()
    {
        return new String(name +"\t\t\t"+score+"\t\t\t"+level+"\t\t\t\n");
    }
}
