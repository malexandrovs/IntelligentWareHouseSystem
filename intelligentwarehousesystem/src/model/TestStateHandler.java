//package intelligentwarehousesystem.src.model;
import java.util.*;

public class TestStateHandler{

  public static void main(String[] args) {


    HashMap<Integer, ArrayList<String>> warehouseState = new HashMap<Integer, ArrayList<String>>() {{
      put(0, new ArrayList<String>(Arrays.asList("0a", "0b", "0c")));
      put(1, new ArrayList<String>(Arrays.asList("8a", "8b", "1c")));
      put(2, new ArrayList<String>(Arrays.asList("2a", "2b", "2c")));
      put(3, new ArrayList<String>(Arrays.asList("3a", "3b", "3c")));
      put(4, new ArrayList<String>(Arrays.asList("4a", "4b", "4c")));
      put(5, new ArrayList<String>(Arrays.asList("5a", "5b", "5c")));
      put(6, new ArrayList<String>(Arrays.asList("6a", "6b", "6c")));
      put(7, new ArrayList<String>(Arrays.asList("7a", "7b", "7c")));
      put(8, new ArrayList<String>(Arrays.asList("8a", "8b", "8c")));
    }};

    HashMap<String, ArrayList<Integer>> warehouse = new HashMap<String, ArrayList<Integer>>() {{
      put("0a", new ArrayList<Integer>(Arrays.asList(0)));
      put("0b", new ArrayList<Integer>(Arrays.asList(0)));
      put("0c", new ArrayList<Integer>(Arrays.asList(0)));
      put("1c", new ArrayList<Integer>(Arrays.asList(1)));
      put("2a", new ArrayList<Integer>(Arrays.asList(2)));
      put("2b", new ArrayList<Integer>(Arrays.asList(2)));
      put("2c", new ArrayList<Integer>(Arrays.asList(2)));
      put("3a", new ArrayList<Integer>(Arrays.asList(3)));
      put("3b", new ArrayList<Integer>(Arrays.asList(3)));
      put("3c", new ArrayList<Integer>(Arrays.asList(3)));
      put("4a", new ArrayList<Integer>(Arrays.asList(4)));
      put("4b", new ArrayList<Integer>(Arrays.asList(4)));
      put("4c", new ArrayList<Integer>(Arrays.asList(4)));
      put("5a", new ArrayList<Integer>(Arrays.asList(5)));
      put("5b", new ArrayList<Integer>(Arrays.asList(5)));
      put("5c", new ArrayList<Integer>(Arrays.asList(5)));
      put("6a", new ArrayList<Integer>(Arrays.asList(6)));
      put("6b", new ArrayList<Integer>(Arrays.asList(6)));
      put("6c", new ArrayList<Integer>(Arrays.asList(6)));
      put("7a", new ArrayList<Integer>(Arrays.asList(7)));
      put("7b", new ArrayList<Integer>(Arrays.asList(7)));
      put("7c", new ArrayList<Integer>(Arrays.asList(7)));
      put("8a", new ArrayList<Integer>(Arrays.asList(1, 8)));
      put("8b", new ArrayList<Integer>(Arrays.asList(1, 8)));
      put("8c", new ArrayList<Integer>(Arrays.asList(8)));
    }};
    StateHandler state = StateHandler.getInstance(warehouse);

    String[] order1 = new String[]{"3a", "4b", "5c"};
    String[] order2 = new String[]{"8a", "8b", "8c"};
    String[] order3 = new String[]{"bla", "0a", "blubb"};
    String[] order4 = new String[]{};
    String[] order5 = null;
    
    //testing numberOfUsedPSUs
    System.out.println("Testing method numberOfUsedPSUs...");
    int[] stateExample = {1, 1, 1, 0, 2, 3, 2};
    compareInt(state.numOfUsedPSUs(stateExample), 4);

    //testing createNeighbours
    System.out.println("Testing method createNeighbours...");

    state.setOrder(order1);
    int[] nstate1 = {0, 0, 0};
    ArrayList<int[]> n1 = state.createNeighbours(nstate1, true);
    System.out.println("Calculated Neighbours: ");
    for( int[] ars : n1){
      System.out.println(Arrays.toString(ars));
    }
    System.out.println();
    ArrayList<int[]> expected = new ArrayList<int[]>();
    int[] ar1 = {3, 0, 0};
    int[] ar2 = {0, 4, 0};
    int[] ar3 = {0, 0, 5};
    expected.add(ar1);
    expected.add(ar2);
    expected.add(ar3);
    for(int i = 0; i < n1.size(); i++) {
      compareInts(n1.get(i), expected.get(i));
    }

    state.setOrder(order3);
    int[] nstate2 = {0, 0, 0};
    ArrayList<int[]> n2 = state.createNeighbours(nstate2, true);
    System.out.println("Calculated Neighbours: ");
    for( int[] ars : n2){
      System.out.println(Arrays.toString(ars));
    }
    System.out.println();
    ArrayList<int[]> expected2 = new ArrayList<int[]>();
    int[] ar2a = {0, -1, 0};
    expected2.add(ar2a);

    for(int i = 0; i < n2.size(); i++) {
      compareInts(n2.get(i), expected2.get(i));
    }

    //testing generateInitialState
    System.out.println("Testing method generateInitialState...");
    state.setOrder(order1);
    int[] initState1 = state.generateInitialState();
    System.out.println(Arrays.toString(initState1));
    int[] initResult1 = {3, 4, 5};
    compareInts(initState1, initResult1);

    state.setOrder(order2);
    int[] initState2 = state.generateInitialState();
    System.out.println(Arrays.toString(initState2));
    int[] initResult2 = {1, 1, 8};
    compareInts(initState2, initResult2);

    state.setOrder(order3);
    int[] initState3 = state.generateInitialState();
    System.out.println(Arrays.toString(initState3));
    int[] initResult3 = {-1, 0, -1};
    compareInts(initState3, initResult3);

    state.setOrder(order4);
    int[] initState4 = state.generateInitialState();
    System.out.println(Arrays.toString(initState4));

    state.setOrder(order5);
    int[] initState5 = state.generateInitialState();
    System.out.println(Arrays.toString(initState5));

    //testing the evaluating function
    System.out.println("Testing the method evaluate...");

    int[] state1 = {1, 1, 1, 1};
    System.out.println("State 1: " + Arrays.toString(state1));
    int result1 = state.evaluate(state1);
    compareInt(result1, 3);

    int[] state2 = {1, 2, 3, 4, 5, 6};
    System.out.println("State 2: " + Arrays.toString(state2));
    int result2 = state.evaluate(state2);
    compareInt(result2, 0);

    int[] state3 = {1, 2, 3, -1};
    System.out.println("State 3: " + Arrays.toString(state3));
    int result3 = state.evaluate(state3);
    compareInt(result3, -1);

    int[] state4 = {};
    System.out.println("State 4: " + Arrays.toString(state4));
    int result4 = state.evaluate(state4);
    compareInt(result4, -2);

    //one complete test
    System.out.println("One complete Test...");

    state.setOrder(order2);
    int[] initialState = state.generateInitialState();
    ArrayList<int[]> neighbours = state.createNeighbours(initialState, true);
    int[] result = new int[order2.length];
    int[] expectedResult = {1, 1, -1};
    for(int i = 0; i < neighbours.size(); i++){
        int[] nstate = neighbours.get(i);
        result[i] = state.evaluate(nstate);
        System.out.println(state.stateValid(nstate));
    }
    compareInts(result, expectedResult);

    System.out.println("... finished testing.");

  }

  private static void compareInt(int result, int expected){
    if(result != expected){
      System.out.println("Not equal: " + result + " and " + expected);
    } else {
      System.out.println("Equal");
    }
  }

  private static void compareInts(int[] result, int[] expected){
    if(Arrays.equals(result, expected)){
      System.out.println("Equal");
    } else {
      System.out.println("Not Equal: " + Arrays.toString(result) + " and " +
      Arrays.toString(expected));
    }
  }
}
