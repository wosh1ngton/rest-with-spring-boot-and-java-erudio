package br.com.erudio.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.dto.v1.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.services.PersonServices;
import br.com.erudio.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name= "Pessoas", description = "endpoint para gestão de pessoas")
public class PersonController {


	@Autowired
	private PersonServices service;
	
	@GetMapping(value = "/{id}", produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,			
			}
	)
	@Operation(summary = "localiza UMA PESSOA", description = "encontra uma pessoa",
	tags = {"People"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
				)
			}),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public PersonDTO findById(@PathVariable(value = "id") Long id) throws Exception
	{	
		return service.findById(id);
	}
	
	
	@PatchMapping(value = "/{id}", produces = { 
			MediaType.APPLICATION_JSON,	MediaType.APPLICATION_XML,MediaType.APPLICATION_YML,			
			}
	)
	@Operation(summary = "desabilita UMA PESSOA", tags = {"People"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
				)
			}),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})	
	public PersonDTO disablePerson(@PathVariable(value = "id") Long id) throws Exception
	{	
		return service.disablePerson(id);
	}
	
	@GetMapping(produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,
			})
	@Operation(summary = "localiza todas as pessoas", description = "encontra tudo",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
						)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) throws Exception 
	{	
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	
	@GetMapping(
			value = "/findPersonByName/{firstName}",
			produces = {MediaType.APPLICATION_JSON,	MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
			})
	@Operation(summary = "localiza pessoas por nome", description = "encontra pessoas por nome",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
						)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findPersonsByName(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) throws Exception 
	{	
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonsByName(firstName, pageable));
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
	@Operation(summary = "Adicionar UMA PESSOA", description = "adiciona uma pessoa",
	tags = {"People"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
				)
			}),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
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
	@Operation(summary = "Atualiza UMA PESSOA", description = "atualiza uma pessoa",
	tags = {"People"},
	responses = {
		@ApiResponse(description = "Updated", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
				)
			}),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),		
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public PersonDTO update(@RequestBody PersonDTO person) throws Exception
	{	
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "exclui UMA PESSOA", description = "exclui uma pessoa",
	tags = {"People"},
	responses = {
		@ApiResponse(description = "No content", responseCode = "204",content = @Content),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),		
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception
	{	
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	
}
