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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.abstractroutingdatasource.repository.agens"
        , entityManagerFactoryRef = "agensEntityManagerFactory"
        , transactionManagerRef = "agensTransactionManager"
)
public class AgensRoutingConfiguration extends RoutingConfiguration{

/*    @Autowired(required = false)
    JpaProperties jpaProperties;

    @Autowired(required = false)
    HibernateProperties hibernateProperties;
    @Bean
    @ConfigurationProperties("datasource.agens")
    public DataSourceProperties agensDatasourProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource agensDatasource() {
        return agensDatasourProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }*/

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

/*    @Primary
    @Bean(name = "agensEntityManager")
    public LocalContainerEntityManagerFactoryBean agenseEntityManagerFactoryBean() {
        System.out.println("AgensRoutingConfiguration agenseEntityManagerFactoryBean 메소드 시작");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(agensDatasource());
        em.setPackagesToScan("com.example.abstractroutingdatasource.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);
        System.out.println("AgensRoutingConfiguration agenseEntityManagerFactoryBean 메소드 끝");
        return em;
    }

    @Primary
    @Bean(name = "agensTranscationManager")
    public JpaTransactionManager agenseTransactionManager(@Qualifier(value = "agensEntityManager") EntityManagerFactory entityManagerFactory) {
        System.out.println("AgensRoutingConfiguration agenseTransactionManager 메소드 시작");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        System.out.println("AgensRoutingConfiguration agenseTransactionManager 메소드 끝");
        return transactionManager;
    }*/

}
