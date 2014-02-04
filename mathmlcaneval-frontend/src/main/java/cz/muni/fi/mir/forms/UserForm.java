/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.list.LazyList;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Empt
 */
public class UserForm
{
    private Long id;    
    @Size(min = 3, max = 255,message = "{validator.user.username.length}")
    private String username;
    @Size(min = 3, max = 255,message = "{validator.user.realnname.length}")
    private String realName; 
    @NotEmpty(message = "{validator.user.password.empty}")
    private String password;    
    private String passwordVerify;    
    @Email(message = "{validator.user.email.format}")
    @NotEmpty(message = "{validator.user.email.empty}")
    private String email;
    private String emailVerify;
    
    private List<UserRoleForm> userRoleForms = LazyList.lazyList(new ArrayList<UserRoleForm>(), FactoryUtils.instantiateFactory(UserRoleForm.class));

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

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPasswordVerify()
    {
        return passwordVerify;
    }

    public void setPasswordVerify(String passwordVerify)
    {
        this.passwordVerify = passwordVerify;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmailVerify()
    {
        return emailVerify;
    }

    public void setEmailVerify(String emailVerify)
    {
        this.emailVerify = emailVerify;
    }

    public List<UserRoleForm> getUserRoleForms()
    {
        return userRoleForms;
    }

    public void setUserRoleForms(List<UserRoleForm> userRoleForms)
    {
        this.userRoleForms = userRoleForms;
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
        final UserForm other = (UserForm) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "UserForm{" + "id=" + id + ", username=" + username + ", realName=" + realName + ", password=" + password + ", passwordVerify=" + passwordVerify + ", userRoleForms=" + userRoleForms + '}';
    }
}
