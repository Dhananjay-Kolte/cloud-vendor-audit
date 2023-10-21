package com.basic.sample.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

	private static final String AUTH_HEADER_KEY = "X-API-KEY";

	@Value("${sample.api-key}")
	private String sample_API_KEY;

	private static final String[] SWAGGER_WHITELIST = {
			// -- swagger ui
			"/v2/api-docs",
			"/swagger-resources",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/",
			"/csrf",
			"/api/v1/attack/subscribe/",
			"/swagger-ui/index.html"
	};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods",
				"ACL, CANCELUPLOAD, CHECKIN, CHECKOUT, COPY, DELETE, GET, HEAD, LOCK, MKCALENDAR, MKCOL, MOVE, OPTIONS, POST, PROPFIND, PROPPATCH, PUT, REPORT, SEARCH, UNCHECKOUT, UNLOCK, UPDATE, VERSION-CONTROL");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Key, Authorization, X-API-KEY");

		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
		} else {
			boolean flag = false;
			for(String pattern : SWAGGER_WHITELIST) {
				if(req.getRequestURI().contains(pattern)) {
					flag = true;
					break;
				}
			}

			if(flag || sample_API_KEY.equals(req.getHeader(AUTH_HEADER_KEY)))
				chain.doFilter(request, response);
			else {
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.reset();
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}
}