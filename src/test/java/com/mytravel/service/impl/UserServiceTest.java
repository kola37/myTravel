package com.mytravel.service.impl;

import com.mytravel.dao.TourDAO;
import com.mytravel.dao.UserDAO;
import com.mytravel.entity.User;
import com.mytravel.exception.DAOException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.TourService;
import com.mytravel.service.UserService;
import com.mytravel.service.impl.UserServiceImpl;
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
 * Test class UserServiceTest provides methods to test UserService class
 *
 * @author Anatolii Koliaka
 */
public class UserServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserServiceLoginReturnOptionalUser() throws ServiceException {
        Mockito.when(userService.login("login","password")).thenReturn(createTestUser());
        Optional<User> actual = userService.login("login","password");
        Assert.assertTrue(actual.isPresent());
        Mockito.verify(userService).login("login","password");
    }

    private Optional<User> createTestUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        return Optional.of(user);
    }
}
