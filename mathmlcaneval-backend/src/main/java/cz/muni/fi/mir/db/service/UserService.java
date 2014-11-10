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

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserService
{
    /**
     * Method creates given user inside system.
     * @param user to be created
     * @throws IllegalArgumentException if user is null 
     */
    void createUser(User user) throws IllegalArgumentException;
    
    /**
     * Method updates given user inside system.
     * @param user to be updated
     * @throws IllegalArgumentException if user is null or does not have valid id. 
     */
    void updateUser(User user) throws IllegalArgumentException;
    
    /**
     * Method deletes given user from database.
     * @param user to be deleted
     * @throws IllegalArgumentException if user is null or does not have valid id. 
     */
    void deleteUser(User user) throws IllegalArgumentException;
    
    /**
     * Method obtains user based on its id.
     * @param id of user
     * @return user with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or less than one
     */
    User getUserByID(Long id) throws IllegalArgumentException;
    
    /**
     * Method obtains user based on its username. 
     * @param username of user
     * @return user with given username, null if there no match
     * @throws IllegalArgumentException if username is null or has zero characters
     */
    User getUserByUsername(String username) throws IllegalArgumentException;
   
    /**
     * Method obtains all users from database.
     *
     * @return list of all users, empty list if there are none yet.
     */ 
    List<User> getAllUsers();
    
    /**
     * Method obtains all users having given user role.
     * @param userRole of users to be fetched
     * @return list of all users having given user role, empty list if there are none yet
     * @throws IllegalArgumentException if user role is null, or does not have valid id.
     */
    List<User> getUsersByRole(UserRole userRole) throws IllegalArgumentException;
    
    /**
     * Method returns system user which is responsible for automatic annotations.
     * 
     * @return system user.
     */
    User getSystemUser();
}
