package model;

import exceptions.InvalidPhoneNumberFormatException;

/**
 * Created by Vult on 2018-06-17.
 */
public class TelephoneFixed extends Telephone {
    private String phoneNumber;

    public TelephoneFixed(String phoneNumber) throws Exception {
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
        if (phoneNumber != null && !phoneNumber.startsWith("02") && !phoneNumber.startsWith("03")) {
            throw new InvalidPhoneNumberFormatException("Fixed phone numbers must start with 02 or 03 !");
        }
    }

    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(this.phoneNumber) - Integer.parseInt(o.toString());
    }

    @Override
    public String toString() {
        return "Fixed number " + phoneNumber;
    }
}
