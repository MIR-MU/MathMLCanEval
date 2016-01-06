/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.api;

import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import java.util.List;

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
    void create(UserDTO user) throws IllegalArgumentException;
    
    /**
     * Method updates given user inside system.
     * @param user to be updated
     * @throws IllegalArgumentException if user is null or does not have valid id. 
     */
    void update(UserDTO user) throws IllegalArgumentException;
    
    /**
     * Method deletes given user from database.
     * @param user to be deleted
     * @throws IllegalArgumentException if user is null or does not have valid id. 
     */
    void delete(UserDTO user) throws IllegalArgumentException;
    
    /**
     * Method obtains user based on its id.
     * @param id of user
     * @return user with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or less than one
     */
    UserDTO getByID(Long id) throws IllegalArgumentException;
   
    /**
     * Method obtains all users from database.
     *
     * @return list of all users, empty list if there are none yet.
     */ 
    List<UserDTO> getAll();    
    
    /**
     * Method returns system user which is responsible for automatic annotations.
     * 
     * @return system user.
     */
    UserDTO getSystemUser() throws RuntimeException;
}
