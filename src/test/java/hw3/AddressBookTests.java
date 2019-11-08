package hw3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.MonthDay;

import edu.nyu.pqs.assignment3.*;
import edu.nyu.pqs.assignment3.Contact.Builder;

import org.junit.Before;
import org.junit.Test;

public class AddressBookTests {

	AddressBook book;
	Builder contactA;
	Builder contactB;
	Builder contactC;
	
	@Before
	public void setUp() throws Exception {
		book = new AddressBook();
		contactA = new Contact.Builder();
		contactB = new Contact.Builder();
		contactC = new Contact.Builder();
		contactA.firstName("Michael").lastName("Lukiman");
		contactB.firstName("Michael").lastName("Schidlowsky");
		contactC.firstName("michael").lastName("Jordan");
	}
	
	
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
	public void testFromJSON() {
		contactC.fromJSON({)
	}
	
	@Test
	public void testEmptyBook() {
		 assertFalse(book.containsContact(contactA.build()));
	}
	

}
