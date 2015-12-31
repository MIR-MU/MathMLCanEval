/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.test.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import org.apache.commons.beanutils.PropertyUtils;
import uk.co.jemos.podam.exceptions.PodamMockeryException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class PodamFactoryImpl extends uk.co.jemos.podam.api.PodamFactoryImpl
{

    @Override
    public <T> T manufacturePojo(Class<T> pojoClass, Type... genericTypeArgs)
    {
         T result = super.manufacturePojo(pojoClass, genericTypeArgs);
        
        try
        {
            PropertyUtils.setProperty(result, "id", null);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            throw new PodamMockeryException(ex.getMessage(), ex);
        }
        
        return result;
    }
    
}
