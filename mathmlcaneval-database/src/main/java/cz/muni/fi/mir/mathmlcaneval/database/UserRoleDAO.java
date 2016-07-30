/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database;

import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserRoleDAO extends GenericDAO<UserRole, Long>
{
    //todo needed?
    /**
     * Method takes {@code roleName} as input and returns matched
     * {@code UserRole} as result.
     *
     * @param roleName to be found
     * @return {@code UserRole} with {@code roleName} null if there is no match
     */
    UserRole getByName(String roleName);
}
