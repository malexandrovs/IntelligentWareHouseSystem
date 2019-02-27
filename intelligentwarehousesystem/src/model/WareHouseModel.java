package model;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Algorithms;
import model.StateHandler;
import model.InvalidOrderException;

public class WareHouseModel {

	// private String order;
	private Algorithms algs;
	private HashMap<String, ArrayList<Integer>> wareHouse;
	private HashMap<Integer, String[]> psus;
	private int [] result;
	private StateHandler stateHandler;

	/**
	 * Constructor of WareHouseModel
	 */
	public WareHouseModel(){
		wareHouse = new HashMap<String, ArrayList<Integer>>();
		psus = new HashMap<Integer,String[]>();
	}

	/**
	 * Method to build the Warehouse representation. It receives a File-Object with the Warehouse.txt and builds
	 * a HashMap with the Items as keys and the ArrayLists of PSUs that contain the items as values.
	 *@return boolean true if setWarehouse was successful, false otherwise.
	 *@param MISSING
	 */
	public boolean setWarehouse(File warehouseTxt){

		boolean successful = true;
		int psu = 0;

		try (
			FileReader fr = new FileReader(warehouseTxt);
			BufferedReader br = new BufferedReader(fr);
		){
			System.out.println("done");
			String[] items = br.readLine().split(" ");
			psus.put(psu, items);
			for (String item : items) {
				wareHouse.put(item, new ArrayList<Integer>());
			}

			// skip empty line
			br.readLine();

			String line = br.readLine();
			// int psu = 0;

			while (line !=  null) {
				for (String item : items) {
					if (line.contains(item)) {
						wareHouse.get(item).add(psu);
					}
				}
				line = br.readLine();
				psu++;
			}
		} catch (IOException e) {
			System.out.println(psu);
			successful = false;
		}
		//j: Changed Class -> no singleton anymore, I changed your call accordingly
		stateHandler = new StateHandler(wareHouse);
		algs = new Algorithms(stateHandler);

		return successful;
 }

 	public void initOrder(String[] order) throws InvalidOrderException{
		stateHandler.setOrder(order);
		int[] aState = stateHandler.generateInitialState();
		if(!stateHandler.stateValid(aState)){
			String unknownItems = "";
			for (int i = 0; i < aState.length; i++) {
				if (aState[i] == -1) {
					unknownItems = unknownItems + order[i] + " ";
				}
			}
			throw new InvalidOrderException(unknownItems);
		}
	 }

 	/**
	  * Method to coordinate the actual search process.
	  * @param alg is the code for the search algorithm.
	  * @param param is the optional parameter that depends on the search algorithm
	  * @param order
	  */
	public void startSearch(int alg, int param) {

		// stateHandler.setOrder(order);
		// int[] initialState = stateHandler.generateInitialState();
		// boolean orderValid = stateHandler.stateValid(initialState);
		// if(!orderValid){ tell the user -> he should change the order
	  	// and dont do any search (? or we remove the items which are not valid?)}
		switch (alg){
			case 0: result = algs.hillClimbing();
				break;
			case 1: result = algs.simulatedAnnealing();
				break;
			case 2: result = algs.localBeam(param);
				break;
			case 3: result = algs.randomRestartHillClimbing(param);
				break;
			case 4: result = algs.firstChoiceHillClimbing();
			//default case missing -> System.err.println("@WareHouseModel: No valid Algorithm was given.")
		}
	}

	//j: not done yet?
	/**get PSU number
	 *@return String with elements
  	 */
	public int[] getResult() {
		//anzahl der PSUs used
		//all used Psus identifiers
		//
		return result;
	}

}
