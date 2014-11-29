/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.wrappers;

import org.springframework.security.core.context.SecurityContext;

import cz.muni.fi.mir.mathmlevaluation.db.domain.User;

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
