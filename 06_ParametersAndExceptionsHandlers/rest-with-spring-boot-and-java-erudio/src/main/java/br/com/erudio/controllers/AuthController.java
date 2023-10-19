package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Endpoint de autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthServices authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Autentica o usuário e retorna um token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkParam(data))							
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);				
		
		var token = authServices.signin(data);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		return token;
		
		
	}

	private boolean checkParam(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null 
				|| data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
}
