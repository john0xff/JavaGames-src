/*
 * Panel that describes behavior of all main game panels
 * to avoid copying code in multiple classes and to implement
 * polymorphic behavior.
 */
package snakeapplication;

import javax.swing.*;
import java.awt.*;

public abstract class ApplicationPanel extends JPanel
{
    public static int width  = 768,
                      height = 528;

    private boolean isActive;
    
    public ApplicationPanel(Color clr)
    {
        setSize(width, height);
        isActive = false;
    }

    public boolean getActive()
    {
        return isActive;
    }

    public void setActive(Boolean isActive)
    {
        this.isActive = isActive;
    }
}