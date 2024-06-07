package com.marinescu.contacts;

import com.marinescu.contacts.model.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class of the Contacts Application.
 * It extends the {@link Application} class.
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("contactsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 240);
        stage.setTitle("Contact list");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method launches the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * This method stops the application and stores the contacts details to the Contacts.txt file.
     */
    @Override
    public void stop() {
        try {
            Data.getInstance().storeContacts();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method initializes the application.
     * If the contacts can be loaded from the Contacts.txt file, the application will start.
     * Otherwise, a message will be shown, and the application will stop.
     */
    @Override
    public void init() {
        try {
            Data.getInstance().loadContacts();
        } catch (IOException e){
            System.out.println("Couldn't load contact items");
            System.out.println(e.getMessage());
        }
    }
}