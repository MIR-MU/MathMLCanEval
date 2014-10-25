/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ProgramDAO;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.service.ProgramService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        checkInput(program);
        
        programDAO.delete(program.getId());
    }

    @Override
    public void updateProgram(Program program) throws IllegalArgumentException
    {
        checkInput(program);
        
        programDAO.update(program);
    }

    @Override
    @Transactional(readOnly = true)
    public Program getProgramByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) < 1)
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
    
    /**
     * Method validates input
     * @param program to be checked
     * @throws IllegalArgumentException if program is null or does not have valid id. 
     */
    private void checkInput(Program program) throws IllegalArgumentException
    {
        if(program == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        if(program.getId() == null || Long.valueOf("0").compareTo(program.getId()) < 1)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+program.getId()+"]");
        }
    }
}
