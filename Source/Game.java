package Fishy;

import java.awt.*;
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
    
    private ArrayList danger;
    private ArrayList nondanger;
    
    private Thread t;
    private Image background;
    
    private ScreenManager screen;
    private static int screenWidth;
    private static int screenHeight;
    private final static int mapWidth = 50000;
    private final static int mapHeight = 50000;
    
    private Image[][] blocks;
    private MapLoader ml;
    
   
    public Game()
    {
        setFocusable(true);
        setDoubleBuffered(true);
        addKeyListener(new KeyAdapter());
        screen = new ScreenManager();
        screenWidth = screen.getCurrentDisplayMode().getWidth();
        screenHeight = screen.getCurrentDisplayMode().getHeight();
        
        blocks = new Image[1000][1000];
        ml = new MapLoader();
        ml.readMap("Map", blocks);
        
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
        
        danger.add(s);
        nondanger.add(g);
        nondanger.add(m);
        nondanger.add(g1);
        nondanger.addAll(new School(screenWidth, screenHeight,"Minnow",300).getSchool());
        nondanger.addAll(new School(screenWidth,screenHeight,"Guppy",200).getSchool());
        
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
            
          /*Checks to see if the same type of fish is in sight then sets whether or not its the closest fish*/
            for(int j = 1+i; j<nondanger.size() - i; j++)
            {
                Fish o = (Fish)nondanger.get(j);
                
                if(f.name.equals(o.name))
                {
                    f.setCurrentClosest(o);
                }
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
       
       /*draws the map blocks*/
       for(int i = 0; i<1000; i++)
       {
           for(int j = 0; j<1000; j++)
           {
               if(blocks[i][j] != null)
               {
                  if( Math.abs(p.getX()  - j*50) <= screenWidth && Math.abs( p.getY() - i*50) <= screenHeight)
                  {
                        ga.drawImage(blocks[i][j], j*50 + offsetX, i*50 + offsetY,null);
                  }
               }
           }
       }
       
       for(int i=0; i<danger.size(); i++)
       {
           Fish s = (Fish) danger.get(i);
           /*Checks to see if the fish is on screen before rendering*/
            if( Math.abs(p.getX()  - s.getX()) <= screenWidth && Math.abs( p.getY() - s.getY() ) <= screenHeight)
            {
                ga.drawImage(s.getImage(), s.getX() + offsetX, s.getY() + offsetY, this);
            }  
       }
       
       for(int i=0; i<nondanger.size(); i++)
       {
           Fish o = (Fish) nondanger.get(i);
           /*Checks to see if the fish is on screen before rendering*/
            if( Math.abs(p.getX()  - o.getX()) <= screenWidth && Math.abs( p.getY() - o.getY() ) <= screenHeight)
            {
                ga.drawImage(o.getImage(), o.getX() + offsetX, o.getY() + offsetY, this);
            }
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
     
     /*for(int h = 0; h<nondanger.size(); h++)
     {
         Fish fish = (Fish)nondanger.get(h);
         
            for(int j = 1+h; j<nondanger.size() - h; j++)
            {
                Fish o = (Fish)nondanger.get(j); 

                if(!fish.CollisionDetected)
                {
                    if(fish.getOutline().intersects(o.getOutline()))
                    {
                        fish.CollisionDetected = true;
                        fish.x = fish.lastX;
                        fish.y = fish.lastY;
                    }
                }
            }
            
            fish.CollisionDetected = false;
     }
     */
    
     
     
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


