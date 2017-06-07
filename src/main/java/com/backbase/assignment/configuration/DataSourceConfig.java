package com.backbase.assignment.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Ahmad on 6/4/2017.
 */
@Configuration
@ComponentScan
class DataSourceConfig {

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${spring.datasource.poolName}")
    private String poolName;

    @Value("${spring.datasource.connectionTimeout}")
    private int connectionTimeout;

    @Value("${spring.datasource.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${spring.datasource.minimumIdle}")
    private int minimumIdle;

    @Value("${spring.datasource.idleTimeout}")
    private int idleTimeout;

    @Bean
    public DataSource primaryDataSource() {
        Properties dsProps = new Properties();
        dsProps.put("url", dataSourceUrl);
        dsProps.put("user", user);
        dsProps.put("password", password);
        dsProps.put("prepStmtCacheSize", 250);
        dsProps.put("prepStmtCacheSqlLimit", 2048);
        dsProps.put("cachePrepStmts", Boolean.TRUE);
        dsProps.put("useServerPrepStmts", Boolean.TRUE);

        Properties configProps = new Properties();
        configProps.put("poolName", poolName);
        configProps.put("maximumPoolSize", maximumPoolSize);
        configProps.put("minimumIdle", minimumIdle);
        configProps.put("dataSourceClassName", dataSourceClassName);
        configProps.put("connectionTimeout", connectionTimeout);
        configProps.put("idleTimeout", idleTimeout);
        configProps.put("dataSourceProperties", dsProps);

        HikariConfig hc = new HikariConfig(configProps);
        return new HikariDataSource(hc);
    }
}