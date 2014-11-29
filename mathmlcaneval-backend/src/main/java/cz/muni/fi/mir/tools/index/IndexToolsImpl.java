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
package cz.muni.fi.mir.tools.index;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository
public class IndexToolsImpl implements IndexTools
{
    private static final Logger logger = Logger.getLogger(IndexToolsImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void index(Object o)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.index(o);
    }

    @Override
    public void reIndexClass(Class clazz)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try
        {
            logger.info("Reindexing has started");
            fullTextEntityManager.createIndexer(clazz).startAndWait();
        }
        catch (InterruptedException ex)
        {
            logger.fatal(ex);
        }
    }

    @Override
    public void optimize(Class clazz)
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.getSearchFactory().optimize(clazz);
    }
}
