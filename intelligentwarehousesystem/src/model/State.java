package model;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// import intelligentwarehousesystem.src.algorithms.*;

/** Class State provides methods for operatig one states.
 * A warehouse and an order needs to be initalized for the most methods.
 * A state can be evluated or neighbours can be created for it. Furthermore
 * an initialState can be created when given an order.
 *
 * @author Julia Kaltenborn (jkaltenborn@uos.de)
 */
public class State {

  private String[] order = null;
  private HashMap<Integer, ArrayList<String>> warehouse = new HashMap<Integer, ArrayList<String>>();
  private static State stateInstance = null;
	
  //singleton
  private State(HashMap<Integer, ArrayList<String>> warehouse){
    this.warehouse = warehouse;
  }
  //if one calls getInstance, but does not update warehouse before
  //-> null Object returned
  public static State getInstance(){
    if(stateInstance == null){
      System.out.println("Null Object returned. State has not yet been initialized with Warehouse.");
    }
    return stateInstance;
  }
  //TODO constructor chaining: order correct?
  /**
   *@param warehouse important! There are only PSUs allowed,
   * no header or similar! HashMap must start with key 0 and must not leave out numbers!
   *@return State which can be used in order to use the other methods of
   * this class
   */
  public static State getInstance(HashMap<Integer, ArrayList<String>> warehouse){
    if(stateInstance == null){
      stateInstance = new State(warehouse);
    }
    return stateInstance;
  }

  public void updateWarehouse(HashMap<Integer, ArrayList<String>> newWarehouse){
    this.warehouse = newWarehouse;
  }
  /**
    *EVALUATION FUNCTION
    *@return int, a value which expresses how good
    *or bad the state is. The higher the number, the better.
    *The maximum is the number of elements minus 1 -> only one set is used.
    *The minimum is 0 -> each element uses one set.
    *Returns -1 if the given state does not fulfill the requirement,
    *that all elements must be found. In this case the given state should
    *not be considered as possible solution.
    *Return -2, if the for-loop was not entered -> severe problem which should be
    *handeled.
    *@param state, current state which shall be evaluated
    */
  public static int evaluate(int[] state){
    int result = -2;
    //testing whether the state is empty
    if(state.length < 1){
      System.out.println("@State: An empty State cannot be evaluated");
      return result;
    }
    Set<Integer> evalArray = new HashSet<Integer>();
    for(int i = 0; i < state.length; i++){
      //illegal state
      if(state[i] == -1){
        return -1;
      }
      //duplicates are not allowed in sets!
      //consequently there will be only the
      //PSUs we need without any duplicates
      evalArray.add(Integer.valueOf(state[i]));
    }
    //result is number of elements minus number of used sets
    result = state.length - evalArray.size();
    //for unexcpected other exceptions
    if(result == -2){
      System.out.println("@State: For-Loop was not entered in the evaluate function.");
    }
    return result;
  }

  //TODO maybe we do need a larger neighbourhood!!

	/** Neighbours which are searched: For each element of the
   * order there is a new PSU found. A neighbours differs only in one PSU
   * from the current State. The number of neighbours corresponds to
   * to the length of the order.
	 *@return ArrayList<int[]> with all neighbours
   *@param currentState from where on the next neighbours
   * should be searched.
	 */
	public ArrayList<int[]> createNeighbours(int[] currentState){
		System.out.println("@State: Starting to create Neighbours");
    ArrayList<int[]> neighbours = new ArrayList<int[]>();
    //check whether we have an order
    if(order == null || order.length < 1){
      System.out.println("@State: Order is empty or null. Set up the order before asking for neighbours.");
    }
    //get the corresponding order of each part of the state.
    for(int i = 0; i < currentState.length; i++){
      String corspOrder = order[i];
      //1 dimensional arrays -> deep copy via clone
      int[] dummyState = currentState.clone();
      //the starting point of our search should be the
      //PSU after our current PSU
      int postPSU = currentState[i] + 1;
      //but we have to consider the case, that the current PSU
      //is already the last PSU
      int lastPSU = warehouse.size();
      //in the case that the current PSU is the last PSU,
      //we must return -1, since there cannot be found any PSU anymore
      //for that element
      //otherwise just search the next PSU
      if(lastPSU > currentState[i]){
        dummyState[i] = findNextPSU(corspOrder, postPSU);
      } else {
        dummyState[i] = -1;
      }
      neighbours.add(dummyState);
    }
    return neighbours;
	}

