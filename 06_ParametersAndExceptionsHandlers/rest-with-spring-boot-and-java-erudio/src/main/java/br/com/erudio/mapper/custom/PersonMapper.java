package br.com.erudio.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.dto.v2.PersonDTOV2;
import br.com.erudio.model.Person;

@Service
public class PersonMapper {
	
	public PersonDTOV2 convertEntityToDTO(Person person) {
		PersonDTOV2 dto = new PersonDTOV2();
		dto.setId(person.getId());
		dto.setAddress(person.getAddress());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setBirthDay(new Date());
		dto.setGender(person.getGender());
		
		return dto;
	}
	
	public Person convertDTOToEntity(PersonDTOV2 dto) {
		
		Person entity = new Person();
		entity.setId(dto.getId());
		entity.setAddress(dto.getAddress());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		//entity.setBirthDay(null);
		entity.setGender(dto.getGender());
		
		return entity;		
	}
}