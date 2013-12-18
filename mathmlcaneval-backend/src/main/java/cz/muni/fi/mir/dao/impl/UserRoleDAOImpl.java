/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.dao.impl;

import cz.muni.fi.mir.dao.UserRoleDAO;
import cz.muni.fi.mir.domain.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "userRoleDAO")
public class UserRoleDAOImpl implements UserRoleDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserRoleDAOImpl.class);

    @Override
    public void createUserRole(UserRole userRole)
    {
        entityManager.persist(userRole);
    }

    @Override
    public void updateUserRole(UserRole userRole)
    {
        entityManager.merge(userRole);
    }

    @Override
    public void deleteUserRole(UserRole userRole)
    {
        UserRole ur = entityManager.find(UserRole.class, userRole.getId());
        if(ur != null)
        {
            entityManager.remove(ur);
        }
    }

    @Override
    public UserRole getUserRoleByID(Long id)
    {
        return entityManager.find(UserRole.class, id);
    }

    @Override
    public List<UserRole> getAllUserRoles()
    {
        List<UserRole> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT ur FROM UserRole ur", UserRole.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
    
}
