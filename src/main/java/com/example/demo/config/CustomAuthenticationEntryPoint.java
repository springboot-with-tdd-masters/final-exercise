package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
    		throws IOException, ServletException {
//
//    	Map response = new HashMap();
//    	response.put("message", "Access Denied");
//    	response.put("timestamp", new Date().toGMTString());
//    	response.put("status", 403);
//
//
//    	res.setContentType("application/json;charset=UTF-8");
//        res.setStatus(403);
//        res.getWriter().write(new ObjectMapper().writeValueAsString(response));
    }
}