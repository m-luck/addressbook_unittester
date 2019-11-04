## Address Book

This library allows you to:

- Create an empty address book.
- Add an entry consisting of a name, postal address, phone number, email address, and a note.
- Remove an entry.
- Search entries.
- Save the address book to a file.
- Read the address book from a file.

Some usage is shown below and others can be found in the javadocs.

Author: Daniel Kramer, dk1844@nyu.edu

##### Note: Requires Java 8+

#### Example Usage

```Java
AddressBook book = new AddressBook();

Contact john = new Contact.Builder().firstName("John").phoneNumber("8015551234")
        .birthday(MonthDay.of(4, 5)).build();
Contact sophie = new Contact.Builder().firstName("sophie").lastName("smith")
        .phoneNumber("1112223333").build();

book.addContacts(john, sophie);
```

##### Searching 
```Java
ArrayList<SearchResult> results = book.searchAllFields("sm");
//SearchResult contains the number of field matches and the sophie contact

// Search a specific field
ArrayList<Contact> results2 = book.search(SearchableField.EMAIL, "email@gmail.com");
```

##### Saving and reading from disk
```Java
// Saving
try (BufferedWriter writer = Files.newBufferedWriter(<FILE PATH>, Charset.forName("UTF-8"))) {
      book.save(writer);
}

// Reading
AddressBook book = null;
try (BufferedReader reader = Files.newBufferedReader(<FILE PATH>, Charset.forName("UTF-8"))) {
  book = AddressBook.load(reader);
}
```
###### Note: the address book is stored in json, so a `.json` file extension is recommended

##### Editing a contact
```Java
Contact dan = new Contact.Builder().firstName("Dan").build();
book.addContact(dan);
Contact newDan = new Contact.Builder().from(dan).lastName("Kramer").build();
book.replaceContact(dan, newDan);
```
