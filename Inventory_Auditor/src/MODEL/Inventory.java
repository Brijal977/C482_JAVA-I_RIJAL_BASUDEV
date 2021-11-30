package MODEL;

/**
 *
 * @author BASUDEV RIJAL
 */
import CONTROLLER.Main_C;
import com.sun.prism.PixelFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class Inventory {
    

//----------VARIABLES------------------------------------------------------------------------------------------------
    /**
     * local variables
     * allparts: Array list of all parts
     * allProducts: Array list of all products
     * lookupParts: used to search parts
     * LookupProducts: used to search products
     */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // List used for searches
    private static ObservableList<Part> lookupParts = FXCollections.observableArrayList();
    private static ObservableList<Product> lookupProducts = FXCollections.observableArrayList();


//========== PARTS TABLE =============================================================================================
//---------- ADD/UPDATE/DELETE ----------------------------------------------------

    /**
     * adds new part to the inventory
     *
     * @param newPart the new part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * gets the lists of all parts
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Searches the parts in part list
     * @return lookupParts
     */
    public static ObservableList<Part> getLookupParts() {
        return lookupParts;
    }


    /**
     * search part by part ID
     *
     * @param partID
     * @return part or null
     */
    public static Part lookupPart(int partID) {
        int i = -1;
        
        for (Part part : getAllParts()) {
            i++;
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }
    // DONE

    /**
     * search part by part Name
     *
     * @param partName
     * @return lookupParts or allParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        if (!(getLookupParts().isEmpty())) {
            getLookupParts().clear();
        }
        
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                lookupParts.add(part);
                return lookupParts;
            }
        }
        return allParts;
    }
    

    

        

//==========PRODUCTS TABLE============================================================================================
//----------ADD/UPDATE/DELETE ----------------------------------------------------------

    /**
     * adds new product to the inventory
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {allProducts.add(newProduct);}

    /**
     * update the existing products
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {allProducts.set(index, newProduct);}

    /**
     * deletes the product from inventory
     * @param newProduct
     */
    public static void deleteProduct(Product newProduct) {allProducts.remove(newProduct);}

    /**
     * update associated parts on product
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        int startPos = -1;

        for (Part part : Inventory.getAllParts()) {
            startPos++;
            if (part.getId() == index) {
                Inventory.getAllParts().set(startPos, selectedPart);
            }
        }

    }

    /**
     * deleted selected associated part of product
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                return getAllParts().remove(part);
            }
        }
        return false;
    }




//---------- SEARCH -------------------------------------------------------------------------------------

    /**
     * Get the list of all products
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {return allProducts;}

    /**
     * get the list of lookup products for searching
     * @return ookupProducts
     */
    public static ObservableList<Product> getLookupProducts() {return lookupProducts;}

    /**
     * Search Product using Product ID
     *
     * @param ProductID
     * @return
     */
    public static Product lookupProduct(int ProductID) {return null;}

    /**
     * Search Product using Product Name
     *
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        if (!(getLookupProducts().isEmpty())) {
            getLookupProducts().clear();
        }
        
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName)) {
                lookupProducts.add(product);
                return lookupProducts;
            }
        }
        return allProducts;
    }



//=====================possible mod_product fix ============================================================
    /**
     * create a instance of Inventory
     */
    private final static Inventory instance = new Inventory();

    /**
     * getter : new Inventory instance
     * @return instance
     */
    public static Inventory getInstance() {
        return instance;
    }


//=================AUTO generated IDs ======================================================================

    /**
     * generates a new part ID for new part
     * @return
     */
    public static int gen_PartId() {
        return getAllParts().size() + 001;
    }

    /**
     * generates a new product ID for new product
     *
     * @return
     */
    public static int gen_ProdId() {
        return getAllProducts().size() + 10001;
    }




}
