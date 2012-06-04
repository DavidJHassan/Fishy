package Fishy;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fish 
{
    private static int numOfFish = 0;
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected boolean isRight = true;
    protected boolean isDead = false;
    protected Rectangle outline = null;
    protected BufferedImage p;
    protected int points;
    
    protected Rectangle sight;
    protected boolean isSighted;
    protected int sightX;
    protected int sightY;
    protected double currentDistance;
    
    protected int randomPointX;
    protected int randomPointY;
     
    
    public Fish()
    {
        numOfFish++;
        isSighted = false;
    }
    
    public int getNumOfFish()
    {
        return numOfFish;
    }
    
    public void move()
    {
        if(isSighted)
            {
                if(sightX > x - getOutline().width)
                {
                    if(!isRight)
                    {
                        p = flipImage(p);
                        isRight = true;
                    }

                    
                    x += 1;
                }
                else if(sightX < x + getOutline().width)
                {
                    if(isRight)
                    {
                        p = flipImage(p);
                        isRight = false;
                    }

                    x -= 1;
                }

                if(sightY > y - getOutline().height)
                {
                    y += 1;
                }
                else if(sightY < y + getOutline().height)
                {
                    y -= 1;
                }   
            }
            else if(x != randomPointX || y != randomPointY)
            {
                if(randomPointX > x)
                {
                    if(!isRight)
                    {
                        p = flipImage(p);
                        isRight = true;
                    }

                    x += 1;
                }
                else if(randomPointX < x)
                {
                    if(isRight)
                    {
                        p = flipImage(p);
                        isRight = false;
                    }

                    x -= 1;
                }

                if(randomPointY > y)
                {
                    y += 1;
                }
                else if(randomPointY < y)
                {
                    y -= 1;
                }   
            }
            else
            {
                randomPointX = -x + new Random(System.currentTimeMillis()*y).nextInt() % (x + 600);
                randomPointY = -y + new Random(System.currentTimeMillis()*x).nextInt() % (x + 600);
            }  
            
            
            isSighted = false;
            currentDistance = 0;
            sightX = 0;
            sightY = 0;
            
    }
    
    public boolean isDead()
    {
        return isDead;
    }
    
    
    public boolean isSighted()
    {
        return isSighted;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    public void setDead()
    {
        isDead = true;
    }
    
    public BufferedImage getImage() 
    {
        if(p == null)
        {
            try 
            {
                throw new Exception("No Image was found for Fish");
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(Fish.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return p;
    }
    
    
    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }
   
    public Rectangle getOutline()
    {
        if(outline != null)
        {
            return new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
        }
        else
        {
            try 
            {
                throw new Exception("Unitialized outline rectangle");
            } 
            catch (Exception ex) 
            {
                outline = new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
            }
            
            return outline;
        }
    }
    
    public Rectangle getSight()
    {
       return new Rectangle(x -  5*(int) Math.ceil(p.getWidth())/2, y - 5*(int) Math.ceil(p.getHeight())/2 , x + 5*(int) Math.ceil(p.getWidth())/2 , y + 5*(int) Math.ceil(p.getHeight())/2);   
    }
  
    public BufferedImage flipImage(BufferedImage i)
    {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-i.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(i, null);
    }
    
    
     public void setCurrentClosest(Fish f)
    {
        if(getSight().intersects(f.getOutline()))
        {
            isSighted = true;
            
            double distance =  Math.sqrt( Math.pow(f.getX() - x , 2) + Math.pow(f.getY() - y , 2) ); 
            
            if(distance < currentDistance)
            {
                currentDistance = distance;
                sightX = f.getX();
                sightY = f.getY();
            }
        }
    }
    
}
