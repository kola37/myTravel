package com.mytravel.service.impl;

import com.mytravel.entity.Hotel;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Test class HotelServiceTest provides methods to test HotelService class
 *
 * @author Anatolii Koliaka
 */
public class HotelServiceTest {

    @Mock
    private HotelServiceImpl hotelService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHotelServiceRetrieveAllReturnListOfHotels() throws ServiceException {
        Mockito.when(hotelService.retrieveAll()).thenReturn(createListOfHotels());
        List<Hotel> actual = hotelService.retrieveAll();
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(5, actual.size());
    }

    private List<Hotel> createListOfHotels() {
        List<Hotel> hotels = new ArrayList<>();
        int i = 0;
        while (i++ < 5) {
            hotels.add(new Hotel());
        }
        return hotels;
    }

}
