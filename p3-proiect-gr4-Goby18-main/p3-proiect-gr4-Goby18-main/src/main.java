package agendatelefonica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa principală care gestionează interfața grafică a aplicației de agenda telefonică.
 * Aceasta creează fereastra, adaugă componentele necesare și definește logica pentru fiecare acțiune.
 */
public class main {
    private static PhoneBook phoneBook = new PhoneBook();

    /**
     * Metoda principală care pornește aplicația și creează interfața grafică.
     * @param args Parametrii liniei de comandă (nu sunt utilizați în acest caz).
     */
    public static void main(String[] args) {
        // Creăm fereastra principală
        JFrame frame = new JFrame("Agenda Telefonică");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Panou principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));  // GridLayout pentru a organiza elementele
        panel.setBackground(new Color(240, 240, 240));  // Un fundal mai deschis pentru panou

        // Câmpuri pentru introducerea contactelor
        JTextField nameField = new JTextField(20);
        JTextField phoneNumberField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Butoane pentru operațiuni
        JButton addButton = new JButton("Adaugă Contact");
        JButton deleteButton = new JButton("Șterge Contact");
        JButton updateButton = new JButton("Actualizează Contact");
        JButton searchButton = new JButton("Caută Contact");
        JButton displayButton = new JButton("Afișează Toate Contactele");

        // Label pentru mesaje
        JLabel messageLabel = new JLabel("Mesaj:");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(Color.RED);

        // Adăugăm componentele în panou
        panel.add(new JLabel("Nume:"));
        panel.add(nameField);
        panel.add(new JLabel("Număr de telefon:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);
        panel.add(displayButton);

        // JTextArea pentru a vizualiza contactele
        JTextArea contactDisplayArea = new JTextArea(10, 40);
        contactDisplayArea.setEditable(false);  // Nu vrem să fie editabil
        contactDisplayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(contactDisplayArea);

        // Adăugăm panoul și zona de afișare a contactelor în fereastra principală
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Acțiunea pentru Adăugarea unui contact
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
                    phoneBook.addContact(new Contact(name, phoneNumber, email));
                    messageLabel.setText("Contact adăugat!");
                    clearFields(nameField, phoneNumberField, emailField); // Curățăm câmpurile
                } else {
                    messageLabel.setText("Completează toate câmpurile!");
                }
            }
        });

        // Acțiunea pentru Ștergerea unui contact
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    phoneBook.deleteContact(name);
                    messageLabel.setText("Contact șters!");
                    clearFields(nameField, phoneNumberField, emailField);
                } else {
                    messageLabel.setText("Introdu numele contactului!");
                }
            }
        });

        // Acțiunea pentru Actualizarea unui contact
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
                    phoneBook.updateContact(name, new Contact(name, phoneNumber, email));
                    messageLabel.setText("Contact actualizat!");
                    clearFields(nameField, phoneNumberField, emailField);
                } else {
                    messageLabel.setText("Completează toate câmpurile!");
                }
            }
        });

        // Acțiunea pentru Căutarea unui contact
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschidem o fereastră pentru căutare
                showSearchDialog(frame, contactDisplayArea);
            }
        });

        // Acțiunea pentru Afișarea tuturor contactelor
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                // Afișăm toate contactele în JTextArea
                for (Contact contact : phoneBook.getAllContacts()) {
                    resultText.append(contact.getName()).append(", ").append(contact.getPhoneNumber())
                            .append(", ").append(contact.getEmail()).append("\n");
                }
                contactDisplayArea.setText(resultText.toString()); // Setăm textul în JTextArea
                messageLabel.setText("Toate contactele au fost afisate.");
            }
        });

        // Afișăm fereastra
        frame.setVisible(true);
    }

    /**
     * Metodă pentru curățarea câmpurilor de introducere.
     * @param nameField Câmpul pentru nume.
     * @param phoneField Câmpul pentru numărul de telefon.
     * @param emailField Câmpul pentru email.
     */
    private static void clearFields(JTextField nameField, JTextField phoneField, JTextField emailField) {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    /**
     * Metodă pentru a deschide fereastra de căutare a unui contact.
     * @param parentFrame Fereastra principală a aplicației.
     * @param contactDisplayArea Zona de afișare a contactelor.
     */
    private static void showSearchDialog(JFrame parentFrame, JTextArea contactDisplayArea) {
        // Cream un JDialog pentru căutare
        JDialog searchDialog = new JDialog(parentFrame, "Căutare Contact", true);
        searchDialog.setSize(300, 200);
        searchDialog.setLayout(new GridLayout(4, 1, 10, 10));

        // Câmpul de text pentru căutare
        JTextField searchField = new JTextField(20);

        // ComboBox pentru a alege criteriul de căutare
        String[] searchOptions = { "Căutare după Nume", "Căutare după Număr de Telefon" };
        JComboBox<String> searchCriteriaComboBox = new JComboBox<>(searchOptions);

        // Butonul de căutare
        JButton searchActionButton = new JButton("Căutare");

        // Adăugăm componentele în JDialog
        searchDialog.add(new JLabel("Alege criteriul de căutare:"));
        searchDialog.add(searchCriteriaComboBox);
        searchDialog.add(new JLabel("Introdu valoarea de căutare:"));
        searchDialog.add(searchField);
        searchDialog.add(searchActionButton);

        // Logica de căutare
        searchActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                String selectedCriteria = (String) searchCriteriaComboBox.getSelectedItem();

                if (!searchText.isEmpty()) {
                    if (selectedCriteria.equals("Căutare după Nume")) {
                        Contact found = phoneBook.searchByName(searchText);
                        if (found != null) {
                            contactDisplayArea.setText("Contact găsit: " + found.getName() + ", " + found.getPhoneNumber() + ", " + found.getEmail());
                        } else {
                            contactDisplayArea.setText("Contactul nu a fost găsit.");
                        }
                    } else if (selectedCriteria.equals("Căutare după Număr de Telefon")) {
                        Contact found = phoneBook.searchByPhoneNumber(searchText);
                        if (found != null) {
                            contactDisplayArea.setText("Contact găsit: " + found.getName() + ", " + found.getPhoneNumber() + ", " + found.getEmail());
                        } else {
                            contactDisplayArea.setText("Contactul nu a fost găsit.");
                        }
                    }
                    searchDialog.dispose(); // Închidem fereastra de căutare după ce am efectuat căutarea
                } else {
                    contactDisplayArea.setText("Introdu valoarea căutată!");
                }
            }
        });

        // Afișăm JDialog-ul
        searchDialog.setVisible(true);
    }
}
