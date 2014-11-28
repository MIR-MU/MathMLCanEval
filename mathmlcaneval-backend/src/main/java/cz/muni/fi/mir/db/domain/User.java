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
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import org.hibernate.search.annotations.Field;

/**
 * @author Dominik Szalai
 * 
 * @since 1.0
 * @version 1.0
 */
@Entity(name = "users")   // because user is 99 resrved word...
public class User implements Serializable
{

    private static final long serialVersionUID = -8362731814249179743L;
    @Id
    @Column(name = "user_id",nullable = false)
    @SequenceGenerator(name="userid_seq", sequenceName="userid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userid_seq")
    private Long id;
    @Column(name = "username", unique = true)
    @Field
    private String username;
    @Column(name = "realName")
    private String realName;
    @Column(name = "password")
    private String password;    
    @Column(name = "email", unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<UserRole> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles)
    {
        this.userRoles = userRoles;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username=" + username + ", realName=" + realName + ", password=" + password + ", email=" + email + ", userRoles=" + userRoles + '}';
    }
}
