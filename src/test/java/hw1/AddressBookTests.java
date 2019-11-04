package hw1;

import static org.junit.Assert.*;

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
	}

	
	@Test 
	public void testBuildName() {
		contactA.firstName("Michael").lastName("Lukiman");
		contactB.firstName("Michael").lastName("Schidlowsky");
		contactC.firstName("michael").lastName("Jordan");
	}
	
	@Test
	public void testEmptyBook() {
		// assertFalse(book.containsContact(contact))
		
	}

}
