package com.capg.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capg.dto.AddressBookFileIO;
import com.capg.dto.Contact;

import junit.framework.Assert;

public class CsvTestCase {

	@Test
	public void test() throws IOException {
		AddressBookFileIO fileIO=new AddressBookFileIO();
		List<Contact> addressBook=new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		boolean b= fileIO.writeIntoCsv(addressBook, "csvFile.csv");
		Assert.assertTrue(b);
	}
	
	@Test
	public void test2() throws IOException {
		AddressBookFileIO fileIO=new AddressBookFileIO();
		List<Contact> addressBook=new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		//boolean b= fileIO.writeIntoCsv(addressBook, "csvFile.csv");
		List<Contact> contactList= fileIO.readFromCsv("csvFile.csv");
		Assert.assertEquals(contactList.size(),addressBook.size());
	}
}
