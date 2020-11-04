package com.capg.dto;

import java.util.List;

import com.capg.dto.Contact;

public class AddressBookDataService {
	
	public enum IOService {CONSOLE_IO, FILE_IO,DB_IO, REST_IO}
	private AddressBookDBService addressBookDBService;
	private List<Contact> addressBookList;
	
	public AddressBookDataService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public List<Contact> readAddressBookData(IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			this.addressBookList = addressBookDBService.readData();
		}
		return this.addressBookList;
	}

	public void updateAddressBookData(String firstName, int phone) {
		int result = addressBookDBService.updateEmployeeData(firstName, phone);
		if(result == 0) {
			return;
		}
		Contact contactData =this.getAddressBookData(firstName);
		if(contactData != null) {
			contactData.setPhoneNo(phone+"");;
		}
	}

	private Contact getAddressBookData(String firstName) {
		Contact contact;
		contact = this.addressBookList.stream()
					  .filter(con -> con.getFirstName().equals(firstName))
					  .findFirst()
					  .orElse(null);
		return contact;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String firstName) {
		List<Contact> addressBookList = addressBookDBService.getAddressBookData(firstName);
		return addressBookList.get(0).equals(getAddressBookData(firstName));
	}

}
