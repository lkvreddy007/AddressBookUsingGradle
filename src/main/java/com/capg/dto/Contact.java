package com.capg.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.opencsv.bean.CsvBindByName;

public class Contact {
	@CsvBindByName
	private String firstName;
	
	@CsvBindByName
	private String lastName;
	
	@CsvBindByName
	private String address;
	
	@CsvBindByName
	private String zip;
	
	@CsvBindByName
	private String phoneNo;
	
	@CsvBindByName
	private String email;

	private LocalDate dateAdded;

	private String city;

	private String state;

	private String type;

	private String bookName;

	private int id;
	
	public Contact() {
		
	}
	
	public Contact(String firstName,String lastName,String address,String zip,String phoneNo,String email){
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setZip(zip);
		setPhoneNo(phoneNo);
		setEmail(email);
	}
	
	public Contact(int id, String firstName, String lastName, String address, String zip, String phoneNo, String email, LocalDate dateAdded,
			String city, String state, String type, String bookName) {
		this(firstName, lastName, address, zip, phoneNo, email);
		this.setId(id);
		this.setDateAdded(dateAdded);
		this.setCity(city);
		this.setState(state);
		this.setType(type);
		this.setBookName(bookName);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public String getFirstName() {
		return this.firstName;
	} 
	
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	public String getLastName() {
		return this.lastName;
	}
	
	public void setAddress(String address) {
		this.address=address;
	}
	public String getAddress() {
		return this.address;
	}
	
	public void setZip(String zip) {
		this.zip=zip;
	}
	public String getZip() {
		return this.zip;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo=phoneNo;
	}
	
	public String getPhoneNo() {
		return this.phoneNo;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	} 
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	
	public String toString() {
		return  "FirstName: "+firstName
				+", LastName: "+lastName
				+", Address: "+address
				+", Zip: "+zip
				+", Phone No: "+phoneNo
				+", Email: "+email;
	}
	
	@Override
	public boolean equals(Object obj) {		
		if(this == obj) {
			return true;
		}
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Contact that = (Contact) obj;
		return firstName.equals(that.firstName) && phoneNo.equals(that.phoneNo) && lastName.equals(that.lastName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}
	
}

