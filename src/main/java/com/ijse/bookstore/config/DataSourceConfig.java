package com.ijse.bookstore.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
    @EnableTransactionManagement
    public class DataSourceConfig {

        @Bean
        @ConfigurationProperties("spring.datasource.command")
        public DataSourceProperties commandDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        @ConfigurationProperties("spring.datasource.query")
        public DataSourceProperties queryDataSourceProperties() {
            return new DataSourceProperties();
        }

        //Command
        @Bean(name = "commandDataSource")
        @Primary
        @ConfigurationProperties("spring.datasource.command.hikari")
        public DataSource commandDataSource() {
            return commandDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .type(HikariDataSource.class)
                    .build();
        }

        //Query
        @Bean(name = "queryDataSource")
        @ConfigurationProperties("spring.datasource.query.hikari")
        public DataSource queryDataSource() {
            return queryDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JpaVendorAdapter jpaVendorAdapter() {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setShowSql(true);
            adapter.setGenerateDdl(true);
            adapter.setDatabase(Database.MYSQL);
            return adapter;
        }
    }
