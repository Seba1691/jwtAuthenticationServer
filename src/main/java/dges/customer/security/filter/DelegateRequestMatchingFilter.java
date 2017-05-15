package dges.customer.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

public class DelegateRequestMatchingFilter extends GenericFilterBean {
    private Filter delegate;
    private RequestMatcher ignoredRequests;

    public DelegateRequestMatchingFilter(RequestMatcher matcher, Filter delegate) {
        this.ignoredRequests = matcher;
        this.delegate = delegate;
    }

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        if(ignoredRequests.matches(request)) {
            chain.doFilter(req,resp);
        } else {
            delegate.doFilter(req,resp,chain);
        }
		
	}

}