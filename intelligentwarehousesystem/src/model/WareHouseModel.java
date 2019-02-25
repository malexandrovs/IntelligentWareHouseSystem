package intelligentwarehousesystem.src.model;

import algorithms.SearchAlgorithm;

public class WareHouseModel {

	private String order;
	private SearchAlgorithm alg;
	private HashMap<Integer, String[]> wareHouse = new HashMap<Integer, String>();
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
