package com.capg.dto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

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
	
	public List<Contact> ReadFromCsv(String filePath) {
		try(Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
			CsvToBean<Contact> csvToBean = new CsvToBeanBuilder(reader)
					.withType(Contact.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			Iterator<Contact> csvIterator= csvToBean.iterator();
			List<Contact> addressBook = new ArrayList<Contact>();
			while(csvIterator.hasNext()) {
				Contact contacts=csvIterator.next();
				addressBook.add(new Contact(contacts.getFirstName(),contacts.getLastName(),contacts.getAddress(),contacts.getZip(),contacts.getPhoneNo(),contacts.getEmail()));
			}
			return addressBook;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean writeIntoCsv(List<Contact> addressBook,String filePath) throws IOException {
		try(Writer writer= Files.newBufferedWriter(Paths.get(filePath))) {
			StatefulBeanToCsv<Contact> csvWriter = new StatefulBeanToCsvBuilder<Contact>(writer)
		            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
		             .build();
			csvWriter.write(addressBook);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
