package br.com.erudio.unitTests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.dto.v1.BookDTO;
import br.com.erudio.dto.v1.PersonDTO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.BookService;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unitTests.mapper.mocks.MockBook;
import br.com.erudio.unitTests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;

	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		var book = service.findById(1);
		assertNotNull(book);
		assertNotNull(book.getKey());
		assertNotNull(book.getLinks());		
		assertNotNull(book.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1",book.getTitle());
		assertEquals("Author Name Test1",book.getAuthor());		
		assertEquals(1,book.getPrice());
	}

	@Test
	void testFindAll() throws Exception {
		
		List<Book> list = input.mockEntityList();		
		when(repository.findAll()).thenReturn(list);
		
		var result = service.findAll();
		assertNotNull(result);
		assertEquals(14,result.size());
		
		var book = result.get(1);
		
		assertNotNull(book);
		assertNotNull(book.getKey());
		assertNotNull(book.getLinks());		
		assertNotNull(book.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1",book.getTitle());
		assertEquals("Author Name Test1",book.getAuthor());		
		assertEquals(1,book.getPrice());
	}

	
	@Test
	void testCreateWithNullPerson() {
		Exception e = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		String expectedMessage = "Não é permitido salvar um registro nulo";
		assertEquals(expectedMessage, e.getMessage());
	} 
	
	
	@Test
	void testCreate() throws Exception {
		
		Book entity = input.mockEntity(1);
		entity.setId(1);
		
		Book persisted = entity;
		persisted.setId(1);
		
		BookDTO dto = input.mockDTO(1);
		dto.setKey(1);
		
		when(repository.save(entity)).thenReturn(persisted);	
		var book = service.create(dto);
		
		assertNotNull(book);
		assertNotNull(book.getKey());
		assertNotNull(book.getLinks());
		
		assertTrue(book.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1",book.getTitle());
		assertEquals("Author Name Test1",book.getAuthor());
		assertNotNull(book.getLaunchDate());
		assertEquals(1,book.getPrice());
		
			
	}
	
	
	
	@Test
	void testUpdate() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1);
		
		Book persisted = entity;
		persisted.setId(1);
		
		BookDTO dto = input.mockDTO(1);
		dto.setKey(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var book = service.update(dto);
		
		assertNotNull(book);
		assertNotNull(book.getKey());
		assertNotNull(book.getLinks());
		
		assertTrue(book.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1",book.getTitle());
		assertEquals("Author Name Test1",book.getAuthor());
		assertNotNull(book.getLaunchDate());
		assertEquals(1,book.getPrice());
		
	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		entity.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		service.delete(1);
	}

}
