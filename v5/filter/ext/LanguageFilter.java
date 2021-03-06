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
 * @author admin
 * 
 * 
 * 
 * Transformations based on Language
 * 
 * 
 */
public class LanguageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String acceptedLanguagesString = httpRequest.getHeader("Accept-Language");
        if (acceptedLanguagesString != null) {
            String[] acceptedLanguages = acceptedLanguagesString.split(",");
        }
    }

    @Override
    public void destroy() {
    }
}
