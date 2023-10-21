package com.basic.sample.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.basic.sample.repository.sample"})
@EnableJpaRepositories(entityManagerFactoryRef = "sampleEntityManagerFactory", transactionManagerRef = "sampleTransactionManager", basePackages = {"com.basic.sample.repository.sample"})
public class DBConfig {

    @Bean(name = "sampleDataSource")
    @ConfigurationProperties(prefix="spring.sample.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sampleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sampleEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("sampleDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.basic.sample.model.sample").persistenceUnit("sample").build();
    }

    @Bean(name = "sampleTransactionManager")
    public PlatformTransactionManager sampleTransactionManager(@Qualifier("sampleEntityManagerFactory") EntityManagerFactory sampleEntityManagerFactory) {
        return new JpaTransactionManager(sampleEntityManagerFactory);
    }
    
    @Bean
    @ConfigurationProperties(prefix = "spring.sample.datasource.liquibase")
    public LiquibaseProperties sampleLiquibaseProperties() {
        return new LiquibaseProperties();
    }
    
    @Bean
    public SpringLiquibase sampleLiquibase() {
        return springLiquibase(dataSource(), sampleLiquibaseProperties());
    }
    
    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}