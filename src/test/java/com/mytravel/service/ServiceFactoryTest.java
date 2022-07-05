package com.mytravel.service;

import com.mytravel.service.HotelService;
import com.mytravel.service.OrderService;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.impl.HotelServiceImpl;
import com.mytravel.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class ServiceFactoryTest provides methods to test ServiceFactory class
 *
 * @author Anatolii Koliaka
 */
public class ServiceFactoryTest {

    @Test
    public void testGetOrderServiceReturnNewOrderServiceImpl() {
        OrderService actual = ServiceFactory.getInstance().getOrderService();
        Assert.assertEquals(actual.getClass(), OrderServiceImpl.class);
    }

    @Test
    public void testGetHotelServiceReturnNewHotelServiceImpl() {
        HotelService actual = ServiceFactory.getInstance().getHotelService();
        Assert.assertEquals(actual.getClass(), HotelServiceImpl.class);
    }
}
