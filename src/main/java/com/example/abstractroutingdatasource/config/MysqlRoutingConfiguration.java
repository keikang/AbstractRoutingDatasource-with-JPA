package com.example.abstractroutingdatasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.abstractroutingdatasource.repository.mysql"
        , entityManagerFactoryRef = "mysqlEntityManagerFactory"
        , transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlRoutingConfiguration extends RoutingConfiguration{


/*    @Autowired(required = false)
    JpaProperties jpaProperties;

    @Autowired(required = false)
    HibernateProperties hibernateProperties;
    @Bean
    @ConfigurationProperties("datasource.mysql")
    public DataSourceProperties mysqlDatasourProperties(){
        return new DataSourceProperties();
    }


    @Bean
    public DataSource mysqlDatasource() {
        return mysqlDatasourProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }*/

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder){
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(mysqlDatasource())
                .packages("com.example.abstractroutingdatasource.entity")
                .persistenceUnit("mysqlEntityManager")
                .properties(properties)
                .build();
    }

    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier(value = "mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
