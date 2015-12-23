/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.database.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Configuration
@ComponentScan(basePackages = "cz.muni.fi.mir.mathmlcaneval.database")

public class DatabaseConfiguration
{
    @Configuration
    @Profile("production")
    @PropertySource(value =
    {
        "classpath:config/database.properties"
    }, ignoreResourceNotFound = true)
    static class Production
    {
    }

    @Configuration
    @Profile("test")
    @PropertySource(value =
    {
        "classpath:config/database-test.properties"
    }, ignoreResourceNotFound = true)
    @EnableTransactionManagement
    static class Test
    {

    }

    @Autowired
    private Environment environment;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource getDataSource() throws Exception
    {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try
        {
            ds.setDriverClass(environment.getProperty("database.hibernate.connection.driver_class"));
            ds.setJdbcUrl(environment.getProperty("database.hibernate.connection.url"));
            ds.setUser("database.hibernate.connection.username");
            ds.setPassword("database.hibernate.connection.password");
            ds.setMinPoolSize(environment.getProperty("database.hibernate.connection.poolsize.min", Integer.class));
            ds.setMaxPoolSize(environment.getProperty("database.hibernate.connection.poolsize.max", Integer.class));
            ds.setCheckoutTimeout(environment.getProperty("database.hibernate.connection.timeout", Integer.class));
            ds.setMaxStatements(environment.getProperty("database.hibernate.connection.max_statements", Integer.class));
            ds.setIdleConnectionTestPeriod(environment.getProperty("database.hibernate.connection.idle_test_period", Integer.class));
            ds.setInitialPoolSize(environment.getProperty("database.hibernate.connection.poolsize.init", Integer.class));
        }
        catch (PropertyVetoException pve)
        {
            throw new Exception(pve);
        }

        return ds;
    }

    @Bean(name = "entityManagerFactory", destroyMethod = "destroy")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws Exception
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(getDataSource());
        entityManagerFactory.setPersistenceUnitName("mathmlcanevalPU");

        Properties prop = new Properties();
        prop.put("hibernate.dialect", environment.getProperty("database.hibernate.connection.dialect"));
        prop.put("hibernate.hbm2ddl.auto", environment.getProperty("database.hibernate.hbm2ddl.auto"));
        prop.put("hibernate.show_sql", environment.getProperty("database.hibernate.show_sql"));
        prop.put("hibernate.format_sql", environment.getProperty("database.hibernate.format_sql"));
        prop.put("hibernate.default_schema", environment.getProperty("database.hibernate.default_schema"));
        prop.put("hibernate.jdbc.batch_size", environment.getProperty("database.hibernate.jdbc.batch_size", Integer.class));

        entityManagerFactory.setJpaProperties(prop);

        return entityManagerFactory;
    }

    @Bean(name = "transactionManager")
    @Profile("test")
    public AbstractPlatformTransactionManager txManager() throws Exception
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        
        return txManager;
    }
}
