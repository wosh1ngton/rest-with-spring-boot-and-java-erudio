package br.com.erudio.services;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.dto.v1.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper _mapper;
	
	public PersonDTO findById(Long id) {
		
		logger.info("Finding one person");		
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));
		var dto = DozerMapper.parseObject(person, PersonDTO.class);
		return dto;
	}
	
	public List<PersonDTO> findAll() {
		
		logger.info("Finding all people");
		var persons = repository.findAll();
		var dto = DozerMapper.parseListObjects(persons, PersonDTO.class);
		return dto;
	}
	
	public PersonDTO create(PersonDTO person) {
		
		logger.info("Creating one person");	
		var entity = DozerMapper.parseObject(person, Person.class);		
		return DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
	}
	
	public PersonDTOV2 createV2(PersonDTOV2 person) {
		
		logger.info("Creating one person v2");	
		var entity = _mapper.convertDTOToEntity(person);		
		return _mapper.convertEntityToDTO(repository.save(entity));
	}
	
	public PersonDTO update(PersonDTO person) {
		
		logger.info("Updating one person");	
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var entitySave = repository.save(entity);		
		var dto = DozerMapper.parseObject(entitySave, PersonDTO.class);
		return dto;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		repository.delete(entity);
		logger.info("Deleting one person");	
	}
	
}
