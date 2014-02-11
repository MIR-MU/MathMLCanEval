package cz.muni.fi.mir.tools;

import java.util.Random;

/**
 * Class responsible for generating random Strings. Taken from http://stackoverflow.com/a/41156
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public class RandomString
{
    private static final char[] symbols = new char[36];
    private final Random random = new Random();
    private final char[] buf;

    static
    {
        for (int idx = 0; idx < 10; ++idx)
        {
            symbols[idx] = (char) ('0' + idx);
        }
        for (int idx = 10; idx < 36; ++idx)
        {
            symbols[idx] = (char) ('a' + idx - 10);
        }
    }

    /**
     * Default and the only constructor for RandomString class
     * @param length determining size of random generated strings
     */
    public RandomString(int length)
    {
        if (length < 1)
        {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        buf = new char[length];
    }

    /**
     * Method provides random alphanumeric string.
     * @return random alphanumeric string.
     */
    public String nextString()
    {
        for (int idx = 0; idx < buf.length; ++idx)
        {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
