package br.com.erudio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Startup extends SpringBootServletInitializer {
	 
	
	public static void main(String[] args) {
		
		 
		SpringApplication.run(Startup.class, args);		

//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//      
//        Pbkdf2PasswordEncoder pbkdf2Encoder =
//       		new Pbkdf2PasswordEncoder(
//    				"", 8, 185000,
//   				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//       
//        encoders.put("pbkdf2", pbkdf2Encoder);
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
//      
//        String result1 = passwordEncoder.encode("h4llh42602");
//       
//        System.out.println("My hash result1 " + result1);
   
	}

}
