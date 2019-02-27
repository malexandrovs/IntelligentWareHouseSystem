package model.algorithms;
import java.util.*;
import model.StateHandler;

/**
* in this class, the different search algorithms are used, which return the best state or list of states they found
*/

public class Algorithms{


  int[] finalState;
  StateHandler stateMethods;


  public Algorithms(StateHandler stateHandler){
    this.stateMethods = stateHandler;
  }


  /**
  * This method is the implemented classical hill climb algorithm
  * @param initialState; the state in which every item is listed with the number of the first PSU it appears in
  * @return we return the best state our search algorithm has found
  */
  public int[] hillClimbing(){
    this.finalState = stateMethods.generateRandomState();

    int stateValue = stateMethods.evaluate(finalState);

    if(stateValue == stateMethods.optimum()){
      return this.finalState;
    }

    for(int iteration = 0; iteration < 100; iteration ++){
       List<int[]> neighbours = stateMethods.createNeighbours(finalState, false);

       int[] bestNeighbour = theBestNeighbour(neighbours);
       int newValue = stateMethods.evaluate(bestNeighbour);

       if(newValue == stateMethods.optimum()){
         return bestNeighbour;
       }

       if(newValue <= stateValue){
         return finalState;
       }

       this.finalState = bestNeighbour;
    }
     return this.finalState;
  }



  /**
  * this method is the implemented simulated Annealing algorithm
  * @param temperature; this parameter is a measure for the probability of the algorithm to perform downhill steps during the search
  * @return we return the state, whcih the algorithm found as the best option for our order
  */

  public int[] simulatedAnnealing(){

    this.finalState = stateMethods.generateInitialState();
    int temperature = 100;

    List<int[]> neighbours = stateMethods.createNeighbours(finalState, true);

    int stateValue = stateMethods.evaluate(finalState);


    if(stateValue == stateMethods.optimum()){
       return this.finalState;
    }

    for(int i = temperature; i > 0; i = temperature - 5){

      for(int j = 0; j < neighbours.size(); j++){
        int[] newState = neighbours.get(j);

        if(newState == null){
          return finalState;
        }

        int newValue = stateMethods.evaluate(newState);

        if(newValue == stateMethods.optimum()){
          return newState;
        }

        int deltaValue = newValue - stateValue;
        Random downstep = new Random();
        double probability = downstep.nextDouble();

        if(deltaValue > 0){
          stateValue = newValue;
          this.finalState = newState;
        }
        else if(probability <= Math.exp(deltaValue / temperature)){
          this.finalState = newState;
          stateValue = newValue;
        }
      }
    }
    return finalState;
  }


  /**
  * This method is the implemented local Beam Search Algorithm
  * @param numOfBeams; this is the lenght of the list of states we want to get in the end
  * @return we return the list of states which was determined the best as a whole by the algorithm
  */

  public int[] localBeam(int numOfBeams){

    List<int[]> current = new ArrayList<int[]>();


  	for(int randomState = 0; randomState < numOfBeams; randomState ++){

  		int[] newRandomState = stateMethods.generateRandomState();

  		current.add(newRandomState);
  	}


  	List<int[]> currentNeighbours = new ArrayList<int[]>();
  	List<int[]> allNeighbours = new ArrayList<int[]>();

  	int[] currentElement;

  	for(int iteration = 0; iteration < 100; iteration ++){


  		for(int position = 0; position < numOfBeams; position ++){
  			currentElement = current.get(position);
  			currentNeighbours = stateMethods.createNeighbours(currentElement,false);
  			allNeighbours.addAll(currentNeighbours);
  		}


  		int[] bestElement;
  		int bestValue;
  		List<int[]> bestNeighbours = new ArrayList<int[]>();
  		int[] nextElement;
  		int nextValue;
      int positionToRemove;

  		for(int position = 0; position < numOfBeams; position ++){
  			bestElement = allNeighbours.get(0);
  			bestValue = stateMethods.evaluate(bestElement);
        positionToRemove = 0;


  			for(int pos = 0; pos < allNeighbours.size(); pos ++){
  				nextElement = allNeighbours.get(pos);
          nextValue = stateMethods.evaluate(nextElement);

          if(nextValue > bestValue){
            bestElement = nextElement;
            bestValue = nextValue;
            positionToRemove = pos;
          }
  			}


        bestNeighbours.add(bestElement);
        allNeighbours.remove(positionToRemove);

  		}

      allNeighbours.clear();

      int allCurrentValues = 0;
      int allBestNeighbourValues = 0;
      int[] element;
      int elementValue;

      for(int position = 0; position < current.size(); position ++){
        element = current.get(position);
        elementValue = stateMethods.evaluate(element);
        allCurrentValues = allCurrentValues + elementValue;
      }


      for(int position = 0; position < bestNeighbours.size(); position ++){
        element = bestNeighbours.get(position);
        elementValue = stateMethods.evaluate(element);
        allBestNeighbourValues = allBestNeighbourValues + elementValue;
      }


      if(allBestNeighbourValues > allCurrentValues){
        current = bestNeighbours;
      }

      else{
        return theBestNeighbour(current);
      }

      bestNeighbours.clear();
  	}
    return theBestNeighbour(current);
  }


  /**
  * This method is the implemented random restart hill climbing algorithm
  * @param iterations; this value is given by the user, it sts the number of independent hill climbing searches we use
  * @return we return the state which is determined the best by the algorithm
  */

  public int[] randomRestartHillClimbing(int iterations){

    this.finalState = stateMethods.generateRandomState();
    int[] newState;
    int finalValue = 0;
    int newValue = 0;

    for(int run = 0; run < iterations; run++){

      newState = hillClimbing();

      finalValue = stateMethods.evaluate(finalState);
      newValue = stateMethods.evaluate(newState);
      if(newValue > finalValue){
        finalState = newState;
      }
    }

    return finalState;
 }



 /**
 * this method is the implemented first choice hill climbing algorithm
 * @param everyone; this method uses the standard hill climbing parameters
 * @return we return the state which was determined the best by the algorithm
 */

  public int[] firstChoiceHillClimbing(){
    this.finalState = stateMethods.generateRandomState();

    for(int iteration = 0; iteration < 100; iteration ++){
      List<int[]> neighbours = new ArrayList<int[]>();
      neighbours= stateMethods.createNeighbours(finalState, false);

      int stateValue = stateMethods.evaluate(finalState);

      if(stateValue == stateMethods.optimum()){
        return finalState;
      }

      int[] newState = neighbours.remove(0);
      boolean bestNeighbourFound = false;
      do{
        if(newState == null){
          return this.finalState;
        }

        int newValue = stateMethods.evaluate(newState);

        if(newValue >= stateValue){
          this.finalState = newState;
          bestNeighbourFound = true;
        }
        newState = neighbours.remove(0);
      }while(!bestNeighbourFound||newState != null);
    }

    return finalState;
 }


 public int[] theBestNeighbour(List<int[]> neighbours){
   int[] currentBest = neighbours.get(0);
   int currentBestValue = stateMethods.evaluate(currentBest);
   int newValue = 0;
   int[] newState;

   for(int position = 1; position < neighbours.size(); position ++){
     newState = neighbours.get(position);
     newValue = stateMethods.evaluate(newState);
     if(currentBestValue < newValue){
       currentBest = newState;
       currentBestValue = newValue;
     }
   }
   return currentBest;
 }
}
