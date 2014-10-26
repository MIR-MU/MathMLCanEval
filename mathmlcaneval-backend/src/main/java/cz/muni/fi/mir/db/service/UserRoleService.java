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
package cz.muni.fi.mir.db.service;

import java.util.List;

import cz.muni.fi.mir.db.domain.UserRole;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserRoleService
{

    /**
     * Method creates given user role inside system.
     *
     * @param userRole to be created
     * @throws IllegalArgumentException if role is null
     */
    void createUserRole(UserRole userRole) throws IllegalArgumentException;

    /**
     * Method updates given user role inside system.
     *
     * @param userRole to be updated
     * @throws IllegalArgumentException if role is null or does not have valid
     * id.
     */
    void updateUserRole(UserRole userRole) throws IllegalArgumentException;

    /**
     * Method deletes given user role from database
     *
     * @param userRole to be deleted
     * @throws IllegalArgumentException if role is null ro does not have valid
     * id.
     */
    void deleteUserRole(UserRole userRole) throws IllegalArgumentException;

    /**
     * Method obtains user role by its id.
     *
     * @param id of user role to be obtained
     * @return user role with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or less than one
     */
    UserRole getUserRoleByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains user role by its name.
     *
     * @param roleName name of role
     * @return role with given name, null if there is no match
     * @throws IllegalArgumentException if name of role is null or has zero
     * characters
     */
    UserRole getUserRoleByName(String roleName) throws IllegalArgumentException;

    /**
     * Method returns all roles from system.
     *
     * @return list of all roles, empty list if there are none yet (which should
     * not be possible)
     */
    List<UserRole> getAllUserRoles();
}
