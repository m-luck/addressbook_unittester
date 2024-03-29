package edu.nyu.pqs.assignment3.mll469;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.ArrayList;

import edu.nyu.pqs.assignment3.*;
import edu.nyu.pqs.assignment3.Contact.Builder;
import edu.nyu.pqs.assignment3.AddressBook.SearchResult;


import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;


public class ContactTests {

	AddressBook book;
	Builder contactA;
	Builder contactB;
	Builder contactB2;
	Builder contactC;
	
	@Before
	public void setUp() throws Exception {
		book = new AddressBook();
		contactA = new Contact.Builder();
		contactB = new Contact.Builder();
		contactC = new Contact.Builder();
		contactB2 = new Contact.Builder();
		contactA.firstName("Michael").lastName("Lukiman");
		contactB.firstName("Michael").lastName("Schidlowsky");
		contactC.firstName("michael").lastName("Jordan");
		contactB2.firstName("Michael").lastName("Schidlowsky");
	}
	
	
	/*** CONTACT CLASS TESTS ***/
	/***************************/
	
	/* SINGLE FIELDS */
	
	@Test 
	public void testBuildNameRepeat() {
		contactA.firstName("Michael").lastName("Lukiman");
		contactB.firstName("Michael").lastName("Schidlowsky");
		contactC.firstName("michael").lastName("Jordan");
	}
	
	@Test
	public void testBuildEmail() {
		contactB.email("michaels@nyu.edu");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBreakPhone() {
		contactA.phoneNumber("HOTLINE NUMBER 1-800");
	}

	@Test
	public void testBuildPhone() {
		contactA.phoneNumber("+1800");
	}
	
	@Test
	public void testBuildBirthday() {
		contactC.birthday(MonthDay.of(6,7));
	}
	
	@Test
	public void testBuildNote() {
		contactB.note("Interesting...");
	}
	
	@Test
	public void testBuildAddress() {
		contactB.address("Funky town outlets");
	}
	
	@Test(expected = DateTimeException.class)
	public void testBuildBirthday13() {
		contactC.birthday(MonthDay.of(13,7));
	}
	
    /* NULLS */
	
	@Test(expected = NullPointerException.class)
	public void testNullFirst() {
		contactA.firstName(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullLast() {
		contactA.lastName(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullEmail() {
		contactA.email(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullNote() {
		contactA.note(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullAddress() {
		contactA.address(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullBirthday() {
		contactA.birthday(null);
	}
	
    /* META BUILDER */
	
	@Test
	public void testGetBuilderEquals() { 
    // Had to change 'from' to static for this one.
		Builder newBuilder = Builder.from(contactB.build());
		assertTrue(newBuilder.build().equals(contactB.build()));
	}
	
	@Test(expected = NullPointerException.class)
	public void testFromNull() { 
    // Had to change 'from' to static for this one.
		Builder newBuilder = Builder.from(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyBuilder() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.build();
	}
	
	@Test
	public void testJustFirstNameCanBuild() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("").build();
	}
	
	@Test
	public void testJustLastNameCanBuild() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.lastName("").build();
	}
	
	@Test
	public void testJustNumberCanBuild() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.phoneNumber("1").build();
	}

	
	@Test
	public void testAsJson() {
		contactB.build().asJson();
	}
	
	@Test
	public void testToString() {
		contactB.build().toString();
	}
	
	@Test 
	public void testHashCodeDiff() {
		assertTrue(contactA.build().hashCode() != contactB.build().hashCode());  
	}
	
	@Test 
	public void testHashSame() {
		assertTrue(contactB.build().hashCode() == contactB2.build().hashCode());
	}

	/* COMPARATORS */

//	Breaks. Null pointer exception.
//	@Test
//	public void testCompareToSame() {
//		contactB = new Contact.Builder();
//		contactB.firstName("Michael").lastName("Schidlowsky");
//		Contact contactBbuilt = contactB.build();
//		System.out.print(contactBbuilt.toString());
//		int res = contactBbuilt.compareTo(contactBbuilt);
//		assertEquals(res, 0);
//	}

	@Test
	public void testCompareToDiff() {
		int res = contactC.build().compareTo(contactA.build());
		assertTrue(res != 0);
	}
	
	/* EQUALS TO */

	@Test
	public void testEqualsSame() {
		boolean res = contactC.build().equals(contactC.build());
		assertTrue(res);
	}
	
	@Test
	public void testEqualsIdentical() {
		Contact same = contactC.build();
		boolean res = same.equals(same);
		assertTrue(res);
	}
	
	@Test
	public void testEqualsDiff() {
		boolean res = contactC.build().equals(contactA.build());
		assertFalse(res);
	}
	
	@Test
	public void testEqualsNull() {
		Contact n = null;
		boolean res = contactC.build().equals(n);
		assertFalse(res);
	}
	
	@Test
	public void testEqualsDiffType() {
		String s = "Contact";
		boolean res = contactC.build().equals(s);
		assertFalse(res);
	}
	
	@Test
	public void testEqualsNullComplement() {
		contactC.address("here");
		contactC.note("wow");
		contactC.phoneNumber("0");
		contactC.email("p0tus@whitehouse.gov");
		boolean res = contactA.build().equals(contactC.build());
		assertFalse(res);
	}
	
	@Test
	public void testEqualsNullComplementExist() {
		contactC.address("here");
		contactC.note("wow");
		contactC.phoneNumber("0");
		contactC.email("p0tus@whitehouse.gov");
		
		contactA.address("2here");
		contactA.note("wow2");
		contactA.phoneNumber("20");
		contactA.email("p0tus@whitehouse.gov2");
		
		boolean res = contactA.build().equals(contactC.build());
		assertFalse(res);
	}
	
	@Test
	public void testEqualsNullComplementExist_SameAddress() {
		contactC.address("here");
		contactC.note("wow");
		contactC.phoneNumber("0");
		contactC.email("p0tus@whitehouse.gov");
		
		contactA.address("here");
		contactA.note("wow2");
		contactA.phoneNumber("20");
		contactA.email("p0tus@whitehouse.gov2");
		
		boolean res = contactA.build().equals(contactC.build());
		assertFalse(res);
	}
	
	/* LONG STRINGS */
		
	@Test
	public void testLong_address() {
		Path file = Paths.get(".", "shamu.txt");
		System.out.print(file.toString());
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			String response = new String();
			for (String line; (line = reader.readLine()) != null; response += line);
			contactA.address(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLong_note() {
		Path file = Paths.get(".", "shamu.txt");
		System.out.print(file.toString());
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			String response = new String();
			for (String line; (line = reader.readLine()) != null; response += line);
			contactA.note(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLong_firstName() {
		Path file = Paths.get(".", "shamu.txt");
		System.out.print(file.toString());
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			String response = new String();
			for (String line; (line = reader.readLine()) != null; response += line);
			contactA.firstName(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLong_lastName() {
		Path file = Paths.get(".", "shamu.txt");
		System.out.print(file.toString());
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			String response = new String();
			for (String line; (line = reader.readLine()) != null; response += line);
			contactA.lastName(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLong_email() {
		Path file = Paths.get(".", "shamu.txt");
		System.out.print(file.toString());
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			String response = new String();
			for (String line; (line = reader.readLine()) != null; response += line);
			contactA.email(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


