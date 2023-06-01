package com.example.endassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

//This controller is the first controller of this application.
public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    private Database database = new Database();
    private List<User> users = Arrays.asList(new User("ana@gmail.com", "1234", "Ana", "Taylor"),
            new User("john@gmail.com", "4321", "John", "Doe"));

    //The initialize method is the first method of this class and its purpose
    // is to read the items and members from the serialized file
    // and then store them in an object of the class Database
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Item> items = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        readFromFile(items, members);

        database.setItems(items);
        database.setMembers(members);
    }

    //This method enables or disables the Login button depending on
    // if there is anything written in the text field or password field
    @FXML
    private void statusOfLoginButton(KeyEvent keyEvent) {
        keyEvent.consume();

        if (!usernameTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            loginButton.setDisable(false);
        } else {
            loginButton.setDisable(true);
            errorLabel.setText("");
        }
    }

    //This method checks if the text from the text field and password field match with an existing user.
    //If it does not match two types of error can occur.
    //If it matches, the setNewControllerAndStage method is called.
    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        event.consume();

        for (User user : users) {

            if (user.getUsername().equals(usernameTextField.getText())
                    && user.getPassword().equals(passwordField.getText())) {

                try {
                    setNewControllerAndStage(user);

                } catch (Exception e) {
                    System.out.println("Error" + e);
                }

            } else if (user.getUsername().equals(usernameTextField.getText())) {
                errorLabel.setText("Invalid Password!");
                return;

            } else {
                errorLabel.setText("Invalid Username!");
            }
        }
    }

    //This method reads from the database file.
    //It reads numberOfItems items from the database then it reads numberOfMembers members.
    private void readFromFile(List<Item> items, List<Member> members) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database"))) {
            while (true) {
                try {

                    int numberOfItems = ois.readInt();
                    for (int i = 0; i < numberOfItems; i++) {
                        Item item = (Item) ois.readObject();
                        items.add(item);
                    }

                    int numberOfMembers = ois.readInt();
                    for (int i = 0; i < numberOfMembers; i++) {
                        Member member = (Member) ois.readObject();
                        members.add(member);
                    }

                } catch (EOFException e) {
                    break;

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //This method creates a new stage for the MainController Object.
    //It also calls the setController method in the mainController class so that the user and database are passed.
    private void setNewControllerAndStage(User user) throws IOException {
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = fxmlLoader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Main View");
        newStage.setScene(new Scene(root));
        newStage.show();

        saveDatabase(newStage);

        MainController mainController = fxmlLoader.getController();
        mainController.setController(user, database);
    }

    //This method saves the current lists of Members/Items from the database object to the database file.
    //This method only happens when the main view of the application is closed.
    private void saveDatabase(Stage newStage) {
        newStage.setOnCloseRequest(windowEvent -> {
            try {

                FileOutputStream fos = new FileOutputStream("database");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeInt(database.getItems().size());
                for (Item item : database.getItems()) {
                    oos.writeObject(item);
                }

                oos.writeInt(database.getMembers().size());
                for (Member member : database.getMembers()) {
                    oos.writeObject(member);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}