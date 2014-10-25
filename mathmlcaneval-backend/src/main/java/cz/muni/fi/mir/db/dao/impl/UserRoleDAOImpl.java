package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.UserRoleDAO;
import cz.muni.fi.mir.db.domain.UserRole;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai
 * @author Robert Siska
 * @version 1.0
 * @since 1.0
 */
@Repository(value = "userRoleDAO")
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Long> implements UserRoleDAO
{    
    private static final Logger logger = Logger.getLogger(UserRoleDAOImpl.class);
    
    public UserRoleDAOImpl()
    {
        super(UserRole.class);
    }

    @Override
    public UserRole getUserRoleByName(String roleName)
    {
        UserRole ur = null;
        try
        {
            ur = entityManager.createQuery("SELECT ur FROM userRole ur WHERE ur.roleName = :roleName", UserRole.class)
                    .setParameter("roleName", roleName).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return ur;
    }

    @Override
    public List<UserRole> getAllUserRoles()
    {
        List<UserRole> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT ur FROM userRole ur", UserRole.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }    
}