package model;

/**
 * Throwm to indicate that a given order has items, which are not listed in the warehouse.
 * @author Michael Alexandrovsky (malexandrovs@uos.de)
 */
public class InvalidOrderException extends Exception{

    private String invalidItems;


    public InvalidOrderException(String invalidItems){
        this.invalidItems = invalidItems;
    }

    /**
     * @return returns a string containing the unaccepted items.
     */
    public String getInvalidItems(){
        return invalidItems;
    }
}