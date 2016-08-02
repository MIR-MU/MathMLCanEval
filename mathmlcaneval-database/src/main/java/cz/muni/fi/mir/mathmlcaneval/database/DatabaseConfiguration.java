package cz.muni.fi.mir.mathmlcaneval.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 30.07.2016.
 */
//@Configuration
//@ComponentScan("cz.muni.fi.mir.mathmlcaneval.database")
@Log4j2
public class DatabaseConfiguration
{
    @Autowired
    private Environment environment;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource()
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try
        {
            dataSource.setDriverClass(environment.getProperty("database.hibernate.connection.driver_class"));
        }
        catch (PropertyVetoException pve)
        {
            throw new RuntimeException(pve);
        }

        dataSource.setJdbcUrl(environment.getProperty("database.hibernate.connection.url"));
        dataSource.setUser(environment.getProperty("database.hibernate.connection.username"));
        dataSource.setPassword(environment.getProperty("database.hibernate.connection.password"));
        dataSource.setMinPoolSize(environment.getProperty("database.hibernate.connection.poolsize.min", Integer.class));
        dataSource.setMaxPoolSize(environment.getProperty("database.hibernate.connection.poolsize.max", Integer.class));
        dataSource.setCheckoutTimeout(environment.getProperty("database.hibernate.connection.timeout", Integer.class));
        dataSource.setMaxStatements(environment.getProperty("database.hibernate.connection.max_statements", Integer.class));
        dataSource.setIdleConnectionTestPeriod(environment.getProperty("database.hibernate.connection.idle_test_period", Integer.class));
        dataSource.setInitialPoolSize(environment.getProperty("database.hibernate.connection.poolsize.init", Integer.class));
        dataSource.setPreferredTestQuery(environment.getProperty("database.hibernate.preferredTestQuery"));

        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("mathmlcanevalPU"); // move to config ?
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties());
        entityManagerFactoryBean.setDataSource(dataSource());

        return entityManagerFactoryBean;
    }


    private Map<String, Object> jpaProperties()
    {
        Map<String, Object> jpaProperties = new HashMap<>();

        jpaProperties.put("hibernate.dialect", environment.getProperty("database.hibernate.connection.dialect"));
        jpaProperties.put("hibernate.default_schema", environment.getProperty("database.hibernate.default_schema"));
        jpaProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("database.hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.show_sql", environment.getProperty("database.hibernate.show_sql", Boolean.class));
        jpaProperties.put("hibernate.format_sql", environment.getProperty("database.hibernate.format_sql", Boolean.class));
        jpaProperties.put("hibernate.jdbc.batch_size", environment.getProperty("database.hibernate.jdbc.batch_size", Integer.class));


        return jpaProperties;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
