package com.capg.dto;

import java.util.regex.Pattern;

import com.capg.dto.EmailInvalidException;
import com.capg.dto.FirstNameInvalidException;
import com.capg.dto.LastNameInvalidException;
import com.capg.dto.PhoneNumberInvalidException;

public class InputValidator {
	private static final String NAME_PATTERN="[A-Z][a-z]{2,}";
	private static final String EMAIL_ID="^[a-zA-Z0-9]+([.+-_][a-zA-Z0-9]+)*@[a-zA-Z0-9]+\\.[a-z]{2,4}(\\.[a-z]{2})?$";
	private static final String PHONENO="[0-9]{2} {1}9[0-9]{9}";
	private static final String PINCODE="^[1-9]{1}[0-9]{2}(\\s)?[0-9]{3}$";
	
	public String analyseMood(String message) {
		if(message.contains("sad")) {
			return "SAD";
		}else {
			return "HAPPY";
		}
	}
	
	public boolean validateFirstName(String fname) throws FirstNameInvalidException {
		Pattern pattern=Pattern.compile(NAME_PATTERN);
		boolean b = pattern.matcher(fname).matches();
		if(b) {
			return b;
		}
		else {
			throw new FirstNameInvalidException("Invalid FirstName");
		}
	}
	
	public boolean validateLastName(String lname) throws LastNameInvalidException {
		Pattern pattern=Pattern.compile(NAME_PATTERN);
		boolean b = pattern.matcher(lname).matches();
		if(b) {
			return b;
		}
		else {
			throw new LastNameInvalidException("Invalid LastName");
		}
	}
	
	public boolean validateStateOrCityName(String state) throws StateOrCityNameInvalidException {
		Pattern pattern=Pattern.compile(NAME_PATTERN);
		boolean b = pattern.matcher(state).matches();
		if(b) {
			return b;
		}
		else {
			throw new StateOrCityNameInvalidException("Invalid State or City Name");
		}
	}
	
	public boolean validateEmail(String email) throws EmailInvalidException {
		Pattern pattern=Pattern.compile(EMAIL_ID);
		boolean b = pattern.matcher(email).matches();
		if(b) {
			return b;
		}
		else {
			throw new EmailInvalidException("Invalid EmailId");
		}
	}
	
	public boolean validateZip(String zip) throws ZipCodeInvalidException {
		Pattern pattern=Pattern.compile(PINCODE);
		boolean b = pattern.matcher(zip).matches();
		if(b) {
			return b;
		}
		else {
			throw new ZipCodeInvalidException("Invalid Zip Code");
		}
	}
	
	public boolean validatePhoneNo(String phoneno) throws PhoneNumberInvalidException {
		Pattern pattern=Pattern.compile(PHONENO);
		boolean b = pattern.matcher(phoneno).matches();
		if(b) {
			return b;
		}
		else {
			throw new PhoneNumberInvalidException("Invalid PhoneNumber");
		}
	}
}
