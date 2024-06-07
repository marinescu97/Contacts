package com.marinescu.contacts.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class represents a contact.
 * It is represented by its properties like first name, last name, phone number and notes.
 */
public class Contact {
    /**
     * The first name.
     */
    private final SimpleStringProperty firstName;
    /**
     * The last name.
     */
    private final SimpleStringProperty lastName;
    /**
     * The phone number.
     */
    private final SimpleStringProperty phoneNumber;
    /**
     * The notes.
     */
    private final SimpleStringProperty notes;

    /**
     * Creates a new contact with the given properties.
     * @param firstName the first name
     * @param lastName the last name
     * @param phoneNumber the phone number
     * @param notes the notes
     */
    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.notes = new SimpleStringProperty(notes);
    }

    /**
     * Creates a new instance of Contact with the specified properties.
     * @param firstName the first name of the contact
     * @param lastName the last name of the contact
     * @param phoneNumber the phone number of the contact
     */
    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.notes = new SimpleStringProperty("");
    }

    /**
     * Gets the first name.
     * @return the first name
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets the first name with a specified value.
     * @param fName the new first name
     */
    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    /**
     * Gets the last name.
     * @return the last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets the last name with a specified value.
     * @param lName the new last name
     */
    public void setLastName(String lName) {
        lastName.set(lName);
    }

    /**
     * Gets the phone number.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Sets the phone number with a specified value.
     * @param pNumber the new phone number
     */
    public void setPhoneNumber(String pNumber) {
        phoneNumber.set(pNumber);
    }

    /**
     * Gets the notes.
     * @return the notes
     */
    public String getNotes() {
        return notes.get();
    }

    /**
     * Sets the notes with a specified value.
     * @param note the new notes
     */
    public void setNotes(String note) {
        notes.set(note);
    }
}
