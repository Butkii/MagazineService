/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

/**
 *
 * @author User
 */
public class Sample extends Application{
    Stage window;
    MagazineService magazine = new MagazineService();
    Magazine mag = new Magazine();
    Customer cust = new Customer();
    Supplement supp = new Supplement();
    Subscription sub = new Subscription();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    ArrayList<Supplement> supplements = new ArrayList<Supplement>();
    ArrayList<String> associates = new ArrayList<String>();
    String payee = null;
    String filename = "magazine.dat";
    
    ObservableList<String> cus;
    ObservableList<String> sups;
    ObservableList<String> aCust;
    ObservableList<String> selection;
    ObservableList<String> associateSups;
    
    ListView supList, custList, aCustList, customerSelection, associateList;
    
    TextField nameField, costField, nameField_supp, costField_supp, nameField_cust, emailField_cust, streetName_cust, streetNum_cust, 
            suburb_cust, postCode_cust, bankAcc_cust, cardType_cust, nameField_Acust, emailField_Acust, streetName_Acust, 
            streetNum_Acust, suburb_Acust, postCode_Acust;

    ToggleGroup typeGroup_cust;
    ChoiceBox<String> paymentBox_cust;
    
//    Alert alert = new Alert(AlertType.NONE);

    /**
     *
     * @param args
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {    
        window = primaryStage;
        window.setTitle("Magazine Subscription");
        window.setScene(homeScene());
        window.show();

    }

    /**
     *
     * @return the home scene
     */
    public Scene homeScene() {
        Scene scene;

        Text text = new Text("Goldfish Aquatics Magazine Subscription");

        Button createBtn = new Button("Create Mode");
        createBtn.setOnAction(e -> window.setScene(createScene()));
        Button editBtn = new Button("Edit Mode");
        editBtn.setOnAction(e -> window.setScene(editScene()));
        Button viewBtn = new Button("View Mode");
        viewBtn.setOnAction(e -> window.setScene(viewScene()));
        Button closeBtn = new Button("Close");
         closeBtn.setOnAction(e -> closeProgram());
        Button openBtn = new Button("Open");
        openBtn.setOnAction(e -> {
            openFile();
        });
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
           createMagFile(nameField.getText(), costField.getText(), magazine.getMag(), magazine.getSub(), filename); 
        });
        
        HBox btns = new HBox(5);
        btns.setAlignment(Pos.BOTTOM_RIGHT);
        btns.getChildren().addAll(closeBtn, openBtn, saveBtn);
        
        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(createBtn, editBtn, viewBtn);
        BorderPane layout = new BorderPane();
        layout.setTop(text);
        layout.setCenter(buttons);
        layout.setBottom(btns);

        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        return scene;
    }

    /**
     *
     * @return the create mode scene
     */
    public Scene createScene() {
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(4);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(2);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(25);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(25);
        RowConstraints row9 = new RowConstraints();
        row9.setPercentHeight(10);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9);
        
        
        Text mode = new Text("Create New Magazine");
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 1);
        
        Text name_label = new Text("Name: ");
        gridpane.add(name_label, 1, 3);
        Text cost_label = new Text("Weekly Cost of Magazine: ");
        gridpane.add(cost_label, 1, 4);
        Text supp_label = new Text("Supplements: ");
        gridpane.add(supp_label, 1, 5);
        Text cust_label = new Text("Customers: ");
        gridpane.add(cust_label, 1, 7);
        
        TextField name_field = new TextField();
        gridpane.add(name_field, 2, 3);
        TextField cost_field = new TextField();
        gridpane.add(cost_field, 2, 4);
        
        ScrollPane supp = new ScrollPane();
        supList = new ListView();
        sups = FXCollections.observableList(mag.getSupplementNames());
        supList.setItems(sups);
        supp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        supp.setContent(supList);
        gridpane.add(supp, 2, 5);
        
        ScrollPane custs = new ScrollPane();
        custList = new ListView();
        cus = FXCollections.observableList(mag.getListOfCustomers());
        custList.setItems(cus);
        custs.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        custs.setContent(custList);
        gridpane.add(custs, 2, 7);
        
        Button addSupp = new Button();
        addSupp.setText("+ Supplement");
        addSupp.setOnAction(e -> {
            e.consume();
            createSupplementScene();
        });
        gridpane.add(addSupp, 2, 6);
        Button addCust = new Button();
        addCust.setText("+ Customer");
        addCust.setOnAction(e -> {
            e.consume();
            createCustomerScene();
        });
        gridpane.add(addCust, 2, 8);
        
        Button homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e -> {
            Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to discard changes?");
            if (answer) {
                window.setScene(homeScene());   
            }
        });
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!name_field.getText().isEmpty() && !cost_field.getText().isEmpty()) {
                System.out.println("Name: " + name_field.getText() + " Cost: " + cost_field.getText());
                filename = name_field.getText() + ".dat";
                createMagFile(name_field.getText(), cost_field.getText(), mag, sub, filename);
                AlertBox.display("Confirm", "Magazine created");
                window.setScene(homeScene());
            } else {
                ErrorBox.display("Error", "Name and cost are required fields");
            }
            
            
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(confirmBtn, homeBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        return scene;
    }
    
    /**
     * to create a supplement
     */
    public void createSupplementScene() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a Supplement");
        window.setMinWidth(250);
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(4);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(30);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(30);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);
        
        
        Text mode = new Text("Create New Supplement");
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 0);
        
        Text nameLabel_supp = new Text("Name: ");
        gridpane.add(nameLabel_supp, 1, 3);
        Text costLabel_supp = new Text("Weekly Cost of Supplement: ");
        gridpane.add(costLabel_supp, 1, 4);
        
        nameField_supp = new TextField();
        gridpane.add(nameField_supp, 2, 3);
        costField_supp = new TextField();
        gridpane.add(costField_supp, 2, 4);

        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> {
            Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to discard changes?");
            if (answer) {
                window.close();
            }
        });
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!nameField_supp.getText().isEmpty() && !costField_supp.getText().isEmpty()) {
                createSupplement();
                window.close();
            } else {
                ErrorBox.display("Error", "Name and cost cannot be empty");
            }
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(backBtn, confirmBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
     * interface to create a customer
     */
    public void createCustomerScene() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a Customer");
        window.setMinWidth(250);
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setMaxHeight(400);
        gridpane.setMinHeight(100);
        gridpane.setPrefHeight(300);
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(5);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(10);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(30);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(10);
        RowConstraints row9 = new RowConstraints();
        row9.setPercentHeight(10);
        RowConstraints row10 = new RowConstraints();
        row10.setPercentHeight(30);
        RowConstraints row11 = new RowConstraints();
        row11.setPercentHeight(10);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10);
        
        
        Text mode = new Text("Create New Customer");
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 0);
        
        Text nameLabel_cust = new Text("Name: ");
        gridpane.add(nameLabel_cust, 1, 2);
        Text emailLabel_cust = new Text("Email: ");
        gridpane.add(emailLabel_cust, 1, 3);
        Text addressLabel_cust = new Text("Address: ");
        gridpane.add(addressLabel_cust, 1, 4);
        Text suppLabel_cust = new Text("Choose your supplements: ");
        gridpane.add(suppLabel_cust, 1, 6);
        Text custType_cust = new Text("Customer Type: ");
        gridpane.add(custType_cust, 1, 7);
        Text paymentLabel_cust = new Text("Payment Type: ");
        gridpane.add(paymentLabel_cust, 1, 8);       
        Text payingCustLabel_cust = new Text("Paying Customer Details: ");
        gridpane.add(payingCustLabel_cust, 1, 8);
        
        nameField_cust = new TextField();
        gridpane.add(nameField_cust, 2, 2);
        emailField_cust = new TextField();
        gridpane.add(emailField_cust, 2, 3);
        streetName_cust = new TextField();
        streetName_cust.setPromptText("Street Name");
        gridpane.add(streetName_cust, 2, 4);
        streetNum_cust = new TextField();
        streetNum_cust.setPromptText("Street Number");
        gridpane.add(streetNum_cust, 3, 4);
        suburb_cust = new TextField();
        suburb_cust.setPromptText("Suburb");
        gridpane.add(suburb_cust, 2, 5);
        postCode_cust = new TextField();
        postCode_cust.setPromptText("Post Code");
        gridpane.add(postCode_cust, 3, 5);
   
        
        ScrollPane supp = new ScrollPane();
        customerSelection = new ListView();
        ObservableList<String> sups = FXCollections.observableList(mag.getSupplementNames());
        customerSelection.setItems(sups);
        customerSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        supp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        supp.setContent(customerSelection);
        gridpane.add(supp, 2, 6);
        
        typeGroup_cust = new ToggleGroup();
        RadioButton paying_cust = new RadioButton("Paying Customer");
        paying_cust.setToggleGroup(typeGroup_cust);
        paying_cust.getProperties().put("type", "paying");
        gridpane.add(paying_cust, 2, 7);
        RadioButton associate_cust = new RadioButton("Associate Customer");
        associate_cust.setToggleGroup(typeGroup_cust);
        associate_cust.getProperties().put("type", "associate");
        gridpane.add(associate_cust, 3, 7);
        
        paymentBox_cust = new ChoiceBox<>();
        paymentBox_cust.getItems().addAll("Credit Card", "Debit Card");
        bankAcc_cust = new TextField();
        bankAcc_cust.setPromptText("Bank Account");
        paymentLabel_cust.setVisible(false);
        paymentBox_cust.setVisible(false);
        bankAcc_cust.setVisible(false);
        gridpane.add(paymentBox_cust, 2, 8);
        gridpane.add(bankAcc_cust, 3, 8);

        
        ChoiceBox<String> payingCust_cust = new ChoiceBox<>();
        payingCust_cust.getItems().addAll(mag.getPayingCustomers());
        payingCust_cust.setOnAction(e -> {
           payee = payingCust_cust.valueProperty().toString();
        });        
        payingCust_cust.setVisible(false);
        payingCustLabel_cust.setVisible(false);
        gridpane.add(payingCust_cust, 2, 8);

        typeGroup_cust.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                final Toggle old_toggle, final Toggle new_toggle) {  
                if (new_toggle.getProperties().get("type") == "paying") {
                    paymentLabel_cust.setVisible(true);
                    paymentBox_cust.setVisible(true);
                    bankAcc_cust.setVisible(true);
                    payingCustLabel_cust.setVisible(false);
                    payingCust_cust.setVisible(false);
                } else {
                    paymentLabel_cust.setVisible(false);
                    paymentBox_cust.setVisible(false);
                    bankAcc_cust.setVisible(false);
                    payingCustLabel_cust.setVisible(true);
                    payingCust_cust.setVisible(true);
                }
           }
        });    
        

        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> {
           Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to discard changes?"); 
           if (answer) {
               window.close();
           }
        });
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!nameField_cust.getText().isEmpty() && !emailField_cust.getText().isEmpty() && 
                    !streetName_cust.getText().isEmpty() && !streetNum_cust.getText().isEmpty() && 
                    !suburb_cust.getText().isEmpty() && !postCode_cust.getText().isEmpty())  {
                
                    createCustomer();
                    window.close();
                
            } else {
                ErrorBox.display("Error", "Please fill in all fields");
            }
            
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(backBtn, confirmBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
     *
     * @return edit mode scene
     */
    public Scene editScene() {
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(4);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(2);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(25);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(25);
        RowConstraints row9 = new RowConstraints();
        row9.setPercentHeight(10);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9);
        
        
        Text mode = new Text("Edit Magazine Details");
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 1);
        
        Text name_label = new Text("Name: ");
        gridpane.add(name_label, 1, 3);
        Text cost_label = new Text("Weekly Cost of Magazine: ");
        gridpane.add(cost_label, 1, 4);
        Text supp_label = new Text("Supplements: ");
        gridpane.add(supp_label, 1, 5);
        Text cust_label = new Text("Customers: ");
        gridpane.add(cust_label, 1, 7);
        
        TextField name_field = new TextField();
        name_field.setText(magazine.getMag().getName());
        System.out.println(magazine.getMag().getName());
        gridpane.add(name_field, 2, 3);
        TextField cost_field = new TextField();
        cost_field.setText(Double.toString(magazine.getMag().getWeeklyCost()));
        System.out.println(magazine.getMag().getWeeklyCost());
        gridpane.add(cost_field, 2, 4);
        
        ScrollPane supp = new ScrollPane();
        supList = new ListView();
        sups = FXCollections.observableList(magazine.getMag().getSupplementNames());
        supList.setItems(sups);
        supp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        supp.setContent(supList);
        gridpane.add(supp, 2, 5);
        
        ScrollPane custs = new ScrollPane();
        custList = new ListView();
        cus = FXCollections.observableList(magazine.getMag().getListOfCustomers());
        custList.setItems(cus);
        custs.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        custs.setContent(custList);
        gridpane.add(custs, 2, 7);
        
        Button addSupp = new Button();
        addSupp.setText("+ Supplement");
        addSupp.setOnAction(e -> {
            e.consume();
            createSupplementScene();
        });
        gridpane.add(addSupp, 2, 6);
        Button editSup = new Button();
        editSup.setText("Edit");
        editSup.setOnAction(e -> {
           e.consume();
           editSupplementScene(magazine.getSupplement(supList.getSelectionModel().getSelectedItems().get(0).toString()));
        });
        gridpane.add(editSup, 3, 6);
        Button delSup = new Button();
        delSup.setText("Delete");
        delSup.setOnAction(e -> {
            String supName = supList.getSelectionModel().getSelectedItems().get(0).toString();
            Boolean answer = ConfirmBox.display("Confirm", "Are you sure you want to delete: " + magazine.getSupplement(supName));
            if (answer) {
                magazine.deleteSupplement(supName);
                sups = FXCollections.observableList(magazine.getMag().getSupplementNames());
                supList.setItems(sups);
            }
        });
        gridpane.add(delSup, 4, 6);
        
        Button addCust = new Button();
        addCust.setText("+ Customer");
        addCust.setOnAction(e -> {
            e.consume();
            createCustomerScene();
        });
        gridpane.add(addCust, 2, 8);
        Button editCus = new Button();
        editCus.setText("Edit");
        editCus.setOnAction(e -> {
           e.consume();
           editCustomerScene(magazine.getCustomer(custList.getSelectionModel().getSelectedItems().get(0).toString()));
        });
        gridpane.add(editCus, 3, 8);
        Button delCus = new Button();
        delCus.setText("Delete");
        delCus.setOnAction(e -> {
            String cusName = custList.getSelectionModel().getSelectedItems().get(0).toString();
            Boolean answer = ConfirmBox.display("Confirm", "Are you sure you want to delete: " + magazine.getCustomer(cusName));
            if (answer) {
                magazine.deleteCustomer(cusName);
            }
        });
        gridpane.add(delCus, 4, 8);
        
        Button homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e -> {
            Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to discard changes?"); 
            if (answer) {
                window.setScene(homeScene());
            }
        });
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!name_field.getText().isEmpty() && !cost_field.getText().isEmpty()) {
                filename = name_field.getText() + ".dat";
                createMagFile(name_field.getText(), cost_field.getText(), magazine.getMag(), magazine.getSub(), filename);
                AlertBox.display("Success!", "Magazine edited");
                window.setScene(homeScene());
            } else {
                ErrorBox.display("Error", "Name and cost are required fields");
            }            
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(confirmBtn, homeBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        return scene;
    }
    
    /**
     * scene to edit supplement details
     * @param Supplement s
     */
    public void editSupplementScene(Supplement s) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Supplement");
        window.setMinWidth(250);
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(4);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(30);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(30);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);
        
        
        Text mode = new Text("Edit Supplement: " + s.getName());
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 0);
        
        Text nameLabel_supp = new Text("Name: ");
        gridpane.add(nameLabel_supp, 1, 3);
        Text costLabel_supp = new Text("Weekly Cost of Supplement: ");
        gridpane.add(costLabel_supp, 1, 4);
        
        nameField_supp = new TextField();
        gridpane.add(nameField_supp, 2, 3);
        nameField_supp.setText(s.getName());
        costField_supp = new TextField();
        gridpane.add(costField_supp, 2, 4);
        costField_supp.setText(Double.toString(s.getWeeklyCost()));

        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> {
           Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to discard changes?"); 
           if (answer) {
               window.close();
           }
        });
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!nameField_supp.getText().isEmpty() && !costField_supp.getText().isEmpty()) {
                editSupplement(s, nameField_supp.getText(), Double.parseDouble(costField_supp.getText()));
                window.close();
            } else {
                ErrorBox.display("Error", "Name and cost cannot be empty");
            }
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(backBtn, confirmBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        window.setScene(scene);
        window.showAndWait();
    }
    
    /**
     * edit customer details scene
     * @param Customer c
     */
    public void editCustomerScene(Customer c) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Customer");
        window.setMinWidth(250);
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setMaxHeight(400);
        gridpane.setMinHeight(100);
        gridpane.setPrefHeight(300);
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(3);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(2);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(8);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(8);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(8);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(8);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(20);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(8);
        RowConstraints row9 = new RowConstraints();
        row9.setPercentHeight(8);
        RowConstraints row10 = new RowConstraints();
        row10.setPercentHeight(20);
        RowConstraints row11 = new RowConstraints();
        row11.setPercentHeight(8);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10);
        
        
        Text mode = new Text("Edit Customer: " + c.getName());
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 0);
        
        Text nameLabel_cust = new Text("Name: ");
        gridpane.add(nameLabel_cust, 1, 2);
        Text emailLabel_cust = new Text("Email: ");
        gridpane.add(emailLabel_cust, 1, 3);
        Text addressLabel_cust = new Text("Address: ");
        gridpane.add(addressLabel_cust, 1, 4);
        Text suppLabel_cust = new Text("Choose your supplements: ");
        gridpane.add(suppLabel_cust, 1, 6);
        
        
        nameField_cust = new TextField();
        nameField_cust.setText(c.getName());
        gridpane.add(nameField_cust, 2, 2);
        emailField_cust = new TextField();
        emailField_cust.setText(c.getEmail());
        gridpane.add(emailField_cust, 2, 3);
        streetName_cust = new TextField();
        streetName_cust.setText(c.getAddress().getStreetName());
        gridpane.add(streetName_cust, 2, 4);
        streetNum_cust = new TextField();
        streetNum_cust.setText(Integer.toString(c.getAddress().getStreetNum()));
        gridpane.add(streetNum_cust, 3, 4);
        suburb_cust = new TextField();
        suburb_cust.setText(c.getAddress().getSuburb());
        gridpane.add(suburb_cust, 2, 5);
        postCode_cust = new TextField();
        postCode_cust.setText(Integer.toString(c.getAddress().getPostCode()));
        gridpane.add(postCode_cust, 3, 5);
   
        
        ScrollPane supp = new ScrollPane();
        customerSelection = new ListView();
        ObservableList<String> sups = FXCollections.observableList(magazine.getSupplementsbyCustomer(c.getName()));
        customerSelection.setItems(sups);
        customerSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        supp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        supp.setContent(customerSelection);
        gridpane.add(supp, 2, 6);
        
        if(c instanceof PayingCustomer) {
            Text paymentLabel_cust = new Text("Payment Type: ");
            gridpane.add(paymentLabel_cust, 1, 7);
            
            paymentBox_cust = new ChoiceBox<>();
            paymentBox_cust.getItems().addAll("Credit Card", "Debit Card");
            bankAcc_cust = new TextField();
            bankAcc_cust.setPromptText("Bank Account");
            gridpane.add(paymentBox_cust, 2, 7);
            gridpane.add(bankAcc_cust, 3, 7);
        } else {
            Text payingCustLabel_cust = new Text("Paying Customer Details: ");
            gridpane.add(payingCustLabel_cust, 1, 7); 
            ChoiceBox<String> payingCust_cust = new ChoiceBox<>();
            payingCust_cust.getItems().addAll(magazine.getPayingCusName());
            payingCust_cust.setOnAction(e -> {
               payee = payingCust_cust.valueProperty().toString();
            });        
            gridpane.add(payingCust_cust, 2, 7);
        }


        Button backBtn = new Button();
        backBtn.setText("Back");
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(e -> {
            if (!nameField_cust.getText().isEmpty() && !emailField_cust.getText().isEmpty() && 
                    !streetName_cust.getText().isEmpty() && !streetNum_cust.getText().isEmpty() && 
                    !suburb_cust.getText().isEmpty() && !postCode_cust.getText().isEmpty())  {
                
                    editCustomer();
                    window.close();
                
            } else {
                ErrorBox.display("Error", "Please fill in all fields");
            }
        });
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(backBtn, confirmBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        window.setScene(scene);
        window.showAndWait();
    }
    
    //view scene
    public Scene viewScene(){
        Scene scene;
        
        GridPane gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(5);
        gridpane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(4);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(2);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(25);
        RowConstraints row7 = new RowConstraints();
        row7.setPercentHeight(10);
        RowConstraints row8 = new RowConstraints();
        row8.setPercentHeight(25);
        RowConstraints row9 = new RowConstraints();
        row9.setPercentHeight(10);
        gridpane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9);
        
        Text mode = new Text("Create New Magazine");
        mode.setTextAlignment(TextAlignment.CENTER);
        mode.setFill(Color.DARKCYAN);
        gridpane.add(mode, 2, 1);
        
        Text name_label = new Text("Name: ");
        gridpane.add(name_label, 1, 3);
        Text name = new Text(magazine.getMag().getName());
        gridpane.add(name, 2, 3);
        Text cost_label = new Text("Weekly Cost of Magazine: ");
        gridpane.add(cost_label, 1, 4);
        Text cost = new Text(Double.toString(magazine.getMag().getWeeklyCost()));
        gridpane.add(cost, 2, 4);
        Text supp_label = new Text("Supplements: ");
        gridpane.add(supp_label, 1, 5);
        Text cust_label = new Text("Customers: ");
        gridpane.add(cust_label, 1, 7);
        
        ScrollPane supp = new ScrollPane();
        ListView supplements = new ListView();
        ObservableList sups = FXCollections.observableList(magazine.getSupplementNames());
        supplements.setItems(sups);
        supp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        supp.setContent(supplements);
        supplements.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gridpane.add(supp, 2, 5);
        
        ScrollPane custs = new ScrollPane();
        ListView customers = new ListView();
        ObservableList cus = FXCollections.observableList(magazine.getCustomerNames());
        customers.setItems(cus);
        custs.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        custs.setContent(customers);
        customers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gridpane.add(custs, 2, 7);
        
        Button viewSupp = new Button("View");
        viewSupp.setOnAction(e -> {
            e.consume();
            String temp = supplements.getSelectionModel().getSelectedItems().get(0).toString();
            AlertBox.display("Supplement Details", magazine.getSupplement(temp).toString());
        });
        gridpane.add(viewSupp, 2, 6);
        
        Button viewCust = new Button("View");
        viewCust.setOnAction(e -> {
            e.consume();
            String temp = customers.getSelectionModel().getSelectedItems().get(0).toString();
            Customer c = magazine.getCustomer(temp);
            String message = c.toString() + magazine.printMonthlyStatement(c);
            AlertBox.display("Customer Details", message);
        });
        gridpane.add(viewCust, 2, 8);
        
        Button homeBtn = new Button();
        homeBtn.setText("Home");
        homeBtn.setOnAction(e -> {
            window.setScene(homeScene());   
        });
        
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(homeBtn);
        
        BorderPane layout = new BorderPane();
        Text text = new Text("Goldfish Aquatics Magazine Subscription");
        text.setTextAlignment(TextAlignment.CENTER);
        layout.setTop(text);
        layout.setCenter(gridpane);
        layout.setBottom(buttons);
        
        scene = new Scene(layout, 800, 600);
        scene.setFill(Color.LIGHTBLUE);
        return scene;
    }
    
    //helper to create supplement
    private void createSupplement() {
        String name = nameField_supp.getText();
        double cost = Double.parseDouble(costField_supp.getText());

        Supplement s;

        s = new Supplement(name, cost);

        if (mag.addSupplement(s)) {
            AlertBox.display("Success!", "Supplement created successfully!");
            sups = FXCollections.observableList(mag.getSupplementNames());
            supList.setItems(sups);
        } else {
            ErrorBox.display("Error", "Supplement could not be created");
        }
    }
    
    private void createCustomer(){
        Customer c;
        System.out.println("CREATING CUSTOMER");

        String name = nameField_cust.getText() != "" ? nameField_cust.getText() : "";
        String email = emailField_cust.getText() != "" ? emailField_cust.getText() : "";
        int streetNum = 0;
        int postCode = 0;
        String streetName = "";
        String suburb = "";
        
        try {
            System.out.println(streetNum_cust.getText());
            streetNum = streetNum_cust.getText().equals("") ? 0 : Integer.parseInt(streetNum_cust.getText());
            postCode = postCode_cust.getText().equals("") ? 0 : Integer.parseInt(postCode_cust.getText());
        } catch (NumberFormatException ex) {
            streetNum = 0;
            postCode = 0;
        }

        streetName = streetName_cust.getText() != "" ? streetName_cust.getText()
                : "Street";
        suburb = suburb_cust.getText() != "" ? suburb_cust.getText()
                : "Suburb";

        selection = customerSelection.getSelectionModel().getSelectedItems();
        List<String> customerSubs = selection.subList(0, selection.size());
        
        try {
            if (typeGroup_cust.getSelectedToggle().getProperties().get("type") == "paying") {
                PayingCustomer pC = new PayingCustomer(name, email, new Address(streetName, streetNum, suburb, postCode));
                pC.setPaymentMethod(bankAcc_cust.getText(), paymentBox_cust.getValue());
                mag.addCustomer(pC);
            } else {
                AssociateCustomer aC = new AssociateCustomer(name, email, new Address(streetName, streetNum, suburb, postCode));
                if (payee != "" && mag.getCustomer(payee) != null) {
                    mag.addCustomer(aC);
                    mag.addAssociatetoPaying(payee, name);
                }
            }

            for (String s : customerSubs) {
                Supplement tempSup = mag.getSupplement(s);
                sub.addSupplement(name, tempSup);
            }
            
            cus = FXCollections.observableList(mag.getListOfCustomers());
            custList.setItems(cus);
            AlertBox.display("Success!", "Customer created successfully");
            
        } catch (Exception e) {
            ErrorBox.display("Error", "Could not create customer");
        }
    }
    
    //helper to edit supplement
    private void editSupplement(Supplement temp, String name, Double cost) {       
        magazine.editSupplement(temp.getName(), name, cost);
        AlertBox.display("Success!", "Supplement created successfully!");
        System.out.println(temp.toString());
        System.out.println(magazine.getMag().toString());
        sups = FXCollections.observableList(magazine.getMag().getSupplementNames());
        supList.setItems(sups);
    }
    
    //helper to edit customer
    private void editCustomer(){
        Customer c;
        System.out.println("CREATING CUSTOMER");

        String name = nameField_cust.getText() != "" ? nameField_cust.getText() : "";
        String email = emailField_cust.getText() != "" ? emailField_cust.getText() : "";
        int streetNum = 0;
        int postCode = 0;
        String streetName = "";
        String suburb = "";
        
        try {
            streetNum = streetName_cust.getText() != "" ? Integer.parseInt(streetName_cust.getText())
                    : 0;
            postCode = postCode_cust.getText() != ""
                    ? Integer.parseInt(postCode_cust.getText())
                    : 0000;
        } catch (NumberFormatException ex) {
            streetNum = 0;
            postCode = 0;
        }

        streetName = streetName_cust.getText() != "" ? streetName_cust.getText()
                : "Street";
        suburb = suburb_cust.getText() != "" ? suburb_cust.getText()
                : "Suburb";

        selection = customerSelection.getSelectionModel().getSelectedItems();
        List<String> customerSubs = selection.subList(0, selection.size());
        
        try {
            if (typeGroup_cust.getSelectedToggle().getProperties().get("type") == "paying") {
                PayingCustomer pC = new PayingCustomer(name, email, new Address(streetName, streetNum, suburb, postCode));
                pC.setPaymentMethod(bankAcc_cust.getText(), paymentBox_cust.getValue());
                magazine.getMag().addCustomer(pC);
            } else {
                AssociateCustomer aC = new AssociateCustomer(name, email, new Address(streetName, streetNum, suburb, postCode));
                if (payee != "" && mag.getCustomer(payee) != null) {
                    magazine.getMag().addCustomer(aC);
                    magazine.addAssociatetoPaying(payee, name);
                }
            }

            for (String s : customerSubs) {
                Supplement tempSup = mag.getSupplement(s);
                sub.addSupplement(name, tempSup);
            }
            
            cus = FXCollections.observableList(mag.getListOfCustomers());
            custList.setItems(cus);
            AlertBox.display("Success!", "Customer created successfully");
            
        } catch (Exception e) {
            ErrorBox.display("Error", "Could not create customer");
        }
    }    
        
    /**
     * open existing file
     */
    public void openFile() {
        // get current working directory
        File recordsDir = new File(System.getProperty("user.dir"));

        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(recordsDir);

        fileChooser.setTitle("Open Magazine Service File");

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Magazine Files", "*.dat"),
                new ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            magazine = new MagazineService();
            magazine.readMagazineService(selectedFile);
            AlertBox.display("Success!", "File opened successfully");
        } else {
            AlertBox.display("Error", "File not found");
        }
    }
    
    //create a new file
    private void createMagFile(String name, String cost, Magazine mag, Subscription sub, String fileName) {
        try {
            System.out.println(name + cost);
            magazine = new MagazineService();
            mag.setName(name);
            mag.setWeeklyCost(Double.parseDouble(cost));
            magazine.setMag(mag);
            magazine.setSub(sub);
            System.out.println("Magazine: " + mag.toString());
            System.out.println("Subscription: " + sub.toString());
            System.out.println("Service: " + magazine.toString());

            File selectedFile = new File(fileName);
            magazine.writeMagazineService(selectedFile);
            AlertBox.display("Success!", "Created file " + fileName);
        } catch (Exception e) {
            ErrorBox.display("Error", "Could not create file " + fileName);
        }
    }
    
    //close the program
    private void closeProgram() {
      Boolean answer = ConfirmBox.display("Confirm", "Are you sure you want to exit?");
      if (answer) {
         window.close();
      }
    }
    
    
    
}
 


