package com.springboot.springmongooauthjwt.filter;

import com.springboot.springmongooauthjwt.exception.BadRequestException;
import com.springboot.springmongooauthjwt.middleware.JWTMiddleware;
import com.springboot.springmongooauthjwt.service.UserService;
import com.springboot.springmongooauthjwt.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTMiddleware jwtUtility;

    @Autowired
    private UserServiceImplementation userService;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        if(null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            //Exception
            userName = jwtUtility.getUsernameFromToken(token);

        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails
                    = userService.loadUserByUsername(userName);


            if(jwtUtility.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
