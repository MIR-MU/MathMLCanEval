package cz.muni.fi.mir.tools;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * http://www.connorgarvey.com/blog/?p=105
 * Tools class maintaining useful operation in entire project.
 * @author Dominik Szalai <a href="mailto:emptulik&#64;gmail.com">emptulik&#64;gmail.com</a>
 */
@Component
public class Tools 
{
    private static Properties props;
    private static Tools instance = new Tools();
    
    @Autowired(required = true)
    public void setProperties(Properties props)
    {
        Tools.props = props;
    }
    
    /**
     * Method use for getting unique instance of this class. See Singleton pattern.
     * @return unique instance
     */    
    public static Tools getInstance()
    {
        return instance;
    }
    
    /**
     * Method gets Property value for given String input as key
     * @param keyValue of property
     * @return value for given key
     */
    public String getProperty(String keyValue)
    {
        return props.getProperty(keyValue);
    } 
    
    /**
     * Checker if collection is empty or not
     * @param <T> Type of collection
     * @param collection to be checked
     * @return true if collection is null, or has no elements
     */
    public <T> boolean isEmpty(Collection<T> collection)
    {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * Method used for checking if string is empty
     * @param s to be checked
     * @return true if string is null, or has no characters
     */
    public boolean stringIsEmpty(String s)
    {
        return s == null || s.isEmpty();
    }
    
    
    /**
     * Method normalizes input string. Simply said it removes diacritics and other nonascii characters.
     * @param input to be normalized
     * @return normalized input, an input without unicode symbols
     * @throws IllegalArgumentException if input contains unsupported encoding
     */
    public String normalizeString(String input) throws IllegalArgumentException
    {
        StringBuilder sb = new StringBuilder();
        
        String s1 = Normalizer.normalize(input, Normalizer.Form.NFKD);
        String regex = Pattern.quote("[\\p{InCombiningDiacriticalMarks}+");

        String s2 = null;
        try
        {
            s2 = new String(s1.replaceAll(regex, "").getBytes("ascii"), "ascii");
        }
        catch(UnsupportedEncodingException uee)
        {
            throw new IllegalArgumentException(uee);
        }
        

        char[] data = s2.toCharArray();
        
        for(char c : data)
        {
            if(c != '?')
            {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Method checks if given object is null or not
     * @param o object to be checked
     * @return true if object is null, false otherwise
     */
    public boolean isNull(Object o )
    {
        return o == null;
    }
    
    /**
     * Method converts input String with given pattern into DateTime.
     * @param input to be converted
     * @param pattern input pattern
     * @return DateTime from given input
     */
    public DateTime formatTime(String input, String pattern)
    {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(input, dtf);
    }
    
    /**
     * Method checks whether ID is valid or not. Valid IDs are not null and greater than zero.
     * @param id to be checked
     * @throws IllegalArgumentException if ID is null or lower than one. 
     */
    public void checkID(Long id) throws IllegalArgumentException
    {
        if(id == null)
        {
            throw new IllegalArgumentException("ERROR: Given input id is null, therefore it's not valid");
        }
        
        if(id.compareTo(new Long(1)) < 0)
        {
            throw new IllegalArgumentException("ERROR: GIven input id is lower than 1, therefore it's not valid");
        }
    } 
    
    /**
     * Method removes element from given List at specified position.
     * @param <T> type of list
     * @param list from which we would like to remove element
     * @param position of element to be deleted
     */
    @Deprecated
    public <T> void removeElementFromList(List<T> list, int position)
    {   // iba mna mohla napadnut niekedy takato blbost a nepouzit sublist... 
        List<T> result = new ArrayList<>();
        int i = 0;
        for(T t : list)
        {
            if(i != position)
            {
                result.add(t);
            }
            i++;
        } 
        
        list.clear();
        list.addAll(result);
    }
    
    
    /**
     * taken from
     * http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
     * method computes sha1
     *
     * @param text to be hashed
     * @return hashed value
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String SHA1(String text) 
    {
        
        return DigestUtils.sha1Hex(text);
//        MessageDigest md = null;        
//        try
//        {
//            md = MessageDigest.getInstance("SHA-1");
//        }
//        catch (NoSuchAlgorithmException ex)
//        {
//            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        byte[] sha1hash = new byte[40];
//        if(md != null)
//        {
//            try
//            {
//                md.update(text.getBytes("iso-8859-1"), 0, text.length());
//            }
//            catch (UnsupportedEncodingException ex)
//            {
//                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            sha1hash = md.digest();
//            return convertToHex(sha1hash);
//        }
//        
//        return null;      
    }

    private String convertToHex(byte[] data) 
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) 
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do 
            {
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                {
                    buf.append((char) ('0' + halfbyte));
                } 
                else 
                {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } 
            while (two_halfs++ < 1);
        }

        return buf.toString();
    }
}