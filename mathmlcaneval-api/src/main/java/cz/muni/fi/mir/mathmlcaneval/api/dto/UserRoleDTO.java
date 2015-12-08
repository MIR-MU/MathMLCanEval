/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.api.dto;

import java.util.Objects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class UserRoleDTO
{

    private Long id;
    private String roleName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final UserRoleDTO other = (UserRoleDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "UserRoleDTO{" + "id=" + id + ", roleName=" + roleName + '}';
    }
}
