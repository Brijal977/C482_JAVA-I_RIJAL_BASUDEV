package MODEL;
/**
 *
 * @author BASUDEV RIJAL
 */
public class InHouse extends Part {
    

// ----------VARIABLES-----------------------------------------------------------------------------
    /**
     * local variable : holds part's Machine ID
     */
    private int machineID;



//----------CONSTRUCTORS----------------------------------------------------------------------------

    /**
     * Constructor of In-house class
     * @param id
     * @param name
     * @param cost
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double cost, int stock, int min, int max, int machineID) {
        super(id, name, cost, stock, min, max);
        this.machineID = machineID;
    }
    

//---------- GETTERS   SETTERS----------------------------------------------------------------------

    /**
     * Setter Machine id
     * @param machineID to in-house parts
     */
    public void setMachineID(int machineID) {this.machineID = machineID;}

    /**
     * Getter Machine ID
     * @return machineID
     */
    public int getMachineID() {
        return machineID;
    }

            
}
