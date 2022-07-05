package com.mytravel.dao.impl;

import com.mytravel.entity.Tour;
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

/**
 * Test class TourDaoTest provides methods to test TourDao class
 *
 * @author Anatolii Koliaka
 */
public class TourDAOTest {

    @Mock
    private TourDAOImpl tourDAO;
    @Mock
    private Connection connection;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllToursReturnList() throws DAOException {
        Mockito.when(tourDAO.findAll(connection)).thenReturn(createListOfTours());
        List<Tour> actual = tourDAO.findAll(connection);
        Assert.assertFalse(actual.isEmpty());
        Mockito.verify(tourDAO).findAll(connection);
    }

    @Test
    public void testFindAllToursReturnListWithCorrectSize() throws DAOException {
        Mockito.when(tourDAO.findAll(connection)).thenReturn(createListOfTours());
        List<Tour> actual = tourDAO.findAll(connection);
        Assert.assertEquals(5, actual.size());
        Mockito.verify(tourDAO).findAll(connection);
    }

    private List<Tour> createListOfTours() {
        List<Tour> tours = new ArrayList<>();
        int i = 0;
        while (i++ < 5) {
            tours.add(new Tour());
        }
        return tours;
    }
}
