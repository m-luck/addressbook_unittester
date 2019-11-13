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
	
//	This test does not do what is expected (return contactA which has "Michael")
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
		
		File toDelete = new File(file.toString());
		toDelete.delete();
	}
	
	@Test
	public void testLoad() {
		Path file = Paths.get(".", "testBook.json");
		System.out.print(file.toString());
		try (BufferedWriter writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"))) {
		      book.save(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.print(file.toString());
		book = null;
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"))) {
			book = AddressBook.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File toDelete = new File(file.toString());
		toDelete.delete();
	}
	
}


