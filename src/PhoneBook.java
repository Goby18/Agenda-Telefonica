package agendatelefonica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa PhoneBook gestionează contactele din agenda telefonică și interacționează cu baza de date.
 * Permite adăugarea, ștergerea, actualizarea și căutarea contactelor.
 */

public class PhoneBook {
    private Connection connection;
    private List<Contact> contacts;

    /**
     * Constructorul clasei PhoneBook.
     * Încarcă contactele din baza de date și inițializează conexiunea.
     */
    
    public PhoneBook() {
        contacts = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook", "root", "0000");

            createTableIfNotExists();
            loadAllContacts();  // Încarcă contactele din baza de date
        } catch (SQLException e) {
            System.out.println("Eroare la conexiunea cu baza de date: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driverul JDBC nu a fost găsit.");
            e.printStackTrace();
        }
    }

    
    /**
     * Obține toate contactele din agenda telefonică.
     * @return Lista de contacte.
     */
    public List<Contact> getAllContacts() {
        return contacts;
    }
    
    

    /**
     * Creează tabelul pentru contactele din baza de date dacă nu există.
     */
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts ("
                + "name TEXT PRIMARY KEY,"
                + "phone_number TEXT,"
                + "email TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Eroare la crearea tabelului: " + e.getMessage());
        }
    }

    /**
     * Adaugă un contact în baza de date și în lista locală.
     * @param contact Contactul care trebuie adăugat.
     */
    public void addContact(Contact contact) {
        String insertSQL = "INSERT INTO contacts (name, phone_number, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhoneNumber());
            pstmt.setString(3, contact.getEmail());
            pstmt.executeUpdate();

            // Adaugă contactul în lista locală
            contacts.add(contact);
        } catch (SQLException e) {
            System.out.println("Eroare la adăugarea contactului: " + e.getMessage());
        }
    }

    /**
     * Șterge un contact din baza de date și din lista locală pe baza numelui.
     * @param name Numele contactului care trebuie șters.
     */
    public void deleteContact(String name) {
        String deleteSQL = "DELETE FROM contacts WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            // Șterge contactul și din lista locală
            contacts.removeIf(contact -> contact.getName().equals(name));
        } catch (SQLException e) {
            System.out.println("Eroare la ștergerea contactului: " + e.getMessage());
        }
    }

    
    /**
     * Actualizează un contact în baza de date și în lista locală.
     * @param name Numele contactului care trebuie actualizat.
     * @param updatedContact Contactul cu datele actualizate.
     */
    public void updateContact(String name, Contact updatedContact) {
        String updateSQL = "UPDATE contacts SET phone_number = ?, email = ? WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, updatedContact.getPhoneNumber());
            pstmt.setString(2, updatedContact.getEmail());
            pstmt.setString(3, name);
            pstmt.executeUpdate();

            // Actualizează contactul și în lista locală
            for (Contact contact : contacts) {
                if (contact.getName().equals(name)) {
                    contact.setPhoneNumber(updatedContact.getPhoneNumber());
                    contact.setEmail(updatedContact.getEmail());
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare la actualizarea contactului: " + e.getMessage());
        }
    }

    /**
     * Căutăm un contact după nume.
     * @param name Numele contactului de căutat.
     * @return Contactul găsit, sau null dacă nu a fost găsit.
     */
    public Contact searchByName(String name) {
        String selectSQL = "SELECT * FROM contacts WHERE name LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, "%" + name + "%"); // % semnifică căutare parțială
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Contact(rs.getString("name"), rs.getString("phone_number"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare la căutarea contactului: " + e.getMessage());
        }
        return null;
    }

    /**
     * Căutăm un contact după numărul de telefon.
     * @param phoneNumber Numărul de telefon de căutat.
     * @return Contactul găsit, sau null dacă nu a fost găsit.
     */
    public List<Contact> searchAllByName(String name) {
        List<Contact> contacts = new ArrayList<>();
        String selectSQL = "SELECT * FROM contacts WHERE name LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(rs.getString("name"), rs.getString("phone_number"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Eroare la căutarea contactelor: " + e.getMessage());
        }
        return contacts;
    }

    /**
     * Încarcă toate contactele din baza de date în lista locală.
     */
    private void loadAllContacts() {
        String selectSQL = "SELECT * FROM contacts";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(rs.getString("name"), rs.getString("phone_number"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Eroare la încărcarea contactelor din baza de date: " + e.getMessage());
        }
    }
    public Contact searchByPhoneNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null; // Dacă nu se găsește
    }

    public void displayAllContacts() {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : getAllContacts()) {
            sb.append("Nume: ").append(contact.getName())
              .append(", Telefon: ").append(contact.getPhoneNumber())
              .append(", Email: ").append(contact.getEmail())
              .append("\n");
        }
        // Afișează toate contactele într-un JTextArea
        // contactTextArea.setText(sb.toString());  // contactTextArea este un JTextArea în care afișezi contactele
    }
}
