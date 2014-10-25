/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.UserRoleDAO;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "userRoleService")
public class UserRoleServiceImpl implements UserRoleService
{
    @Autowired UserRoleDAO userRoleDAO;

    @Override
    @Transactional(readOnly = false)
    public void createUserRole(UserRole userRole)
    {
        userRoleDAO.create(userRole);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserRole(UserRole userRole)
    {
        userRoleDAO.update(userRole);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUserRole(UserRole userRole)
    {
        userRoleDAO.delete(userRole.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserRole getUserRoleByID(Long id)
    {
        return userRoleDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRole getUserRoleByName(String roleName)
    {
        return userRoleDAO.getUserRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRole> getAllUserRoles()
    {
        return userRoleDAO.getAllUserRoles();
    }
}
