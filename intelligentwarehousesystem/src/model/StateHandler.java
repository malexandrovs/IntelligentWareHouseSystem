package model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Class State provides methods for operatig one states.
 * A warehouse and an order needs to be initalized for the most methods.
 * A state can be evluated or neighbours can be created for it. Furthermore
 * an initialState can be created when given an order.
 *
 * @author Julia Kaltenborn (jkaltenborn@uos.de)
 */
public class StateHandler {

  private String[] order = null;
  private HashMap<String, ArrayList<Integer>> warehouse = new HashMap<String, ArrayList<Integer>>();
  private static StateHandler stateInstance = null;

  /**
   *singelton
   *only one instance can be created
   *@param warehouse
   */
  private StateHandler(HashMap<String, ArrayList<Integer>> warehouse){
    this.warehouse = warehouse;
  }

  /**
   *@return StateHandler on which the user can operate.
   * If the StateHandler has not been initialized with a warehouse,
   * the method returns a null object.
   */
  public static StateHandler getInstance(){
    if(stateInstance == null){
      System.out.println("@StateHandler: Null Object returned. State has not yet been initialized with Warehouse.");
    }
    return stateInstance;
  }

  /**
   *@param warehouse, a hashmap containing String as key, which represents
   * an item. The ArrayList<Integer> (value of the hashmap) contains all the
   * PSUS that contain the key item. The integers in ArrayList should be ordered
   * ascending. 0 is allowed as Integer. Do not use empty Strings.
   * Strings must be unique identifiers for the items.
   *@return StateHandler on which the user can operate.
   */
  public static StateHandler getInstance(HashMap<String, ArrayList<Integer>> warehouse){
    if(stateInstance == null){
      stateInstance = new StateHandler(warehouse);
    }
    return stateInstance;
  }

  /**
   *@param newWarehouse which should be used
   */
  public void updateWarehouse(HashMap<String, ArrayList<Integer>> newWarehouse){
    this.warehouse = newWarehouse;
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
    *@param state, current state which shall be evaluated
    */
  public static int evaluate(int[] state){
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

	/** Neighbours which are searched: For each element of the
   * order there is a new PSU found, containing that item.
   * A neighbours differs only in one PSU from the current State.
	 *@return ArrayList<int[]> with all neighbours, do not skip indices and start with 0.
   *@param currentState from where on the next neighbours
   * should be searched.
   *@param stopAtEndOfWarehouse if true, it will return -1
   * if it has reached the End of arraylist containing all valid Psus
   * for that item. if false, the next neighbor will be choosen from
   * the beginning of the arraylist
	 */
	public ArrayList<int[]> createNeighbours(int[] currentState, boolean stopAtEndOfWarehouse){
		System.out.println("@StateHandler: Starting to create Neighbours");
    ArrayList<int[]> neighbours = new ArrayList<int[]>();
    if(order == null || order.length < 1){
      System.out.println("@StateHandler: Order is empty or null. Set up the order before asking for neighbours.");
    }

    for(int i = 0; i < order.length; i++){
      int[] currentNeighbour = currentState.clone();
      String currentItem = order[i];
      ArrayList<Integer> allPSUsContainingItem = warehouse.get(currentItem);
      int currentPSU = currentState[i];
      if((currentPSU != -1) & (allPSUsContainingItem != null)){
          int indexOfCurrentPSU = allPSUsContainingItem.indexOf(new Integer(currentPSU));
          if(indexOfCurrentPSU < allPSUsContainingItem.size()-1){
            currentNeighbour[i] = allPSUsContainingItem.get(indexOfCurrentPSU+1);
          } else if ((indexOfCurrentPSU >= allPSUsContainingItem.size()-1) && stopAtEndOfWarehouse){
            currentNeighbour[i] = -1;
          } else if ((indexOfCurrentPSU >= allPSUsContainingItem.size()-1) && !stopAtEndOfWarehouse){
            currentNeighbour[i] = allPSUsContainingItem.get(0);
          }
          neighbours.add(currentNeighbour);
      }
    }
    return neighbours;
  }

  /**
  *generates a Random State, if order given beforehand
  *@return int[], the random state. The ith number of the array represents
  * a PSU that contains the ith item of the order. -1 stands for item
  * not contained in the warehouse.
  */
  public int[] generateRandomState(){
    Random randomGenerator = new Random();
    if(order == null || order.length < 1){
      System.out.println("@StateHandler: Order is empty or null. Set up the order before creating a random state.");
      return null;
    }
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
   * generates an initial State if order is given beforehand
   *@return int[], the first initial state. The ith number of the array represents
   * a PSU that contains the ith item of the order. -1 stands for item
   * not contained in the warehouse.
   */
  public int[] generateInitialState() {
    if(order == null || order.length < 1){
      System.out.println("@StateHandler: Order is empty or null. Set up the order before creating an initial state.");
      return null;
    }
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

  /** Order hast to be set before generateInitialState
   * generateRandomState or createNeighbours
   *@param order for changing the order
   */
  public void setOrder(String[] order){
    this.order = order;
  }

  /**
   *@return the current order
   */
  public String[] getOrder(){
    return this.order;
  }

  /**
   * @return int which is the max value
   * a state can reach via {@evaluate}
   */
  public int optimum(){
    if(order == null || order.length < 1){
      System.out.println("@StateHandler: Order is empty or null. Optimum cannot be calculated without order.");
    }
    return order.length - 1;
  }

  //TODO do we need this method?
  /**
   *@return boolean if state is valid or not.
   * returns false for invalid state and true for valid state.
   * Invalid States might not be considered during Search
   *@param state which should be checked
   */
  public boolean stateValid(int[] state){
    if(state == null || state.length < 1){
      System.out.println("@StateHandler: State is null or empty.");
      return false;
    }
    //we do that in order to reduce time complexity
    //of course we could also just use a for-loop instead
    List<Integer> list = Arrays.stream(state).boxed().collect(Collectors.toList());
    return (!list.contains(-1));
  }

  /**
   *@return number of used PSUs in a state
   *@param state of which we want to know the number of used PSUS
   */
  public int numOfUsedPSUs(int[] state){
    int result;
    Set<Integer> stateAsSet = new HashSet<Integer>();
    for(int i = 0; i < state.length; i++){
      if(state[i] == -1){
        return -1;
      }
      stateAsSet.add(Integer.valueOf(state[i]));
    }
    result = stateAsSet.size();
    if(stateAsSet.contains(Integer.valueOf(-1))){
      result = result -1;
      System.out.println("@StateHandler: Attention, State for which number of used PSUs was asked is not valid.");
    }
    return result;
  }
}
