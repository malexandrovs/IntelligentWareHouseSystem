package model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Map.Entry.*;

/** Class State provides methods for operatig one states.
 * A warehouse and an order needs to be initalized for the most methods.
 * The warehouse must be given in two different forms.
 * A state can be evluated or neighbours can be created for it. Furthermore
 * an initialState or a randomState can be created.
 *
 * @author Julia Kaltenborn (jkaltenborn@uos.de)
 */
public class StateHandler {

  private String[] order = null;
  //two representations make it easier to create different evaluation functions 
  //and leads to higher flexibility and faster runtime (different processes can use different representations
  private Map<String, ArrayList<Integer>> itemWarehouse = new HashMap<String, ArrayList<Integer>>();
  private Map<Integer, String[]> psuWarehouse = new HashMap<Integer, String[]>();
  //key: PSU number; value: how many elements of the order are contained in the PSU
  private Map<Integer, Integer> evaluationMap = new HashMap<Integer, Integer>();

  /** Constructor for creating a StateHandler.
   * @param itemWarehouse of type HashMap<String, ArrayList<Integer>>.
   * The warehouse must consist of a HashMap with String as keys (Item) and an ArrayList as values
   * (PSUs containing the item). Do not use empty Strings. Strings must be unique identifiers for
   * the items. Do not skip indices of the ArrayList. Start with index 0.
   * Do not use empty warehouse or a null object.
   * @param psuWarehouseString of type HashMap<Integer, String> contains PSUs and a String describing which
   * items the psu holds.
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
   *@param itemWarehouse of type HashMap<String, ArrayList<Integer>>. The new warehouse.
   *@param psuWarehouseString of type HashMap<Integer, String>, the second representation of the warehouse.
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
  /** The evaluation function which worked best for the given problem.
   * @param state of type int[] which shall be evaluated. The state is constructed in 
   * the following way: the ith position of state corresponds to the ith position of the order.
   * Consequently, the state describes psus which contain at least one item of the order.
   * @return int, a number describing how good a state is. The higher the number, the better.
   * The method finds out how many items of the order each psu contains. The psu containing the 
   * most items will be considered with factor 100.
   */
  public int evaluate(int[] state){
    int evaluationResult = -1;
    int[] consideredValues = new int[state.length];
    for(int psu = 0; psu < state.length; psu++){
      int valueOfPSU = this.evaluationMap.get(state[psu]);
      evaluationResult = evaluationResult + valueOfPSU;
      consideredValues[psu] = valueOfPSU;
    }
    int bestPSU = Arrays.stream(consideredValues).max().getAsInt();
    //more general: evaluationResult + (bestPSU*10*state.length) - bestPSU
    //even better: Arrays.sort(consideredValues)
    //             and for each value: 2^value
    return evaluationResult + (bestPSU * 100) - bestPSU;
  }

  /** Creates the neighbours of a given State.
    * Neighbours which are searched: For each item of the
    * order there is a new PSU found, containing that item.
    * A neighbours differs only in one PSU from the current State.
    *@return ArrayList<int[]> with all neighbours, does not skip indices and starts with 0.
    *@param currentState of type int[]: from where on the next neighbours
    * should be searched.
    */
    public ArrayList<int[]> createNeighbours(int[] currentState){
      ArrayList<int[]> neighbours = new ArrayList<int[]>();
      //go through complete order and
      for(int i = 0; i < order.length; i++){
      	int[] currentNeighbour = currentState.clone();
      	String currentItem = order[i];
      	ArrayList<Integer> allPSUsContainingItem = itemWarehouse.get(currentItem);
      	int currentPSU = currentState[i];
      	//if the item does exist
      	if((currentPSU != -1) & (allPSUsContainingItem != null)){
          int indexOfCurrentPSU = allPSUsContainingItem.indexOf(Integer.valueOf(currentPSU));
	  //take all the other psus which contain that item,
	  //leave the rest of the psus the same and add this as neighbour
          for(int j = 0; j < allPSUsContainingItem.size(); j++){
	    if(currentPSU != j){
            	currentNeighbour[i] = allPSUsContainingItem.get(j);
            	neighbours.add(currentNeighbour);
	    }
          }
      	} else { //otherwise we do not need to search for any neighbours
      	  System.out.println("@StateHandler: Item does not exist");
      	}
      }
      return neighbours;
    }

  /**
   *Generates a Random State, if order given beforehand.
   *@return int[], the random state. The ith number of the array represents
   * a PSU that contains the ith item of the order. -1 stands for item
   * not contained in the warehouse.
   */
  public int[] generateRandomState(){
    Random randomGenerator = new Random();
    int[] randomState = new int[order.length];
    //go through the order
    //catch for each item of the order a random psu containing that item
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
   * Generates an initial State if order is given beforehand.
   *@return int[], the first initial state. The ith number of the array represents
   * a PSU that contains the ith item of the order. -1 stands for item
   * not contained in the warehouse.
   */
  public int[] generateInitialState() {
    int[] initialState = new int[order.length];
    //goes through the order and assigns for each item of the order
    //the first PSU containing that item
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
   * generateRandomState or createNeighbours. 
   * The evaluation map is initialized here, because it depends on the order.
   *@param order of type String[] which should be set.
   */
  public void setOrder(String[] order){
    if(order == null || order.length < 1){
      System.err.println("@StateHandler: Order was not set, since it was empty or null");
    } else {
      this.order = order;
      List<String> orderList = Arrays.asList(this.order);
      //initializing the EvaluationMap
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
