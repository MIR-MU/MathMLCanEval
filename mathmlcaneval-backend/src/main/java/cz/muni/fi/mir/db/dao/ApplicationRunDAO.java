package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;

/**
 * The purpose of this interface is to provide basic CRUD operations and search
 * functionality on {@link cz.muni.fi.mir.db.domain.ApplicationRun} objects
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
public interface ApplicationRunDAO
{

    /**
     * Method creates ApplicationRun inside database.
     *
     * @param applicationRun ApplicationRun to be persisted.
     */
    void createApplicationRun(ApplicationRun applicationRun);

    /**
     * Method updates given ApplicationRun inside database
     *
     * @param applicationRun ApplicationRun to be updated.
     */
    void updateApplicationRun(ApplicationRun applicationRun);

    /**
     * Method removes given ApplicationRun from database. Because Entity manager
     * just checks ID, no other values than ID have to be set.
     *
     * @param applicationRun to be deleted
     */
    void deleteApplicationRun(ApplicationRun applicationRun);

    /**
     * Method fetches ApplicationRun by given id passed as method argument.
     *
     * @param id of ApplicationRun to be obtained.
     * @return ApplicationRun with given ID passed as method argument, if there
     * is no such ID then null is returned.
     */
    ApplicationRun getApplicationRunByID(Long id);

    /**
     * Method obtains all ApplicationRuns from database in <b>DESCENDING</b>
     * order. Newer ApplicationRuns are therefore in front and the old ones are
     * in the back.
     *
     * @return List of all ApplicationRuns in descending order. If there are no
     * ApplicationRuns yet, empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRuns();

    /**
     * Method obtains all ApplicationRuns from database in <b>DESCENDING</b>
     * order. Newer ApplicationRuns are therefore in front and the old ones are
     * in the back. Method is usable for processing AJAX request, e.g. when
     * using pagination and we do not want to get all entries at once, which is
     * not effective when having lots of records.
     *
     * @param start start position of result set
     * @param end end position of result set
     * @return List of ApplicationRuns from given range, if there are no entries
     * yet, empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end);

    /**
     * Method obtains all ApplicationRuns made by given user specified as method
     * argument. Result is ordered in <b>DESCENDING</b> order.
     *
     * @param user of whose ApplicationRuns we want to retrieve.
     * @return List of ApplicationRuns made by given User. If there are no
     * entries for User yet, then empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRunsByUser(User user);

    /**
     * Method obtains all ApplicationRuns run with given Revision specified as
     * method argument. Result is ordered in <b>DESCENDING</b> order.
     *
     * @param revision used for ApplicationRun
     * @return List of ApplicationRuns which used given Revision, if there are
     * no such ApplicationRuns then empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision);

    /**
     * Method obtains all ApplicationRuns run with given Configuration specified
     * as method argument. Result is ordered in <b>DESCENDING</b> order.
     *
     * @param configuration used for ApplicationRun
     * @return List of ApplicationRuns which used given Configuration, if there
     * are no such ApplicationRuns then empty List is returned.
     */
    List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration);
}
