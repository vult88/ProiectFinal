package model;

import enums.OrderCriteria;
import enums.TelephoneType;
import exceptions.DuplicateContactException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Vult on 02-Jun-18.
 * Agenda that holds all contacts
 */
public class Agenda implements Serializable {
    private Set<Contact> contacts = new HashSet<>();

    private Map<String, Comparator> criteriaToComparator = new HashMap<>();
    private Predicate<Contact> filterCriteria = contact -> true;
    private OrderCriteria orderCriteria;


    public Agenda() {
        criteriaToComparator.put("firstName", Comparator.comparing(Contact::getFirstName));
        criteriaToComparator.put("lastName", Comparator.comparing(Contact::getLastName));
        criteriaToComparator.put("telephone", Comparator.comparing(Contact::getTelephoneNumber));
        criteriaToComparator.put("date", Comparator.comparing(Contact::getDateOfBirth));
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

    public void filterOnFixedTelephonType() {
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
}
