package com.mytravel.service.impl;

import com.mytravel.entity.Tour;
import com.mytravel.entity.User;
import com.mytravel.exception.DAOException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.TourService;
import com.mytravel.service.UserService;
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
 * Test class TourServiceTest provides methods to test TourService class
 *
 * @author Anatolii Koliaka
 */
public class TourServiceTest {

    @Mock
    private TourServiceImpl tourService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTourServiceRetrieveTourByIdReturnOptionalTour() throws ServiceException {
        Mockito.when(tourService.retrieveTourById(1)).thenReturn(createTestTour());
        Optional<Tour> actual = tourService.retrieveTourById(1);
        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals("My test tour", actual.orElseThrow().getName());
        Mockito.verify(tourService).retrieveTourById(1);
    }

    @Test(expected = ServiceException.class)
    public void testRetrieveTourByIdThrowServiceException() throws ServiceException {
        Mockito.when(tourService.retrieveTourById(0)).thenThrow(new ServiceException("Cannot retrieve tour with id=0"))
                .thenReturn(Optional.empty());
        tourService.retrieveTourById(0);
    }

    private Optional<Tour> createTestTour() {
        Tour tour = new Tour.Builder()
                .withId(1)
                .withName("My test tour")
                .build();
        return Optional.of(tour);
    }
}
