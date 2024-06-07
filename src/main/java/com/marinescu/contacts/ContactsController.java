package com.marinescu.contacts;

import com.marinescu.contacts.model.Contact;
import com.marinescu.contacts.model.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is the controller for the contactsView.fxml file.
 */
public class ContactsController {
    /**
     * The table with the contacts.
     */
    @FXML
    private TableView<Contact> contactsTable;
    /**
     * The table column.
     */
    @FXML
    private TableColumn<Contact, String> firstNameColumn, lastNameColumn, phoneNumberColumn, notesColumn;
    /**
     * The main container.
     */
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the window.
     */
    public void initialize(){
        contactsTable.setEditable(true);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction(actionEvent -> {
            Contact item = contactsTable.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });
        contextMenu.getItems().addAll(deleteMenu);
        contactsTable.setContextMenu(contextMenu);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setFirstName(t.getNewValue())
        );

        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setLastName(t.getNewValue())
        );

        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPhoneNumber(t.getNewValue())
        );

        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setNotes(t.getNewValue())
        );

        contactsTable.setItems(Data.getInstance().getItems());
        contactsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Opens a dialog for adding a new contact.
     */
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add new contact item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogView.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            controller.addContact();
        }
    }

    /**
     * Deletes a contact from the table.
     * @param item the contact to delete
     */
    public void deleteItem(Contact item){
        if (contactsTable.getSelectionModel().getSelectedItem()!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete contact item");
            alert.setHeaderText("Delete contact: " + item.getFirstName() + " " + item.getLastName());
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Data.getInstance().deleteContact(item);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select a contact");
            alert.show();
        }
    }

    /**
     * Handles the delete key press event on the contacts table.
     * This method is triggered when a key is pressed while the contacts' table is focused.
     * If the delete key is pressed, and an item is selected in the table, the selected item will be deleted.
     * @param keyEvent the key event that triggered this handler
     */
    @FXML
    public void handleDeleteKey(KeyEvent keyEvent){
        Contact selectedItem = contactsTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            if (keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedItem);
            }
        }
    }

    /**
     * This method deletes the selected item from the table, when the “Delete” button is clicked.
     */
    @FXML
    public void handleDeleteMenuItem(){
        Contact item = contactsTable.getSelectionModel().getSelectedItem();
        deleteItem(item);
    }

    /**
     * Opens a dialog for updating the selected contact in the table.
     */
    @FXML
    public void showUpdateDialog(){
        Contact contact = contactsTable.getSelectionModel().getSelectedItem();
        int index = contactsTable.getItems().indexOf(contact);
        if (contact!=null){
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(borderPane.getScene().getWindow());
            dialog.setTitle("Update the contact");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dialogView.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e){
                System.out.println("Couldn't load the dialog");
                e.printStackTrace();
                return;
            }

            DialogController controller = fxmlLoader.getController();
            controller.setfNameField(contact.getFirstName());
            controller.setLNameField(contact.getLastName());
            controller.setPNumberField(contact.getPhoneNumber());
            controller.setNotesField(contact.getNotes());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (controller.updateContact(index)){
                    alert.setHeaderText("The contact has been updated!");
                    alert.show();
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setHeaderText("Something went wrong, please try again later.");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select a contact");
            alert.show();
        }
    }
}