package model;

import exceptions.DuplicateContactException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vult on 02-Jun-18.
 * Agenda that holds all contacts
 */
public class Agenda implements Serializable {
    private static Set<Contact> contacts = new HashSet<>();

    public static void addContact(Contact contact) throws Exception {
        if (!contacts.add(contact)) {
            throw new DuplicateContactException("Contact already exists in Agenda !");
        }
        ;
    }

    public static void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public static Set<Contact> getContacts() {
        return contacts;
    }
}
