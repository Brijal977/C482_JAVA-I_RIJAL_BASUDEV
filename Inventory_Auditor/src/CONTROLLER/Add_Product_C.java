package CONTROLLER;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import MODEL.*;


import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author BASUDEV RIJAL
 */
public class Add_Product_C implements Initializable {



    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //========================= VARIABLE MAPPING=====================================================
    /**
     * UI-Controller VARIABLE MAPPING -
     *Text fields : Product [ID,Name, INV(stock), Cost, Min, Max]
     *
     * Tableview Prod_Addpart_Table : Columns product[ID, Name, INV(stock), Cost]
     * lists all parts in inventory
     *
     *Tableview Prod_Removepart_Table : Columns product[ID, Name, INV(stock), Cost]
     * lists associated parts to the current product
     */

    @FXML private TextField Part_Search_Txt;
    @FXML private TextField Prod_ID_Txt;
    @FXML private TextField Prod_Name_Txt;
    @FXML private TextField Prod_INV_Txt;
    @FXML private TextField Prod_Cost_Txt;
    @FXML private TextField Prod_Min_Txt;
    @FXML private TextField Prod_Max_Txt;



    @FXML private TableView<Part> Prod_Addpart_Table;
    @FXML private TableColumn<Part, Integer> Padd_Id_col;
    @FXML private TableColumn<Part, String> Padd_Name_col;
    @FXML private TableColumn<Part, Integer> Padd_INV_col;
    @FXML private TableColumn<Part, Double> Padd_Cost_col;

    @FXML private TableView<Part> Prod_Removepart_Table;
    @FXML private TableColumn<Part, Integer> Premove_Id_col;
    @FXML private TableColumn<Part, String> Premove_Name_col;
    @FXML private TableColumn<Part, Integer> Premove_INV_col;
    @FXML private TableColumn<Part, Double> Premove_Cost_col;







//===================================navigation================================================

    /**
     *  navigates back to main screen - when user select cancel button
     *
     * * @param event AddPart_Cancel_Clicked event
     * @return void
     *
     *
     */
    @FXML
    public void AddProduct_Cancel_Clicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
        }
    }



    //=================================== ADD/REMOVE/SAVE ====================================

    /**
     * adds associated parts to the current product
     * @param event
     */
    @FXML
    public void AddProduct_addpart_Clicked(ActionEvent event) {
        Part selected = Prod_Addpart_Table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Main_C.Alert_msg("no parts seleted to add");return;}

        for (Part part : associatedParts) {
            if (part.getId() == selected.getId()) {
                Main_C.Alert_msg("Part already has been added");
                return;
            }
        }
        associatedParts.add(selected);
        Prod_Removepart_Table.setItems(associatedParts);
    }

    /**
     * removes associated parts to the current product
     * @param event
     */
    @FXML
    public void AddProduct_removepart_Clicked(ActionEvent event) {
        Part selected = Prod_Removepart_Table.getSelectionModel().getSelectedItem();
        if (selected == null) { Main_C.Alert_msg("no parts seleted to remove");return;}

        associatedParts.remove(selected);
    }


    /**
     * Saves the Product and associated parts - in INVENTORY
     *
     *
     * @param event
     * @throws IOException
     */

    @FXML
    public void AddProduct_Save_Clicked(ActionEvent event) throws IOException {

        Add_Part_C.error_flag = false;
        Add_Part_C.Validate(Prod_Name_Txt, Prod_INV_Txt, Prod_Cost_Txt, Prod_Min_Txt, Prod_Max_Txt);

        if (!Add_Part_C.error_flag) {
            Prod_ID_Txt.setText(String.valueOf(Inventory.gen_ProdId()));
            int id = Integer.parseInt(Prod_ID_Txt.getText());
            String name = Prod_Name_Txt.getText();
            int stock = Integer.parseInt(Prod_INV_Txt.getText());
            double cost = Double.parseDouble(Prod_Cost_Txt.getText());
            int min = Integer.parseInt(Prod_Min_Txt.getText());
            int max = Integer.parseInt(Prod_Max_Txt.getText());

            Product product = new Product(id, name, cost, stock, min, max);

            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }

            Inventory.addProduct(product);
            Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
            Main_C.Alert_msg(" New Product Added");
        } else {
            Main_C.Alert_msg("error found during execution");
        }
    }


    /**
     * Initializes the Add product UI
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Prod_Addpart_Table.getItems().setAll(Inventory.getAllParts());
        Prod_Removepart_Table.setItems(associatedParts);

        Padd_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        Padd_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        Padd_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Padd_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));

        Premove_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        Premove_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        Premove_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Premove_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));

        Prod_ID_Txt.setText(String.valueOf(Inventory.gen_ProdId()));


    //=================================== SEARCH ====================================
        /**
         * searches Products using Part ID or Part  Name
         */
        Main_C.Search_part(Part_Search_Txt, Prod_Addpart_Table);

    }

}
