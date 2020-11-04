package com.capg.test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
		Assert.assertEquals(8, addressBookData.size());
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
		Assert.assertEquals(4, addressBookData.size());
	}
	
	@Test
	public void givenAddressBookInDB_WhenRetrievedbyCity_ShouldReturnCount() {
		AddressBookDataService addressBookService = new AddressBookDataService();
		addressBookService.readAddressBookData(IOService.DB_IO);
		int count = addressBookService.getNumberOfContactsInCity(IOService.DB_IO,"Hyderabad");
		Assert.assertEquals(4,count);
	}
	
}
