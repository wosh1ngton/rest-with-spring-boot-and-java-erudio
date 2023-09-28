package br.com.erudio.dto.v2;

import java.util.Date;

public class PersonDTOV2 {

	private static final long serialVersionUID = 2L;
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
	private Date birthDay;
	
	public PersonDTOV2() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	};
	
	
	
}
