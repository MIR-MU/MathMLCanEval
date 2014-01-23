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
 * @author Empt
 */
@Service(value = "programService")
public class ProgramServiceImpl implements ProgramService
{
    @Autowired private ProgramDAO programDAO;

    @Override
    @Transactional(readOnly = false)
    public void createProgram(Program program)
    {
        programDAO.createProgram(program);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteProgram(Program program)
    {
        programDAO.deleteProgram(program);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateProgram(Program program)
    {
        programDAO.updateProgram(program);
    }

    @Override
    @Transactional(readOnly = true)
    public Program getProgramByID(Long id)
    {
        return programDAO.getProgramByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getProgramByName(String name)
    {
        return programDAO.getProgramByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getProgramByNameAndVersion(String name, String version)
    {
        return programDAO.getProgramByNameAndVersion(name, version);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Program> getAllPrograms()
    {
        return programDAO.getAllPrograms();
    }
    
}
