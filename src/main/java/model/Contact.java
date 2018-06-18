package model;

import enums.TelephoneType;
import exceptions.DateInTheFutureException;
import exceptions.InvalidContactNameException;
import exceptions.InvalidPhoneTypeException;

import java.io.Serializable;
import java.time.LocalDate;

import static enums.TelephoneType.Fixed;
import static enums.TelephoneType.Mobile;

/**
 * Created by Vult on 02-Jun-18.
 * Contact model
 */
public class Contact implements Serializable {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Enum<TelephoneType> telephoneType;
    private Telephone telephoneNumber;

    public Contact(String firstName, String lastName, LocalDate dateOfBirth, Telephone telephoneNumber, Enum<TelephoneType> telephoneType) throws Exception {
        inputDataValidation(firstName, lastName, dateOfBirth, telephoneType);

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.telephoneType = telephoneType;
    }

    private void inputDataValidation(String firstName, String lastName, LocalDate dateOfBirth, Enum<TelephoneType> telephoneType) throws Exception {
        firstNameValidations(firstName);
        lastNameValidations(lastName);
        dateOfBirthValidations(dateOfBirth);
        telephoneTypeValidations(telephoneType);
    }

    private void firstNameValidations(String firstName) throws Exception {
        if (firstName == null || firstName.length() < 2) {
            throw new InvalidContactNameException("First name must be at least 2 characters long !");
        }
        if (!firstName.matches("^[\\p{L} .'-]+$")) {
            throw new InvalidContactNameException("First name should only contain letters, spaces and separating characters !");
        }
    }

    private void lastNameValidations(String lastName) throws Exception {
        if (lastName == null || lastName.length() < 2) {
            throw new InvalidContactNameException("Last name must be at least 2 characters long !");
        }
        if (!lastName.matches("^[\\p{L} .'-]+$")) {
            throw new InvalidContactNameException("Last name should only contain letters, spaces and separating characters !");
        }
    }

    private void dateOfBirthValidations(LocalDate dateOfBirth) throws Exception {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new DateInTheFutureException(dateOfBirth);
        }
    }

    private void telephoneTypeValidations(Enum<TelephoneType> telephoneType) throws Exception {
        if (telephoneType != Mobile && telephoneType != Fixed) {
            throw new InvalidPhoneTypeException("Phone of type " + telephoneType.toString() + " not supported !");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws Exception {
        firstNameValidations(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws Exception {
        lastNameValidations(lastName);
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) throws Exception {
        dateOfBirthValidations(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public Enum<TelephoneType> getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(Enum<TelephoneType> telephoneType) throws Exception {
        telephoneTypeValidations(telephoneType);
        this.telephoneType = telephoneType;
    }

    public Telephone getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(Telephone telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String toString() {
        return firstName + ' ' +
                lastName + " born on " +
                dateOfBirth + ". " +
                (telephoneType == TelephoneType.Mobile ? "Mobile" : "Fixed") +
                " number : " +
                telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!firstName.equals(contact.firstName)) return false;
        if (!lastName.equals(contact.lastName)) return false;
        if (!dateOfBirth.equals(contact.dateOfBirth)) return false;
        if (!telephoneType.equals(contact.telephoneType)) return false;
        return telephoneNumber.equals(contact.telephoneNumber);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + telephoneType.hashCode();
        result = 31 * result + telephoneNumber.hashCode();
        return result;
    }
}
