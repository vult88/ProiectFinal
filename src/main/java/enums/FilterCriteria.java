package enums;

/**
 * Created by Vult on 2018-06-17.
 *
 */
public enum FilterCriteria {
    SHOW_ALL("Show all contacts"),
    BORN_TODAY("Born today"),
    BORN_CURRENT_MONTH("Born this month"),
    TELEPHONE_TYPE_MOBILE("Has a mobile phone number"),
    TELEPHONE_TYPE_FIXED("Has a fixed phone number"),
    CUSTOM_FILTER("Custom filter");

    private String message;

    FilterCriteria(String message) {
        this.message = message;
    }

    public static String enumToText(FilterCriteria filterCriteria) {
        return filterCriteria.getMessage();
    }

    public static FilterCriteria fromString(String message) {
        for (FilterCriteria filterCriteria : FilterCriteria.values()) {
            if (filterCriteria.message.equalsIgnoreCase(message)) {
                return filterCriteria;
            }
        }
        return SHOW_ALL;
    }

    public String getMessage() {
        return message;
    }

}
