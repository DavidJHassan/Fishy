
package Fishy;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class Player extends Guppy
{
    private int lastCheck;
    private float scale = (float) 1.25;
    private int width;
    private int height;
    private Rectangle eatZone;
    private BufferedImage original;
    private BufferedImage originalState;
    
    public Player()
    {
        super();
        x = 100;
        y = 100;
        points = 0;
        lastCheck = 0;
        originalState = p;
        width = p.getWidth();
        height = p.getHeight();
        eatZone = new Rectangle(getX(), getY(), width - width/3, height);
    }
    
    
    public void checkToResize()
    {
        if(points % 1 == 0 && points != lastCheck)
        {
            lastCheck = points;
            width++;
            height++;
            int scaleHeight = (int) Math.rint(height - height%scale);
            
            /*Makes sure we are redrawing with the original image facing the right direction*/
            if(!isRight)
            {
                original = flipImage(originalState);
            }
            else
            {
                original = originalState;
            }
            
            resizeImageWithHint(width, scaleHeight);
        }
    }
    
    public Rectangle getEatZone()
    {
        if(isRight)
        {
            return new Rectangle(getX(), getY(), width - width/3, height);
        }
        else
        {
            return new Rectangle(getX(), getY(), (width - 2*width/3), height);
        }
    }
    
    
    @Override
    public void move()
    {
        if(dx < 0 && isRight)
        {
            p = flipImage(p);
            isRight = false;
        }
        else if(dx > 0 && !isRight )
        {
            p = flipImage(p);
            isRight = true;
        }
        
        eatZone = getEatZone();
       
        x += dx;
        y += dy;
    }
    
    public void setDx(int dx) 
    {
        this.dx = dx;
        
    }
    
    public void setDy(int dy) 
    {
        this.dy = dy;
    }
    
    public void resizeImageWithHint(int IMG_WIDTH, int IMG_HEIGHT)
    {
            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, p.getType());
            Graphics2D g = resizedImage.createGraphics();
            
            g.drawImage(original, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();	
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

            p = resizedImage;
    }
    
    public void increasePoints(int points)
    {
        this.points+=points;
    }
}
