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
package cz.muni.fi.mir.mathmlevaluation.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.mathmlevaluation.db.dao.SourceDocumentDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.SourceDocument;
import cz.muni.fi.mir.mathmlevaluation.db.service.SourceDocumentService;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "sourceDocumentService")
@Transactional(readOnly = false)
public class SourceDocumentServiceImpl implements SourceDocumentService
{
    @Autowired SourceDocumentDAO sourceDocumentDAO;
    
    @Override
    public void createSourceDocument(SourceDocument sourceDocument)
    {
        if(sourceDocument == null)
        {
            throw new IllegalArgumentException("Given input is null");
        }
        
        sourceDocumentDAO.create(sourceDocument);
    }

    @Override
    public void updateSourceDocument(SourceDocument sourceDocument)
    {
        InputChecker.checkInput(sourceDocument);
        
        sourceDocumentDAO.update(sourceDocument);
    }

    @Override
    public void deleteSourceDocument(SourceDocument sourceDocument)
    {
        InputChecker.checkInput(sourceDocument);        
        
        sourceDocumentDAO.delete(sourceDocument.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDocument getSourceDocumentByID(Long id)
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+id+"]");
        }
        
        return sourceDocumentDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourceDocument> getAllDocuments()
    {
        return sourceDocumentDAO.getAllDocuments();
    }
}
