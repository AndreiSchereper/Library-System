package com.example.endassignment;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {
    //Panes
    @FXML
   private Pane firstPane;
    @FXML
   private Pane secondPane;
    @FXML
    private Pane thirdPane;
    @FXML
    private Pane addItemPane;
    @FXML
    private Pane editItemPane;
    @FXML
    private Pane addMemberPane;
    @FXML
    private Pane editMemberPane;

    //Labels
    @FXML
    private Label nameLabel;
    @FXML
    private Label labelLending;
    @FXML
    private Label labelReceive;

    //TextFields
    @FXML
    private TextField lendItemCodeTextField;
    @FXML
    private TextField memberIdentifierTextField;
    @FXML
    private TextField receiveItemCodeTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField titleTextField2;
    @FXML
    private TextField authorTextField2;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField2;
    @FXML
    private TextField lastNameTextField2;
    @FXML
    private TextField searchItemTextField;
    @FXML
    private TextField searchMemberTextField;

    //Buttons
    @FXML
    private Button firstCancelButton;
    @FXML
    private Button secondCancelButton;
    @FXML
    private Button thirdCancelButton;
    @FXML
    private Button fourthCancelButton;
    @FXML
    private Button confirmItemButton;
    @FXML
    private Button deleteItemButton;
    @FXML
    private Button confirmItemEditButton;
    @FXML
    private Button deleteMemberButton;
    @FXML
    private Button editMemberButton;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button confirmMemberButton;
    @FXML
    private Button confirmMemberEditButton;
    @FXML
    private Button lendButton;
    @FXML
    private Button receiveButton;
    @FXML
    private Button firstMainButton;
    @FXML
    private Button secondMainButton;
    @FXML
    private Button thirdMainButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button editItemButton;

    //Tables
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, Integer> codeColumn;
    @FXML
    private TableColumn<Item, String> availableColumn;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> authorColumn;
    private ObservableList<Item> items;
    @FXML
    private TableView<Member> memberTable;
    @FXML
    private TableColumn<Member, Integer> identifierColumn;
    @FXML
    private TableColumn<Member, String> firstNameColumn;
    @FXML
    private TableColumn<Member, String> lastNameColumn;
    @FXML
    private TableColumn<Member, LocalDate> birthDateColumn;
    private ObservableList<Member> members;

    //DatePickers
    @FXML
    private DatePicker itemDatePicker;
    @FXML
    private DatePicker memberDatePicker;
    @FXML
    private DatePicker memberEditDatePicker;

    @FXML
    private CheckBox itemCheckBox;
    private Item selectedEditItem;
    private Member selectedEditMember;
    private Database database;
    private User user;

    //This method gets the user and the database from the loginController.
    public void setController(User user, Database database) {
        this.user = user;
        this.database=database;
        nameLabel.setText("Welcome " + this.user.getFirstName() + " " + this.user.getLastName());
    }

    //This method changes the pane depending on the event.
    @FXML
    private void changePaneAction(ActionEvent event) {
        event.consume();

        if (event.getSource().equals(firstMainButton)) {
            showFirstMainPane();

        } else if (event.getSource().equals(secondMainButton)) {
            showSecondMainPane();

        } else if (event.getSource().equals(thirdMainButton)) {
            showThirdMainPane();
        }
    }

    //This method enables buttons depending on where the user types in the main pane.
    @FXML
    private void onKeyTypedInMain(KeyEvent event) {
        if (event.getSource().equals(lendItemCodeTextField) ||
                event.getSource().equals(memberIdentifierTextField)) {

            if (!lendItemCodeTextField.getText().isEmpty() && !memberIdentifierTextField.getText().isEmpty()) {
                lendButton.setDisable(false);

            } else {
                lendButton.setDisable(true);
                labelLending.setText("");
            }

        } else if (event.getSource().equals(receiveItemCodeTextField)) {

            if (!receiveItemCodeTextField.getText().isEmpty()) {
                receiveButton.setDisable(false);

            } else {
                receiveButton.setDisable(true);
                labelReceive.setText("");
            }
        }
    }

    //This method calls other methods depending on what button was pressed.
    @FXML
    private void onButtonClickedInMain(ActionEvent event) {
        if (event.getSource().equals(lendButton)) {
            lendButtonAction();

        } else if (event.getSource().equals(receiveButton)) {
            receiveButtonAction();
        }
    }

    //This method enables the editButtons and deleteButtons depending on the mouseEvent.
    @FXML
    private void enableEditAndDelete(MouseEvent mouseEvent) {
        mouseEvent.consume();

        if (mouseEvent.getSource().equals(itemTable)) {

            if(itemTable.getSelectionModel().getSelectedItem()!=null) {
                deleteItemButton.setDisable(false);
                editItemButton.setDisable(false);
            }

        } else if (mouseEvent.getSource().equals(memberTable)) {

            if(memberTable.getSelectionModel().getSelectedItem()!=null) {
                deleteMemberButton.setDisable(false);
                editMemberButton.setDisable(false);
            }
        }
    }

    //This method makes the addItemPane visible or calls the editItemButtonAction method
    // depending on what button was pressed (addItem button or editItem button).
    @FXML
    private void openNewItemPane(ActionEvent event) {
        event.consume();

        secondPane.setDisable(true);
        firstMainButton.setDisable(true);
        thirdMainButton.setDisable(true);
        searchItemTextField.setText("");

        if (event.getSource().equals(addItemButton)) {
            addItemPane.setVisible(true);

        } else if (event.getSource().equals(editItemButton)) {
            editItemButtonAction();
        }
    }

    //This method calls other delete methods depending on what pane the user is (Item pane or Member pane).
    @FXML
    private void deleteItemOrMember(ActionEvent event) {
        event.consume();

        if (event.getSource().equals(deleteItemButton)) {
            deleteItemFromDatabase();

        } else if (event.getSource().equals(deleteMemberButton)) {
            deleteMemberFromDatabase();
        }
    }

    //This method calls other methods depending on what pane the user is (Item pane or MemberPane)
    @FXML
    private void searchKeyTyped(KeyEvent event) {
        event.consume();

        if(event.getSource().equals(searchItemTextField)){
            showSearchedItem();

        } else if (event.getSource().equals(searchMemberTextField)) {
            showSearchedMember();
        }
    }

    //This method makes the itemDatePicker visible or not depending on the status of the itemCheckBox.
    @FXML
    private void clickCheckBox(ActionEvent event) {
        event.consume();

        if (itemCheckBox.isSelected()) {
            itemDatePicker.setVisible(false);

        } else {
            itemDatePicker.setVisible(true);
            itemDatePicker.setValue(LocalDate.now());
        }
    }

    //This method enables the addItem button if the user typed a title and an author.
    @FXML
    private void enableAddItemButton(KeyEvent event) {
        event.consume();

        if (!titleTextField.getText().isEmpty() && !authorTextField.getText().isEmpty()) {
            confirmItemButton.setDisable(false);

        } else {
            confirmItemButton.setDisable(true);
        }
    }

    //This method calls other methods depending on what the user wants to do (add item or edit item).
    @FXML
    private void confirmItemAction(ActionEvent event) {
        event.consume();

        if (event.getSource().equals(confirmItemButton)) {

            int newCode = getNewItemCode();
            addNewItemInDatabase(newCode);

        } else if (event.getSource().equals(confirmItemEditButton)) {

            editItem();
        }
        refreshSecondMainPane();
    }

    //This method cancels any action the user made in the addItemPane or editItemPane.
    @FXML
    private void cancelItemAction(ActionEvent event) {
        event.consume();

        if (event.getSource().equals(firstCancelButton)) {
            addItemPane.setVisible(false);

        } else if (event.getSource().equals(secondCancelButton)) {
            editItemPane.setVisible(false);
        }

        secondPane.setDisable(false);
        firstMainButton.setDisable(false);
        thirdMainButton.setDisable(false);
    }

    //This method makes the addMemberPane visible or calls the editMemberButtonAction method
    // depending on what button was pressed (addMember button or editMember button).
    @FXML
    private void openNewMemberPane(ActionEvent event) {
        event.consume();

        thirdPane.setDisable(true);
        firstMainButton.setDisable(true);
        secondMainButton.setDisable(true);
        searchMemberTextField.setText("");

        if (event.getSource().equals(addMemberButton)) {
            addMemberPane.setVisible(true);

        } else if (event.getSource().equals(editMemberButton)) {
            editMemberButtonAction();
        }
    }

    //This method enables the addItem button if the user typed a first name and a last name.
    @FXML
    private void enableAddMemberButton(KeyEvent event) {
        event.consume();

        if (!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()) {
            confirmMemberButton.setDisable(false);

        } else {
            confirmMemberButton.setDisable(true);
        }
    }

    //This method cancels any action the user made in the addMemberPane or editMemberPane.
    @FXML
    private void cancelMemberAction(ActionEvent event) {
        event.consume();

        if (event.getSource().equals(thirdCancelButton)) {
            addMemberPane.setVisible(false);

        } else if (event.getSource().equals(fourthCancelButton)) {
            editMemberPane.setVisible(false);
        }

        thirdPane.setDisable(false);
        firstMainButton.setDisable(false);
        secondMainButton.setDisable(false);
    }

    //This method calls other methods depending on what the user wants to do (add member or edit member).
    @FXML
    private void confirmMemberAction(ActionEvent event) {
        event.consume();

        if(event.getSource().equals(confirmMemberButton)){
            int newCode = getNewMemberCode();
            addNewMemberInDatabase(newCode);

        } else if (event.getSource().equals(confirmMemberEditButton)) {
            editMember();
        }

        refreshThirdMainPane();
    }

    //This method sets up the first main pane (Lend/Receive Pane).
    private void showFirstMainPane() {
        firstPane.setVisible(true);
        secondPane.setVisible(false);
        thirdPane.setVisible(false);

        firstMainButton.setDisable(true);
        secondMainButton.setDisable(false);
        thirdMainButton.setDisable(false);

        labelLending.setText("");
        labelReceive.setText("");

        lendItemCodeTextField.setText("");
        memberIdentifierTextField.setText("");
        receiveItemCodeTextField.setText("");

        lendButton.setDisable(true);
        receiveButton.setDisable(true);
    }

    //This method sets up the second main pane (Items Pane).
    private void showSecondMainPane() {
        secondPane.setVisible(true);
        firstPane.setVisible(false);
        thirdPane.setVisible(false);

        secondMainButton.setDisable(true);
        firstMainButton.setDisable(false);
        thirdMainButton.setDisable(false);

        searchItemTextField.setText("");

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        items = FXCollections.observableArrayList(database.getItems());
        itemTable.setItems(items);
        itemTable.refresh();
    }

    //This method sets up the third main pane (Members Pane).
    private void showThirdMainPane() {
        thirdPane.setVisible(true);
        firstPane.setVisible(false);
        secondPane.setVisible(false);

        thirdMainButton.setDisable(true);
        firstMainButton.setDisable(false);
        secondMainButton.setDisable(false);

        searchMemberTextField.setText("");

        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        members = FXCollections.observableArrayList(database.getMembers());
        memberTable.setItems(members);
        memberTable.refresh();
    }

    //This method changes the database or shows errors depending on the item code and member identifier
    private void lendButtonAction() {
        int index = 0;

        try {
            for (Item item : database.getItems()) {

                if (item.getCode() == Integer.parseInt(lendItemCodeTextField.getText())) {

                    if (verifyMember(Integer.parseInt(memberIdentifierTextField.getText()))) {
                        memberExistsAction(index, item);
                        return;

                    } else if (!verifyMember(Integer.parseInt(memberIdentifierTextField.getText()))) {
                        labelLending.setTextFill(Color.RED);
                        labelLending.setText("The member does not exist!");
                        return;
                    }

                } else if (item.getCode() != Integer.parseInt(lendItemCodeTextField.getText())
                        && !verifyMember(Integer.parseInt(memberIdentifierTextField.getText()))) {
                    labelLending.setTextFill(Color.RED);
                    labelLending.setText("The item code and the member identifier are incorrect!");
                    return;

                } else if (item.getCode() != Integer.parseInt(lendItemCodeTextField.getText())) {
                    labelLending.setTextFill(Color.RED);
                    labelLending.setText("The item does not exist!");
                }

                index++;
            }
        }catch (Exception e){
            labelLending.setText("Please use only numbers");
        }
    }

    //This method is called when the member identifier is contained in the database
    private void memberExistsAction(int index, Item item) {
        if (item.getIsAvailable().equals("no")) {
            labelLending.setTextFill(Color.RED);
            labelLending.setText("Item is not available");
            return;
        }
        database.getItems().get(index).setIsAvailable("no");
        database.getItems().get(index).setLendingDate(LocalDate.now());
        labelLending.setTextFill(Color.GREEN);
        labelLending.setText("The item status has been changed!");
        lendItemCodeTextField.setText("");
        memberIdentifierTextField.setText("");
        lendButton.setDisable(true);
    }

    //This method checks if the identifier provided as the parameter is contained in the database.
    private boolean verifyMember(int identifier) {
        for (Member member : database.getMembers()) {
            if (identifier == member.getIdentifier()) {
                return true;
            }
        }
        return false;
    }

    //This method shows errors or calls other methods depending on the item code of a book.
    private void receiveButtonAction() {
        int index = 0;

        try {
            for (Item item : database.getItems()) {

                if (item.getIsAvailable().equals("no")) {

                    long days = Duration.between(item.getLendingDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();

                    if (item.getCode() == Integer.parseInt(receiveItemCodeTextField.getText()) && days > 21) {
                        bookIsLateAction(index, days);
                        return;

                    } else if (item.getCode() == Integer.parseInt(receiveItemCodeTextField.getText())) {
                        bookReceivedAction(index);
                        return;
                    }

                } else if (item.getCode() == Integer.parseInt(receiveItemCodeTextField.getText())) {
                    labelReceive.setTextFill(Color.RED);
                    labelReceive.setText("The book is not lent");
                    return;

                } else {
                    labelReceive.setTextFill(Color.RED);
                    labelReceive.setText("Incorrect Code!");
                }

                index++;
            }
        }catch (Exception e){
            labelReceive.setText("Please use only numbers");
        }
    }

    //This method is called when a book is late
    // ,and it displays a message telling the user by how many days the book is late
    // and changes the isAvailable field of the object.
    private void bookIsLateAction(int index, long days) {
        labelReceive.setTextFill(Color.RED);
        labelReceive.setText("Item is late by " + (days - 21) + " days!");
        database.getItems().get(index).setIsAvailable("yes");
        database.getItems().get(index).setLendingDate(null);
        receiveItemCodeTextField.setText("");
        itemDatePicker.setVisible(false);
        receiveButton.setDisable(true);
    }

    //This method is called when a book is received without any problems.
    private void bookReceivedAction(int index) {
        labelReceive.setTextFill(Color.GREEN);
        labelReceive.setText("The book has been received!");
        database.getItems().get(index).setIsAvailable("yes");
        database.getItems().get(index).setLendingDate(null);
        receiveItemCodeTextField.setText("");
        itemDatePicker.setVisible(false);
        receiveButton.setDisable(true);
    }

    //This method sets up the editItem Pane depending on the isAvailable field of the selected item from the itemTableView.
    private void editItemButtonAction() {

        editItemPane.setVisible(true);
        selectedEditItem = itemTable.getSelectionModel().getSelectedItem();
        titleTextField2.setText(selectedEditItem.getTitle());
        authorTextField2.setText(selectedEditItem.getAuthor());

        if (selectedEditItem.getIsAvailable().equals("yes")) {
            itemCheckBox.setSelected(true);
            itemDatePicker.setVisible(false);

        } else {
            itemCheckBox.setSelected(false);
            itemDatePicker.setVisible(true);
            itemDatePicker.setValue(selectedEditItem.getLendingDate());
        }
    }

    //This method generate a new item code.
    private int getNewItemCode() {
        int newCode;

        if (database.getItems().size() == 0) {
            newCode = 1;

        } else {
            newCode = database.getItems().get(database.getItems().size() - 1).getCode() + 1;
        }
        return newCode;
    }

    //This method adds a new item in the database and closes and refreshes the current pane.
    private void addNewItemInDatabase(int newCode) {
        Item newItem = new Item(titleTextField.getText(), authorTextField.getText(), "yes", newCode);
        database.getItems().add(newItem);
        titleTextField.setText("");
        authorTextField.setText("");
        confirmItemButton.setDisable(true);
        addItemPane.setVisible(false);
    }

    //This method deletes an item from the database and refreshes the itemTableView.
    private void deleteItemFromDatabase() {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            database.getItems().remove(selectedItem);
            items = FXCollections.observableArrayList(database.getItems());
            itemTable.setItems(items);
            itemTable.refresh();
            editItemButton.setDisable(true);
            deleteItemButton.setDisable(true);
        }
    }

    //This method refreshes the itemTableview with the items
    // that start with the letters which the user typed in the search bar.
    private void showSearchedItem() {
        List<Item> searchItems=new ArrayList<>();
        for(Item item: database.getItems()){
            if(item.getTitle().toLowerCase().startsWith(searchItemTextField.getText().toLowerCase())
                    || item.getAuthor().toLowerCase().startsWith(searchItemTextField.getText().toLowerCase())){
                searchItems.add(item);
                items = FXCollections.observableArrayList(searchItems);
                itemTable.setItems(items);
                itemTable.refresh();
            }
        }
    }

    //This method edits the selected item and changes it in the database and closes the current pane.
    private void editItem() {
        Item item = database.getItems().get(database.getItems().indexOf(selectedEditItem));
        item.setTitle(titleTextField2.getText());
        item.setAuthor(authorTextField2.getText());
        if (itemCheckBox.isSelected()) {
            item.setIsAvailable("yes");
            item.setLendingDate(null);
        } else {
            item.setIsAvailable("no");
            item.setLendingDate(itemDatePicker.getValue());
        }
        editItemPane.setVisible(false);
    }

    //This method refreshes the secondMainPane.
    private void refreshSecondMainPane() {
        items = FXCollections.observableArrayList(database.getItems());
        itemTable.setItems(items);
        itemTable.refresh();
        secondPane.setDisable(false);
        firstMainButton.setDisable(false);
        thirdMainButton.setDisable(false);
    }

    //This method sets up the editMemberPane.
    private void editMemberButtonAction() {
        editMemberPane.setVisible(true);
        selectedEditMember=memberTable.getSelectionModel().getSelectedItem();
        firstNameTextField2.setText(selectedEditMember.getFirstName());
        lastNameTextField2.setText(selectedEditMember.getLastName());
        memberEditDatePicker.setValue(selectedEditMember.getBirthDate());
    }

    //This method generates a new member code.
    private int getNewMemberCode() {
        int newCode;
        if(database.getMembers().size()==0){
            newCode=1;
        }
        else{
            newCode=database.getMembers().get(database.getMembers().size() - 1).getIdentifier() + 1;
        }
        return newCode;
    }

    //This method adds a new member in the database and closes and refreshes the current pane.
    private void addNewMemberInDatabase(int newCode) {
        Member newMember=new Member(firstNameTextField.getText(), lastNameTextField.getText(),memberDatePicker.getValue(), newCode);
        database.getMembers().add(newMember);
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        memberDatePicker.setValue(null);
        confirmMemberButton.setDisable(true);
        addMemberPane.setVisible(false);
    }

    //This method deletes a member from the database and refreshes the memberTableView.
    private void deleteMemberFromDatabase() {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            database.getMembers().remove(selectedMember);
            members = FXCollections.observableArrayList(database.getMembers());
            memberTable.setItems(members);
            memberTable.refresh();
            editMemberButton.setDisable(true);
            deleteMemberButton.setDisable(true);
        }
    }

    //This method refreshes the memberTableview with the members
    // that start with the letters which the user typed in the search bar.
    private void showSearchedMember() {
        List<Member> searchMembers=new ArrayList<>();
        for(Member member:database.getMembers()){
            if(member.getFirstName().toLowerCase().startsWith(searchMemberTextField.getText().toLowerCase())
                    || member.getLastName().toLowerCase().startsWith(searchMemberTextField.getText().toLowerCase())){
                searchMembers.add(member);
                members=FXCollections.observableArrayList(searchMembers);
                memberTable.setItems(members);
                memberTable.refresh();
            }
        }
    }

    //This method edits the selected member and changes it in the database and closes the current pane.
    private void editMember() {
        Member member=database.getMembers().get(database.getMembers().indexOf(selectedEditMember));
        member.setFirstName(firstNameTextField2.getText());
        member.setLastName(lastNameTextField2.getText());
        member.setBirthDate(memberEditDatePicker.getValue());
        editMemberPane.setVisible(false);
    }

    //This method refreshes the thirdMainPane.
    private void refreshThirdMainPane() {
        members = FXCollections.observableArrayList(database.getMembers());
        memberTable.setItems(members);
        memberTable.refresh();
        thirdPane.setDisable(false);
        firstMainButton.setDisable(false);
        secondMainButton.setDisable(false);
    }
}