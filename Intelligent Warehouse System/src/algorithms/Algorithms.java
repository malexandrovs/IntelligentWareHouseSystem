public abstract class Algorithms{

  //TODO create subclasses which implement the algorithms

  /**@author Danny
   *@return State, which describes the local optimal result
   *
   */

  public State hillClimbing(State initState){


     int samePSU = 0;

     ArrayList int[] neighbours = state.createNeighbours(initState);

     int stateValue = state.evaluate(initState);



    do{

       if(stateValue == optimum()){
         finalState = initState;
         break;
       }

       State newState = neighbours.getHead();

       if(newState == null){
         finalState = initState;
         break;
       }

       int newValue = state.evaluate(newState);

       if(newValue == optimum()){
         finalState = newState;
         break;
       }

       if(newValue >= stateValue){
         stateValue = newValue;
         initState = newState;
       }

       neighbours.removehead();


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
        ....

        return finalState
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
