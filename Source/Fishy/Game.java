package Fishy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable
{
    private Player p;
    private Guppy g;
    private Guppy g1;
    private Minnow m;
    private Shark s;
    private School minnows;
    
    private ArrayList danger;
    private ArrayList nondanger;
    
    private Thread t;
    private Image background;
    private static int screenWidth;
    private static int screenHeight;
    private final static int mapWidth = 120000;
    private final static int mapHeight = 120000;
  
    
    private ScreenManager screen;
    
    public Game()
    {
        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(new KeyAdapter());
        screen = new ScreenManager();
        screenWidth = screen.getCurrentDisplayMode().getWidth();
        screenHeight = screen.getCurrentDisplayMode().getHeight();
        
        try 
        {
            background = ImageIO.read(new File("Images\\underwater.png"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        danger = new ArrayList();
        nondanger = new ArrayList();
        
        p = new Player();
        s = new Shark();
        g = new Guppy();
        g1 = new Guppy(225, 130);
        m = new Minnow(300,300);
        minnows = new School(300,400, "Minnow",300);
        
        danger.add(s);
        nondanger.add(g);
        nondanger.add(m);
        nondanger.add(g1);
        nondanger.addAll(minnows.getSchool());
        
        t = new Thread(this);
        t.start();
    }

    public void moveFish()
    {
        p.move();
              
        for(int i = 0; i<danger.size(); i++)
        {
            Enemy d = (Enemy)danger.get(i);
            d.move(p);
        }

        for(int i = 0; i<nondanger.size(); i++)
        {
            Fish f = (Fish)nondanger.get(i);
            
          
            for(int j = 1+i; j<nondanger.size() - i; j++)
            {
                Fish o = (Fish)nondanger.get(j); 
                f.setCurrentClosest(o);
            }

            f.move();
           
        }
    }
    
    @Override
    public void run() 
    {
       while(true)
       {
            moveFish();
           
            try 
            {
                Thread.sleep(5);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
            
            checkCollision();
            p.checkToResize();

            repaint();
        }
    }
    
   @Override
    public void paint(Graphics g)
   {
       super.paint(g);
       Graphics2D ga = (Graphics2D)g;
       
       int offsetX = getOffsetX(p);
       int offsetY = getOffsetY(p);
       
       //creating parallax background coordinates
       int x = offsetX * (screenWidth - background.getWidth(null)) / (screenWidth - mapWidth);
       int y = offsetY * (screenHeight - background.getHeight(null)) / (screenHeight - mapHeight);
       ga.drawImage(background, x, y, null);
       
       for(int i=0; i<danger.size(); i++)
       {
           Fish s = (Fish) danger.get(i);
           ga.drawImage(s.getImage(), s.getX() + offsetX, s.getY() + offsetY, this);
       }
       
       for(int i=0; i<nondanger.size(); i++)
       {
           Fish o = (Fish) nondanger.get(i);
           ga.drawImage(o.getImage(), o.getX() + offsetX, o.getY() + offsetY ,this);
       }
       
       ga.drawImage(p.getImage(), p.getX() + offsetX, p.getY() + offsetY, this);
       
       
       
       
       Toolkit.getDefaultToolkit().sync();
       g.dispose(); 
   }
   
  public int getOffsetX(Fish p)
  {
      int offsetX = screenWidth / 2 - p.getX();
      offsetX = Math.min(offsetX, 0);
      offsetX = Math.max(offsetX, screenWidth - mapWidth);
       
      return offsetX;
  }
  
  public int getOffsetY(Fish p)
  {
        int offsetY = screenHeight / 2 - p.getY();
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, screenWidth - mapHeight);
        return offsetY;
  }
  
  public void checkCollision()
  {
      //Checking to see if you can eat a dangerous fish
     for(int k = 0; k<danger.size(); k++)
     {
         Enemy fish = (Enemy)danger.get(k);
         
        if(fish.getOutline().intersects(p.getEatZone()) && p.getOutline().width > fish.getOutline().width   && p.getOutline().height > fish.getOutline().height)
        {
            danger.remove(k);
            p.increasePoints(fish.getPoints());
        }
     }
     //Checking if enemies can eat you
     for(int i = 0; i<danger.size(); i++)
     {
        if(((Enemy)danger.get(i)).getEatZone().intersects(p.getOutline()))
        {
            p.setDead();
        }
     }
     //Checking if you can eat non dangerous fish
     for(int j = 0; j<nondanger.size(); j++)
     {
         Fish fish = (Fish)nondanger.get(j);
         
        if(fish.getOutline().intersects(p.getEatZone()))
        {
            nondanger.remove(j);
            p.increasePoints(fish.getPoints());
        }
     }
     
     
  }
  
   private class KeyAdapter implements KeyListener
   {
        @Override
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) 
        {
            p.setDx(-3);
        }

        if (key == KeyEvent.VK_D) 
        {
            p.setDx(3);
        }

        if (key == KeyEvent.VK_W) 
        {
            p.setDy(-3);
        }

        if (key == KeyEvent.VK_S) 
        {
            p.setDy(3);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) 
        {
            p.setDx(0);
        }

        if (key == KeyEvent.VK_D) 
        {
            p.setDx(0);
        }

        if (key == KeyEvent.VK_W) 
        {
            p.setDy(0);
        }

        if (key == KeyEvent.VK_S) 
        {
            p.setDy(0);
        }
    }

        @Override
        public void keyTyped(KeyEvent e) 
        {
            
        }
    }
}


