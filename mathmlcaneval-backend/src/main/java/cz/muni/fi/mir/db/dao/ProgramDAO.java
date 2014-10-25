package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Program;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ProgramDAO extends GenericDAO<Program,Long>
{      
    /**
     * Method obtains programs based on their names and versions. There may be
     * multiple programs with same name and version but different input
     * parameters. Name and version is checked by exact match.
     *
     * @param name of program
     * @param version version of program
     * @return list of programs having given name and version
     */
    List<Program> getProgramByNameAndVersion(String name, String version);
    
    /**
     * Method loads all programs out of database.
     * @return list of all programs, empty list if there are none yet.
     */
    List<Program> getAllPrograms();    
}
