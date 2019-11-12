package hw3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import edu.nyu.pqs.assignment3.Contact;
import edu.nyu.pqs.assignment3.AddressBook.SearchResult;


import org.junit.Before;
import org.junit.Test;


public class AddressBookTests {

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
	public void testEmptyBook() {
		 assertFalse(book.containsContact(contactA.build()));
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
	public void testEqualsDiff() {
		boolean res = contactC.build().equals(contactA.build());
		assertFalse(res);
	}
	
	
	/*** ADDRESS BOOK CLASS TESTS ***/
	/********************************/	
	
	
//	Trouble using class SearchableField as an API.
//	@Test
//	public void testSearchResult() {
//		ArrayList<Contact> res = book.search(SearchableField.FIRST_NAME, "Michael");
//	}
	
	@Test
	public void testAddContact() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		book.addContact(newC);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddContactNull() {
		Contact newC = null;
		book.addContact(newC);
	}
	
	@Test
	public void testAddContacts() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		emptyBuilder.firstName("Neueue");
		Contact newC2 = emptyBuilder.build();
		book.addContacts(newC, newC2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddContactsNull() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = null;
		Contact newC2 = emptyBuilder.build();
		book.addContacts(newC, newC2);
	}
	
	@Test
	public void testAddContactsAlreadyStored() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC2 = emptyBuilder.build();
		book.addContact(newC2);
		book.addContact(newC2);
	}
	
	@Test
	public void testRemoveContact() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		book.addContact(newC);
		book.addContact(newC);
		book.removeContact(newC);
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testRemoveContactNull() {
		book.removeContact(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveContactNotExist() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		book.removeContact(newC);
	}
	
	@Test
	public void testRemoveContacts() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		emptyBuilder.firstName("Neueue");
		Contact newC2 = emptyBuilder.build();
		book.addContact(newC);
		book.addContact(newC2);
		book.removeContacts(newC, newC2);
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveContactsNull() {
		Builder emptyBuilder = new Builder();
		emptyBuilder.firstName("Neue");
		Contact newC = emptyBuilder.build();
		emptyBuilder.firstName("Neueue");
		Contact newC2 = emptyBuilder.build();
		book.addContact(newC);
		book.addContact(newC2);
		book.removeContacts(newC, null);
	}
	
//	Breaks. This test does not do what is expected (return contactA which has "Michael")
//	@Test
//	public void testSearchAllFields() {
//		Builder contactA = new Contact.Builder();
//		contactA.firstName("Michael").lastName("Lukiman");
//		book.addContact(contactA.build());
//		
//		ArrayList<SearchResult> res = book.searchAllFields("Michael");
//		assertEquals(res.get(0).relevance, 1); 
//		
//	}
	
	
	@Test
	public void testSearchAllFieldsLowerCaseSearch() {
		Builder contactA = new Contact.Builder();
		contactA.firstName("Michael").lastName("lukiman");
		book.addContact(contactA.build());
		
		ArrayList<SearchResult> res = book.searchAllFields("michael");
		assertEquals(res.get(0).relevance, 1); 
	}
	
	@Test
	public void testReplace() {
		book.addContact(contactA.build());
		book.replaceContact(contactA.build(), contactB.build());
	}
	
	/* IMPORT / EXPORT */
	
	@Test
	public void testSave() {
		Path file = Paths.get(".", "testBook.json");
		System.out.print(file.toString());
		try (BufferedWriter writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"))) {
		      book.save(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoad() {
		Path file = Paths.get(".", "testBook.json");
		System.out.print(file.toString());
		AddressBook book = null;
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
		  book = AddressBook.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


