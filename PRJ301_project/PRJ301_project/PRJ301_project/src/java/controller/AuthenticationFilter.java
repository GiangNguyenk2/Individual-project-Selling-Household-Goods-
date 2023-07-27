/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    private List<String> whitelist;
    private List<String> whitelistEnd;

    @Override
    public void init(FilterConfig config) throws ServletException {
        whitelist = Arrays.asList("/public,/cart,/signup,/login,/search,/admin/products".split(","));
        whitelistEnd = Arrays.asList(".css,.js,.png,.svg,.jpg,.ttf,.eot,.woff,.woff2".split(","));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
//        chain.doFilter(request, response);
        String requestedUri = request.getRequestURI();
        if (whitelist.stream().anyMatch(path -> requestedUri.replace(request.getContextPath(), "").startsWith(path))
                || whitelistEnd.stream().anyMatch(path -> requestedUri.replace(request.getContextPath(), "").endsWith(path))) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = request.getContextPath() + "/login";

        boolean isLoginRequest = request.getRequestURI().equals(loginURI);
        boolean isLoginPage = request.getRequestURI().endsWith("login.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            response.sendRedirect(request.getContextPath() + "/");
        } else if (isLoggedIn || isLoginRequest || isLoginPage) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
        // cleanup code
    }
}
