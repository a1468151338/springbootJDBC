package zkl.filter;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import zkl.service.SysResourceService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/31.
 */
@Order(1)
@WebFilter(filterName = "resourceFilter",urlPatterns = "/*")
public class ResourceFilter implements Filter {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		filterChain.doFilter(request,response);
	}

	@Override
	public void destroy() {

	}
}
