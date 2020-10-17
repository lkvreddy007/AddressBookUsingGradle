package com.capg.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.capg.dto.Contact;
import com.capg.dto.AddressBookFileIO;

import junit.framework.Assert;

public class AddressBookFileIOTest {
	final String FILEPATH="addressbook_file.txt";
	@Test
	public void inputContactsDetails_WhenCorrect_ShouldWriteIntoTextFile() {
		AddressBookFileIO fileIO= new AddressBookFileIO();
		List<Contact> addressBook = new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		fileIO.writeData(addressBook,FILEPATH);
		Assert.assertTrue(3==fileIO.countNumEntries(FILEPATH));
	}
	
	@Test
	public void readContactsDetailsFromFile_WhenCorrect_ShouldCreateAddressBook() {
		AddressBookFileIO fileIO= new AddressBookFileIO();
		List<Contact> addressBook = new ArrayList<Contact>();
		addressBook.add(new Contact("Raghava", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmammi@gmail.com"));
		addressBook.add(new Contact("Vikas", "Mammi", "Hyderabad", "500 019","91 9999999999", "rammi@gmail.com"));
		addressBook.add(new Contact("Ragha", "Mammi", "Hyderabad", "500 019","91 9999999999", "rmami@gmail.com"));
		fileIO.writeData(addressBook,FILEPATH);
		List<Contact> adb=fileIO.readFileAndCreateAddressBook(FILEPATH);
		System.out.println(adb.get(0).getFirstName());
		Assert.assertEquals(addressBook.get(0).getFirstName(),adb.get(0).getFirstName());
	}
}
