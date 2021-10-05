package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.security.DefaultDosProtectionService;
import com.es.phoneshop.model.security.DosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    private DosProtectionService protectionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectionService = DefaultDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (protectionService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
