/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Fishy;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
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
       name = "Minnow";
       x = 200;
       y = 100;

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
            Logger.getLogger(Minnow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       points = 2;
       name = "Minnow";
       this.x = x;
       this.y = y;
            
    }
}

