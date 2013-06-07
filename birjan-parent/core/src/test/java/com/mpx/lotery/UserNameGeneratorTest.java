package com.mpx.lotery;

import org.junit.Test;

import com.mpx.birjan.core.UsernameGenerator;

public class UserNameGeneratorTest {

	@Test
	public void test1() {

		String name = "z";
		String surname = "z";
		int agencyId = 99;
		
		
		for (int i = 0; i < 50; i++) {
			String hash = UsernameGenerator.hash(name, surname, agencyId);
			System.out.println(hash);			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
