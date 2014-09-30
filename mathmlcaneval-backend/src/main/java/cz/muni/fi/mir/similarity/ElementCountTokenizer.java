/*
 * Copyright 2014 emptak.
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

package cz.muni.fi.mir.similarity;

import java.io.Reader;
import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.util.Version;

/**
 *
 * @author emptak
 */
public class ElementCountTokenizer extends CharTokenizer
{

    public ElementCountTokenizer(Version matchVersion, Reader input)
    {
        super(matchVersion, input);
    }

    @Override
    protected boolean isTokenChar(int c)
    {
        switch(c)
        {
            case 44 :                   // [']
            case 32 :                   // [ ]
            case 123 :                  // [{]
            case 125 :                  // [}]
                return false;    
            default: 
                return true;
        }
    }
}