  /** GENERATE INITIAL STATE
   * The first valid state is established within this method.
   * The algorithm runs through the warehouse and searches the first PSUs
   * which contain the ordered items.
   * Important! Before asking for neighbours,
   * we needs always an order before. That means that {@link generateInitialState}
   * must always be called before {@link createNeighbours} (or the order should be set
   * in advance manually {@setOrder})
   *@return int[] first initial state. If the array contains a -1, the
   * state is not valid and there will also not be any valid states.
   * But the result can be used in order to change the order in an appropiate way.
   *@param order (Stringarray with different items for which we want to find
   * a minimal set number)
   */
  public int[] generateInitialState(String[] order) {
    this.order = order;
    if(order == null || order.length < 1){
      System.out.println("@State: Order is empty or null. Set up the order before creating an initial state.");
      //we should return null here (or -2) in order to make
      //sure that this exception is not confused with an element
      //not being existent. This essential the order not being existent.
      //nothing should happen afterwards.
      return null;
    }
    //length of state is always equal to length of order
    int[] firstState = new int[order.length];

    //initialize completely with -1 -> if element is not found
    //in the whole warehouse, there will always remain the -1
    for(int i = 0; i < firstState.length; i++){
      firstState[i] = -1;
    }

    //goes through the order and searches for each ordered
    //element the first PSU it can get
    for(int orderElem = 0; orderElem < order.length; orderElem++){
      String currSearchedElem = order[orderElem];
      firstState[orderElem] = findNextPSU(currSearchedElem, 0);
    }
    return firstState;
  }

  /** Finds the next PSU which contains a given Element
   * @return int the identifier of the PSU (line number).
   * returns -1 if the element cannot be found.
   * @param currSearchedElem Element which should be found
   * in the warehouse.
   * @param psuNum indicated where the method should start to search.
   * E.g. when initalizing the first State we begin with the first PSU 0.
   */
  private int findNextPSU(String currSearchedElem, int psuNum){
    int firstPSU = -1;
    for(int i = psuNum; i < warehouse.size(); i++){
      //TODO check whether we have to cast i to Integer
      ArrayList<String> currentPSU = warehouse.get(i);
      if(currentPSU == null){
        System.out.println("PSU " + i + " does not exist. Check the warehouse.");
      }
      if(currentPSU.contains(currSearchedElem)){
        firstPSU = i;
        return firstPSU;
      }
    }
    return firstPSU;
  }

  /**
   *@param order for changing the order
   *Should not be done during a search process,
   *since things will get mixed up.
   */
  public void setOrder(String[] order){
    this.order = order;
  }

  /**
   *@return String[] the current order
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
      System.out.println("@State: Order is empty or null. Optimum cannot be calculated without order.");
    }
    return order.length - 1;
  }

  /** //TODO Do we really need that method??
   *@return boolean if state is valid or not.
   * returns false for invalid state and true for valid state.
   * Invalid States might not be considered during Search
   *@param state which should be checked
   */
  public boolean stateValid(int[] state){
    if(state == null || state.length < 1){
      System.out.println("State is null or empty.");
      return false;
    }
    //we do that in order to reduce time complexity
    //of course we could also just use a for-loop instead
    List<Integer> list = Arrays.stream(state).boxed().collect(Collectors.toList());
    return (!list.contains(-1));
  }
}
