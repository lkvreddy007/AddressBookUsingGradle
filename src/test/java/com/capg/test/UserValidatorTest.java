package com.capg.test;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.capg.dto.FirstNameInvalidException;
import com.capg.dto.LastNameInvalidException;
import com.capg.dto.PhoneNumberInvalidException;
import com.capg.dto.StateOrCityNameInvalidException;
import com.capg.dto.ZipCodeInvalidException;
import com.capg.dto.InputValidator;

public class UserValidatorTest {
	//TestCase for MoodAnalyser
	@Test
	public void testMoodAnalysis() throws Exception{
		InputValidator moodAnalyser=new InputValidator();
		String mood=moodAnalyser.analyseMood("This is a sad message");
		Assert.assertThat(mood, CoreMatchers.is("SAD"));
	}
	
	//TestCases for FirstName
	@Test
	public void givenFirstName_WhenProper_ShouldReturnTrue() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateFirstName("Krishna");
			Assert.assertTrue(result);
		} 
		catch (FirstNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenFirstName_WhenShort_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateFirstName("Kr");
			Assert.assertFalse(result);
		} 
		catch (FirstNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenFirstName_WhenHasSplChars_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateFirstName("Krishna@");
			Assert.assertFalse(result);
		} 
		catch (FirstNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenFirstName_WhenFirstCharIsSmall_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateFirstName("krishna");
			Assert.assertFalse(result);
		} 
		catch (FirstNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	//TestCases for LastName
	@Test
	public void givenLastName_WhenProper_ShouldReturnTrue() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateLastName("Krishna");
			Assert.assertTrue(result);
		} 
		catch (LastNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenLastName_WhenShort_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateLastName("Kr");
			Assert.assertFalse(result);
		} 
		catch (LastNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenLastName_WhenHasSplChars_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateLastName("Krishna@");
			Assert.assertFalse(result);
		} 
		catch (LastNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenLastName_WhenFirstCharIsSmall_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateLastName("krishna");
			Assert.assertFalse(result);
		} 
		catch (LastNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	//TestCases for Phone
	@Test
	public void givenPhoneNo_WhenProper_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validatePhoneNo("91 9490067618");
			Assert.assertTrue(result);
		} 
		catch (PhoneNumberInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenPhoneNo_WhenNoSpaceAfterCountryCode_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validatePhoneNo("919490067618");
			Assert.assertFalse(result);
		} 
		catch (PhoneNumberInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenPhoneNo_WhenShort_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validatePhoneNo("91 9490067");
			Assert.assertFalse(result);
		} 
		catch (PhoneNumberInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenPhoneNo_NoCountryCode_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validatePhoneNo(" 9490067618");
			Assert.assertFalse(result);
		} 
		catch (PhoneNumberInvalidException e) {
			System.out.println(e);
		}
	}
	
	//TestCaseForAddress
	@Test
	public void givenAddress_WhenProper_ShouldReturnTrue() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateStateOrCityName("Hyderabad");
			Assert.assertTrue(result);
		} 
		catch (StateOrCityNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenAddress_WhenShort_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateStateOrCityName("Hy");
			Assert.assertFalse(result);
		} 
		catch (StateOrCityNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenAddress_WhenHasSplChars_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateStateOrCityName("Hyderabad@");
			Assert.assertFalse(result);
		} 
		catch (StateOrCityNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void givenAddress_WhenFirstCharIsSmall_ShouldReturnFalse() {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateStateOrCityName("krishna");
			Assert.assertFalse(result);
		} 
		catch (StateOrCityNameInvalidException e) {
			System.out.println(e);
		}
	}
	
	//TestForZip
	//TestCases for LastName
		@Test
		public void givenZip_WhenProper_ShouldReturnTrue() {
			InputValidator validator=new InputValidator();
			boolean result;
			try {
				result = validator.validateZip("509001");
				Assert.assertTrue(result);
			} 
			catch (ZipCodeInvalidException e) {
				System.out.println(e);
			}
		}
}
