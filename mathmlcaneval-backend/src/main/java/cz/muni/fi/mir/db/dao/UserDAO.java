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
package cz.muni.fi.mir.db.dao;

import java.util.List;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public interface UserDAO extends GenericDAO<User, Long>
{

    /**
     * Method obtains user based on its username.
     *
     * @param username of user
     * @return user with given username, null if there no match
     */
    User getUserByUsername(String username);

    /**
     * Method obtains all users from database.
     *
     * @return list of all users, empty list if there are none yet.
     */
    List<User> getAllUsers();

    /**
     * Method obtains all users having given user role.
     *
     * @param userRole of users to be fetched
     * @return list of all users having given user role, empty list if there are
     * none yet
     */
    List<User> getUsersByRole(UserRole userRole);
}
