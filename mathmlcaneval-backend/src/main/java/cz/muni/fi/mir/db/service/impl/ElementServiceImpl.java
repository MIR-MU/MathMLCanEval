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

package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ElementDAO;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.service.ElementService;
import cz.muni.fi.mir.tools.EntityFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emptak
 */
@Service(value = "elementService")
public class ElementServiceImpl implements ElementService
{
    private static final Logger logger = Logger.getLogger(ElementServiceImpl.class);
    @Autowired
    private ElementDAO elementDAO;

    @Override
    @Transactional(readOnly = false)
    public void createElement(Element element)
    {
        elementDAO.create(element);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateElement(Element element)
    {
        elementDAO.update(element);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteElement(Element element)
    {
        elementDAO.delete(element.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Element getElementByID(Long id)
    {
        return elementDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Element getElementByName(String name)
    {
        return elementDAO.getElementByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Element> getAllElements()
    {
        return elementDAO.getAllElements();
    }

    @Override
    @Transactional(readOnly = false)
    public void reCreate()
    {
        InputStream inputStream = ElementService.class.getClassLoader().getResourceAsStream("other/mathmlelements.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<Element> newList = new ArrayList<>();
        
        String line;
        try
        {
            while((line = br.readLine()) != null)
            {
                newList.add(EntityFactory.createElement(line));
            }
        }
        catch(IOException ex)
        {
            logger.error(ex);
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch(IOException ex)
            {
                logger.error(ex);
            }            
        }
        
        if(!newList.isEmpty())
        {
            for(Element e : newList)
            {
                elementDAO.create(e);
            }
        }      
    }
    
}
