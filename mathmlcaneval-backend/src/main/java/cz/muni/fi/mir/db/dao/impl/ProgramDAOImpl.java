/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.ProgramDAO;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.tools.Tools;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "programDAO")
public class ProgramDAOImpl extends GenericDAOImpl<Program, Long> implements ProgramDAO
{    
    private static final Logger logger = Logger.getLogger(ProgramDAOImpl.class);

    public ProgramDAOImpl()
    {
        super(Program.class);
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
