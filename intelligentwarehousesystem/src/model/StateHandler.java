package model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Class State provides methods for operatig one states.
 * A warehouse and an order needs to be initalized for the most methods.
 * A state can be evluated or neighbours can be created for it. Furthermore
 * an initialState or a randomState can be created.
 *
 * @author Julia Kaltenborn (jkaltenborn@uos.de)
 */
public class StateHandler {

  private String[] order = null;
  private HashMap<String, ArrayList<Integer>> warehouse = new HashMap<String, ArrayList<Integer>>();
  private static StateHandler stateInstance = null;

  /** Constructor for creating a StateHandler.
   * @param warehouse of type HashMap<String, ArrayList<Integer>>.
   * The warehouse must consist of a HashMap with String as keys (Item) and an ArrayList as values
   * (PSUs containing the item). Do not use empty Strings. Strings must be unique identifiers for
   * the items. Do not skip indices of the ArrayList. Start with index 0.
   * Do not use empty warehouse or a null object.
   */
  public StateHandler(HashMap<String, ArrayList<Integer>> warehouse){
    if(warehouse == null || warehouse.size() == 0){
      System.err.println("@StateHandler: Warehouse is null or empty.");
    }
    this.warehouse = warehouse;
  }

  /** Updates the warehouse, replaces the current warehouse with a new one.
   *@param newWarehouse of type HashMap<String, ArrayList<Integer>>. The new warehouse.
   */
  public void updateWarehouse(HashMap<String, ArrayList<Integer>> newWarehouse){
    if(newWarehouse == null || newWarehouse.size() < 1){
      System.err.println("@StateHandler: New Warehouse was not set since it is empty or null.");
    } else{
      this.warehouse = newWarehouse;
    }
  }

  /**
    *EVALUATION FUNCTION
    *@return int, a value which expresses how good
    *or bad the state is. The higher the number, the better.
    *The maximum is the number of elements minus 1 -> only one set is used.
    *The minimum is 0 -> each element uses one set.
    *Returns -1 if the element could not be found in the psus.
    *Return -2, if the for-loop was not entered -> severe problem which should be
    *handeled.
    *@param state, current state which shall be evaluated.
    */
  public int evaluate(int[] state){
    int result = -2;
    if(state.length < 1){
      System.out.println("@StateHandler: An empty State cannot be evaluated");
      return result;
    }
    Set<Integer> stateAsSet = new HashSet<Integer>();
    for(int i = 0; i < state.length; i++){
      if(state[i] == -1){
        return -1;
      }
      stateAsSet.add(Integer.valueOf(state[i]));
    }
    //result is number of elements minus number of used sets
    result = state.length - stateAsSet.size();
    //for unexcpected other exceptions
    if(result == -2){
      System.out.println("@StateHandler: For-Loop was not entered in the evaluate function.");
    }
    return result;
  }

  /** Creates the neighbours of a given State.
   * Neighbours which are searched: For each element of the
   * order there is a new PSU found, containing that item.
   * A neighbours differs only in one PSU from the current State.
   *@return ArrayList<int[]> with all neighbours, does not skip indices and starts with 0.
   *@param currentState of type int[]: from where on the next neighbours
   * should be searched.
   *@param stopAtEndOfWarehouse of type boolean. If true, it will return -1 for items,
   * if it has reached the End of the warehouse. Ff false, the next neighbor will be choosen from
   * the beginning of the beginning of the warehouse. In this case there should be an iteration limit.
   */
    public ArrayList<int[]> createNeighbours(int[] currentState, boolean stopAtEndOfWarehouse){
    System.out.println("Creating Neighbours");
    ArrayList<int[]> neighbours = new ArrayList<int[]>();

    for(int i = 0; i < order.length; i++){
      int[] currentNeighbour = currentState.clone();
      String currentItem = order[i];
      ArrayList<Integer> allPSUsContainingItem = warehouse.get(currentItem);
      int currentPSU = currentState[i];
      //otherwise we do not need to search for any neighbours
      if((currentPSU != -1) & (allPSUsContainingItem != null)){
          int indexOfCurrentPSU = allPSUsContainingItem.indexOf(Integer.valueOf(currentPSU));

          for(int j = 0; j < allPSUsContainingItem.size(); j++){
            currentNeighbour[i] = allPSUsContainingItem.get(j);
            neighbours.add(currentNeighbour);
          }

//          if ((indexOfCurrentPSU >= allPSUsContainingItem.size()-1) && stopAtEndOfWarehouse){
//            currentNeighbour[i] = -1;
//          } else if ((indexOfCurrentPSU >= allPSUsContainingItem.size()-1) && !stopAtEndOfWarehouse){
//            for(int j = 0; j < indexOfCurrentPSU; j++){
//              currentNeighbour[i] = allPSUsContainingItem.get(j);
//              neighbours.add(currentNeighbour);
//            }
//          }
      } else {
        System.out.println("@StateHandler: Item does not exist");
      }
    }
    System.out.println(neighbours.size());
    return neighbours;
  }

