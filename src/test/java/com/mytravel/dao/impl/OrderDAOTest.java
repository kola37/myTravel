package com.mytravel.dao.impl;


import com.mytravel.entity.Order;
import com.mytravel.exception.DAOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Test class OrderDAOTest provides methods to test OrderDAO class
 *
 * @author Anatolii Koliaka
 */
public class OrderDAOTest {

    @Mock
    private OrderDAOImpl orderDAO;
    @Mock
    private Connection connection;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindOrdersByUserIdReturnListOfOrders() throws DAOException {
        Mockito.when(orderDAO.findOrdersByUserId(connection,1)).thenReturn(createListOfOrders());
        List<Order> actual = orderDAO.findOrdersByUserId(connection,1);
        Assert.assertEquals(8, actual.size());
        Mockito.verify(orderDAO).findOrdersByUserId(connection,1);
    }

    @Test(expected = DAOException.class)
    public void testFindOrderByIdThrowDAOException() throws DAOException {
        Mockito.when(orderDAO.findById(connection,0)).thenThrow(new DAOException("Cannot find order with id=0"))
                .thenReturn(Optional.empty());
        orderDAO.findById(connection,0);
    }

    private List<Order> createListOfOrders() {
        List<Order> orders = new ArrayList<>();
        int i = 0;
        while (i++ < 8) {
            orders.add(new Order());
        }
        return orders;
    }

}
