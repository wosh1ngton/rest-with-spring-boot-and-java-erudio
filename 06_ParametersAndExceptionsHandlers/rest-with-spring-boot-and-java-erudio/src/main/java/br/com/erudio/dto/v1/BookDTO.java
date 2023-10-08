package br.com.erudio.dto.v1;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dozermapper.core.Mapping;

import jakarta.persistence.Column;


public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	@Mapping("Id")
	private int key;	
	
	private String Author;	
	
	private Date LaunchDate;	
	
	private double Price;	

	private String Title;
	

	
	public BookDTO() {}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public Date getLaunchDate() {
		return LaunchDate;
	}

	public void setLaunchDate(Date launchDate) {
		LaunchDate = launchDate;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}		
	
}
