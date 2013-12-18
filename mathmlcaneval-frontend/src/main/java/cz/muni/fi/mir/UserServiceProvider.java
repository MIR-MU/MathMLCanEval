/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir;

import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Empt
 */
public class UserServiceProvider implements UserDetailsService
{
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserDetails ud = null;
        
        User u = userService.getUserByUsername(username);
        if(u == null)
        {
            throw new UsernameNotFoundException("yolo");
        }
        
        
        ud = new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), getAuthorities(u));
        
        return ud;
        
    }
    
    
    public Collection<GrantedAuthority> getAuthorities(User u )
    {
        List<GrantedAuthority> result = new ArrayList<>();
        
        for(UserRole ur : u.getUserRoles())
        {
            result.add(new SimpleGrantedAuthority(ur.getRoleName()));
        }
        
        
        
        return result;
    }
    
}
