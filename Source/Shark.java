package Fishy;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Shark extends Enemy
{
    private Random r;

    
    public Shark()
    {
        super();
        
        try 
        {
            p = ImageIO.read(new File("Images\\Shark.png"));
            eatZone = new Rectangle(getX(), getY(), p.getWidth() - p.getWidth()/3, p.getHeight());
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Shark.class.getName()).log(Level.SEVERE, null, ex);
        }
       name = "Shark";
       r = new Random();
       points = 100;
       x = r.nextInt(1600) - 400;
       y = r.nextInt(1200) - 300;

    }
    
   
}

