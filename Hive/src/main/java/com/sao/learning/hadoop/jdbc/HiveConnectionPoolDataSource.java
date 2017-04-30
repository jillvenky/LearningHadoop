package com.sao.learning.hadoop.jdbc;

import com.sao.learning.hadoop.KerberosAuth;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by saopr on 4/30/2017.
 */
@Component
@PropertySource("classpath:hive.properties")
@ConfigurationProperties(prefix = "datasource.hive")
public class HiveConnectionPoolDataSource extends BasicDataSource {

    Logger logger = LoggerFactory.getLogger(HiveConnectionPoolDataSource.class);

    @Autowired
    private KerberosAuth kerberosAuth;

    @Override
    public Connection getConnection() throws SQLException {
        return kerberosAuth.authenticate((PrivilegedExceptionAction<Connection>)() -> super.getConnection());
    }
}
