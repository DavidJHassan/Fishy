
package Fishy;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Guppy extends Fish
{
    
    public Guppy()
    {
        super();
        
        try 
        {
            p = ImageIO.read(new File("Images\\Guppy.png"));
            outline = new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Guppy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       points = 2;
 
       name = "Guppy";
       x = 0;
       y = 0;
       
    }
    
    public Guppy(int x, int y)
    {
        super();
        
        try 
        {
            p = ImageIO.read(new File("Images\\Guppy.png"));
            outline = new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Guppy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       points = 2;
       name = "Guppy";
       this.x = x;
       this.y = y;
       
    
        
    }

    
     
}
    


