package enums;

/**
 * Created by Vult on 02-Jun-18.
 * Enums for ordering in the window
 */
public enum OrderCriteria {
    ORDER_BY_FIRSTNAME("First name"),
    ORDER_BY_LASTNAME("Last name"),
    ORDER_BY_DATE_OF_BIRTH("Date of birth"),
    ORDER_BY_TELEPHONE_NUMBER("Telephone number");

    private String message;

    OrderCriteria(String message) {
        this.message = message;
    }

    public static String enumToText(OrderCriteria orderCriteria) {
        return orderCriteria.getMessage();
    }

    public static OrderCriteria fromString(String message) {
        for (OrderCriteria orderCriteria : OrderCriteria.values()) {
            if (orderCriteria.message.equalsIgnoreCase(message)) {
                return orderCriteria;
            }
        }
        return ORDER_BY_FIRSTNAME;
    }

    public String getMessage() {
        return message;
    }
}
