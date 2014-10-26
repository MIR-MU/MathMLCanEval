/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
