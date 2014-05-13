package cz.muni.fi.mir.wrappers;

import cz.muni.fi.mir.db.domain.User;
import org.springframework.security.core.context.SecurityContext;

/**
 *
 * @author Dominik Szalai emptulik@gmail.com
 * @since 1.0
 * @version 1.0
 */
public interface SecurityContextFacade
{

    /**
     * Method used for obtaining security context
     *
     * @return security context
     */
    SecurityContext getContext();

    /**
     * Method used for setting security context
     *
     * @param securityContext to be set
     */
    void setContext(SecurityContext securityContext);

    /**
     * Method used for obtaining logged user from Security context. If there is
     * no user logged in "#null" value is returned. Otherwise we get username of
     * logged user.
     *
     * @return username of logged user, or #null if there is none user logged.
     */
    String getLoggedUser();

    /**
     * Method converts result from {@link #getLoggedUser() } into proper
     * database object. If not found null is returned.
     *
     * @return null, or representation of user with given username (really
     * should not occur).
     */
    User getLoggedEntityUser();
}
