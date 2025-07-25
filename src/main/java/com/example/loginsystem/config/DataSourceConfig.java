package com.example.loginsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
/**
 *
 *
 * spring boot would auto configure the data source if you have the right dependencies in your pom.xml
 * this type is hard coded, use application properties to configure the data source better than this
 *
 *
 @Configuration
public class DataSourceConfig {
        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3307/login_db");
            dataSource.setUsername("root");
            dataSource.setPassword("kk95681932");
            return dataSource;
        }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }




}

*/

