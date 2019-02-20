public abstract class Algorithms{

  //TODO create subclasses which implement the algorithms

  /**@author Danny
   *@return State, which describes the local optimal result
   *
   */

   int[] finalState;
    State stateMethods = State.getInstance();

  public int[] hillClimbing(int[] initState){

    finalState = initState;

     ArrayList int[] neighbours = stateMethods.createNeighbours(initState);
     //ceate neighbours of initial State

     int stateValue = stateMethods.evaluate(initState);
     //evaluate inital State

     if(stateValue == stateMethods.optimum()){
       return finalState;
     }
     //check wether current value is already the optimal value

    do{

       int[] newState = neighbours.getHead();

       if(newState == null){
         finalState = initState;
         return finalState;
       }
       //checking if the neighbour list is empty

       int newValue = stateMethods.evaluate(newState);
       //evaluate next neighbour

       if(newValue == stateMethods.optimum()){
         finalState = newState;
         return finalState;
       }
       //check neighbour for optimality

       if(newValue >= stateValue){
         stateValue = newValue;
         initState = newState;
       }
       //replace the initial state with the neigbour, if the evaluation value is better

       neighbours.removehead();
       //delete head of the neighbour list to get next neighbour


     } while(newState != null);

     return finalState;
  }





  public int[] simulatedAnnealing(int[] initState, double initTemp){
     ....

     return finalState
  }

  public int[] localBeam(int[] initState, int numOfPos){
     ....

     return finalState
  }

  public int[] randomRestartHillClimbing(int[] initState, int iterations){

    finalState = initState;

    int[] firstfinalState = finalState;

    boolean firstRun = true;

    for(int run = 1; run < iterations; run++){

      if(firstRun != true){
        //createNewInitial state;
        initState = //new initState ;
      }
      //in every new hill climb we need a new initial State

      ArrayList int[] neighbours = stateMethods.createNeighbours(initState);
      //ceate neighbours of initial State

      int stateValue = stateMethods.evaluate(initState);
      //evaluate inital State

      if(stateValue == stateMethods.optimum()){
        finalState = initState;
        return finalState;
      }
      //check wether current value is already the optimal value

     do{
        int[] newState = neighbours.getHead();

        if(newState == null){
          firstfinalState = initState;
        }
        //checking if the neighbour list is empty

        else{
          int newValue = stateMethods.evaluate(newState);
          //evaluate next neighbour

          if(newValue == stateMethods.optimum()){
            finalState = newState;
            return finalState;
          }
          //check neighbour for optimality

          if(newValue >= stateValue){
            stateValue = newValue;
            initState = newState;
          }
          //replace the initial state with the neigbour, if the evaluation value is better

          neighbours.removehead();
          //delete head of the neighbour list to get next neighbour
        }

        if(stateMethods.evaluate(firstfinalState) >= stateMethods.evaluate(finalState)){
          finalState = firstfinalState;
        }
        //evaluate the final state of every hill climb and use the best one as the final state

      } while(newState != null);
      firstRun = false;
    }

    return finalState;
 }


  public int[] firstChioceHillClimbing(int[] initState){

    finalState = initState;

    ArrayList int[] neighbours = stateMethods.createNeighbours(initState);
    //ceate neighbours of initial State

    int stateValue = stateMethods.evaluate(initState);
    //evaluate inital State

    if(stateValue == stateMethods.optimum()){
      return finalState;
    }
    //check wether current value is already the optimal value

   do{

      int[] newState = neighbours.getHead();

      if(newState == null){
        finalState = initState;
        return finalState;
      }
      //checking if the neighbour list is empty, if yes, the current inital state is returned as best state

      int newValue = stateMethods.evaluate(newState);
      //evaluate next neighbour

      if(newValue >= stateValue){
        finalState = newState;
        return finalState;
      }
      //return the neighbour as the best state, if it is better than the current state

      neighbours.removehead();
      //delete head of the neighbour list to get next neighbour


    } while(newState != null);

    return finalState;
 }



  public Object getHead(){

    if(head == null){
      return null;
    }
    else{
      return head.getObject();
    }
  }

  public Object void removeHead(){

    Element first = head;

    head = e.getNext();
    e.setNext(null);

    if(tail == e){
      tail= null;
    }

  }

}
