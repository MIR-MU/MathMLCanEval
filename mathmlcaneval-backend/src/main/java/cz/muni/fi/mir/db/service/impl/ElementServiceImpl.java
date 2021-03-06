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
package cz.muni.fi.mir.db.service.impl;

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

import cz.muni.fi.mir.db.dao.ElementDAO;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.ElementService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.XMLUtils;
import java.util.HashSet;
import java.util.Set;

@Service(value = "elementService")
@Transactional(readOnly = false)
public class ElementServiceImpl implements ElementService
{
    private static final Logger logger = Logger.getLogger(ElementServiceImpl.class);
    @Autowired
    private ElementDAO elementDAO;
    @Autowired
    private XMLUtils xmlUtils;

    @Override
    public void createElement(Element element) throws IllegalArgumentException
    {
        if(element == null)
        {
            throw new IllegalArgumentException("Given input is null");
        }
        elementDAO.create(element);
    }

    @Override
    @Transactional(readOnly = true)
    public Element getElementByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given id is out of valid range. Should be grater than one but was ["+id+"]");
        }
        return elementDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Element> getAllElements()
    {
        return elementDAO.getAllElements();
    }

    @Override
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
                Element temp = elementDAO.getElementByName(e.getElementName());
                if(temp == null)
                {
                    elementDAO.create(e);
                }                
            }
        }      
    }

    @Override
    public List<Element> extractElements(Formula formula)
    {
        Set<Element> temp = new HashSet<>();

        org.w3c.dom.Document doc = xmlUtils.parse(formula.getXml());

        if (doc != null)
        {
            org.w3c.dom.NodeList nodeList = doc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                temp.add(EntityFactory.createElement(nodeList.item(i).getNodeName()));
            }
        }
        List<Element> result = null;

        if (formula.getElements() == null || formula.getElements().isEmpty())
        {
            result = new ArrayList<>(temp.size());
        }
        else
        {
            result = new ArrayList<>(formula.getElements());
        }

        result.addAll(temp);

        return result;
    }
}
