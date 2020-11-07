package com.capg.test;

import static com.capg.EmployeePayrollService.IOService.REST_IO;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.capg.EmployeePayrollData;
import com.capg.EmployeePayrollService;
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
		Contact[] arrayOfEmps = new Gson().fromJson(response.asString(),Contact[].class);
		return arrayOfEmps;
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
		long entries = addressBookDataService.countEntries(REST_IO);
		Assert.assertEquals(2, entries);
	}
	
}
