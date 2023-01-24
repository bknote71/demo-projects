package com.bknote71.ajouportal.security;

import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PermitAllFilter extends FilterSecurityInterceptor {

    private final List<AntPathRequestMatcher> permitAllResources;
    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private boolean observeOncePerRequest = true;


    public PermitAllFilter(String... permitAllResources) {
        this.permitAllResources = Arrays.stream(permitAllResources)
                .map(AntPathRequestMatcher::new)
                .toList();
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        if (checkIfPermitAllResource(object))
            return null;

        return super.beforeInvocation(object);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // first time this request being called, so perform security checking
        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }
        InterceptorStatusToken token = beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    private boolean isApplied(FilterInvocation filterInvocation) {
        return (filterInvocation.getRequest() != null)
                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
    }

    private boolean checkIfPermitAllResource(Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (AntPathRequestMatcher resource : permitAllResources) {
            if (resource.matches(request))
                return true;
        }
        return false;
    }
}
