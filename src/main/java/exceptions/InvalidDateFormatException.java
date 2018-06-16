package exceptions;

import java.io.IOException;

/**
 * Created by Vult on 10-Jun-18.
 * Exception class when the date has an invalid format
 */
public class InvalidDateFormatException extends IOException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
