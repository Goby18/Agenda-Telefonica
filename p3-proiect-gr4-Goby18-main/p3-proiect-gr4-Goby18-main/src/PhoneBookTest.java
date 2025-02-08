package agendatelefonica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneBookTest {
    private PhoneBook phoneBook;

    @BeforeEach
    public void setUp() {
        phoneBook = new PhoneBook();
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact("Ana Popescu", "0722123456", "ana.popescu@gmail.com");
        phoneBook.addContact(contact);

        Contact result = phoneBook.searchByName("Ana Popescu");
        assertNotNull(result);
        assertEquals("Ana Popescu", result.getName());
        assertEquals("0722123456", result.getPhoneNumber());
        assertEquals("ana.popescu@gmail.com", result.getEmail());
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact("Ion Ionescu", "0733987654", "ion.ionescu@firma.com");
        phoneBook.addContact(contact);
        phoneBook.deleteContact("Ion Ionescu");

        assertNull(phoneBook.searchByName("Ion Ionescu"));
    }

    @Test
    public void testUpdateContact() {
        Contact contact = new Contact("Maria Tudor", "0744555999", "maria.tudor@yahoo.com");
        phoneBook.addContact(contact);

        Contact updatedContact = new Contact("Maria Tudor", "0711111111", "maria.noua@email.com");
        phoneBook.updateContact("Maria Tudor", updatedContact);

        Contact result = phoneBook.searchByName("Maria Tudor");
        assertNotNull(result);
        assertEquals("0711111111", result.getPhoneNumber());
        assertEquals("maria.noua@email.com", result.getEmail());
    }

    @Test
    public void testSearchByPhoneNumber() {
        Contact contact = new Contact("Andrei Radu", "0751333222", "andrei.radu@gmail.com");
        phoneBook.addContact(contact);

        Contact result = phoneBook.searchByPhoneNumber("0751333222");
        assertNotNull(result);
        assertEquals("Andrei Radu", result.getName());
    }

    @Test
    public void testDisplayAllContacts() {
        Contact contact1 = new Contact("Ana Popescu", "0722123456", "ana.popescu@gmail.com");
        Contact contact2 = new Contact("Ion Ionescu", "0733987654", "ion.ionescu@firma.com");

        phoneBook.addContact(contact1);
        phoneBook.addContact(contact2);

   
    }
}

