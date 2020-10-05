package com.capg.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.capg.dto.InputValidator;
import com.capg.dto.EmailInvalidException;

@RunWith(Parameterized.class)
public class EmailValidatorTest {
	private String email;
	private boolean expectedResult;
	
	public EmailValidatorTest(String email,boolean expectedResult) {
		this.email=email;
		this.expectedResult=expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {{"abc@yahoo.com",true},
											{"abc-100@yahoo.com",true},
											{"abc.100@yahoo.com",true},
											{"abc111@abc.com",true},
											{"abc-100@abc.net",true},
											{"abc.100@abc.com.au",true},
											{"abc@1.com",true},
											{"abc+100@gmail.com",true},
											{"abc@gmail.com.com",false},
											{"abc",false},
											{"abc@.com.my",false},
											{"abc123@gmail.a",false},
											{"abc123@.com",false},
											{"abc123@.com.com",false},
											{".abc@abc.com",false},
											{"abc()*@gmail.com",false},
											{"abc@%*.com",false},
											{"abc..2002@gmail.com",false},
											{"abc.@gmail.com",false},
											{"abc@gmail.com.1a",false},
											{"abc@gmail.com.aa.au",false}});
	}
	
	@Test
	public void givenEmailAsVar_ShouldReturnAsPerParameter() throws EmailInvalidException {
		InputValidator validator=new InputValidator();
		boolean result;
		try {
			result = validator.validateEmail(this.email);
			Assert.assertEquals(this.expectedResult, result);
		} 
		catch (EmailInvalidException e) {
			System.out.println(e);
		}
	}
}
