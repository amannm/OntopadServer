/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.filter.ext;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Transformations based on Charset
 * 
 * 
 * 
 */
public class CharsetFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String acceptedCharsetsString = httpRequest.getHeader("Accept-Charset");
        if (acceptedCharsetsString != null) {
            String[] acceptedCharsets = acceptedCharsetsString.split(",");
        }
    }

    @Override
    public void destroy() {
    }
}
