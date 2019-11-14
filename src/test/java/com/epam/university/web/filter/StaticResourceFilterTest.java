package com.epam.university.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InjectMocks;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StaticResourceFilterTest {

    public static final String CONTEXT_PATH = "/context";
    public static final String APP_PATH = "/app/someCommand";
    public static final String UI_PATH = CONTEXT_PATH + "/ui/file.exe";
    public static final String RESOURCE_PATH = CONTEXT_PATH + "/resources/somefile.ext";
    @InjectMocks
    private StaticResourceFilter instance;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
    }

    @Test
    public void shouldSkipWhenResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(RESOURCE_PATH);
        
        instance.doFilter(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldSkipWhenUIResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(UI_PATH);
        
        instance.doFilter(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    } 
    @Test
    public void shouldSkipWhenServletPath() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(CONTEXT_PATH + APP_PATH);
        
        instance.doFilter(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void shouldForwardResourceToServlet() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(CONTEXT_PATH + "/someCommand");
        when(request.getRequestDispatcher(APP_PATH)).thenReturn(requestDispatcher);

        instance.doFilter(request, response, filterChain);
        
        verify(requestDispatcher).forward(request, response);
    }
}
