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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author emptak
 */
public class SimilarityFormConverterWrapper
{
    private static final Logger logger = Logger.getLogger(SimilarityFormConverterWrapper.class);
    private static SimilarityFormConverterWrapper wrapper;
    private static SimilarityFormConverter converter;

    @Autowired(required = true)
    public void setConverter(SimilarityFormConverter converter)
    {
        SimilarityFormConverterWrapper.converter = converter;
    }
    
    public static SimilarityFormConverterWrapper newInstance()
    {
        if(wrapper == null)
        {
            wrapper = new SimilarityFormConverterWrapper();
        }
        
        return wrapper;
    }
    
    
    public static SimilarityFormConverter getConverter()
    {
        return converter;
    }
}
