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

import cz.muni.fi.mir.mathmlevaluation.db.dao.ProgramDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Program;
import cz.muni.fi.mir.mathmlevaluation.db.service.ProgramService;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "programService")
@Transactional(readOnly = false)
public class ProgramServiceImpl implements ProgramService
{   
    @Autowired private ProgramDAO programDAO;
    
    @Override
    public void createProgram(Program program) throws IllegalArgumentException
    {
        if(program == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        
        programDAO.create(program);
    }

    @Override
    public void deleteProgram(Program program) throws IllegalArgumentException
    {
        InputChecker.checkInput(program);
        
        programDAO.delete(program.getId());
    }

    @Override
    public void updateProgram(Program program) throws IllegalArgumentException
    {
        InputChecker.checkInput(program);
        
        programDAO.update(program);
    }

    @Override
    @Transactional(readOnly = true)
    public Program getProgramByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+id+"]");
        }
         
        return programDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getProgramByNameAndVersion(String name, String version) throws IllegalArgumentException
    {
        if(name == null || name.length() < 1 || version == null || version.length() <1)
        {
            throw new IllegalArgumentException("One of inputs was empty name["+name+"] and version["+version+"]");
        }
        
        return programDAO.getProgramByNameAndVersion(name, version);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getAllPrograms()
    {
        return programDAO.getAllPrograms();
    }
}
