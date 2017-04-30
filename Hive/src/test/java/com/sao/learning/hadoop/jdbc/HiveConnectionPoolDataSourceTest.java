package com.sao.learning.hadoop.jdbc;

import com.sao.learning.hadoop.KerberosAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by saopr on 4/30/2017.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {KerberosAuth.class, HiveConnectionPoolDataSource.class})
@ComponentScan
@EnableAutoConfiguration
public class HiveConnectionPoolDataSourceTest {

    private final Long MAX_WAIT = new Long(1000 * 60 * 5);
    private final Integer MAX_ACTIVE = new Integer(8);
    private final String VALIDATION_QUERY = "select * from ";

    @Autowired
    HiveConnectionPoolDataSource hiveConnectionPoolDataSource;

    @Test
    public void testConnection() throws SQLException {
        hiveConnectionPoolDataSource.setMaxWait(MAX_WAIT);
        hiveConnectionPoolDataSource.setMaxActive(MAX_ACTIVE);
        hiveConnectionPoolDataSource.setValidationQuery(VALIDATION_QUERY);
        hiveConnectionPoolDataSource.setTestOnBorrow(true);
        Connection connection = hiveConnectionPoolDataSource.getConnection();
        assertThat(connection, is(notNullValue()));
    }
}
