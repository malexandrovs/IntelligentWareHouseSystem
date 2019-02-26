package intelligentwarehousesystem.src.algorithms;
import java.util.*;
import intelligentwarehousesystem.src.model.*;

public class Algorithms{

  //TODO create subclasses which implement the algorithms

  /**@author Danny
   *@return State, which describes the local optimal result
   *
   */
  int[] finalState;
  State stateMethods = State.getInstance();



  public int[] hillClimbing(int[] initState){

    finalState = initState;

    for(int iterations = 0; iterations < 100; iterations ++){
       List<int[]> neighbours = new ArrayList<int[]>();
       neighbours = (ArrayList<int[]>) stateMethods.createNeighbours(initState);
       //ceate neighbours of initial State

       int stateValue = stateMethods.evaluate(initState);
       //evaluate inital State

       if(stateValue == stateMethods.optimum()){
         return finalState;
       }
       //check wether current value is already the optimal value

       int[] bestNeighbour = theBestNeighbour(neighbours);
       int newValue = stateMethods.evaluate(bestNeighbour);
       //evaluate best neighbour

       if(newValue == stateMethods.optimum()){
         finalState = bestNeighbour;
         return finalState;
       }
       //check neighbour for optimality

       if(newValue <= stateValue){
         finalState = initState;
         return finalState;
       }
       //replace the initial state with the neigbour, if the evaluation value is better

       initState = bestNeighbour;
       stateValue = newValue;
       finalState = initState;
    }


     return finalState;
  }





  public int[] simulatedAnnealing(int[] initState){

    int temperature = 100;
    //temperature for simulated annealing

    finalState = initState;

    List<int[]> neighbours = new ArrayList<>();
    neighbours= stateMethods.createNeighbours(initState);
     //ceate neighbours of initial State

    int stateValue = stateMethods.evaluate(initState);
     //evaluate inital State

    if(stateValue == stateMethods.optimum()){
       return finalState;
    }
     //check wether current value is already the optimal value

    for(int t = 1; t < 100; t++){

      temperature = temperature -5;

      if(temperature == 0){
        finalState = initState;
        return finalState;
      }
      //if temperature reaches zero before we found the optimal state, we return the current state as the best ome

       int[] newState = neighbours.remove(0);

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

       int deltaValue = newValue - stateValue;
       Random downstep = new Random();
       double probability = downstep.nextDouble();
       //declare necessary variables for simulated annealing downstep


       if(deltaValue > 0){
         stateValue = newValue;
         initState = newState;
       }
       //replace the initial state with the neigbour, if the evaluation value is better
       else if(probability <= Math.exp(deltaValue / temperature)){
         initState = newState;
         stateValue = newValue;
       }
       //if not, do it either way with a specific probability

       //neighbours.removeHead();
       //delete head of the neighbour list to get next neighbour
    }
  }





