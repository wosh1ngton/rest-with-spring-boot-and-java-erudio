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

import br.com.erudio.dto.v1.BookDTO;
import br.com.erudio.services.BookService;
import br.com.erudio.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name= "Livros", description = "endpoint para gestão de livros")
public class BookController {

	@Autowired
	private BookService service;
	
	@GetMapping(value = "/{id}", produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,			
			}
	)
	@Operation(summary = "localiza um livro", description = "localiza um livro",
	tags = {"Livros"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
				)
			}),
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public BookDTO findById(@PathVariable(value = "id") int id) throws Exception
	{	
		return service.findById(id);
	}
	
	@GetMapping(produces = { 
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML,
			})
	@Operation(summary = "localiza todas os livros", description = "encontra tudo",
			tags = {"Livros"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
						)
					}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public List<BookDTO> findAll() throws Exception 
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
	@Operation(summary = "Adicionar um livro", description = "adiciona um livro",
	tags = {"Livros"},
	responses = {
		@ApiResponse(description = "Success", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
				)
			}),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public BookDTO create(@RequestBody BookDTO book) throws Exception
	{	
		return service.create(book);
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
	@Operation(summary = "Atualiza um livro", description = "atualiza um livro",
	tags = {"Livros"},
	responses = {
		@ApiResponse(description = "Updated", responseCode = "200",
			content = {
				@Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
				)
			}),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),		
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public BookDTO update(@RequestBody BookDTO book) throws Exception
	{	
		return service.update(book);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "exclui um livro", description = "exclui um livro",
	tags = {"Livros"},
	responses = {
		@ApiResponse(description = "No content", responseCode = "204",content = @Content),		
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),		
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<?> delete(@PathVariable(value = "id") int id) throws Exception
	{	
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	
}
