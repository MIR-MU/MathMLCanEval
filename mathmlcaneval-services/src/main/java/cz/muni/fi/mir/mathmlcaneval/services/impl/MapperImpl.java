/*
 * Copyright 2015 Math.
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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MapperImpl extends DozerBeanMapper implements Mapper
{
    private static final Logger LOG = LogManager.getLogger(MapperImpl.class);
    
    @Override
    public <T, U> List<T> mapList(List<U> input, Class<T> destinationClass) throws MappingException
    {
        final List<T> resultList = new ArrayList<>(input.size());
        
        for(U u : input)
        {
            resultList.add(super.map(u,destinationClass));
        }
        
        return resultList;
    }

    @Override
    public <T> T map(Object source, Class<T> destinationClass) throws MappingException
    {
        if(source == null)
        {
            return null;
        }
        else
        {
            return super.map(source, destinationClass);
        }
    }
}
