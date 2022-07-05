package com.mytravel.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Test class DBUtilsTest that provides a methods to test Connection object and connection pool
 *
 * @author Anatolii Koliaka
 */
public class DBUtilsTest {

    @Mock
    private DBUtils dbUtils;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBUtilsGetConnection() throws SQLException {
        Mockito.when(dbUtils.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        int value = dbUtils.getConnection().createStatement().executeUpdate("");
        Assert.assertEquals(value, 1);
    }
}
