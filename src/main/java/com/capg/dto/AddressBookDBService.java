package com.capg.dto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	private static AddressBookDBService addressBookDBService;
	private  PreparedStatement addressBookDataStatement;

	public static AddressBookDBService getInstance() {
		if(addressBookDBService == null) {
			addressBookDBService = new AddressBookDBService();
		}
		return addressBookDBService;
	}

	public List<Contact> readData() {
		String sql = "Select * from person_details;";
		return this.getAddressBookUsingDB(sql);
	}

	private List<Contact> getAddressBookUsingDB(String sql) {
		List<Contact> addressBookList = new ArrayList<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addressBookList = this.getAddressBookData(result);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private List<Contact> getAddressBookData(ResultSet result) {
		List<Contact> addressBookList = new ArrayList<>();
		try {
			while(result.next()) {
				String firstName = result.getString("firstname");
				String lastName = result.getString("lastname");
				String address = result.getString("address");
				String zip = result.getString("zip");
				String phoneNumber = result.getString("phone");
				String email = result.getString("email");
				addressBookList.add(new Contact(firstName,lastName,address,zip,phoneNumber,email));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private Connection getConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?allowPublicKeyRetrieval=true&useSSL=false";
		String userName = "root";
		String password = "Ritz@5369";
		Connection connection = null;
		System.out.println("Connecting to database "+jdbcURL);
		try {
			System.out.println("Connecting to database:"+jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful "+connection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public int updateAddressBookData(String firstName, int phone) {
		String sql = "update person_details set phone = ? where firstname = ?;";
		try(Connection connection = this.getConnection()){
			PreparedStatement prepStatement = connection.prepareStatement(sql);
			prepStatement.setDouble(1, phone);
			prepStatement.setString(2, firstName);
			return prepStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Contact> getAddressBookData(String firstName) {
		List<Contact> addressBookList = null;
		if(this.addressBookDataStatement == null) {
			this.prepareStatementForAddressBook();
		}
		try {
			addressBookDataStatement.setString(1,firstName);
			ResultSet resultSet = addressBookDataStatement.executeQuery();
			addressBookList = this.getAddressBookData(resultSet);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	private void prepareStatementForAddressBook() {
		try {
			Connection connection = this.getConnection();
			String sql = "Select * from person_details where firstname = ?;";
			addressBookDataStatement = connection.prepareStatement(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> getAddressBookForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql = String.format("select * from person_details where date_added between '%s' and '%s';", Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getAddressBookUsingDB(sql);
	}

	public int getNumberOfContactsInCity(String city) {
		String sql = String.format("select city,count(firstName) as count from zip,person_details where zip.zip=person_details.zip and city='%s' group by (city);",city);
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int count = result.getInt("count");
				return count;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Contact addContactToAddressBook(int id, String firstName, String lastName, String address, int zip,
			int phone, String email, LocalDate date_added, String city, String state, String type,
			String addressBookName) {
		Connection connection = null;
		Contact contact = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement statement = connection.createStatement()){
			String sql = String.format("Insert into zip (zip,city,state) values (%s,'%s','%s')", zip, city,state);
			statement.executeUpdate(sql);
			contact = new Contact(firstName,lastName,address, ""+zip, ""+phone, email);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try(Statement statement = connection.createStatement()){
			String sql = String.format("Insert into person_details (person_id,firstname,lastname,address,zip,phone,email,date_added) values (%s,'%s','%s','%s',%s,%s,'%s','%s')",
										id,firstName,lastName,address,zip,phone,email,Date.valueOf(date_added));
			statement.executeUpdate(sql);
			contact = new Contact(firstName,lastName,address, ""+zip, ""+phone, email);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		try(Statement statement = connection.createStatement()){
			String sql = String.format("Insert into addressbook_shelf (person_id,type,bookname) values (%s,'%s','%s')", id, type,addressBookName);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				contact = new Contact(firstName,lastName,address, ""+zip, ""+phone, email);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return contact;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			connection.commit();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return contact;
	}

}
