package br.com.erudio.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PathVariable;
=======
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> 8819ad3b36b9901f1fe538e1d75aecbe46177713
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.ERole;
import br.com.erudio.model.LoginRequest;
import br.com.erudio.model.MessageResponse;
import br.com.erudio.model.Role;
import br.com.erudio.model.SignupRequest;
import br.com.erudio.model.User;
import br.com.erudio.model.UserInfoResponse;
import br.com.erudio.repositories.RoleRepository;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.services.UserDetailsImpl;
import br.com.erudio.utils.JwtUtils;
import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
<<<<<<< HEAD
	AuthServices authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Autentica o usuário e retorna um token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		
		if (checkParam(data)) {							
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);				
		}
		var token = authServices.signin(data);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		return token;
		
	}

	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username,
			@RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		var token = authServices.refreshToken(username, refreshToken);
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		return token;
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}
	
	private boolean checkParam(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null 
				|| data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
=======
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(
				new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
>>>>>>> 8819ad3b36b9901f1fe538e1d75aecbe46177713
	}

	@PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
	    }

	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(),
	                         signUpRequest.getEmail(),
	                         encoder.encode(signUpRequest.getPassword()));

	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	        switch (role) {
	        case "admin":
	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(adminRole);

	          break;
	        case "mod":
	          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(modRole);

	          break;
	        default:
	          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	          roles.add(userRole);
	        }
	      });
	    }
	    user.setRoles(roles);
	    userRepository.save(user);

	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	  }
	
	 @PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {
	    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body(new MessageResponse("You've been signed out!"));
	  }

}
