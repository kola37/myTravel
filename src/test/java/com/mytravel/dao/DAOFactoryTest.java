package com.mytravel.dao;

import com.mytravel.dao.impl.TourDAOImpl;
import com.mytravel.dao.impl.UserDAOImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class DAOFactoryTest provides methods to test DAOFactory class
 *
 * @author Anatolii Koliaka
 */
public class DAOFactoryTest {

    @Test
    public void testGetUserDAOReturnNewUserDAOImpl() {
        UserDAO actual = DAOFactory.getInstance().getUserDAO();
        Assert.assertEquals(actual.getClass(), UserDAOImpl.class);
    }

    @Test
    public void testGetTourDAOReturnNewTourDAOImpl() {
        TourDAO actual = DAOFactory.getInstance().getTourDAO();
        Assert.assertEquals(actual.getClass(), TourDAOImpl.class);
    }
}
