package com.marinescu.contacts.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class provides a singleton instance of data from the Contacts.txt file.
 */
public class Data {
    /**
     * The instance of the class.
     */
    private static final Data instance = new Data();
    /**
     * The file in which the data is located.
     */
    private static final String fileName = "Contacts.txt";
    /**
     * The list of the contacts.
     */
    private ObservableList<Contact> items;

    /**
     * Gets the instance of the class.
     * @return The instance of the class.
     */
    public static Data getInstance() {
        return instance;
    }

    /**
     * Gets the list of the contacts.
     * @return The list of the contacts.
     */
    public ObservableList<Contact> getItems() {
        return items;
    }

    /**
     * Reads the contacts from the file.
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void loadContacts() throws IOException {
        items = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(" -> ");

                String firstName = itemPieces[0];
                String lastName = itemPieces[1];
                String phoneNumber = itemPieces[2];
                String notes = itemPieces[3];

                Contact contactItem = new Contact(firstName, lastName, phoneNumber, notes);
                items.add(contactItem);
            }
        }
    }

    /**
     * Writes contacts to the file.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void storeContacts() throws IOException{
        Path path = Paths.get(fileName);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Contact item : items) {
                bw.write(String.format("%s -> %s -> %s -> %s", item.getFirstName(), item.getLastName(), item.getPhoneNumber(), item.getNotes()));
                bw.newLine();
            }
        }
    }

    /**
     * Adds a new contact to the list.
     * @param item the new contact
     */
    public void addContactItem(Contact item){
        items.add(item);
    }

    /**
     * Removes a contact from the list.
     * @param item the contact to remove
     */
    public void deleteContact(Contact item){
        items.remove(item);
    }

    /**
     * Updates a contact from the list.
     * @param index the index of the contact
     * @param item the contact to update
     */
    public void updateContact(int index, Contact item){
        items.set(index, item);
    }
}