  /**
   *Generates a Random State, if order given beforehand
   *@return int[], the random state. The ith number of the array represents
   * a PSU that contains the ith item of the order. -1 stands for item
   * not contained in the warehouse.
   */
  public int[] generateRandomState(){
    Random randomGenerator = new Random();
    int[] randomState = new int[order.length];
    //go through the order
    //catch for each element of the order a random number of the corresponding ArrayList<Integer>
    for(int i = 0; i < order.length; i++){
      String currentItem = order[i];
      ArrayList<Integer> allPSUsContainingItem = warehouse.get(currentItem);
      if(allPSUsContainingItem == null || allPSUsContainingItem.size() == 0){
        randomState[i] = -1;
      } else {
        int randomPSUContainingItem = (allPSUsContainingItem.get(randomGenerator.nextInt(allPSUsContainingItem.size()))).intValue();
        randomState[i] = randomPSUContainingItem;
      }
    }
    return randomState;
  }

  /**
   * Generates an initial State if order is given beforehand
   *@return int[], the first initial state. The ith number of the array represents
   * a PSU that contains the ith item of the order. -1 stands for item
   * not contained in the warehouse.
   */
  public int[] generateInitialState() {
    int[] initialState = new int[order.length];
    //goes through the order and assigns for each item
    //the first PSU contained in the ArrayList "allPSUsContainingItem"
    for(int i = 0; i < order.length; i++){
      String currentItem = order[i];
      ArrayList<Integer> allPSUsContainingItem = warehouse.get(currentItem);
      if(allPSUsContainingItem == null || allPSUsContainingItem.size() == 0){
        initialState[i] = -1;
      } else {
        initialState[i] = allPSUsContainingItem.get(0);
      }
    }
    return initialState;
  }

  /**Method for changing or setting the order.
   * Order hast to be set before generateInitialState
   * generateRandomState or createNeighbours.
   *@param order of type String[] which should be set.
   */
  public void setOrder(String[] order){
    if(order == null || order.length < 1){
      System.err.println("@StateHandler: Order was not set, since it was empty or null");
    } else {
      this.order = order;
    }
  }

  /**
   *@return String[], the current order.
   */
  public String[] getOrder(){
    return this.order;
  }

  /** Return the best value a state can reach
   * via evaluate with the current order.
   * @return int which is the max value
   * a state can reach via {@evaluate}
   */
  public int optimum(){
    return order.length - 1;
  }

  /**Checks if given state is a valid state. Valid states do not contain -1.
   * -1 represents an item of the order that was not found.
   *@return boolean if state is valid or not.
   * Returns false for invalid state and true for valid state.
   * Invalid States might not be considered during Search.
   *@param state of type[], which should be checked.
   */
  public boolean stateValid(int[] state){
    if(state == null || state.length < 1){
      System.out.println("@StateHandler: State is null or empty.");
      return false;
    }
    List<Integer> list = Arrays.stream(state).boxed().collect(Collectors.toList());
    return (!list.contains(-1));
  }

  /** Returns the number of used PSUs.
   *@return number of used PSUs in a state.
   *@param state of type[] of which we want to know the number of used PSUS.
   */
  public int numOfUsedPSUs(int[] state){
    int result;
    Set<Integer> stateAsSet = new HashSet<Integer>();
    for(int i = 0; i < state.length; i++){
      stateAsSet.add(Integer.valueOf(state[i]));
    }
    result = stateAsSet.size();
    if(stateAsSet.contains(Integer.valueOf(-1))){
      result = result -1;
      System.out.println("@StateHandler: Attention, State for which number of used PSUs was asked is not valid.");
    }
    return result;
  }

  /** Shows the PSU a State uses without duplicates.
   *@param state of type int[], of which we want to know the used PSUs.
   *If it contains a -1 at least one item was not found.
   *@return an int[] with the identifiers of the used PSUs.
   */
  public int[] showUsedPSUs(int[] state){
    //short way for getting a set, runtime is still the same.
    Set<Integer> hashSet = new HashSet<Integer>();
    for(int i = 0; i < state.length; i++){
      hashSet.add(Integer.valueOf(state[i]));
    }
    Integer[] result = hashSet.toArray(new Integer[hashSet.size()]);
    return Arrays.stream(result).mapToInt(Integer::intValue).toArray();
  }
}
