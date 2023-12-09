package br.com.erudio.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.securityJwt.JWTTokenProvider;

@Service
public class AuthServices {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {			
			
			//Tenta autenticar o usuário, se falhar a exceção BadCredentials é exibida			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(data.getUsername(),  data.getPassword()));
			
			//Passando pela autenticação recupera os dados do usuário
			var user = repository.findByUsername(data.getUsername());

			var tokenResponse = new TokenVO();
			
			if (user != null) {
				//Armazena o token
				tokenResponse = tokenProvider.createAccessToken(data.getUsername(), user.getRoles());
			}else {
				throw new UsernameNotFoundException("Username " + data.getUsername() + " não encontrado!");
			}
			
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Nome de usuário ou senha inválidos");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = repository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
	
	
	
	
}