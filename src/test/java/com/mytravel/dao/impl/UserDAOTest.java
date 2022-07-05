package com.mytravel.dao.impl;

import com.mytravel.entity.User;
import com.mytravel.exception.DAOException;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.Connection;
import java.util.Optional;

/**
 * Test class UserDAOTest provides methods to test UserDAO class
 *
 * @author Anatolii Koliaka
 */
public class UserDAOTest {

    @Mock
    private UserDAOImpl userDAO;
    @Mock
    private Connection connection;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validate() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testFindByIdOptionalUserIsPresent() throws DAOException {
        Mockito.when(userDAO.findById(connection, 1)).thenReturn(createTestUser());
        Optional<User> actual = userDAO.findById(connection, 1);
        Assert.assertTrue(actual.isPresent());
        Mockito.verify(userDAO).findById(connection, 1);
    }

    @Test
    public void testFindByIdOptionalUserIsCorrect() throws DAOException {
        Mockito.when(userDAO.findById(connection, 1)).thenReturn(createTestUser());
        Optional<User> actual = userDAO.findById(connection, 1);
        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals("My firstName", actual.get().getFirstName());
        Assert.assertEquals("My lastName", actual.get().getLastName());
        Mockito.verify(userDAO).findById(connection, 1);
    }

    @Test
    public void testFindByLoginOptionalUserIsPresentAndCorrect() throws DAOException {
        Mockito.when(userDAO.findByLogin(connection, "myLogin")).thenReturn(createTestUser());
        Optional<User> actual = userDAO.findByLogin(connection, "myLogin");
        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals("myLogin", actual.orElseThrow().getLogin());
        Mockito.verify(userDAO).findByLogin(connection, "myLogin");
    }

    private Optional<User> createTestUser() {
        User user = new User.Builder()
                .withLogin("myLogin")
                .withFirstName("My firstName")
                .withLastName("My lastName")
                .build();
        return Optional.of(user);
    }

}

