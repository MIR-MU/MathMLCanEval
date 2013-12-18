
package cz.muni.fi.mir.wrappers;

import org.springframework.security.core.context.SecurityContext;

/**
 *
 * @author Dominik Szalai <a href="mailto:emptulik&#64;gmail.com">emptulik&#64;gmail.com</a>
 */
public interface SecurityContextFacade
{
    /**
     * Method used for obtaining security context
     * @return security context
     */
    SecurityContext getContext();
    
    /**
     * Method used for setting security context
     * @param securityContext to be set
     */
    void setContext(SecurityContext securityContext);
    
    /**
     * Method used for obtaining logged user from Security context. If there is no user logged in 
     * "#null" value is returned. Otherwise we get username of logged user.
     * @return username of logged user, or #null if there is none user logged.
     */
    String getLoggedUser();
}
