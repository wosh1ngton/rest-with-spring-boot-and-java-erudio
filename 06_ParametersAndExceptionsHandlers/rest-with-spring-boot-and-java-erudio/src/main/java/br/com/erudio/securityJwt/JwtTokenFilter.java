package br.com.erudio.securityJwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider tokenProvider;
	
	
	public JwtTokenFilter(JWTTokenProvider tokenProvider) {		
		this.tokenProvider = tokenProvider;
	}	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String token = tokenProvider.resolveToken((HttpServletRequest) request);
			
		      if (token != null && tokenProvider.validateToken(token)) {
		    	  Authentication auth = tokenProvider.getAuthentication(token);
					if(auth != null) {
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
		      }
		    } catch (Exception e) {
		      logger.error("Cannot set user authentication: {}", e);
		    }

		    filterChain.doFilter(request, response);
		
	}
	
}
