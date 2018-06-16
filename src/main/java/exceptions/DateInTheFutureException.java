package exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Vult on 10-Jun-18.
 * Exception class when the date is in the future. Because it is a date of birth, it is not logical to be in the future
 * Unless we are dealing with the Terminator that came from the future...
 */
public class DateInTheFutureException extends IllegalArgumentException {

    public DateInTheFutureException(LocalDate date) {
        super("Date of birth " + date.format(DateTimeFormatter.ofPattern("dd.LL.yyyy")) + " cannot be in the future!");
    }
}
