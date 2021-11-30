package MODEL;
/**
 * Supplied class Part.java
 */

/**
 *
 * @author BASUDEV RIJAL
 */
public abstract class Part {
    

//----------VARIABLES------------------------------------------------------------------
    private int id;
    private String name;
    private double cost;
    private int stock;
    private int min;
    private int max;
    

//----------CONSTRUCTORS----------------------------------------------------------------
    public Part(int id, String name, double cost, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

//------------ GETTER / SETTER --------------------------------------------------------
    /**
     * @return the id
     */
    public int getId() {return id;}

    /**
     * @param id the id to set
     */
    public void setId(int id) {this.id = id;}

    /**
     * @return the name
     */
    public String getName() {return name;}

    /**
     * @param name the name to set
     */
    public void setName(String name) {this.name = name;}

    /**
     * @return the cost
     */
    public double getCost() {return cost;}

    /**
     * @param cost the price to set
     */
    public void setCost(double cost) {this.cost = cost;}

    /**
     * @return the stock
     */
    public int getStock() {return stock;}

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {this.stock = stock;}

    /**
     * @return the min
     */
    public int getMin() {return min;}

    /**
     * @param min the min to set
     */
    public void setMin(int min) {this.min = min;}

    /**
     * @return the max
     */
    public int getMax() {return max;}

    /**
     * @param max the max to set
     */
    public void setMax(int max) {this.max = max;}

    
}
