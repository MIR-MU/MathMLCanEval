/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.api;

import cz.muni.fi.mir.mathmlcaneval.api.dto.UserRoleDTO;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserRoleService
{
    void create(UserRoleDTO userRoleDTO) throws IllegalArgumentException;
    void update(UserRoleDTO userRoleDTO) throws IllegalArgumentException;
    void delete(UserRoleDTO userRoleDTO) throws IllegalArgumentException;
    
    UserRoleDTO getByID(Long id) throws IllegalArgumentException;
    UserRoleDTO getByName(String roleName) throws IllegalArgumentException;
    
    List<UserRoleDTO> getAll();
}
