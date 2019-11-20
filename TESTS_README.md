### Using JUnit4

The unit tests are separated to target each class. 

With the tests open each can be run with Shift+Ctrl+F11 on Linux Eclipse.

The file AllTests.java is just the combination of the two test classes in order to make running (and coverage information) easier. It is just for the assignment. 

The tests have >80% in Contacts (due to the large package private fromJson method not being accessed from the larger class) and the AddressBook class has coverage of >90%. 

The file AllTestsWFails reveals the catches.

1) Searching for with an uppercase fails.
2) Comparator to structurally same Contact fails. 
3) Searchable field type is inaccessible as it is package private.

Confirmed this with original author that this was not intended behavior.

Permutative nested if-else fall through checks with same logic patterns were tested with confidence, and then extrapolated to prevent exponential test growth (i.e coverage of equals() in Contact). 

Thank you!

mll469@nyu.edu
