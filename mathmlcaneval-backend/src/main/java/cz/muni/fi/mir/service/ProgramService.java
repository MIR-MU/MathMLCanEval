/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service;

import cz.muni.fi.mir.domain.Program;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface ProgramService
{
    void createProgram(Program program);
    void deleteProgram(Program program);
    void updateProgram(Program program);
    
    Program getProgramByID(Long id);
    
    List<Program> getProgramByName(String name);
    List<Program> getProgramByNameAndVersion(String name, String version);
    
    
    List<Program> getAllPrograms();  
}
