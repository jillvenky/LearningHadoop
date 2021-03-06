package com.sao.learning.hadoop.jdbc;

import com.sao.learning.hadoop.KerberosAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by saopr on 4/19/2017.
 */
@Component
@PropertySource("classpath:hive.properties")
@ConfigurationProperties(prefix = "datasource.hive")
public class HiveDataSource extends DriverManagerDataSource {

    Logger logger = LoggerFactory.getLogger(HiveDataSource.class);

    @Autowired
    private KerberosAuth kerberosAuth;

    @Override
    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        return kerberosAuth.authenticate((PrivilegedExceptionAction<Connection>) () -> super.getConnectionFromDriverManager(url, props));
    }
}
