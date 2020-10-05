package com.capg.dto;

import java.util.*;
import java.util.stream.Collectors;

public class FinalMain {
	static AddressBookMain abm=new AddressBookMain();
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
			System.out.println("5.Exit");
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
					exit=false;
					break;
					
				default:
					System.out.println("Enter between 1 to 5");
			}
		}
	}
}
