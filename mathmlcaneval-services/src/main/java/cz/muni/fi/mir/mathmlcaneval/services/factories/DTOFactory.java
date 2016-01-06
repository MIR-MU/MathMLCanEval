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
package cz.muni.fi.mir.mathmlcaneval.services.factories;

import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class DTOFactory
{
    public UserRoleDTO newUserRole()
    {
        return new UserRoleDTO();
    }
    
    public UserRoleDTO newUserRole(Long id)
    {
        UserRoleDTO ur = newUserRole();
        ur.setId(id);
        
        return ur;
    }
    
    public UserRoleDTO newUserRole(Long id, String roleName)
    {
        UserRoleDTO ur = newUserRole(id);
        ur.setRoleName(roleName);
        
        return ur;
    }
    
    public UserDTO newUser()
    {
        return new UserDTO();
    }
    
    public UserDTO newUser(Long id)
    {
        UserDTO u = newUser();
        u.setId(id);
        
        return u;
    }
    
    public UserDTO newUser(Long id, String username, String password, String email, String realName, List<UserRoleDTO> roles)
    {
        UserDTO u = newUser(id);
        u.setEmail(email);
        u.setPassword(password);
        u.setRealName(realName);
        u.setRoles(roles);
        u.setUsername(username);
        
        return u;
    }
    
    public UserDTO newUser(Long id, String username, String password, String email, String realName, UserRoleDTO role)
    {
        UserDTO u = newUser(id, username, password, email, realName, new ArrayList<UserRoleDTO>());
        List<UserRoleDTO> roles = new ArrayList<>();
        roles.add(role);
        u.setRoles(roles);
        
        return u;
    }
}

