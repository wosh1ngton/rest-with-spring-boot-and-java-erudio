package br.com.erudio.integrationtests.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonEmbeddedDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("personDTOList")
	private List<PersonDTO> persons;

	public PersonEmbeddedDTO() {
		super();
	}

	public PersonEmbeddedDTO(List<PersonDTO> persons) {
		super();
		this.persons = persons;
	}

	public List<PersonDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonDTO> persons) {
		this.persons = persons;
	}

	@Override
	public int hashCode() {
		return Objects.hash(persons);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonEmbeddedDTO other = (PersonEmbeddedDTO) obj;
		return Objects.equals(persons, other.persons);
	}
	
	
	
}
