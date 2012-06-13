
package Fishy;

import java.security.SecureRandom;
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
                fish.add(new Guppy( Math.abs(new SecureRandom().nextInt())% x ,Math.abs(new SecureRandom().nextInt())% y));
            }
            else if(name.equals("Minnow"))
            {
                 fish.add(new Minnow( Math.abs(new SecureRandom().nextInt())% x ,Math.abs(new SecureRandom().nextInt())% y));
            }
        }   
    }
    
    public Collection getSchool()
    {
        return fish;
    }
    
}
