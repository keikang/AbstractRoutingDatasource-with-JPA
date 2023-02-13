package com.example.abstractroutingdatasource.config;

import com.example.abstractroutingdatasource.ClientDataSourceRouter;
import com.example.abstractroutingdatasource.ClientDatasource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.abstractroutingdatasource.repository.agens"
        , transactionManagerRef = "transcationManager"
        , entityManagerFactoryRef = "entityManager"
)
public class AgensRoutingConfiguration {

/*    @Bean
    public ClientService clientService(){
        return new ClientService(clientDatasouSource());
    }*/
    @Bean
    public ClientDatasource clientDatasource(){
        System.out.println("RoutingTestConfiguration clientDatasource 생성자");
        return new ClientDatasource(getclientDatasSource());
    }
/*    @Bean("RoutingDataSource")
    public DataSource getclientDatasSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource agensDatasource = agensDatasource();
        DataSource mysqlDatasource = ageDatasource();

        targetDataSources.put(ClientDatabase.AGENS, agensDatasource);
        targetDataSources.put(ClientDatabase.MYSQL, mysqlDatasource);

        ClientDataSourceRouter clientDataSourceRouter = new ClientDataSourceRouter();
        clientDataSourceRouter.setTargetDataSources(targetDataSources);
        clientDataSourceRouter.setDefaultTargetDataSource(agensDatasource);
        return clientDataSourceRouter;
    }*/
    @Bean
    @Primary
    public DataSource getclientDatasSource() {
        ClientDataSourceRouter clientDataSourceRouter = new ClientDataSourceRouter();
        clientDataSourceRouter.initDatasource(agensDatasource(), mysqlDatasource());
        return clientDataSourceRouter;
    }
    @Bean
    @ConfigurationProperties("datasource.agens")
    public DataSourceProperties agensDatasourProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("datasource.mysql")
    public DataSourceProperties mysqlDatasourProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource agensDatasource() {
        return agensDatasourProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSource mysqlDatasource() {
        return mysqlDatasourProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean agenseEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(agensDatasource());
        em.setPackagesToScan("com.example.abstractroutingdatasource.repository.agens");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);
        return em;
    }
/*
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(getclientDatasSource()).packages(Member.class).build();
    }*/

    @Bean(name = "transcationManager")
    public JpaTransactionManager agenseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(agenseEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

/*    @Bean("routingLazyDataSource")
    public DataSource routingLazyDataSource(@Qualifier("RoutingDataSource") DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("routingLazyDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }*/
/*    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(getclientDatasSource()).packages(Member.class).build();
    }*/

}
