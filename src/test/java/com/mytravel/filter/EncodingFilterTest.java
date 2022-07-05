package com.mytravel.filter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilterTest {

    final EncodingFilter encodingFilter = new EncodingFilter();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEncodingFilterDoFilter() throws ServletException, IOException {

        Mockito.when(request.getCharacterEncoding()).thenReturn("UTF-8");
        Mockito.when(request.getRequestURI()).thenReturn("http://localhost:8080/myTravelAgency/");

        encodingFilter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);

    }
}
