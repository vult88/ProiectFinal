package exceptions;

/**
 * Created by Vult on 10-Jun-18.
 * Exception class when the name has an invalid format
 */
public class InvalidContactNameException extends IllegalArgumentException {
    public InvalidContactNameException(String message) {
        super(message);
    }
}
