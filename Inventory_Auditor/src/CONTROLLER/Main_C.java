package CONTROLLER;

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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import MODEL.*;


/**
 *
 * @author BASUDEV RIJAL
 */
public  class  Main_C  implements Initializable {


//========================= VARIABLE MAPPING=====================================================
    /**
     * UI-Controller VARIABLE MAPPING -
     * Text Fields : [part search, Product search]
     *
     * Tableview Parts Table : Columns product[ID, Name, INV(stock), Cost]
     * lists all parts in inventory
     *
     *Tableview Product Table : Columns product[ID, Name, INV(stock), Cost]
     * lists all products in inventory
     */

    @FXML
    private TableView<Part> part_Table_view;
    @FXML
    private TextField part_Search_Txt;

    @FXML
    private TableColumn<Part, Integer> part_Id_col;
    @FXML
    private TableColumn<Part, String> part_Name_col;
    @FXML
    private TableColumn<Part, Integer> part_INV_col;
    @FXML
    private TableColumn<Part, Double> part_Cost_col;

    @FXML
    private TableView<Product> prod_Table_view;
    @FXML
    private TextField prod_Search_Txt;

    @FXML
    private TableColumn<Product, Integer> prod_Id_col;
    @FXML
    private TableColumn<Product, String> prod_Name_col;
    @FXML
    private TableColumn<Product, Integer> prod_INV_col;
    @FXML
    private TableColumn<Product, Double> prod_Cost_col;


    //===================================navigation========================================================

    /**
     * Reusable Navigation function : will be used whenever we need to navigate back to main screen
     *
     * @return void
     */
    @FXML
    public static void scene_switch(ActionEvent event, String view, String view_title) throws IOException {
        Stage Scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Objects.requireNonNull(Main_C.class.getResource("/VIEW/" + view + ".fxml")));
        Scene.setTitle(view_title);
        Scene.setScene(new Scene(scene));
        Scene.show();

    }


    /**
     * Reuseable Alerting function - used throughout the program to inform user
     * @param message
     */
    // alert messages -
    @FXML
    public static void Alert_msg(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT MESSAGE");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> confirmation = alert.showAndWait();


    }
    // alert warning-


    /**
     * Navigates to ADD PART screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Add_Part_bttn_Clicked(ActionEvent event) throws IOException {
        scene_switch(event, "Add_Part", "ADD PART");
    }

    /**
     * Navigates to ADD PRODUCT screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Add_Product_bttn_Clicked(ActionEvent event) throws IOException {
        scene_switch(event, "Add_Product", "ADD Product");
    }


    //-------------------------------track part/product selection for modify----------

    /**
     * local variable
     * holds the record of selected part - to be modifies
     */
    private static Part part_Selected;

    /**
     * local variable
     * holds the record of selected product - to be modifies
     */
    private static Product product_Selected;

    /**
     * Getter
     * @return part_Selected
     */
    public static Part get_Part_Selected() {
        return part_Selected;
    }

    /**
     * Getter
     * @return product_Selected
     */
    public static Product get_Prod_Selected() {
        return product_Selected;
    }

    /**
     * Navigates to Modify Part screen
     * @param event
     * @throws IOException
     */
    @FXML
    public void Mod_Part_bttn_Clicked(ActionEvent event) throws IOException {

        part_Selected = part_Table_view.getSelectionModel().getSelectedItem();
        if (part_Selected == null) {
            Alert_msg("you must select a part to be modified");
        } else {
            scene_switch(event, "Mod_Part", "MODIFY PART");
        }
    }


    /**
     * Navigates to MODIFY PRODUCT screen
     * @param event
     * @throws IOException
     */
    @FXML
    public void Mod_Product_bttn_Clicked(ActionEvent event) throws IOException {

        product_Selected = prod_Table_view.getSelectionModel().getSelectedItem();
        if (product_Selected == null) {
            Alert_msg("you must select a product to be modified");
        } else {
            scene_switch(event, "Mod_Product", "MODIFY PART");
        }
    }


    //=================================== DELETE PART/PRODUCT =======================================================

    /**
     * Deletes the selected part
     * warns the user before delete
     */
    @FXML
    public void Del_Part_bttn_Clicked() {
        Part selected = part_Table_view.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setContentText("Are you sure? this will delete the selected part permanently");
        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            Inventory.deletePart(selected);
        }


    }

    /**
     * Deletes the selected product
     * warns the user before delete
     */
    @FXML
    public void Del_Product_bttn_Clicked() {
        Product selected = prod_Table_view.getSelectionModel().getSelectedItem();

        if (selected.getAllAssociatedParts().size() > 0) {
            Alert_msg("Product has associated parts, remove them first");
            return;}

        else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRM");
            alert.setContentText("Are you sure? this will delete the selected product permanently");
            Optional<ButtonType> confirmation = alert.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                Inventory.deleteProduct(selected);
                Alert_msg("Product Deleted");
            }
        }


    }


