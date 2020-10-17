package com.capg.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIO {
	
	public void writeData(List<Contact> addressBook, String filePath) {
		StringBuffer adbBuffer= new StringBuffer();
		addressBook.forEach(contact->{
			String contactDataString=contact.toString().concat("\n");
			adbBuffer.append(contactDataString);
		});
		try {
			Files.write(Paths.get(filePath),adbBuffer.toString().getBytes());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void printData(String filePath) {
		try {
			Files.lines(new File(filePath).toPath())
						.forEach(System.out::println);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Contact> readFileAndCreateAddressBook(String filePath) {
		List<Contact> addressBook = new ArrayList<Contact>();
		try {
			Files.lines(new File(filePath).toPath())
			.forEach(line->{
				line.trim();
				String[] inputs = line.split("[\\s,:]+");
				Contact con= new Contact(inputs[1],
										inputs[3],
										inputs[5],
										inputs[7],
										inputs[9],
										inputs[11]);
				addressBook.add(con);
			});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return addressBook;
	}
	
	public long countNumEntries(String filePath) {
		long entries=0;
		try {
			entries = Files.lines(new File(filePath).toPath()).count();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
