/*
 * Sort of useless class that I made to allow me to 
 * set a JPanel's color and layout in one line. 
 */

package snakeapplication;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel
{
    Color clr;

    public ContentPanel(Color clr)
    {
        setLayout(new BorderLayout());
        setBackground(clr);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
}
