/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Fishy;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Minnow extends Fish
{
      
    
    public Minnow()
    {
        super();
        
        try 
        {
            p = ImageIO.read(new File("Images\\Minnow.png"));
            outline = new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Guppy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       points = 1;
 
       x = 200;
       y = 100;
       
       randomPointX = -x + new Random(System.currentTimeMillis()*y).nextInt() % (x + 600);
       randomPointY = -y + new Random(System.currentTimeMillis()*x).nextInt() % (x + 600);
      
    }
    
    public Minnow(int x, int y)
    {
        super();
        
        try 
        {
            p = ImageIO.read(new File("Images\\Minnow.png"));
            outline = new Rectangle(getX(),getY(),getImage().getWidth(), getImage().getHeight());
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Guppy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       points = 2;
 
       this.x = x;
       this.y = y;
       
       randomPointX = -x + new Random().nextInt() % (x + 600);
       randomPointY = -y + new Random().nextInt() % (x + 600);
       
    }
    

}

