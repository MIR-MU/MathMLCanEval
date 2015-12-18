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
package cz.muni.fi.mir.mathmlcaneval.database.factories;

import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class DomainFactory
{
    
    public UserRole newUserRole()
    {
        return new UserRole();
    }
    
    public UserRole newUserRole(Long id)
    {
        UserRole ur = newUserRole();
        ur.setId(id);
        
        return ur;
    }
    
    public UserRole newUserRole(Long id, String roleName)
    {
        UserRole ur = newUserRole(id);
        ur.setRoleName(roleName);
        
        return ur;
    }
}

