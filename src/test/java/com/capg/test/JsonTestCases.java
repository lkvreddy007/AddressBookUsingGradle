package com.capg.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capg.dto.AddressBookFileIO;
import com.capg.dto.Contact;

import junit.framework.Assert;

public class JsonTestCases {

	@Test
	public void givenAddressBookAsInput_WhenCorrect_ShouldWriteIntoJsonFileAndTrue() {
		AddressBookFileIO fileIO=new AddressBookFileIO();
		List<Contact> addressBook=new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		boolean b= fileIO.writeIntoJson(addressBook, "jsonFile.json");
		Assert.assertTrue(b);
	}
	
	@Test
	public void givenJsonFileAsInput_WhenCorrect_ShouldCreateAddressBook() {
		AddressBookFileIO fileIO=new AddressBookFileIO();
		List<Contact> addressBook=new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		fileIO.writeIntoJson(addressBook, "jsonFile.json");
		List<Contact> contactList= fileIO.readFromJson("jsonFile.json");
		Assert.assertEquals(contactList.size(),addressBook.size());
	}

}
