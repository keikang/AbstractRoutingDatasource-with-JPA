package com.example.abstractroutingdatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.abstractroutingdatasource.repository.agens"
        , entityManagerFactoryRef = "agensEntityManagerFactory"
        , transactionManagerRef = "agensTransactionManager"
)
public class AgensRoutingConfiguration extends RoutingConfiguration{


    @Primary
    @Bean(name = "agensEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean agensEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(agensDatasource());
        em.setPackagesToScan("com.example.abstractroutingdatasource.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean(name = "agensTransactionManager")
    public JpaTransactionManager agenseTransactionManager(@Qualifier(value = "agensEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
