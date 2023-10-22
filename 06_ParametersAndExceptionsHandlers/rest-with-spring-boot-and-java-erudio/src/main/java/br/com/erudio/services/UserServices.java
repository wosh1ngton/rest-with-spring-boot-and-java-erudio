package br.com.erudio.services;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService {
	
	@Autowired
	UserRepository repository;	
	
	public UserServices(UserRepository repository) {		
		this.repository = repository;
	}
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name" + username + "!");
		var user = repository.findByUsername(username);
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		
	}
	
	
	
	
}