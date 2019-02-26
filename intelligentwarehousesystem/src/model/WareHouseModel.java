package intelligentwarehousesystem.src.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.SearchAlgorithm;

public class WareHouseModel {

	private String order;
	private SearchAlgorithm alg;
	private HashMap<String, ArrayList<Integer>> wareHouse = new HashMap<String, ArrayList<Integer>>();
	private String result;

	public WareHouseModel(){
		//leerer Konstruktor, damit Algorithmen diese Klasse
		//erstellen koennen
	}
	/**
	 *@return boolean true if setWarehouse was successful
	 * false if setWarehouse was not successful
	 */
	public boolean setWarehouse(File warehouseTxt){
	//dafuer sorgen, dass aus dem txt Tile eine
	//HashMap wird mit zeilen als key uebersetzt und
	//Stringarrays als value
	//put that in a try-catch thing!

	try (
		FileReader fr = new FileReader(warehousetxt);
		BufferedReader br = new BufferedReader(fr);
	){
		String[] itmes = br.readLine().split(" ");
		for (String item : items) {
			wareHouse.put(item, new ArrayList<Integer>());
		}

		//skip empty line
		br.readLine();

		String line = br.readLine();
		int psu = 1;

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
		//TODO: handle exception
	}
 }

	public void startSearch(SearchAlgorithm alg, String[] order) {
		result = alg.search(order, wareHouse);

		//ruft generateState
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
