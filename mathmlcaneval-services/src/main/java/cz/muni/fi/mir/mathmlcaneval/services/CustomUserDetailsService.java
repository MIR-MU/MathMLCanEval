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
package cz.muni.fi.mir.mathmlcaneval.services;

import cz.muni.fi.mir.mathmlcaneval.database.UserDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "cstomUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserDetails userDetails = null;
        cz.muni.fi.mir.mathmlcaneval.database.domain.User result = userDAO.getByUsername(username);
        if(result == null)
        {
            throw new UsernameNotFoundException(username);
        }
        else
        {
            userDetails = new User(result.getUsername(), result.getPassword(), getAuthorities(result));
        }
        
        return userDetails;
        
    }
    
    private Collection<GrantedAuthority> getAuthorities(cz.muni.fi.mir.mathmlcaneval.database.domain.User user)
    {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());
        for(UserRole ur : user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(ur.getRoleName()));
        }
        
        return authorities;
    }
    
}
