/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * http://stackoverflow.com/a/6584631
 * @author emptak
 * @param <K>
 * @param <V>
 */
public class MapValueComparator<K,V>
{
    public enum Ordering
    {
        ASCENDING,DESCENDING
    }
    
    /**
     * http://stackoverflow.com/questions/12738216/sort-hashmap-with-duplicate-values
     * TODO REDO na nieco krajsie
     * @param map
     * @return 
     */
    public Map<K,V> sortByValues(Map<K,V> map)
    {
        
        List list = new ArrayList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
//        List list = new ArrayList<>(map.entrySet());
//        Collections.sort(list,new ValueComparator<>(Ordering.DESCENDING));
//        
//        Map<K,V> result = new LinkedHashMap<>();
//        
//        for(Iterator it = list.iterator(); it.hasNext();)
//        {
//            K key = (K) it.next();
//            result.put(key, map.get(key));
//        }
//        
//        return result;    
        
        //OLD v1
        
//        List<Integer> countValues = new ArrayList<>(map.values());
//        
//        Collections.sort(countValues);
//        
//        LinkedHashMap<String,Integer> sorted = new LinkedHashMap<>();
//        
//        
//        for(Integer i : countValues)
//        {
//            String toDelete = "";
//            for(String s : map.keySet())
//            {
//                if(map.get(s).equals(i))
//                {
//                    sorted.put(s, i);
//                    toDelete = s;
//                    break;
//                }
//            }
//            map.remove(toDelete);
//        }
    }
    
    private class ValueComparator<K extends Comparable> implements Comparator<K>
    {
        private final Ordering ordering;
        
        public ValueComparator(Ordering ordering)
        {
            super();
            this.ordering = ordering;
        }

        @Override
        public int compare(K o1, K o2)
        {
            int result = o1.compareTo(o2);
            if(ordering == Ordering.ASCENDING)
            {
                return result;
            }
            else
            {
                return -1*result;
            }
        }        
    }
}
