package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.Algorithms;

public class WareHouseModel {

	// private String order;
	private Algorithms algs;
	private HashMap<String, ArrayList<Integer>> wareHouse;
	private String result;
	private StateHandler stateHandler;

	public WareHouseModel(){
		wareHouse = new HashMap<String, ArrayList<Integer>>();
	}

	/**
	 * Method to build the Warehouse representation. It receives a File-Object with the Warehouse.txt and builds
	 * a HashMap with the Items as keys and the ArrayLists of PSUs that contain the items as values.
	 *@return boolean true if setWarehouse was successful, false otherwise.
	 */
	public boolean setWarehouse(File warehouseTxt){

		boolean successful = true;
	

		try (
			FileReader fr = new FileReader(warehouseTxt);
			BufferedReader br = new BufferedReader(fr);
		){
			String[] itmes = br.readLine().split(" ");
			for (String item : items) {
				wareHouse.put(item, new ArrayList<Integer>());
			}

			// skip empty line
			br.readLine();

			String line = br.readLine();
			int psu = 0;

			while (line !=  null) {
				for (String item : items) {
					if (line.contains(item)) {
						wareHouse.get(item).add(psu);
					}
				}
				line = br.readLine();
				i++;	
			}
		} catch (IOException e) {
			successful = false;
		}
		stateHandler = StateHandler.getInstance(wareHouse);
		alg = new Algorithms(stateHandler);

		return successful;
 }

 	/**
	  * Method to coordinate the actual search process.
	  * @param alg is the code for the search algorithm.
	  * @param param is the optional parameter that depends on the search algorithm
	  * @param order
	  */
	public void startSearch(int alg, int param, String[] order) {
		
		stateHandler.setOrder(order);
		int[] initialState = stateHandler.getInitialState();
		switch (alg){
			case 0: result = algs.hillClimbing(initialState);
				break;
			case 1: result = algs.simmulatedAnnealing(initialState);
				break;
			case 2: result = algs.localBeam(initialState, param);
				break;
			case 3: result = algs.randomRestartHillClimbin(initialState, param);
				break;
			case 4: result = firstChoiceHillClimbing(initialState);
		}
	}


	//get PSU number
	//returns String with elements

	public String getResult() {
		//anzahl der PSUs used
		//all used Psus identifiers
		//
		return result;
	}

}