//=================================== SEARCH PART/PRODUCT ==========================================================

    /**
     * initialize the main screen
     * populates the parts and product tables
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        part_Table_view.setItems(Inventory.getAllParts());
        prod_Table_view.setItems(Inventory.getAllProducts());

        part_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));

        prod_Id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        prod_Name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        prod_INV_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prod_Cost_col.setCellValueFactory(new PropertyValueFactory<>("cost"));

        Search_part(part_Search_Txt,  part_Table_view);
        Search_product(prod_Search_Txt,  prod_Table_view);




    }



//=======================================SEARCH PRODUCT =======================================================

    /**
     * search the products from inventory
     * @param prod_Search_Txt
     * @param prod_Table_view
     */
    @FXML
    public static void Search_product(TextField prod_Search_Txt, TableView prod_Table_view) {
        FilteredList<Product> filtered_Products = new FilteredList<>(Inventory.getAllProducts(), b -> true);

        prod_Search_Txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filtered_Products.setPredicate(product_Predicate(newValue));
            if (filtered_Products.size() == 0) {
                Alert_msg("No matching parts found!");
            }
        });

        prod_Table_view.setItems(filtered_Products);
    }

    /**
     * show all products if the search box is empty
     * As string types filters the product tableview items
     * @param string
     * @return product items
     */
    private static Predicate<Product> product_Predicate(String string) {
        return product -> {
            if (string == null || string.isEmpty()) return true;
            return searchProducts(product, string);
        };
    }

    /**
     * checks if inventory parts contains strings on part search box
     * @param product
     * @param string
     * @return true/false
     */
    private static boolean searchProducts(Product product, String string) {
        return (product.getName().toLowerCase().contains(string.toLowerCase())) ||
                Integer.valueOf(product.getId()).toString().equals(string.toLowerCase());
    }



//=======================================SEARCH PART =======================================================

    /**
     * search the parts from inventory
     * @param part_Search_Txt
     * @param part_Table_view
     */
    @FXML
    public static void Search_part(TextField part_Search_Txt, TableView part_Table_view) {

        FilteredList<Part> filtered_Parts = new FilteredList<>(Inventory.getAllParts(), b -> true);
        part_Search_Txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filtered_Parts.setPredicate(part_Predicate(newValue));
            if (filtered_Parts.size() == 0) {
                Alert_msg("Matching parts found!");
            }
        });

        part_Table_view.setItems(filtered_Parts);

    }

    /**
     * show all parts if the search box is empty
     * As string types filters the product tableview items
     * @param string
     * @return parts items
     */
    public static Predicate<Part> part_Predicate(String string){
        return part -> {
            if (string == null || string.isEmpty()) {
                return true;
            }
            return searchParts(part, string);
        };
    }
    /**
     *  checks if inventory parts contains strings on part search box
     * @param part
     * @param string
     * @return true/false
     */
    public static boolean searchParts(Part part, String string){
        return (part.getName().toLowerCase().contains(string.toLowerCase())) ||
                Integer.valueOf(part.getId()).toString().equals(string.toLowerCase());
    }

    /**
     * exits the program
     * @param event
     */
    public void Exit_bttn_Clicked(ActionEvent event) {
        System.exit(0);
    }
}




    
    
    
    
    
    

