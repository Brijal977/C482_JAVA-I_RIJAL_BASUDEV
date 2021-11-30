package MODEL;
/**
 *
 * @author BASUDEV RIJAL
 */
public class Outsourced extends Part {


//----------VARIABLES------------------------------------------------------------------------------------
    private String companyName;
    /**
     * local variable : holds part's Company Name
     */

//----------CONSTRUCTORS---------------------------------------------------------------------------------

    /**
     *  Constructor of Outsourced class
     * @param id
     * @param name
     * @param cost
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double cost, int stock, int min, int max, String companyName) {
        super(id, name, cost, stock, min, max);
        this.companyName = companyName;
    }



//---------- GETTERS- -  SETTERS ---------------------------------------------------------------------------
    /**
     * Setter Machine id
     * @param companyName to Outsourced parts
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter Machine ID
     * @return companyName
     *
     */

    public String getCompanyName() {
        return companyName;
    }
    
    
    
}
