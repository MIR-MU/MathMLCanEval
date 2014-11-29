/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.tools;

import java.util.Objects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <K>
 * @param <V>
 */
public class Pair<K,V>
{
    private K key;
    private V value;

    public Pair()
    {
    }

    public Pair(K k, V v)
    {
        this.key = k;
        this.value = v;
    }
    
    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.key);
        hash = 17 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (!Objects.equals(this.key, other.key))
        {
            return false;
        }
        return Objects.equals(this.value, other.value);
    }
    
    

    @Override
    public String toString()
    {
        return "Pair{" + "k=" + key + ", v=" + value + '}';
    }
}
