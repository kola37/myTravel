package com.mytravel.dao.impl;

import com.mytravel.entity.Hotel;
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
import java.util.Optional;

/**
 * Test class HotelDaoTest provides methods to test HotelDao class
 *
 * @author Anatolii Koliaka
 */
public class HotelDAOTest {

    @Mock
    private HotelDAOImpl hotelDAO;
    @Mock
    private Connection connection;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByNameReturnOptionalHotel() throws DAOException {
        Mockito.when(hotelDAO.findByName(connection,"Test Hotel")).thenReturn(createTestHotel());
        Optional<Hotel> actual = hotelDAO.findByName(connection,"Test Hotel");
        Assert.assertTrue(actual.isPresent());
        Mockito.verify(hotelDAO).findByName(connection,"Test Hotel");
    }

    private Optional<Hotel> createTestHotel() {
        Hotel hotel = new Hotel.Builder()
                .withName("Test Hotel")
                .build();
        return Optional.of(hotel);
    }
}
