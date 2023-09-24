package br.com.erudio.services;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public Person findById(Long id) {
		
		logger.info("Finding one person");
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("wos");
		person.setLastName("Silva");
		person.setAddress("BSB");
		person.setGender("Home");
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));
	}
	
	public List<Person> findAll() {
		
		logger.info("Finding all people");
		return repository.findAll();
	}
	
	public Person create(Person person) {
		
		logger.info("Creating one person");		
		return repository.save(person);
	}
	
	public Person update(Person person) {
		
		logger.info("Updating one person");	
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		repository.delete(entity);
		logger.info("Deleting one person");	
	}
	
}
