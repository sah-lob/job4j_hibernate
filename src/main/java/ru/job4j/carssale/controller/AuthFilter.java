package ru.job4j.carssale.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        servletResponse.setCharacterEncoding("UTF-8");
        if (request.getRequestURI().contains("login.html") || request.getRequestURI().contains("auth")) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            synchronized (session) {
                if (session.getAttribute("login") == null) {
                    ((HttpServletResponse) servletResponse).sendRedirect(request.getContextPath() + "/carssale/login.html");
                    return;
                }
            }
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}