package agendatelefonica;
/**
 * Clasa Contact reprezintă un contact din agenda telefonică.
 * Fiecare contact conține un nume, un număr de telefon și o adresă de email.
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    /**
     * Constructorul clasei Contact.
     *
     * @param name        Numele contactului. Este un șir de caractere care reprezintă numele complet al contactului.
     * @param phoneNumber Numărul de telefon al contactului. Este un șir de caractere care reprezintă numărul de telefon asociat contactului.
     * @param email       Adresa de email a contactului. Este un șir de caractere care reprezintă adresa de email asociată contactului.
     */
    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Obține numele contactului.
     *
     * @return Numele contactului ca șir de caractere.
     */
    public String getName() {
        return name;
    }

    /**
     * Setează numele contactului.
     *
     * @param name Noul nume pentru contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obține numărul de telefon al contactului.
     *
     * @return Numărul de telefon al contactului ca șir de caractere.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setează numărul de telefon al contactului.
     *
     * @param phoneNumber Noul număr de telefon pentru contact.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Obține adresa de email a contactului.
     *
     * @return Adresa de email a contactului ca șir de caractere.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setează adresa de email a contactului.
     *
     * @param email Noua adresă de email pentru contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + phoneNumber + "," + email;
    }
}
