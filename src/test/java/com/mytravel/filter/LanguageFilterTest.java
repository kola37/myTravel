package com.mytravel.filter;

import com.mytravel.controller.PagePath;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageFilterTest {

    private static final String PARAM_CURRENT_LOCALE = "currentLocale";
    private static final String PARAM_LANG = "en";

    final LanguageFilter languageFilter = new LanguageFilter();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpSession session;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLanguageFilterDoFilterWithLanguageParameter() throws ServletException, IOException {

        Mockito.when(request.getParameter(PARAM_CURRENT_LOCALE)).thenReturn(PARAM_LANG);
        Mockito.when(request.getSession()).thenReturn(session);

        languageFilter.doFilter(request, response, filterChain);

        Mockito.verify(response).sendRedirect(request.getContextPath() + PagePath.PAGE_SUCCESS);
    }

}
