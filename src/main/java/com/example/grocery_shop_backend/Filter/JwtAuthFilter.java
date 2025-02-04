package com.example.grocery_shop_backend.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;

public class JwtAuthFilter extends GenericFilterBean {

    private final String SECRET_KEY = "S54pMNsJRxq8vHt28rUSzWfKn6AG6aN2dfrs548dg48d545dfdgf"; // Same as in JwtUtil

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null || isTokenExpired(token)) {
            throw new ServletException("Unauthorized: No valid token found or token is expired");
        }

        // Set customer info to request for further use (like in controllers)
        Claims claims = getClaimsFromToken(token);
        httpRequest.setAttribute("customerEmail", claims.getSubject()); // Or customer ID if needed

        chain.doFilter(request, response);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }
}
