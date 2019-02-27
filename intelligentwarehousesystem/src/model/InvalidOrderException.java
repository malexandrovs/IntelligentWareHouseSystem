package model;
public class InvalidOrderException extends Exception{

    private String invalidItems;

    public InvalidOrderException(String invalidItems){
        this.invalidItems = invalidItems;
    }

    public String getInvalidItems(){
        return invalidItems;
    }
}