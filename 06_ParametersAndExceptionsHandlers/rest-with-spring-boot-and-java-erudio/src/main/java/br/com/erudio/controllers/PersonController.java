package br.com.erudio.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.dto.v1.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.services.PersonServices;
import br.com.erudio.utils.MediaType;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {


	@Autowired
	private PersonServices service;
	
	@GetMapping(value = "/{id}", produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,			
			}
	)	public PersonDTO findById(@PathVariable(value = "id") Long id) throws Exception
	{	
		return service.findById(id);
	}
	
	@GetMapping(produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,
			})
	public List<PersonDTO> findAll() throws Exception 
	{	
		return service.findAll();
	}
	
	@PostMapping(produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
			}, 
			consumes = { 
					MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML 
					})
	public PersonDTO create(@RequestBody PersonDTO person) throws Exception
	{	
		return service.create(person);
	}
	
	@PostMapping(value = "/v2", produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
			}, consumes = { 
					MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML
					})
	public PersonDTOV2 create(@RequestBody PersonDTOV2 person) throws Exception
	{	
		return service.createV2(person);
	}
	
	@PutMapping(produces = {
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
			}, consumes = { 
					MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML
					})
	public PersonDTO update(@RequestBody PersonDTO person) throws Exception
	{	
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception
	{	
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	
}
