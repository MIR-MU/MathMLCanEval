package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Program;
import java.util.List;

/**
 * The purpose of this interface is to provide basic CRUD operations and search
 * functionality on {@link cz.muni.fi.mir.db.domain.Program} objects
 * persisted inside any given database engine specified by configuration. Since
 * there might be some functionality that requires more operation calls, no
 * transaction management should be managed on this layer. Also no validation is
 * made on this layer so make sure you do not pass non-valid objects into
 * implementation of this DAO (Database Access Object) layer.
 *
 * @author Dominik Szalai
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface ProgramDAO
{
    void createProgram(Program program);
    void deleteProgram(Program program);
    void updateProgram(Program program);
    
    Program getProgramByID(Long id);
    
    List<Program> getProgramByName(String name);
    List<Program> getProgramByNameAndVersion(String name, String version);
    
    
    List<Program> getAllPrograms();    
}
