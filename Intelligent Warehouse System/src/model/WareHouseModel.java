package model;

import algorithms.SearchAlgorithm;

public class WareHouseModel {

	private String order;
	private SearchAlgorithm alg;
	private WareHouse wareHouse;
	private String result;
	
	
	private void generateState() {
		//create first state
	}
	
	public void setWarehouse(String wareHousePath) {
		wareHouse = new WareHouse(wareHousePath);
	}
	
	public void startSearch(SearchAlgorithm alg, String order) {
		result = alg.search(order, wareHouse);
	}
	
	public String getResult() {
		return result;
	}

}
