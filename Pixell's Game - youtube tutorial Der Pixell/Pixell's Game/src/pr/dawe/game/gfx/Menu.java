package pr.dawe.game.gfx;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.*;

import pr.dawe.game.Game;
import pr.dawe.game.level.Level;


public class Menu extends JFrame {
  
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  public static boolean enterLevel;
  public static boolean enterDungeonForest;
  public static boolean credtis;
  public static boolean running = false;
  private BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int credits = 0;

  
  public Menu(String title) { 
  
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 650; 
    int frameHeight = 500;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
	

    
    jButton1.setBounds(168, 80, 305, 57);
    jButton1.setText("Start new Level");
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton1_ActionPerformed(evt);
      }
    });
    jButton1.setBackground(Color.WHITE);
    jButton1.setBorder(BorderFactory.createEtchedBorder(0, Color.GREEN, Color.BLACK));
    cp.add(jButton1);
    jButton2.setBounds(168, 168, 305, 57);
    jButton2.setText("Enter Dungeon (Forest)");
    jButton2.setMargin(new Insets(2, 2, 2, 2));
    jButton2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton2_ActionPerformed(evt);
      }
    });
    jButton2.setBackground(Color.WHITE);
    jButton2.setBorder(BorderFactory.createEtchedBorder(0, Color.GREEN, Color.BLACK));
    cp.add(jButton2);
    jButton3.setBounds(168, 256, 305, 57);
    jButton3.setText("Credits");
    jButton3.setMargin(new Insets(2, 2, 2, 2));
    jButton3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        jButton3_ActionPerformed(evt);
      }
    });
    jButton3.setBackground(Color.WHITE);
    jButton3.setBorder(BorderFactory.createEtchedBorder(0, Color.RED, Color.BLACK));
    cp.add(jButton3);
    cp.setBackground(new Color(0xFFC800));
   
    setVisible(true);
  } 
  

  public void jButton1_ActionPerformed(ActionEvent evt) { // ENTER LEVEL
    if(running == false) {
    	Game.main(null);
    	closeMenu();
    	} else {
    		System.out.println("Already running!");
    	}
  } 
  
  public void jButton2_ActionPerformed(ActionEvent evt) { // ENTER DUNGEON
    if(running == false) {
    	Game.main(null);
    	Level.generateForest();
    	closeMenu();
    	} else {
    		Level.generateForest();
    		closeMenu();
    	}
  } 
  
  public void jButton3_ActionPerformed(ActionEvent evt) { // CREDITS
    credtis = true;
    if (credits == 0) {
        jButton3.setText("This Game was made by Pixell...");
        }
	if (credits == 1) {
		jButton3.setText("...with Help from Ryan van Zeben");
		}
	if (credits == 2) {
		jButton3.setText("Have Fun!");
		}
	if (credits == 3) {
		jButton3.setText("And stop clicking me!");
		}
	if (credits == 4) {
		jButton3.setText("For real man, that is annoying!");
		}
	if (credits == 5) {
		jButton3.setText("Staahhp!!!");
		}
	if (credits == 6) {
		jButton3.setText("Ok one more time and I'll close myself...");
		}
	if (credits == 7) {
		closeMenu();
		}
	credits++;
  } 
  
  public void closeMenu() {
      WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
      Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
}
  

  
  public static void main(String[] args) {
    new Menu("Menu");
  } 
  
} 
