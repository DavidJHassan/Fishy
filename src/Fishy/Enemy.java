
package Fishy;

import java.awt.Rectangle;

public class Enemy extends Fish
{
    protected Rectangle eatZone;
    
    public Enemy()
    {
        super();
    }
    
     public Rectangle getEatZone()
    {
        if(isRight)
        {
            return new Rectangle(getX(), getY(), p.getWidth() - p.getWidth()/3, p.getHeight());
        }
        else
        {
            return new Rectangle(getX(), getY(), (p.getWidth() - 2*p.getWidth()/3), p.getHeight());
        }
    }
     
      public void move(Fish f)
    {
        
        if(getOutline().width < f.getOutline().width   && getOutline().height < f.getOutline().height)
        {
            if(f.getX() > x)
            {
                if(isRight)
                {
                    p = flipImage(p);
                    isRight = false;
                }
                
                x--;
            }
            
            if(f.getX() < x)
            {
                if(!isRight)
                {
                    p = flipImage(p);
                    isRight = true;
                }
                x++;
            }
            
            if(f.getY() > y)
            {
                y--;
            }
            
            if(f.getY() < y)
            {
                y++;
            }
        }
        else
        {
        
            if(f.getX() > x + p.getWidth())
            {
                if(!isRight)
                {
                    p = flipImage(p);
                    isRight = true;
                }

                x += 1;
            }
            else if(f.getX() < x)
            {
                if(isRight)
                {
                    p = flipImage(p);
                    isRight = false;
                }

                x -= 1;
            }

            if(f.getY() > y + p.getHeight()/2)
            {
                y += 1;
            }
            else if(f.getY() < y + p.getHeight()/2)
            {
                y -= 1;
            }   

            eatZone = getEatZone();
        }
    }
     
}
