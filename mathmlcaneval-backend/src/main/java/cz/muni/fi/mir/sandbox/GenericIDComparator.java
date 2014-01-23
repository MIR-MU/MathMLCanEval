/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.sandbox;

import cz.muni.fi.mir.db.domain.User;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO
 * @author Empt
 */
public class GenericIDComparator<T> implements Comparator<T>
{
    private boolean ascending;
    
    public GenericIDComparator(boolean ascending)
    {
        this.ascending = ascending;
    }

    @Override
    public int compare(T o1, T o2)
    {
        Long id1 = Long.MIN_VALUE;
        Long id2 = Long.MIN_VALUE;
        
        try
        {
            Method m = o1.getClass().getDeclaredMethod("getId");
            id1 = (Long) m.invoke(o1);
            id2 = (Long) m.invoke(o2);
        } 
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex)
        {
            Logger.getLogger(GenericIDComparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ascending)
        {
            return id1.compareTo(id2);
        }
        else
        {
            return id2.compareTo(id1);
        }
    }
    
    
    public static void main(String[] args)
    {
        User u = new User();
        User u2 = new User();
        User u3 = new User();
        u.setId(new Long(1));
        u2.setId(new Long(2));
        u3.setId(new Long(3));
        
        List<User> us = new ArrayList<>(3);
        us.add(u);
        us.add(u2);
        us.add(u3);
        
        Collections.sort(us, new GenericIDComparator<>(true));
        System.out.println(us);
        
        Collections.sort(us, new GenericIDComparator<>(false));
        System.out.println(us);
    }
}
