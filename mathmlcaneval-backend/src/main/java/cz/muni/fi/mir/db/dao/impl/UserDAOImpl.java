/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.UserDAO;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "userDAO")
public class UserDAOImpl implements UserDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserDAOImpl.class);

    @Override
    public void createUser(User user)
    {
        entityManager.persist(user);
    }

    @Override
    public User getUserByUsername(String username)
    {
        User u = null;
        try
        {
            u = entityManager.createQuery("SELECT u FROM users u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return u;
    }

    @Override
    public void updateUser(User user)
    {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user)
    {
        User u = entityManager.find(User.class, user.getId());
        if(u != null)
        {
            entityManager.remove(u);
        }
        else
        {
            logger.info("Trying to delete User with ID that has not been found. The ID is ["+user.getId().toString()+"]");
        }
    }

    @Override
    public User getUserByID(Long id)
    {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers()
    {
        List<User> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT u FROM users u", User.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<User> getUsersByRole(UserRole userRole)
    {
        List<User> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT u FROM users u WHERE :userRoleId MEMBER OF u.userRoles", User.class)
                    .setParameter("userRoleId", userRole.getId()).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<User> findUserByRealName(String name)
    {
        List<User> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT u FROM users u WHERE u.realName LIKE :realName", User.class)
                    .setParameter("realName", "%"+name+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    /**
     * TODO
     * @param roles
     * @return 
     */
    @Override
    public List<User> getUsersByRoles(List<UserRole> roles)
    {
        
        List<User> resultList = new ArrayList<>();
        
        
        StringBuilder query = new StringBuilder("SELECT u FROM users u WHERE ");
        for(int i =0;i<roles.size();i++)
        {
            query.append(":roles").append(i).append(" MEMBER OF u.userRole");
            if(i < roles.size() -1)
            {
                query.append(" AND ");
            }
        }
        
        try
        {
            TypedQuery<User> q = entityManager.createQuery(query.toString(), User.class);
            for(int i =0;i<roles.size();i++)
            {
                q.setParameter("roles"+i, roles.get(i));
            }
            resultList = q.getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public User getUserByEmail(String email)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