  public List<int[]> localBeam(int[] initState, int numOfBeams){
    List<int[]> current = new ArrayList<>();
  	current.add(initState);

  	for(int randomState = 1; randomState < numOfBeams; randomState ++){

  		//create new random state;
  		//current.add(random State);
  	}
    //create List of current random states

  	List<int[]> currentNeighbours = new ArrayList<>();
  	List<int[]> neighbours = new ArrayList<>();
  	neighbours = stateMethods.createNeighbours(initState);

  	int[] currentElement;

  	for(int iteration = 0; iteration < 100; iteration ++){
      //we do this search 100 times at the most

  		for(int position = 0; position < numOfBeams; position ++){
  			currentElement = current.get(position);
  			currentNeighbours = stateMethods.createNeighbours(currentElement);
  			neighbours.addAll(currentNeighbours);
  		}
      //we create the list of all neighbours

  		int[] bestElement;
  		int bestValue;
  		List<int[]> bestNeighbours = new ArrayList<>();
  		int[] nextElement;
  		int nextValue;
      int positionToRemove;

  		for(int position = 0; position < numOfBeams; position ++){
  			bestElement = neighbours.get(0);
  			bestValue = stateMethods.evaluate(bestElement);
        positionToRemove = 0;


  			for(int pos = 0; pos < neighbours.size(); pos ++){
  				nextElement = neighbours.get(pos);
          nextValue = stateMethods.evaluate(nextElement);

          if(nextValue > bestValue){
            bestElement = nextElement;
            bestValue = nextValue;
            positionToRemove = pos;
          }
  			}
        //we search for the best Neighbour in our current Neighbour List

        bestNeighbours.add(bestElement);
        neighbours.remove(positionToRemove);
        //we create our List of best neighbours, which is numOfBeams long and delete the Values in this List from our List of all neighbours
  		}

      int allCurrentValues = 0;
      int allBestNeighbourValues = 0;
      int[] element;
      int elementValue;

      for(int position = 1; position < current.size(); position ++){
        element = current.get(position -1);
        elementValue = stateMethods.evaluate(element);
        allCurrentValues = allCurrentValues + elementValue;
      }
      //we compute the sum of all current Values

      for(int position = 1; position < bestNeighbours.size(); position ++){
        element = bestNeighbours.get(position -1);
        elementValue = stateMethods.evaluate(element);
        allBestNeighbourValues = allBestNeighbourValues + elementValue;
      }
      //we compute the sum of all best neighbour Values

      if(allBestNeighbourValues > allCurrentValues){
        current = bestNeighbours;
      }
      //if the best neighbour sum is better than our current sum, we use the best neighbour list as the new current list

      else{
        return current;
      }
      //if the best neighbour sum does not improve our current sum, we return the current list as the best list we have
  	}
    return current;
  }




  public int[] randomRestartHillClimbing(int[] initState, int iterations){

    finalState = initState;

    int[] firstfinalState = finalState;

    boolean firstRun = true;

    for(int run = 1; run < iterations; run++){

      if(firstRun != true){
        //createNewInitial state;
        // initState = ; //new initState ;
      }
      //in every new hill climb we need a new initial State

      List<int[]> neighbours = new ArrayList<>();
      neighbours= stateMethods.createNeighbours(initState);
      //ceate neighbours of initial State

      int stateValue = stateMethods.evaluate(initState);
      //evaluate inital State

      if(stateValue == stateMethods.optimum()){
        finalState = initState;
        return finalState;
      }
      //check wether current value is already the optimal value

     int[] newState = neighbours.remove(0);

     do{


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

          //neighbours.removeHead();
          //delete head of the neighbour list to get next neighbour
        }

        if(stateMethods.evaluate(firstfinalState) >= stateMethods.evaluate(finalState)){
          finalState = firstfinalState;
        }
        //evaluate the final state of every hill climb and use the best one as the final state

        newState = neighbours.remove(0);

      } while(newState != null);
      firstRun = false;
    }

    return finalState;
 }





  public int[] firstChioceHillClimbing(int[] initState){

    finalState = initState;

    List<int[]> neighbours = new ArrayList<>();
    neighbours= stateMethods.createNeighbours(initState);
    //ceate neighbours of initial State

    int stateValue = stateMethods.evaluate(initState);
    //evaluate inital State

    if(stateValue == stateMethods.optimum()){
      return finalState;
    }
    //check wether current value is already the optimal value

    int[] newState = neighbours.remove(0);

   do{

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

      //neighbours.removeHead();
      //delete head of the neighbour list to get next neighbour

      newState = neighbours.remove(0);
    } while(newState != null);

    return finalState;
 }


 public int[] theBestNeighbour(List<int[]> neighbours){
   int[] currentBest = neighbours.get(0);
   int currentBestValue = stateMethods.evaluate(currentBest);
   int newValue = 0;
   int[] newState;

   for(int position = 1; position < neighbours.size(); position ++){
     newState = neighbours.get(position);
     if(currentBestValue < stateMethods.evaluate(newState)){
       currentBest = newState;
       currentBestValue = newValue;
     }
   }
   return currentBest;
 }
 //we use this to compute the best neighbour for Hill Climbing




}
