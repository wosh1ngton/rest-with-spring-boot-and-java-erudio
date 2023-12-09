package br.com.erudio.services;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.erudio.controllers.BookController;
import br.com.erudio.dto.v1.BookDTO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

@Service
public class BookService {	
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository repository;	
	
	public BookDTO findById(int id) throws Exception {
		
		logger.info("Finding one book");		
		Book book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));
		var dto = DozerMapper.parseObject(book, BookDTO.class);
		dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return dto;
	}
	
	public List<BookDTO> findAll() throws Exception {
		
		logger.info("Finding all books");
		var books = repository.findAll();
		var dto = DozerMapper.parseListObjects(books, BookDTO.class);
		
		dto.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});			
			
		return dto;
	}
	
	public BookDTO create(BookDTO book) throws Exception {
		
		if(book == null) throw new RequiredObjectIsNullException();
		logger.info("Creating one book");	
		var entity = DozerMapper.parseObject(book, Book.class);		
		var dto = DozerMapper.parseObject(repository.save(entity), BookDTO.class);
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}	
	
	
	public BookDTO update(BookDTO book) throws Exception {
		
		if(book == null) throw new RequiredObjectIsNullException();
		logger.info("Updating one book");	
		Book entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var entitySave = repository.save(entity);		
		var dto = DozerMapper.parseObject(entitySave, BookDTO.class);
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}
	
	public void delete(int id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		repository.delete(entity);
		logger.info("Deleting one person");	
	}
	
}