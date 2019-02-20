public abstract class Algorithms{

  //TODO create subclasses which implement the algorithms

  /**@author Danny
   *@return State, which describes the local optimal result
   *
   */

   State finalState;

  public State hillClimbing(State initState){

     ArrayList int[] neighbours = state.createNeighbours(initState);
     //ceate neighbours of initial State

     int stateValue = state.evaluate(initState);
     //evaluate inital State

     if(stateValue == state.optimum()){
       finalState = initState;
       return finalState;
     }
     //check wether current value is already the optimal value

    do{

       State newState = neighbours.getHead();

       if(newState == null){
         finalState = initState;
         return finalState;
       }
       //checking if the neighbour list is empty

       int newValue = state.evaluate(newState);
       //evaluate next neighbour

       if(newValue == state.optimum()){
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





  public State simulatedAnnealing(State initState, double initTemp){
     ....

     return finalState
  }

  public State localBeam(State initState, int numOfPos){
     ....

     return finalState
  }

  public State randomRestartHillClimbing(State initState){
        ....

        return finalState
  }


  public State firstChioceHillClimbing(State initState){

    ArrayList int[] neighbours = state.createNeighbours(initState);
    //ceate neighbours of initial State

    int stateValue = state.evaluate(initState);
    //evaluate inital State

    if(stateValue == state.optimum()){
      finalState = initState;
      return finalState;
    }
    //check wether current value is already the optimal value

   do{

      State newState = neighbours.getHead();

      if(newState == null){
        finalState = initState;
        return finalState;
      }
      //checking if the neighbour list is empty, if yes, the current inital state is returned as best state

      int newValue = state.evaluate(newState);
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
