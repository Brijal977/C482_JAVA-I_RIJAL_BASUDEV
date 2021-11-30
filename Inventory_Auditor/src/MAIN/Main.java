package MAIN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import MODEL.*;

/**
 *
 * @author BASUDEV RIJAL
 */
public class Main  extends Application {
    @Override

//===================================navigation====================================
    //to be optimized : repetitive navigation functions
    /**
     * loads the main screen at start
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/VIEW/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * preloads some product and parts to the inventory
     * @param args
     */
    public static void main(String[] args) {
        Inventory Inv = Inventory.getInstance();

        Inv.addPart(new InHouse(Inv.gen_PartId(),"Mouse", 25.32, 100, 50, 200, 1));
        Inv.addPart(new InHouse(Inv.gen_PartId(), "Keyboard", 100.00, 60, 30, 200, 2));
        Inv.addPart(new InHouse(Inv.gen_PartId(), "screws", 0.90, 1000, 250, 2000, 3));
        Inv.addPart(new Outsourced(Inv.gen_PartId(), "power cables", 20.50, 100, 75, 300, "Atari ltd."));
        Inv.addPart(new Outsourced(Inv.gen_PartId(), "Power Adaptor", 35.95, 68, 25, 200, "Atari ltd."));
        Inv.addPart(new Outsourced(Inv.gen_PartId(), "Headphone", 199.99, 50, 10, 100, "Beats"));


        Inv.addProduct(new Product(Inv.gen_ProdId(), "DEll XPS 15", 1432.99, 10, 5, 50));
        Inv.addProduct(new Product(Inv.gen_ProdId(), "Toyota Corolla", 25593.99, 2, 1, 5));
        Inv.addProduct(new Product(Inv.gen_ProdId(), "Vacuum cleaner", 99.99, 5, 3, 5));



        launch(args);
    }

}
