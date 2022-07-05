package com.mytravel.service.impl;

import com.mytravel.dao.impl.OrderDAOImpl;
import com.mytravel.entity.Order;
import com.mytravel.entity.User;
import com.mytravel.exception.ServiceException;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Test class UserServiceTest provides methods to test UserService class
 *
 * @author Anatolii Koliaka
 */
public class OrderServiceTest {

    @Mock
    private OrderServiceImpl orderService;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOrderServiceAddNewOrderReturnTrueWhenOrderCreated() throws ServiceException {
        Date date = new Date();
        Mockito.when(orderService.addNewOrder(1, "1", "registered",date, "0", "100")).thenReturn(true);
        boolean actual = orderService.addNewOrder(1, "1", "registered",date, "0", "100");
        Assert.assertTrue(actual);
        Mockito.verify(orderService).addNewOrder(1, "1", "registered",date, "0", "100");
    }

    @Test
    public void testOrderServiceRetrieveAllReturnListOfOrders() throws ServiceException {
        Mockito.when(orderService.retrieveAll()).thenReturn(createListOfOrders());
        List<Order> actualOrders = orderService.retrieveAll();
        Assert.assertFalse(actualOrders.isEmpty());
        Assert.assertEquals(8, actualOrders.size());
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
