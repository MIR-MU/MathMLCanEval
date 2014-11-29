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

import cz.muni.fi.mir.mathmlevaluation.db.dao.RevisionDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Revision;
import cz.muni.fi.mir.mathmlevaluation.db.service.RevisionService;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "revisionService")
@Transactional(readOnly = false)
public class RevisionServiceImpl implements RevisionService
{
    @Autowired private RevisionDAO revisionDAO;

    @Override
    public void createRevision(Revision revision) throws IllegalArgumentException
    {
        if(revision == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        
        revisionDAO.create(revision);
    }

    @Override
    public void deleteRevision(Revision revision) throws IllegalArgumentException
    {
        InputChecker.checkInput(revision);
        
        revisionDAO.delete(revision.getId());
    }

    @Override
    public void updateRevision(Revision revision) throws IllegalArgumentException
    {
        InputChecker.checkInput(revision);
        
        revisionDAO.update(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public Revision getRevisionByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+id+"]");
        }
        
        return revisionDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revision> getAllRevisions() 
    {
        return revisionDAO.getAllRevisions();
    } 
}
