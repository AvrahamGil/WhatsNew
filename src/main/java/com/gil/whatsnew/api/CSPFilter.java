package com.gil.whatsnew.api;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CSPFilter implements Filter{

	@Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, ServletException {

        /*
    	HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Content-Security-Policy", "script-src 'self' 'nonce-headers' 'nonce-js' https://www.google.com/recaptcha/api.js?render=explicit&onload=ng2recaptchaloaded;"
        		+ " frame-ancestors 'self'; style-src 'self' 'nonce-styling' 'nonce-headers'  https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css https://pro.fontawesome.com/releases/v5.10.0/css/all.css styles.434ded97de11e74b.css ; "
        		+ " ");
        httpResponse.setHeader("Referrer-Policy", "no-referrer");
        httpResponse.setHeader("Permissions-Policy", "geolocation=(self https://whatsnew.me)");

        */

        chain.doFilter(request, response);  
    }
}
