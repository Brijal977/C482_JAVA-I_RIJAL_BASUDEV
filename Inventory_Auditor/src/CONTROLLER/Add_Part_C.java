package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.RadioButton;
import MODEL.*;


/**
 *
 * @author BASUDEV RIJAL
 */
public class Add_Part_C implements Initializable {

    /**
     * Boolean variable: notifies if there is input errors
     */
    public static Boolean  error_flag = false;   //local variable - used for input validation as a flag

    //========================= VARIABLE MAPPING=====================================================

    /**
     * UI-Controller VARIABLE MAPPING -
     * Radio Buttons : [in-house, OutSourced]
     * Text fields : Part [ID,Name, INV(stock), Cost, Min, Max, Machine ID/Company name]
     * label : Company Name/ Machine ID : label switches as we click different radio button
     *
     */

    @FXML
    private RadioButton  InHouse_Rad_bttn;
    @FXML
    private RadioButton Outsourced_Rad_bttn;
    @FXML
    private TextField PartID_Txt;
    @FXML
    private TextField PartName_Txt;
    @FXML
    private TextField PartINV_Txt;
    @FXML
    private TextField PartCost_Txt;
    @FXML
    private TextField PartMin_Txt;
    @FXML
    private TextField PartMax_Txt;
    @FXML
    private Label CompanyName_Lbl;
    @FXML
    private TextField CompanyName_txt;





    //=================================== Navigation ==================================================

    /**
     *  navigates back to main screen - when user select cancel button
     *
     * * @param event AddPart_Cancel_Clicked event
     * @return void
     *
     *  //to be optimized : repetitive navigation function   - DONE !!
     */

    @FXML
    public void AddPart_Cancel_Clicked(ActionEvent event) throws IOException {
        Main_C.Alert_msg("the unsaved entries will be lost");
        Main_C.scene_switch(event, "Main", "MAIN");                //switches back to Main
    }


    //=================================== label switch  =======================================================
    /**
     * switches CompanyName_Lbl label - as we click different radio button
     *
     * @param event: InHouse_Rad_bttn_Checked, Outsourced_Rad_bttn_Checked
     * @return void
     */
    @FXML
    public void InHouse_Rad_bttn_Checked(ActionEvent event) {
        Outsourced_Rad_bttn.setSelected(false);
        CompanyName_Lbl.setText("Machine ID");
    }

    @FXML
    public void Outsourced_Rad_bttn_Checked(ActionEvent event) {
        InHouse_Rad_bttn.setSelected(false);
        CompanyName_Lbl.setText("Company Name");
    }



    //=================================== SAVE ================================================================

    /**
     * Saves the part - in INVENTORY
     * //@FXML public void send ( ) {} function for both ADD/MODIFY part save function   -- future improvement
     *
     * @param event: AddPart_Save_Clicked
     * @return void
     *
     * LOGICAL ISSUE: the part was being saved if the validator displays the error:
     *FIX: the variable: error_flag and if-else statement fixed the issue
     */

    @FXML
    public void AddPart_Save_Clicked(ActionEvent event) throws IOException {


        Validate(PartName_Txt, PartINV_Txt, PartCost_Txt, PartMin_Txt, PartMax_Txt);

        if (!error_flag) {
            PartID_Txt.setText(String.valueOf(Inventory.gen_PartId()));
            int id = Integer.parseInt(PartID_Txt.getText());
            String name = PartName_Txt.getText();
            int stock = Integer.parseInt(PartINV_Txt.getText());
            double cost = Double.parseDouble(PartCost_Txt.getText());
            int min = Integer.parseInt(PartMin_Txt.getText());
            int max = Integer.parseInt(PartMax_Txt.getText());

            if (InHouse_Rad_bttn.isSelected()) {
                int machineID = Integer.parseInt(CompanyName_txt.getText());
                Inventory.addPart(new InHouse(id, name, cost, stock, min, max, machineID));
            } else {
                String companyName = CompanyName_txt.getText();
                Inventory.addPart(new Outsourced(id, name, cost, stock, min, max, companyName));
            }

            Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
            Main_C.Alert_msg("new Part  Added");

        } else {
            Main_C.Alert_msg("error found during execution");
        }
    }


    //=================================== initializer ================================================================

    /**
     * Initializes the ADD Part UI
     * @param url
     * @param resourceBundle
     */
        @FXML
        public void initialize (URL url, ResourceBundle resourceBundle){
            PartID_Txt.setText(String.valueOf(Inventory.gen_PartId()));

            InHouse_Rad_bttn.setSelected(true);

            CompanyName_Lbl.setText("Machine ID");


        }


//=================================== Rijal's Validator ================================================================

    /**
     * Game changer rijal's validator:
     * reusable throughout the program : except Machine ID/Company Name and Associated parts for product
     * Runtime error: I could not compare textfield.getext() into integer and compare (min>stock>max) values
     * Fix: Used String  array - and created local variable to store their integer value
     *
     *
     * @param Name
     * @param Stock
     * @param Cost
     * @param Min
     * @param Max
     */
        public static void Validate (TextField Name, TextField Stock, TextField Cost, TextField Min, TextField Max) {

            Alert error_msg = new Alert(Alert.AlertType.ERROR);
            error_msg.setHeaderText("Invalid Input");

            // checks if input is empty or blank

            String[] input_array = new String[]{Name.getText(), Cost.getText(), Stock.getText(), Min.getText(), Max.getText()};
            String[] input_array_title = new String[]{"Name", "Cost", "Stock", "Min", "Max"};

            String error = "";


            for (int i = 0; i < input_array.length; i++) {
                if (input_array[i].isBlank()) {
                    error = (error + "\n *   " + input_array_title[i] + "  Input field cannot be empty or null");
                    error_flag = true;

                }
                else {  // checks data type
                    if (i == 0) {
                        continue;
                    } else if (i == 1) {
                        try {
                            Boolean x = (Double.parseDouble(input_array[1]) > 0);
                        } catch (NumberFormatException ex) {
                            error = (error + "\n *    Cost Input Must be a Float Number");
                            error_flag = true;
                        }

                    } else {
                        try {
                            Boolean x = (Integer.parseInt(input_array[i]) > 0);
                            // accidentally wrote "return" here ---- took 4 hrs to fix logical error
                        } catch (NumberFormatException ex) {
                            error = (error + "\n *   " + input_array_title[i] + "  Input Must be a Number");
                            error_flag = true;
                        }
                    }

                    int stock = Integer.parseInt(input_array[2]);
                    int min = Integer.parseInt(input_array[3]);
                    int max = Integer.parseInt(input_array[4]);


                    if (min > max) {
                        error = (error + "\n *    Min value must less than Stock and Max values");
                        error_flag = true;
                    } else {
                        if (stock > max || stock < min) {
                            error = (error + "\n *   Stock value must be between Min and Max values");
                            error_flag = true;
                        }

                    }
                }

            }

            if (error.isBlank()) {
                error_msg.hide();
            } else {
                error_msg.setContentText(error);
                error_msg.showAndWait();
            }

        }

    }
