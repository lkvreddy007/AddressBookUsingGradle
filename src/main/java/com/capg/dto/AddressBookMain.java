package com.capg.dto;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {
	static AddressBook abm = new AddressBook();
	static AddressBookFileIO fileIO = new AddressBookFileIO();
	static HashMap<String, List<Contact>> shelf=new HashMap<>();
	static HashMap<String,List<Contact>> cityContactDict=new HashMap<>();
	private static List<Contact> addressBook=new ArrayList<Contact>();
	static Scanner sc=new Scanner(System.in);
	
	public static void searchPersonInAState(String city){
		List<Contact> matchingObj=new ArrayList<Contact>();
		for(List<Contact> li:shelf.values()) {
			matchingObj=li.stream()
				  .filter(a->a.getAddress().equals(city))
				  .collect(Collectors.toList());
		}
		System.out.println("Contacts from "+city+" are :");
		for(Contact c:matchingObj) {
			System.out.println(c.getFirstName()+c.getLastName());
		}
	}
	
	public static List<Contact> makeCityContactsDict(String cityName) {
		List<String> cities=new ArrayList<String>();
		for(List<Contact> list:shelf.values()) {
			for(Contact c:list){
				if(cities.contains(c.getAddress())) {
					
				}
				else {
					cities.add(c.getAddress());
				}
			}
		}
		List<Contact> matchingObj=new ArrayList<Contact>();
		for(String city:cities) {
			for(List<Contact> li:shelf.values()) {
				matchingObj=li.stream()
				  .filter(a->a.getAddress().equals(city))
				  .collect(Collectors.toList());
			}
			cityContactDict.put(city,matchingObj);
		}
		List<Contact> l=cityContactDict.get(cityName);
		return l;
		
	}
	
	public static void main(String[] args) {
		boolean exit = true;
		while(exit) {
			System.out.println("\nMenu");
			System.out.println("1.Add Address Book to Shelf");
			System.out.println("2.Display names of all address books in shelf");
			System.out.println("3.Display contacts by City/State across multiple Address Books");
			System.out.println("4.Display contacts by City/State using Dictionary");
			System.out.println("5.Find Number Of Contacts in City");
			System.out.println("6.Add Address Book created by reading from file to Shelf ");
			System.out.println("7.Write Address Book To File");
			System.out.println("8.Write Address Book To Csv File");
			System.out.println("9.Read Address Book From Csv File and add to shelf");
			System.out.println("10.Write Address Book To Json File");
			System.out.println("11.Read Address Book From Json File");
			System.out.println("12.Exit");
			System.out.println("Enter your choice");
			int check=Integer.parseInt(sc.nextLine());
			switch(check){
				case 1:
					boolean a=true;
					String name="";
					while(a) {
						System.out.println("\nEnter the name for Address Book:");
						name=sc.nextLine();
						if(shelf.containsKey(name)) {
							System.out.println("Key already exists.");
						} 
						else {
							addressBook = abm.addressBookCreator();
							shelf.put(name, addressBook);
							System.out.println("Address book added to shelf");
							a=false;
						}
					}
					break;
					
				case 2:
					Set<String> keys=shelf.keySet();
					System.out.println("Address books in the shelf are:");
					for(String key:keys) {
						System.out.println(key);
					}
					break;
					
				case 3:
					System.out.println("Enter city name to display contacts");
					String city= sc.nextLine();
					searchPersonInAState(city);
					break;
				
				case 4:
					System.out.println("Enter city name to display contacts");
					String cityName=sc.nextLine();
					List<Contact> l=makeCityContactsDict(cityName);
					for(Contact con:l) {
						System.out.println(con.getFirstName()+" "+con.getLastName());
					}
					break;
				
				case 5:
					System.out.println("Enter the city to find number of contacts");
					String cityname=sc.nextLine();
					List<Contact> list=makeCityContactsDict(cityname);
					System.out.println(list.size());
					break;
				
				case 6:
					
					System.out.println("Give the Path of the file to read contacts");
					String filePath=sc.nextLine();
					boolean b=true;
					while(b) {
						System.out.println("Enter the name for Address Book:");
						String key=sc.nextLine();
						if(shelf.containsKey(key)) {
							System.out.println("Key already exists.");
						} 
						else {
							addressBook = fileIO.readFileAndCreateAddressBook(filePath);
							shelf.put(key, addressBook);
							System.out.println("Address book added to shelf");
							b=false;
						}
					}
					break;
					
				case 7:
					System.out.println("Give the Path of the file to write contacts");
					String fPath=sc.nextLine();
					boolean u=true;
					while(u) {
						System.out.println("Enter the name of Address Book to write into File");
						String k=sc.nextLine();
						if(shelf.containsKey(k)) {
							fileIO.writeData(shelf.get(k), fPath);
							b=false;
							System.out.println("Address book written to text file");
						}
						else {
							System.out.println("Address Book with "+k+" name doesnot exist");
						}
					}
					break;
				
				case 8:
					System.out.println("Give the file path to Write");
					String file=sc.nextLine();
					boolean x= true;
					while(x) {
						System.out.println("Enter the name of Address Book");
						String key= sc.nextLine();
						try {
							if(shelf.containsKey(key)) {
								fileIO.writeIntoCsv(shelf.get(key), file);
								x=false;
								System.out.println("Address Book written to Csv file");
							}
							else {
								System.out.println("Address Book  with "+key+" name doesnot exist");
							}
						}
						catch(IOException e) {
							e.printStackTrace();
						}
					}
					break;
					
				case 9:
					System.out.println("Enter the Csv file name to read");
					String path= sc.nextLine();
					boolean k=true;
					while(k) {
						System.out.println("Enter the name of AddressBook");
						String nameAddressBook =sc.nextLine();
						if(shelf.containsKey(nameAddressBook)) {
							System.out.println("Key already exists.");
						}
						else {
							addressBook=fileIO.readFromCsv(path);
							shelf.put(nameAddressBook, addressBook);
							System.out.println("Address Book added to Shelf");
							k=false;
						}
					}
					break;
					
				case 10:
					System.out.println("Give the file path to Write");
					String filepath=sc.nextLine();
					boolean y= true;
					while(y) {
						System.out.println("Enter the name of Address Book");
						String key= sc.nextLine();
						if(shelf.containsKey(key)) {
							fileIO.writeIntoJson(shelf.get(key), filepath);
							y=false;
							System.out.println("Address Book written to Json file");
						}
						else {
							System.out.println("Address Book  with "+key+" name doesnot exist");
						}
					}
					break;
					
				case 11:
					System.out.println("Enter the Json file name to read");
					String filpath= sc.nextLine();
					boolean m=true;
					while(m) {
						System.out.println("Enter the name of AddressBook");
						String nameAddressBook =sc.nextLine();
						if(shelf.containsKey(nameAddressBook)) {
							System.out.println("Key already exists.");
						}
						else {
							addressBook=fileIO.readFromJson(filpath);
							shelf.put(nameAddressBook, addressBook);
							System.out.println("Address Book added to Shelf");
							m=false;
						}
					}
					break;
					
				case 12:
					exit=false;
					break;
					
				default:
					System.out.println("Enter between 1 to 5");
			}
		}
	}
}
