package model;

import enums.OrderCriteria;
import enums.TelephoneType;
import exceptions.DuplicateContactException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static enums.OrderCriteria.*;

/**
 * Created by Vult on 02-Jun-18.
 * Agenda that holds all contacts
 */
public class Agenda implements Serializable {
    private Set<Contact> contacts = new HashSet<>();

    private Map<OrderCriteria, Comparator> criteriaToComparator = new HashMap<>();
    private Predicate<Contact> filterCriteria = contact -> true;


    public Agenda() {
        criteriaToComparator.put(ORDER_BY_FIRSTNAME, Comparator.comparing(Contact::getFirstName));
        criteriaToComparator.put(ORDER_BY_LASTNAME, Comparator.comparing(Contact::getLastName));
        criteriaToComparator.put(ORDER_BY_TELEPHONE_NUMBER, Comparator.comparing(Contact::getTelephoneNumber));
        criteriaToComparator.put(ORDER_BY_DATE_OF_BIRTH, Comparator.comparing(Contact::getDateOfBirth));
    }

    public void addContact(Contact contact) throws Exception {
        if (!contacts.add(contact)) {
            throw new DuplicateContactException("Contact already exists in Agenda !");
        }
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    private void removeAllContacts() {
        contacts.clear();
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void renewContacts(Set<Contact> contactsList) {
        removeAllContacts();
        contacts.addAll(contactsList);
    }

    public void filterOnFixedTelephoneType() {
        filterCriteria = contact -> TelephoneType.Fixed.equals(contact.getTelephoneType());
    }

    public void filterOnMobileTelephoneType() {
        filterCriteria = contact -> TelephoneType.Mobile.equals(contact.getTelephoneType());
    }

    public void filterOnBornToday() {
        filterCriteria = contact -> contact.getDateOfBirth().isEqual(LocalDate.now());
    }

    public void filterOnBornThisMonth() {
        filterCriteria = contact -> contact.getDateOfBirth().getMonth().equals(LocalDate.now().getMonth());
    }

    public void filterOnCustomFilter(String filter) {
        filterCriteria = contact -> contact.getFirstName().toLowerCase().contains(filter.toLowerCase())
                || contact.getLastName().toLowerCase().contains(filter.toLowerCase())
                || contact.getTelephoneNumber().toString().contains(filter);
    }

    public void filterOnNothing() {
        filterCriteria = contact -> true;
    }

    public Predicate<Contact> getFilterCriteria() {
        return filterCriteria;
    }

    public Map<OrderCriteria, Comparator> getCriteriaToComparator() {
        return criteriaToComparator;
    }

    public Comparator<Contact> getComparator(OrderCriteria key) {
        return criteriaToComparator.get(key);
    }
}
