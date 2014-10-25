/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Program;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ProgramService
{
    /**
     * Method creates given program.
     * @param program to be created
     * @throws IllegalArgumentException if input is null 
     */
    void createProgram(Program program) throws IllegalArgumentException;
    
    /**
     * Method deletes given program
     * @param program to be updated
     * @throws IllegalArgumentException if program is null or does not have valid id.
     */
    void deleteProgram(Program program) throws IllegalArgumentException;
    
    /**
     * Method updates program inside database.
     * @param program to be updated
     * @throws IllegalArgumentException if program is null or does not have valid id. 
     */
    void updateProgram(Program program) throws IllegalArgumentException;
    
    /**
     * Method obtains program based on its id.
     * @param id of program to be fetched
     * @return program with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or less than one
     */
    Program getProgramByID(Long id) throws IllegalArgumentException;    
    
    /**
     * Method obtains programs based on their names and versions. There may be
     * multiple programs with same name and version but different input
     * parameters. Name and version is checked by exact match.
     *
     * @param name of program
     * @param version version of program
     * @return list of programs having given name and version
     * @throws IllegalArgumentException if any of input is null or zero length
     * string
     */
    List<Program> getProgramByNameAndVersion(String name, String version) throws IllegalArgumentException;
    
    /**
     * Method loads all programs out of database.
     * @return list of all programs, empty list if there are none yet.
     */
    List<Program> getAllPrograms();
}
