package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Algorithms;
import model.StateHandler;
import model.InvalidOrderException;

/**
 * Provides representation of the Warehouse and manages the Order search.
 * 
 * @author Michael Alexandrovsky (malexandrovs@uos.de)
 */

public class WareHouseModel {

	// private String order;
	private Algorithms algs;
	private HashMap<String, ArrayList<Integer>> wareHouse;
	private HashMap<Integer, String> psus;
	private int [] optimum;
	private StateHandler stateHandler;


	public WareHouseModel(){
		wareHouse = new HashMap<String, ArrayList<Integer>>();
		psus = new HashMap<Integer,String>();
	}

	/**
	 * Method to build the Warehouse representation. It receives a File-Object with the Warehouse.txt and builds
	 * a HashMap with the Items as keys and the ArrayLists of PSUs that contain the items as values.
	 *@return boolean true if setWarehouse was successful, false otherwise.
	 *@param wareHouseTxt a file object holding the .txt with the warehouse information.
	 */
	public boolean initWarehouse(File warehouseTxt){

		boolean successful = true;
		int psu = 0;

		try (
			FileReader fr = new FileReader(warehouseTxt);
			BufferedReader br = new BufferedReader(fr);
		){
			System.out.println("done");
			String[] items = br.readLine().split(" ");
			for (String item : items) {
				wareHouse.put(item, new ArrayList<Integer>());
			}

			// skip empty line
			br.readLine();

			String line = br.readLine();
			// int psu = 0;

			while (line !=  null) {
				psus.put(psu, line);

				for (String item : items) {
					if (line.contains(item)) {
						wareHouse.get(item).add(psu);
					}
				}
				line = br.readLine();
				psu++;
			}
			
			stateHandler = new StateHandler(wareHouse,psus);
			algs = new Algorithms(stateHandler);

		} catch (IOException e) {
			successful = false;
		}
		


		return successful;
 }

 /**
  * extracts relevant order information from the order.txt File and tests if the order is valid.
  * @param orderTxt a File object holding the .txt with the order information.
  * @return returns true if the order initialization was successful, false otherwise.
  * @throws InvalodOrderException thrown if some items in the order are not stored in the warehouse.
  */
 	public boolean initOrder(File orderTxt) throws InvalidOrderException{
		 boolean successful = true;
		 String[] order = null;
		 try(
			 FileReader fr = new FileReader(orderTxt);
			 BufferedReader br = new BufferedReader(fr);
		 ){
			 order = br.readLine().split(" ");
		 } catch (IOException e){
			successful = false;
		 } 
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
		return successful;
	 }

 	/**
	  * Method to coordinate the actual search process.
	  * @param alg is the code for the search algorithm.
	  * @param param is the optional parameter that depends on the search algorithm
	  */
	public void startSearch(int alg, int param) {
		optimum = null;

		switch (alg){
			case 0: optimum = algs.hillClimbing();
				break;
			case 1: optimum = algs.simulatedAnnealing();
				break;
			case 2: optimum = algs.localBeam(param);
				break;
			case 3: optimum = algs.randomRestartHillClimbing(param);
				break;
			case 4: optimum = algs.firstChoiceHillClimbing();
			//default case missing -> System.err.println("@WareHouseModel: No valid Algorithm was given.")
		}
	}

	/**
	 * Returns the result string, containing relevant information about the search: The number of PSUs that are used in the proposed solution,
	 * the individual PSUs and the items they contain.
	 *@return returns amswer String for the user
  	 */
	public String getResult() {
		int[] usedPSUs = stateHandler.showUsedPSUs(optimum);

		StringBuilder result = new StringBuilder();

		result.append("This solution uses ");
		result.append(usedPSUs.length);
		result.append(" PSUs. \nThe used PSUs are: \n \n \n");

		


		for (int psu : usedPSUs) {

			result.append(psu);
			result.append(" - with the following items: ");
			result.append(psus.get(psu));
			result.append("\n \n");
		
		}

		return result.toString();
	}

	/**
	 * Returns a HashMap<String,int[]>, where the Key is the name of the item as a String and
	 * the value is an int-array with the identifieres of the PSUs that contain the item.
	 * @return Returns a HashMap<String,int[]> as a representation of the warehouse.
	 */
	public HashMap getWarehouse() {
		return wareHouse;
	}

	public StateHandler getStateHandler() {
		return stateHandler;
	}

	public Algorithms getAlgorithms() {
		return algs;
	}

}
