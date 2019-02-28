package model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Map.Entry.*;

/** Class State provides methods for operatig one states.
 * A warehouse and an order needs to be initalized for the most methods.
 * A state can be evluated or neighbours can be created for it. Furthermore
 * an initialState or a randomState can be created.
 *
 * @author Julia Kaltenborn (jkaltenborn@uos.de)
 */
public class StateHandler {

  private String[] order = null;
  private Map<String, ArrayList<Integer>> itemWarehouse = new HashMap<String, ArrayList<Integer>>();
  private Map<Integer, String[]> psuWarehouse = new HashMap<Integer, String[]>();
  //key: PSU number; value: how many elements of the order are contained in the PSU
  private Map<Integer, Integer> evaluationMap = new HashMap<Integer, Integer>();

  /** Constructor for creating a StateHandler.
   * @param warehouse of type HashMap<String, ArrayList<Integer>>.
   * The warehouse must consist of a HashMap with String as keys (Item) and an ArrayList as values
   * (PSUs containing the item). Do not use empty Strings. Strings must be unique identifiers for
   * the items. Do not skip indices of the ArrayList. Start with index 0.
   * Do not use empty warehouse or a null object.
   */
  public StateHandler(HashMap<String, ArrayList<Integer>> itemWarehouse, HashMap<Integer, String> psuWarehouseString){
    if(itemWarehouse == null || itemWarehouse.size() == 0){
      System.err.println("@StateHandler: Item Warehouse is null or empty.");
    }
    this.itemWarehouse = itemWarehouse;
    if(psuWarehouseString == null || psuWarehouseString.size() == 0){
      System.err.println("@StateHandler: PSU Warehouse is null or empty.");
    }
    for(int psu = 0; psu < psuWarehouseString.size(); psu++){
      psuWarehouse.put(psu, psuWarehouseString.get(psu).split(" "));
    }

  }

  /** Updates the warehouse, replaces the current warehouse with a new one.
   *@param newWarehouse of type HashMap<String, ArrayList<Integer>>. The new warehouse.
   */
  public void updateWarehouse(HashMap<String, ArrayList<Integer>> itemWarehouse, HashMap<Integer, String> psuWarehouseString){
    if(itemWarehouse == null || itemWarehouse.size() == 0){
      System.err.println("@StateHandler: itemWarehouse is null or empty.");
    }
    this.itemWarehouse = itemWarehouse;
    if(psuWarehouseString == null || psuWarehouseString.size() == 0){
      System.err.println("@StateHandler: psuWarehouse are null or empty.");
    }
    for(int psu = 0; psu < psuWarehouseString.size(); psu++){
      psuWarehouse.put(psu, psuWarehouseString.get(psu).split(" "));
    }
  }
  /**
   *
   */
  public int evaluate(int[] state){
    //go through the evaluationMap and give each state the number
    //assigned as value
    //add everything together
    int evaluationResult = -1;
    int[] consideredValues = new int[state.length];
    for(int psu = 0; psu < state.length; psu++){
      int valueOfPSU = this.evaluationMap.get(state[psu]);
      evaluationResult = evaluationResult + valueOfPSU;
      consideredValues[psu] = valueOfPSU;
    }
    int bestPSU = Arrays.stream(consideredValues).max().getAsInt();
    //e.g. for an order of 5 items: if the first psu contains five items and the rest only 1,
    //that should be stil better than a 5 psus containing all 4 elements of the order!
    return evaluationResult + (bestPSU * 100) - bestPSU;
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
    ArrayList<int[]> neighbours = new ArrayList<int[]>();

    for(int i = 0; i < order.length; i++){
      int[] currentNeighbour = currentState.clone();
      String currentItem = order[i];
      ArrayList<Integer> allPSUsContainingItem = itemWarehouse.get(currentItem);
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
//    for(int i = 0; i < neighbours.size(); i++){
//      int result = this.evaluate(neighbours.get(i));
//      System.out.println("Value of Neighbour:" + result);
//    }
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
      ArrayList<Integer> allPSUsContainingItem = itemWarehouse.get(currentItem);
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
      ArrayList<Integer> allPSUsContainingItem = itemWarehouse.get(currentItem);
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
   * generateRandomState or createNeighbours. We also initialize
   * values for our evaluation function here.
   *@param order of type String[] which should be set.
   */
  public void setOrder(String[] order){
    if(order == null || order.length < 1){
      System.err.println("@StateHandler: Order was not set, since it was empty or null");
    } else {
      this.order = order;
      List<String> orderList = Arrays.asList(this.order);
      //initialize EvaluationMap
      //each psu gets a value describing how many items of the order it holds
      for(int i = 0; i < psuWarehouse.size(); i++){
        String[] allItems = psuWarehouse.get(i);
        int valueOfPSU = 0;
        for(int j = 0; j < allItems.length; j++){
          if(orderList.contains(allItems[j])){
            valueOfPSU++;
          }
        }
        evaluationMap.put(Integer.valueOf(i), Integer.valueOf(valueOfPSU));
      }
    }
  }

  /**
   *@return String[], the current order.
   */
  public String[] getOrder(){
    return this.order;
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
  /**
   *@return int[] containing i ints. The ith value corresponds to
   * the ith ordered item.
   */
  public int[] showUsedPSUs(int[] state){
    List<Integer> usedPSUs = new ArrayList<Integer>();
    List<String> orderList = new LinkedList<String>(Arrays.asList(this.order));

    //we create a map with all psus of the state and their values
    Map<Integer, Integer> psusWithValue = new HashMap<Integer, Integer>();
    for(int j = 0; j < state.length; j++){
      Integer value = evaluationMap.get(Integer.valueOf(state[j]));
      psusWithValue.put(Integer.valueOf(state[j]), value);
    }
    System.out.println(psusWithValue);

    //we sort state[] -> psu with highest value at the beginning (descending order)
    for(int k = 0; k < psusWithValue.size(); k++){
      int maxValue= Collections.max(psusWithValue.values());
      boolean maxFound = false;
      for(int i = 0; i < psusWithValue.size() & !maxFound; i++){
        Integer currentPSU = (Integer) psusWithValue.keySet().toArray()[i];
        if(psusWithValue.get(currentPSU) == maxValue){
          state[k] = currentPSU;
          psusWithValue.put(currentPSU, -1);
          maxFound = true;
        }
      }
    }
    //we go through the ordered states and add the psu to the usedPSU List while removing the
    //considered items from the order List
    for(int i = 0; i < state.length; i++){
      int currentPSU = state[i];
      String[] itemsContainedByPSU = psuWarehouse.get(currentPSU);
      for(int j = 0; j < itemsContainedByPSU.length; j++){
        String item = itemsContainedByPSU[j];
        if(orderList.contains(item)){
          if(!(usedPSUs.contains(currentPSU))){
              usedPSUs.add(Integer.valueOf(currentPSU));
          }
          orderList.remove(item);
        }
      }
    }
    int[] result = new int[usedPSUs.size()];
    for(int k = 0; k < result.length; k++){
      result[k] = usedPSUs.get(k);
    }
    System.out.println("@StateHandler: used PSUs" + Arrays.toString(result));
    return result;
  }

  public int numOfUsedPSUs(int[] state){
    int[] stateResult = this.showUsedPSUs(state);
    return stateResult.length;
  }
}
