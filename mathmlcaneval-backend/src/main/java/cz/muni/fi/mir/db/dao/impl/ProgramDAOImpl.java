/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.ProgramDAO;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.tools.Tools;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "programDAO")
public class ProgramDAOImpl implements ProgramDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProgramDAOImpl.class);

    @Override
    public void createProgram(Program program)
    {
        entityManager.persist(program);
    }

    @Override
    public void deleteProgram(Program program)
    {
        Program p = entityManager.find(Program.class, program.getId());
        
        if(p != null)
        {
            entityManager.remove(p);
        }
        else
        {
            logger.info("Trying to delete Program with ID that has not been found. The ID is ["+program.getId().toString()+"]");
        }
    }

    @Override
    public void updateProgram(Program program)
    {
        entityManager.merge(program);
    }

    @Override
    public Program getProgramByID(Long id)
    {
        return entityManager.find(Program.class, id);
    }

    @Override
    public List<Program> getProgramByName(String name)
    {
        List<Program> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT p FROM program p WHERE p.name = :name",Program.class)
                    .setParameter("name", name).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Program> getProgramByNameAndVersion(String name, String version)
    {
        List<Program> resultList = Collections.emptyList();
        if(Tools.getInstance().stringIsEmpty(name) && Tools.getInstance().stringIsEmpty(version))
        {
            return getAllPrograms();
        }
        
        try
        {           
            resultList = entityManager.createQuery("SELECT p FROM program p WHERE p.name = :name AND p.version = :version", Program.class)
                    .setParameter("name", name).setParameter("version", version).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Program> getAllPrograms()
    {
        List<Program> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT p FROM program p", Program.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
