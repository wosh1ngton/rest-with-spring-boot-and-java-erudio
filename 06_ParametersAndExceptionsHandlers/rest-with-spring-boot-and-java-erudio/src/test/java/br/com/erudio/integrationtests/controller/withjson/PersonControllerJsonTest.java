package br.com.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.dto.AccountCredentialsVO;
import br.com.erudio.integrationtests.dto.PersonDTO;
import br.com.erudio.integrationtests.dto.TokenVO;
import br.com.erudio.integrationtests.dto.WrapperPersonDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonDTO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonDTO();
		
	}
	
	@Test
	@Order(0)
	public void testAuthorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("wos", "h4llh42602");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)				
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
						
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();		
		
		
		var content =
			given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)					
					.body(person)					
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body().asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		System.out.print(person.toString());
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		
		assertTrue(createdPerson.getId() > 0);
		
		
		assertEquals("Nelson",createdPerson.getFirstName());
		assertEquals("Mandela",createdPerson.getLastName());
		assertEquals("BSB - DF",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
		
		
		
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		
		person.setLastName("Mandela");		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)			
				.body(person)
				.when()
							.post()
						.then()
							.statusCode(200)
						.extract()
							.body().asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertTrue(createdPerson.getEnabled());
		
		assertEquals(1,createdPerson.getId());
		assertEquals("Nelson",createdPerson.getFirstName());
		assertEquals("Mandela",createdPerson.getLastName());
		assertEquals("BSB - DF",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
		
		
		
		
	}	
	
	@Test
	@Order(3)
	public void testDisabledPersonById() throws JsonMappingException, JsonProcessingException {
		
			
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)					
							.pathParam("id", person.getId())					
						.when()
							.patch("{id}")
						.then()
							.statusCode(200)
						.extract()
							.body().asString();
		System.out.println("igual a content: " + content.toString());
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		System.out.println("igual a createdPerson: " + createdPerson.toString());
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertFalse(createdPerson.getEnabled());
		
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals(1,createdPerson.getId());
		assertEquals("Nelson",createdPerson.getFirstName());
		assertEquals("Mandela",createdPerson.getLastName());
		assertEquals("BSB - DF",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
		
		
		
	}	
	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)					
							.pathParam("id", person.getId())					
						.when()
							.get("{id}")
						.then()
							.statusCode(200)
						.extract()
							.body().asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals(1,createdPerson.getId());
		assertEquals("Nelson",createdPerson.getFirstName());
		assertEquals("Mandela",createdPerson.getLastName());
		assertEquals("BSB - DF",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
		
		
		
	}	

	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {	
			
		
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)					
							.pathParam("id", person.getId())					
						.when()
							.delete("{id}")
						.then()
							.statusCode(204);
		
		
	}
	
	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.queryParams("page", 3, "limit", 10, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
		var people = wrapper.getEmbedded().getPersons();
		
		PersonDTO foundPersonOne = people.get(0);
				
		assertNotNull(foundPersonOne.getId());		
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		assertTrue(foundPersonOne.getEnabled());
		
		assertEquals(676, foundPersonOne.getId());
		
		assertEquals("Allyn", foundPersonOne.getFirstName());
		assertEquals("Drinkeld", foundPersonOne.getLastName());
		assertEquals("50 John Wall Circle", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());
		
		PersonDTO foundPersonSix = people.get(5);
		
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getFirstName());
		assertNotNull(foundPersonSix.getLastName());
		assertNotNull(foundPersonSix.getAddress());
		assertNotNull(foundPersonSix.getGender());
		
		assertEquals(97, foundPersonSix.getId());
		
		assertEquals("Amerigo", foundPersonSix.getFirstName());
		assertEquals("Lipprose", foundPersonSix.getLastName());
		assertEquals("02 Northwestern Terrace", foundPersonSix.getAddress());
		assertEquals("Male", foundPersonSix.getGender());
		
		
		
	}
	
	@Test
	@Order(7)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.accept(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("firstName", "an")
					.queryParams("page",0,"limit",6,"direction","asc")
					.when()
					.get("findPersonByName/{firstName}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
		var people = wrapper.getEmbedded().getPersons();
		
		PersonDTO foundPersonOne = people.get(0);
		assertNotNull(foundPersonOne);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());
		
		assertEquals(810, foundPersonOne.getId());
		
		assertEquals("Abrahan", foundPersonOne.getFirstName());
		assertEquals("O'Callaghan", foundPersonOne.getLastName());
		assertEquals("861 Emmet Point", foundPersonOne.getAddress());
		assertEquals("Male", foundPersonOne.getGender());	
		
		
	}
	
	@Test
	@Order(8)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
			.setBasePath("/api/person/v1")
			.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
		
		given().spec(specificationWithoutToken)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.when()
				.get()
			.then()
				.statusCode(401);
	}
	
	
	@Test
	@Order(9)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.accept(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 3, "limit", 10, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/676\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/496\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/927\"}}}"));
		
		assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=0&limit=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=2&limit=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/person/v1?page=3&limit=10&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=4&limit=10&sort=firstName,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/person/v1?direction=asc&page=100&limit=10&sort=firstName,asc\"}}"));
		
		assertTrue(content.contains("\"page\":{\"limit\":10,\"totalElements\":1004,\"totalPages\":101,\"number\":3}}"));
	}
	
	
	private void mockPerson() {
		person.setId(1L);
		person.setFirstName("Nelson");
		person.setLastName("Mandela");
		person.setAddress("BSB - DF");
		person.setGender("Male");	
		person.setEnabled(true);
		
	}

}
