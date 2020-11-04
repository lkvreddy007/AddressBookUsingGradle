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
		String password = "XXXXX";
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
		String sql = String.format("select * from person_details where start between '%s' and '%s';", Date.valueOf(startDate),Date.valueOf(endDate));
		return this.getAddressBookUsingDB(sql);
	}


}
