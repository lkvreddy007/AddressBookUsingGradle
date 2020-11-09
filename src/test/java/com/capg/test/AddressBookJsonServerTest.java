package com.capg.test;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.capg.dto.AddressBookDataService;
import com.capg.dto.AddressBookDataService.IOService;
import com.capg.dto.Contact;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class AddressBookJsonServerTest {
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private Contact[] getContactList() {
		Response response = RestAssured.get("/addressBook");
		System.out.println("CONTACTS IN JSONServer:\n"+response.asString());
		Contact[] arrayOfContacts = new Gson().fromJson(response.asString(),Contact[].class);
		return arrayOfContacts;
	}
	
	private Response addContactToJsonServer(Contact contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(contactJson);
		return request.post("/addressBook");
	}
	
	@Test
	public void givenNewContact_WhenAdded_ShouldMatch201ResponseCount() {
		Contact[] arrayOfContacts = getContactList();
		AddressBookDataService addressBookDataService;
		addressBookDataService = new AddressBookDataService(Arrays.asList(arrayOfContacts));
		
		Contact contact = null;
		contact = new Contact(0, "Krishna", "lankala","Srinivasa Colony","Mahabubnagar","Telangana","509001","4848848","jjdjdj@gmail.com");
		
		Response response = addContactToJsonServer(contact);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		contact = new Gson().fromJson(response.asString(), Contact.class);
		addressBookDataService.addContactToAddressBook(contact, IOService.REST_IO);
		long entries = addressBookDataService.countEntries(IOService.REST_IO);
		Assert.assertEquals(2, entries);
	}
	
	@Test 
	public void givenContactsInJSONServer_WhenRetrieved_ShouldMatchTheCount() {
		Contact[] arrayContacts = getContactList();
		AddressBookDataService addressBookDataService;
		addressBookDataService = new AddressBookDataService(Arrays.asList(arrayContacts));
		long entries = addressBookDataService.countEntries(IOService.REST_IO);
		Assert.assertEquals(2, entries);
	}
	
	@Test
	public void givenMultipleContacts_WhenAdded_ShouldMatch201ResponseCount() {
		Contact[] arrContacts = getContactList();
		AddressBookDataService addressBookDataService;
		addressBookDataService = new AddressBookDataService(Arrays.asList(arrContacts));
		
		Contact[] arrayOfContacts = {
				new Contact(0,"Rohith","MK", "Gajuvaka","Vizag","Andhra Pradesh","409002","9030303","jdksj@gmail.com"),
				new Contact(0, "Vamsi","G","RS Colony","Guntur","Andhra Pradesh","408223","49949943","sjksjda@gmail.com"),
				new Contact(0, "Anil", "M","New town","Mahabubnagar","Telangana","509001","3888393","dkakjsj@gmail.com"),
		};
		
		for(Contact contact :arrayOfContacts) {
			Response response = addContactToJsonServer(contact);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			
			contact = new Gson().fromJson(response.asString(), Contact.class);
			addressBookDataService.addContactToAddressBook(contact, IOService.REST_IO);
		}
		
		long entries = addressBookDataService.countEntries(IOService.REST_IO);
		Assert.assertEquals(5, entries);
	}
	
}
