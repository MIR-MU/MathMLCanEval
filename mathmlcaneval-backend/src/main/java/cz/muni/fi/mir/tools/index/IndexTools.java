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
package cz.muni.fi.mir.tools.index;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface IndexTools
{
    /**
     * Method indexes given object.
     * @param o 
     */
    void index(Object o);   
    
    /**
     * Method reindex given class type
     * @param clazz class to be reindexed
     */
    void reIndexClass(Class clazz);
    
    /**
     * Method creates manual optimization of index for given class
     * @param clazz to be optimized
     */
    void optimize(Class clazz);
}
