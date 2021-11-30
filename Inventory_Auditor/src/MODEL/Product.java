package MODEL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {

    /**
     *
     * @author BASUDEV RIJAL
     */
//----------VARIABLES--------------------------------------------------------------------------------------------
    /**
     * local variables : product [id, name, cost, stock, min, max ]
     */
    private int id;
    private String name;
    private double cost;
    private int stock;
    private int min;
    private int max;


//----------CONSTRUCTORS-----------------------------------------------------------------------------------------
    public Product(int id, String name, double cost, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

//------------ GETTER / SETTER ---------------------------------------------------------------------------------

    /**
     * @return the id
     */
    public int getId() {return id;}

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    

//---------- Associated Parts --------------------------------------------------------------------------------------------
    /**
     *
     * stores: product's associated parts
     */
    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Getter: gets the list of associated parts
     * @return associatedParts
     */
    public  ObservableList<Part> getAllAssociatedParts() {return associatedParts;}

    /**
     *
     * Adds: new associated parts to the product's associated parts list
     * @param part
     */
    public  void addAssociatedPart(Part part) {associatedParts.add(part);}

    /**
     * removes: the associated parts from the product's associated parts list
     * @param part
     */
    public  void deleteAssociatedPart(Part part) {associatedParts.remove(part);}



    
}
