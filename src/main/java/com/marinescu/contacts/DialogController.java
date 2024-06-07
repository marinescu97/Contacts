package com.marinescu.contacts;

import com.marinescu.contacts.model.Contact;
import com.marinescu.contacts.model.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the controller for the dialogView.fxml file.
 */
public class DialogController {
    /**
     * The main container.
     */
    @FXML
    private DialogPane mainContainer;
    /**
     * The text field of the dialog.
     */
    @FXML
    private TextField fNameField, lNameField, pNumberField, notesField;
    /**
     * An alert object.
     */
    private final Alert alert = new Alert(Alert.AlertType.WARNING);

    /**
     * Sets the first name field with a given value.
     * @param firstName the given first name
     */
    public void setfNameField(String firstName) {
        this.fNameField.setText(firstName);
    }

    /**
     * Sets the last name field with a given value.
     * @param lastName the given last name
     */
    public void setLNameField(String lastName) {
        this.lNameField.setText(lastName);
    }

    /**
     * Sets the phone number field with a given value.
     * @param phoneNumber the given phone number
     */
    public void setPNumberField(String phoneNumber) {
        this.pNumberField.setText(phoneNumber);
    }

    /**
     * Sets the notes' field with a given value.
     * @param notes the given notes
     */
    public void setNotesField(String notes) {
        this.notesField.setText(notes);
    }

    /**
     * Adds a new contact to the list.
     */
    public void addContact(){
        mainContainer.setHeaderText("Add a new contact");
        String fName = fNameField.getText().trim();
        String lName = lNameField.getText().trim();
        String phoneNumber = pNumberField.getText().trim();
        String notes = notesField.getText().trim();

        String regex = "^07\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (phoneNumber!=null && !matcher.matches()){
            alert.setHeaderText("Please enter a valid phone number");
            alert.show();
        } else {
            Contact contact = new Contact(fName, lName, phoneNumber, notes);
            Data.getInstance().addContactItem(contact);
        }
    }

    /**
     * Updates a selected contact from the list.
     * @param index the index of the contact
     * @return true if the contact was updated, false otherwise
     */
    public boolean updateContact(int index){
        mainContainer.setHeaderText("Update the contact");
        String fName = fNameField.getText().trim();
        String lName = lNameField.getText().trim();
        String phoneNumber = pNumberField.getText().trim();
        String notes = notesField.getText().trim();

        String regex = "^07\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (phoneNumber!=null && !matcher.matches()){
            alert.setHeaderText("Please enter a valid phone number");
            alert.show();
        } else {
            Contact contact = new Contact(fName, lName, phoneNumber, notes);
            Data.getInstance().updateContact(index, contact);
            return true;
        }
        return false;
    }
}
