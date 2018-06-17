package model;

import enums.TelephoneType;
import exceptions.DateInTheFutureException;
import exceptions.InvalidContactNameException;
import exceptions.InvalidPhoneNumberFormatException;
import exceptions.InvalidPhoneTypeException;

import java.time.LocalDate;

import static enums.TelephoneType.Fixed;
import static enums.TelephoneType.Mobile;

/**
 * Created by Vult on 02-Jun-18.
 * Contact model
 */
public class Contact {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Enum<TelephoneType> telephoneType;

    public Contact(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, Enum<TelephoneType> telephoneType) throws Exception {
        inputDataValidation(firstName, lastName, dateOfBirth, phoneNumber, telephoneType);

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.telephoneType = telephoneType;
    }

    private void inputDataValidation(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, Enum<TelephoneType> telephoneType) throws Exception {
        firstNameValidations(firstName);
        lastNameValidations(lastName);
        dateOfBirthValidations(dateOfBirth);
        phoneNumberValidations(phoneNumber, telephoneType);
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

    private void phoneNumberValidations(String phoneNumber, Enum<TelephoneType> telephoneType) throws Exception {
        if (phoneNumber != null && phoneNumber.length() != 10) {
            throw new InvalidPhoneNumberFormatException("Phone number must have 10 digits !");
        }
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            throw new InvalidPhoneNumberFormatException("Phone number must contain only digits !");
        }
        switch ((TelephoneType) telephoneType) {
            case Mobile:
                if (phoneNumber != null && !phoneNumber.startsWith("07")) {
                    throw new InvalidPhoneNumberFormatException("Mobile phone numbers must start with 07 !");
                }
                break;
            case Fixed:
                if (phoneNumber != null && !phoneNumber.startsWith("02") && !phoneNumber.startsWith("03")) {
                    throw new InvalidPhoneNumberFormatException("Fixed phone numbers must start with 02 or 03 !");
                }
                break;
            default:
                throw new InvalidPhoneTypeException("Phone of type " + telephoneType.toString() + " not supported !");
        }
    }

    private void telephoneTypeValidations(Enum<TelephoneType> telephoneType) throws Exception {
        if (telephoneType != Mobile && telephoneType != Fixed) {
            throw new InvalidPhoneTypeException("Phone of type " + telephoneType.toString() + " not supported !");
        }
    }

    public void setPhoneNumber(String phoneNumber, Enum<TelephoneType> telephoneType) throws Exception {
        phoneNumberValidations(phoneNumber, telephoneType);
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Enum<TelephoneType> getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(Enum<TelephoneType> telephoneType) throws Exception {
        telephoneTypeValidations(telephoneType);
        this.telephoneType = telephoneType;
    }

    @Override
    public String toString() {
        return firstName + ' ' +
                lastName + " born on " +
                dateOfBirth + ". " +
                telephoneType + " number : " +
                phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!firstName.equals(contact.firstName)) return false;
        if (!lastName.equals(contact.lastName)) return false;
        if (!dateOfBirth.equals(contact.dateOfBirth)) return false;
        if (!phoneNumber.equals(contact.phoneNumber)) return false;
        return telephoneType.equals(contact.telephoneType);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + telephoneType.hashCode();
        return result;
    }
}
