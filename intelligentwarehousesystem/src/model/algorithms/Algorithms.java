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
    int[] initState = stateMethods.generateInitialState();

    this.finalState = initState;

    int stateValue = stateMethods.evaluate(finalState);

    if(stateValue == stateMethods.optimum()){
      return this.finalState;
    }

    for(int iteration = 0; iteration < 100; iteration ++){
       List<int[]> neighbours = stateMethods.createNeighbours(finalState);

       int[] bestNeighbour = theBestNeighbour(neighbours);
       int newValue = stateMethods.evaluate(bestNeighbour);

       if(newValue == stateMethods.optimum()){
         return bestNeighbour;
       }

       if(newValue <= stateValue){
         return initState;
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

    int[] initState = stateMethods.generateInitialState();
    int temperature = 100;


    this.finalState = initState;

    List<int[]> neighbours = stateMethods.createNeighbours(initState);

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

    int[] initState = stateMethods.generateInitialState();
    List<int[]> current = new ArrayList<>();
  	current.add(initState);

  	for(int randomState = 1; randomState < numOfBeams; randomState ++){

  		int[] newRandomState = stateMethods.generateRandomState();

  		current.add(newRandomState);
  	}


  	List<int[]> currentNeighbours = new ArrayList<>();
  	List<int[]> neighbours = stateMethods.createNeighbours(initState);

  	int[] currentElement;

  	for(int iteration = 0; iteration < 100; iteration ++){


  		for(int position = 0; position < numOfBeams; position ++){
  			currentElement = current.get(position);
  			currentNeighbours = stateMethods.createNeighbours(currentElement);
  			neighbours.addAll(currentNeighbours);
  		}


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


        bestNeighbours.add(bestElement);
        neighbours.remove(positionToRemove);

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


      for(int position = 1; position < bestNeighbours.size(); position ++){
        element = bestNeighbours.get(position -1);
        elementValue = stateMethods.evaluate(element);
        allBestNeighbourValues = allBestNeighbourValues + elementValue;
      }


      if(allBestNeighbourValues > allCurrentValues){
        current = bestNeighbours;
      }

      else{
        return theBestNeighbour(current);
      }
  	}
    return theBestNeighbour(current);
  }


  /**
  * This method is the implemented random restart hill climbing algorithm
  * @param iterations; this value is given by the user, it sts the number of independent hill climbing searches we use
  * @return we return the state which is determined the best by the algorithm
  */

  public int[] randomRestartHillClimbing(int iterations){

    int[] initState = stateMethods.generateInitialState();
    int[] firstfinalState = initState;
    int finalValue = 0;
    int stateValue = 0;

    finalState = initState;

    boolean firstRun = true;

    for(int run = 1; run < iterations; run++){

      if(firstRun != true){
        initState = stateMethods.generateRandomState();

      }


      firstfinalState = initState;

      for(int iteration = 0; iteration < 100; iteration ++){
         List<int[]> neighbours = new ArrayList<int[]>();
         neighbours = (ArrayList<int[]>) stateMethods.createNeighbours(initState);


         stateValue = stateMethods.evaluate(initState);


         if(stateValue == stateMethods.optimum()){
           return firstfinalState;
         }


         int[] bestNeighbour = theBestNeighbour(neighbours);
         int newValue = stateMethods.evaluate(bestNeighbour);


         if(newValue == stateMethods.optimum()){
           firstfinalState = bestNeighbour;
           return finalState;
         }


         if(newValue <= stateValue){
           firstfinalState = initState;
           return finalState;
         }


         initState = bestNeighbour;
         stateValue = newValue;
         firstfinalState = initState;

      }
      finalValue = stateMethods.evaluate(finalState);
      if(stateValue > finalValue){
        finalState = firstfinalState;
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
    int[] initState = stateMethods.generateInitialState();
    finalState = initState;

    for(int iteration = 0; iteration < 100; iteration ++){
      List<int[]> neighbours = new ArrayList<>();
      neighbours= stateMethods.createNeighbours(initState);

      int stateValue = stateMethods.evaluate(initState);

      if(stateValue == stateMethods.optimum()){
        return finalState;
      }

      int[] newState = neighbours.remove(0);

      do{
        if(newState == null){
          finalState = initState;
          return finalState;
        }

        int newValue = stateMethods.evaluate(newState);

        if(newValue >= stateValue){
          initState = newState;
          stateValue = newValue;
          finalState = initState;
        }
        newState = neighbours.remove(0);
      }while(newState != null);
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
     if(currentBestValue < stateMethods.evaluate(newState)){
       currentBest = newState;
       currentBestValue = newValue;
     }
   }
   return currentBest;
 }
}
