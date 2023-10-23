package br.com.erudio.services;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.dto.v1.PersonDTO;
import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {	
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper _mapper;
	
	public PersonDTO findById(Long id) throws Exception {
		
		logger.info("Finding one person");		
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));
		var dto = DozerMapper.parseObject(person, PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return dto;
	}
	
	@Transactional
	public PersonDTO disablePerson(Long id) throws Exception {		
		
		logger.info("Desabilitando one person");		
		repository.disablePerson(id);
		
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));
		var dto = DozerMapper.parseObject(person, PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return dto;
	}
	
	public List<PersonDTO> findAll() throws Exception {
		
		logger.info("Finding all people");
		var persons = repository.findAll();
		var dto = DozerMapper.parseListObjects(persons, PersonDTO.class);
		
		dto.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});			
			
		return dto;
	}
	
	public PersonDTO create(PersonDTO person) throws Exception {
		
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Creating one person");	
		var entity = DozerMapper.parseObject(person, Person.class);		
		var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}
	
	public PersonDTOV2 createV2(PersonDTOV2 person) {
		
		logger.info("Creating one person v2");	
		var entity = _mapper.convertDTOToEntity(person);		
		return _mapper.convertEntityToDTO(repository.save(entity));
	}
	
	public PersonDTO update(PersonDTO person) throws Exception {
		
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Updating one person");	
		Person entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var entitySave = repository.save(entity);		
		var dto = DozerMapper.parseObject(entitySave, PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foram encontrados registros para este id"));	
		repository.delete(entity);
		logger.info("Deleting one person");	
	}
	
}
