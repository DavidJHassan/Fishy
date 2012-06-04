
package Fishy;

import java.util.ArrayList;
import java.util.Collection;

public class School
{
    private Collection fish;
    
    public School(int x, int y, String name, int amount)
    {
        super();
        fish = new ArrayList();
        
        for(int i = 0; i<amount; i++)
        {
            if(name.equals("Guppy"))
            {
                fish.add(new Guppy(x+i*25,y+i*25));
            }
            else if(name.equals("Minnow"))
            {
                fish.add(new Minnow(x+i*25,y+i*25));
            }
        }   
    }
    
    public Collection getSchool()
    {
        return fish;
    }
    
}
