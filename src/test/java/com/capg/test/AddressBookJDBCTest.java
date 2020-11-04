package com.capg.test;

import java.util.List;

import org.junit.Test;

import com.capg.dto.AddressBookDataService;
import com.capg.dto.Contact;
import com.capg.dto.AddressBookDataService.IOService;

import junit.framework.Assert;

public class AddressBookJDBCTest {
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrived_ShouldMatchEmployeeCount() {
		AddressBookDataService employeePayRollService = new AddressBookDataService();
		List<Contact> addressBookData = employeePayRollService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(8, addressBookData.size());
	}
	
}
