
package Fishy;

import java.awt.Image;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class MapLoader 
{
   
    Image sandBlock;
    
    public MapLoader()
    {
        try 
        {
            sandBlock = ImageIO.read(new File("Images\\sandblock.png"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readMap(String textfilename, Image[][] blocks)
    {
        FileInputStream fstream = null;

        try 
        {
            fstream = new FileInputStream(textfilename+".txt");
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        try 
        {
            int i =0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   
            {
                
                    for(int j = 0; j<1000; j++)
                    {
                        System.out.print(strLine.charAt(j));
                        if(strLine.charAt(j) == '1')
                        {
                           blocks[i][j] = sandBlock; 
                           
                        }
                        else
                        {
                            blocks[i][j] = null;
                        }
                    }
                    
                i++;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CreateMapOutline(String textfilename)
    {
                 
       FileOutputStream fout = null;
       PrintStream ps = null;
            
       try 
       {
          fout = new FileOutputStream(textfilename+".txt");
          ps = new PrintStream(fout);
       } 
       catch (FileNotFoundException ex) 
       {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
       }
        
      for(int i = 0; i<999; i++)
      {
         ps.print("1");
      }
      
      ps.println("1");//1000
      
      for(int i = 0; i<998; i++)
      {
          ps.print("1");
          
          for(int j = 0; j<998; j++)
          {
              ps.print("0");
          }
          
          ps.println("1");
      }
      
      for(int i = 0; i<999; i++)
      {
         ps.print("1");
      }
      
      ps.print("1");//10000
      
      
        try 
        {
            fout.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
