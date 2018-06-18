package model;

import exceptions.InvalidPhoneNumberFormatException;

import java.io.Serializable;

/**
 * Created by Vult on 2018-06-17.
 */
public class TelephoneMobile extends Telephone implements Serializable {

    public TelephoneMobile(String phoneNumber) throws Exception {
        super(phoneNumber);
        validateNumber(phoneNumber);
    }

    @Override
    public void validateNumber(String phoneNumber) throws Exception {
        if (phoneNumber != null && phoneNumber.length() != 10) {
            throw new InvalidPhoneNumberFormatException("Phone number must have 10 digits !");
        }
        if (phoneNumber != null && !phoneNumber.matches("\\d{10}")) {
            throw new InvalidPhoneNumberFormatException("Phone number must contain only digits !");
        }
        if (phoneNumber != null && !phoneNumber.startsWith("07")) {
            throw new InvalidPhoneNumberFormatException("Mobile phone numbers must start with 07 !");
        }
    }

    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(getPhoneNumber()) - Integer.parseInt(o.toString());
    }

    @Override
    public String toString() {
        return getPhoneNumber();
    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }
}
