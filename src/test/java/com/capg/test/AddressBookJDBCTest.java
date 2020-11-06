package com.capg.test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.capg.dto.AddressBookDataService;
import com.capg.dto.Contact;
import com.capg.dto.AddressBookDataService.IOService;

import junit.framework.Assert;

public class AddressBookJDBCTest {
	
	@Test
	public void givenAddressBookInDB_WhenRetrived_ShouldMatchContactCount() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(9, addressBookData.size());
	}
	
	@Test
	public void givenAddressBookInDB_WhenUpdated_ShouldBeInSyncWithDB() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		List<Contact> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.updateAddressBookData("Vikas",940409495);
		boolean result = addressBookService.checkAddressBookPayrollInSyncWithDB("Vikas");
		Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchContactCount() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018,01,01);
		LocalDate endDate = LocalDate.now();
		List<Contact> addressBookData = addressBookService.readAddressBookForDateRange(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(5, addressBookData.size());
	}
	
	@Test
	public void givenAddressBookInDB_WhenRetrievedbyCity_ShouldReturnCount() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		int count = addressBookService.getNumberOfContactsInCity(IOService.DB_IO,"Hyderabad");
		Assert.assertEquals(5,count);
	}
	
	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addContactToAddressBook(IOService.DB_IO,10,"BJEJS","fdjjd","Kukkatpally",500032,8494949,"elonmusk@gmail.com",LocalDate.of(2019,8,19),"Hyderabad","Telangana","Friend","B");
		boolean result = addressBookService.checkAddressBookPayrollInSyncWithDB("Harsha");
		Assert.assertTrue(result);
	}
	
	@Test
	public void given4Contacts_WhenAddedToDBUsingThreads_ShouldMatchAddressBookEntries() {
		Contact[] arrayOfContacts = {
				new Contact(11,"Chaitanya","Malampati","Kukkatpally","500062","8484892","chai@gmail.com",LocalDate.of(2019,7,19),"Hyderabad","Telangana","Family","A") ,
				new Contact(12,"Ram","Rao","Hebbal","400032","84944949","ket@gmail.com",LocalDate.of(2019,8,19),"Banglore","Karnataka","Friend","B"),
				new Contact(13,"Dhruv","Srivastav","Faridabad","300062","94494994","dhruv@gmail.com",LocalDate.of(2019,11,29),"Faridabad","Delhi","Friend","A"),
		};
		AddressBookDataService addressbookDataService = new AddressBookDataService();
		addressbookDataService.readAddressBookData(IOService.DB_IO);
		Instant threadStart = Instant.now();
		addressbookDataService.addContactsToAddressBookWithThreads(Arrays.asList(arrayOfContacts));
		Instant threadEnd = Instant.now();
		System.out.println("Duration with Thread: "+Duration.between(threadStart, threadEnd));
		Assert.assertEquals(13, addressbookDataService.countEntries(IOService.DB_IO));
	}
	
}
