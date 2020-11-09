package com.capg.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capg.dto.Contact;

public class AddressBookDataService {
	
	public enum IOService {CONSOLE_IO, FILE_IO,DB_IO, REST_IO}
	private AddressBookDBService addressBookDBService;
	private List<Contact> addressBookList;
	
	public AddressBookDataService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public AddressBookDataService(List<Contact> addressBookList) {
		this();
		this.addressBookList = new ArrayList<>(addressBookList);
	}

	public List<Contact> readAddressBookData(IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			this.addressBookList = addressBookDBService.readData();
		}
		return this.addressBookList;
	}

	public void updateAddressBookData(String firstName, int phone) {
		int result = addressBookDBService.updateAddressBookData(firstName, phone);
		if(result == 0) {
			return;
		}
		Contact contactData =this.getAddressBookData(firstName);
		if(contactData != null) {
			contactData.setPhoneNo(phone+"");;
		}
	}
	
	public void updateAddressBookJson(String firstName, int phone) {
		Contact contact = this.getAddressBookData(firstName);
		if(contact != null) {
			contact.setPhoneNo(""+phone);
		}
	}

	public Contact getAddressBookData(String firstName) {
		Contact contact;
		contact = this.addressBookList.stream()
					  .filter(con -> con.getFirstName().equals(firstName))
					  .findFirst()
					  .orElse(null);
		return contact;
	}

	public boolean checkAddressBookPayrollInSyncWithDB(String firstName) {
		List<Contact> addressBookList = addressBookDBService.getAddressBookData(firstName);
		return addressBookList.get(0).equals(getAddressBookData(firstName));
	}

	public List<Contact> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
		if(ioService.equals(IOService.DB_IO)) {
			return addressBookDBService.getAddressBookForDateRange(startDate, endDate);
		}
		return null;
	}

	public int getNumberOfContactsInCity(IOService ioService, String city) {
		if(ioService.equals(IOService.DB_IO)) {
			return addressBookDBService.getNumberOfContactsInCity(city);
		}
		return 0;
	}

	public void addContactToAddressBook(IOService ioService,int id, String firstName, String lastName, String address, int zip, int phone,
			String email,LocalDate date_added, String city, String state, String type, String addressBookName) {
		addressBookList.add(addressBookDBService.addContactToAddressBook(id, firstName, lastName, address, zip, phone,
				 email, date_added, city, state, type, addressBookName));
	}

	public long countEntries(IOService iosService) {
		return addressBookList.size();
	}

	public void addContactsToAddressBookWithThreads(List<Contact> addressBList) {
		Map<Integer,Boolean> contactAdditionStatus = new HashMap<Integer,Boolean>();
		addressBList.forEach(contact->{
			Runnable task = () ->{
				contactAdditionStatus.put(contact.hashCode(), false);
				System.out.println("Contact Being Added: "+ Thread.currentThread().getName());
				this.addContactToAddressBook(IOService.DB_IO, contact.getId(), contact.getFirstName(), contact.getLastName(), 
										 contact.getAddress(), Integer.parseInt(contact.getZip()), Integer.parseInt(contact.getPhoneNo()), 
										 contact.getEmail(), contact.getDateAdded(), contact.getCity(), contact.getState(), contact.getType(), contact.getBookName());
				contactAdditionStatus.put(contact.hashCode(), true);
				System.out.println("Contact Added "+Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
		});
		while (contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			}
			catch (InterruptedException e) {
			
			}
		}
	}

	public void addContactToAddressBook(Contact contact, IOService ioService) {
		addressBookList.add(contact);
	}

	public void deleteContact(String firstName, IOService iosService) {
		if(iosService.equals(IOService.REST_IO)) {
			Contact contact = this.getAddressBookData(firstName);
			addressBookList.remove(contact);
		}
	}

}
