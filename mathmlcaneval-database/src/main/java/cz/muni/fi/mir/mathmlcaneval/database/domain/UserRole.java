/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * @author Dominik Szalai - emptulik at gmail.com
 */
@NamedQuery(name = "UserRole.getAll", query = "SELECT ur FROM usersrole ur")
@Entity(name = "usersrole")
@Data
public class UserRole implements Serializable
{
    private static final long serialVersionUID = -2286980006085077045L;

    @Id
    @Column(name = "userrole_id", nullable = false)
    @SequenceGenerator(name = "userroleid_seq", sequenceName = "userroleid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userroleid_seq")
    private Long id;
    @Column(name = "rolename")
    private String roleName;
}
