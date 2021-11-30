package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.RadioButton;
import MODEL.*;


/**
 *
 * @author BASUDEV RIJAL
 */
public class Mod_Part_C implements Initializable  {



    //========================= VARIABLE MAPPING=====================================================
    /**
     * UI-Controller VARIABLE MAPPING -
     * Radio Buttons : [in-house, OutSourced]
     * Text fields : Part [ID,Name, INV(stock), Cost, Min, Max, Machine ID/Company name]
     * label : Company Name/ Machine ID : label switches as we click different radio button
     *
     */

    @FXML private RadioButton InHouse_Rad_bttn;
    @FXML private RadioButton Outsourced_Rad_bttn;

    @FXML private TextField PartID_Txt;
    @FXML private TextField PartName_Txt;
    @FXML private TextField PartINV_Txt;
    @FXML private TextField PartCost_Txt;
    @FXML private TextField PartMin_Txt;
    @FXML private TextField PartMax_Txt;

    @FXML private Label CompanyName_Lbl;
    @FXML private TextField CompanyName_txt;




//===================================navigation====================================
    /**
     *  navigates back to main screen - when user select cancel button
     * * @param event AddPart_Cancel_Clicked event
     * @return void
     *
     *  //to be optimized : repetitive navigation function   - DONE !!
     */

    @FXML
    public void ModPart_Cancel_Clicked(ActionEvent event) throws IOException {
        Main_C.Alert_msg("the unsaved entries will be lost");
        Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
    }



//=================================== label switch  =======================================================
    /**
     * switches CompanyName_Lbl label - as we click different radio button
     *
     * there were typo in FX_id --- caused radio buttons not to be mutual exclusive -----fixed
     *
     * @param event: InHouse_Rad_bttn_Checked, Outsourced_Rad_bttn_Checked
     * @return void
     */

    @FXML public void InHouse_Rad_bttn_Checked(ActionEvent event) {
        Outsourced_Rad_bttn.setSelected(false);
        CompanyName_Lbl.setText("Machine ID");}

   @FXML public void Outsourced_Rad_bttn_Checked(ActionEvent event) {
       InHouse_Rad_bttn.setSelected(false);
        CompanyName_Lbl.setText("Company Name");}



//=================================== SAVE ====================================
//the associated parts were not being saved with product --- fixed

    /**
     * Update and Saves the part -  after modification in INVENTORY
     *
     *
     * @param event: AddPart_Save_Clicked
     * @return void
     *
     *
     */

    @FXML
    public void ModPart_Save_Clicked(ActionEvent event) throws IOException {
        Add_Part_C.error_flag = false;
        Add_Part_C.Validate(PartName_Txt, PartINV_Txt, PartCost_Txt, PartMin_Txt, PartMax_Txt);

        if (!Add_Part_C.error_flag) {
            int id = Integer.parseInt(PartID_Txt.getText());
            String name = PartName_Txt.getText();
            int stock = Integer.parseInt(PartINV_Txt.getText());
            double cost = Double.parseDouble(PartCost_Txt.getText());
            int min = Integer.parseInt(PartMin_Txt.getText());
            int max = Integer.parseInt(PartMax_Txt.getText());
            int machineID = 0;
            String companyName = CompanyName_txt.getText();

            if (InHouse_Rad_bttn.isSelected()) {
                Inventory.updatePart(id, new InHouse(id, name, cost, stock, min, max, machineID));
            } else {
                Inventory.updatePart(id, new Outsourced(id, name, cost, stock, min, max, companyName));
            }
            Main_C.scene_switch(event, "Main", "MAIN");             //switches back to Main
            Main_C.Alert_msg("One  Part  Modified");

        }
        else {
            Main_C.Alert_msg("error found during execution");
        }
    }

    /**
     * holds the record of selected part - to retrieve part records from main screen
     */
    private  Part part_Selected;


    /**
     * initialize to Modify part form
     * preloads the values of selected part in input fields to be modified
     *
     * @param url
     * @param resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        part_Selected = Main_C.get_Part_Selected();

        if (part_Selected instanceof Outsourced) {
            Outsourced_Rad_bttn.setSelected(true);
            Outsourced_Rad_bttn_Checked(new ActionEvent());
            CompanyName_txt.setText(((Outsourced) part_Selected).getCompanyName());}

        else {
            InHouse_Rad_bttn.setSelected(true);
            CompanyName_txt.setText(String.valueOf(((InHouse) part_Selected).getMachineID()));}

        PartID_Txt.setText(String.valueOf(part_Selected.getId()));
        PartName_Txt.setText(part_Selected.getName());
        PartINV_Txt.setText(String.valueOf(part_Selected.getStock()));
        PartCost_Txt.setText(String.valueOf(part_Selected.getCost()));
        PartMax_Txt.setText(String.valueOf(part_Selected.getMax()));
        PartMin_Txt.setText(String.valueOf(part_Selected.getMin()));
    }
}
