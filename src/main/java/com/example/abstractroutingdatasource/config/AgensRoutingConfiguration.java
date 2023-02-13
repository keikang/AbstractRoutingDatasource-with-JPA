package com.example.abstractroutingdatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.abstractroutingdatasource.repository.agens"
        , entityManagerFactoryRef = "agensEntityManagerFactory"
        , transactionManagerRef = "agensTransactionManager"
)
public class AgensRoutingConfiguration extends RoutingConfiguration{

    @Primary
    @Bean(name = "agensEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean agensEntityManagerFactory(EntityManagerFactoryBuilder builder){
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(agensDatasource())
                .packages("com.example.abstractroutingdatasource.entity")
                .persistenceUnit("agensEntityManager")
                .properties(properties)
                .build();
    }

    @Bean(name = "agensTransactionManager")
    @Primary
    public PlatformTransactionManager agensTransactionManager(@Qualifier(value = "agensEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
