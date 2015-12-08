/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity
public class UserRole implements Serializable
{
    private static final long serialVersionUID = -2286980006085077045L;
    
    @Id
    @Column(name = "userrole_id",nullable = false)
    @SequenceGenerator(name="userroleid_seq", sequenceName="userroleid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userroleid_seq")
    private Long id;
    @Column(name="rolename")
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
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final UserRole other = (UserRole) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "UserRole{" + "id=" + id + ", roleName=" + roleName + '}';
    }
}
