package cz.muni.fi.mir.mathmlcaneval.test.dao;

import cz.muni.fi.mir.mathmlcaneval.database.DatabaseConfiguration;
import cz.muni.fi.mir.mathmlcaneval.test.support.PodamFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.jemos.podam.api.PodamFactory;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 30.07.2016.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config/database-test.properties")
public class DatabaseTestConfiguration extends DatabaseConfiguration
{
    @Autowired
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        JpaTransactionManager txm = new JpaTransactionManager();
        txm.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());

        return txm;
    }

    @Bean
    public PodamFactory podamFactory()
    {
        return new PodamFactoryImpl();
    }


}
