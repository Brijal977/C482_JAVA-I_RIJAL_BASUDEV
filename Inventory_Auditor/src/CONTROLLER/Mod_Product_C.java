package CONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import MODEL.*;

/**
 *
 * @author BASUDEV RIJAL
 */
public class Mod_Product_C  implements Initializable {

    private Product product;
    private Inventory inventory = Inventory.getInstance();
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

    @FXML private TextField Prod_ID_Txt;
    @FXML private TextField Prod_Name_Txt;
    @FXML private TextField Prod_INV_Txt;
    @FXML private TextField Prod_Cost_Txt;
    @FXML private TextField Prod_Min_Txt;
    @FXML private TextField Prod_Max_Txt;

    @FXML private TextField Part_Search_Txt;

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




    //===================================navigation=========================================================
    /**
     *  navigates back to main screen - when user select cancel button
     * //to be optimized : repetitive navigation function   -- fixed
     *
     * * @param event AddPart_Cancel_Clicked event
     * @return void
     *
     *
     */
    @FXML
    public void ModProduct_Cancel_Clicked(ActionEvent event) throws IOException {
        Main_C.Alert_msg("the unsaved entries will be lost");
        Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
    }



    //=================================== ADD/REMOVE/SAVE =======================================================
    /**
     * adds associated parts to the current product
     * @param event
     */
    @FXML
    public void ModProduct_addpart_Clicked(ActionEvent event) {

        Part selected = Prod_Addpart_Table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Main_C.Alert_msg("no parts selected to add");
            return;
        }

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
    public void ModProduct_removepart_Clicked(ActionEvent event) {
        Part selected = Prod_Removepart_Table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Main_C.Alert_msg("no parts seleted to remove");
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRM");
            alert.setContentText("Are you sure? this will remove one associated part from product");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                associatedParts.remove(selected);
                Main_C.Alert_msg("one Associated part removed Deleted");
            }
        }

    }


    /**
     * Saves the Product and associated parts - in INVENTORY
     *
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void ModProduct_Save_Clicked(ActionEvent event) throws IOException {
        Add_Part_C.error_flag = false;
        Add_Part_C.Validate(Prod_Name_Txt, Prod_INV_Txt, Prod_Cost_Txt, Prod_Min_Txt, Prod_Max_Txt);


        if (!Add_Part_C.error_flag) {
        int         id = Integer.parseInt(Prod_ID_Txt.getText());
        String      name = Prod_Name_Txt.getText();
        int         stock = Integer.parseInt(Prod_INV_Txt.getText());
        double      cost = Double.parseDouble(Prod_Cost_Txt.getText());
        int         min = Integer.parseInt(Prod_Min_Txt.getText());
        int         max = Integer.parseInt(Prod_Max_Txt.getText());

        Product product = new Product(id, name, cost, stock, min, max);

        for(Part part: associatedParts) {
            product.addAssociatedPart(part);
        }

        Inventory.updateProduct(Inventory.getAllProducts().indexOf(product_Selected),product);
        Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
            Main_C.Alert_msg(" one Product modified");
        } else {
            Main_C.Alert_msg("error found during execution");
        }
    }





    /**
     * holds the record of selected product in main screen - to be modified
     */
    private Product product_Selected;

    //logical error: it's either : associated parts are not being saved or its not being loaded in initialization
    // it shows associated parts right after i added a product but once i hit modify product it erases the associated parts
    /**
     * Initializes to Modify product UI
     * populates the records of selected product in input fields
     *
     *
     *  logical error: it's either : associated parts were being erased once i hit modify product
     *  FIX: forgot to load associated parts in initiation, and when i saved after modify , its saved none
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        product_Selected = Main_C.get_Prod_Selected();

        Prod_ID_Txt.setText(String.valueOf(product_Selected.getId()));
        Prod_Name_Txt.setText(product_Selected.getName());
        Prod_INV_Txt.setText(String.valueOf(product_Selected.getStock()));
        Prod_Cost_Txt.setText(String.valueOf(product_Selected.getCost()));
        Prod_Max_Txt.setText(String.valueOf(product_Selected.getMax()));
        Prod_Min_Txt.setText(String.valueOf(product_Selected.getMin()));


        Prod_Addpart_Table.getItems().setAll(Inventory.getAllParts());
        Padd_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        Padd_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        Padd_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Padd_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));

        associatedParts = product_Selected.getAllAssociatedParts();  //FIX - associated parts being erased

        Prod_Removepart_Table.setItems(associatedParts);
        Premove_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        Premove_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        Premove_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Premove_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));


//=================================== SEARCH ====================================
        /**
         * searches Products using Part ID or Part  Name
         */
        Main_C.Search_part(Part_Search_Txt,  Prod_Addpart_Table);

    }
}
