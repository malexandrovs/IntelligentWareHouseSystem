package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import model.InvalidOrderException;
import model.WareHouseModel;
import view.Gui;
/**
 * The WarehouseController connects the the GUI and the functionality of the program
 * according to the Model-View-Controller pattern.
 * 
 * It uses inner classes as ActionListeners for the actions that the user may perform
 * in the GUI.
 * @author Michael Alexandrovsky (malexandrovs@uos.de)
 */
public class WarehouseController {

	private Gui theView;
	private WareHouseModel theModel;
	private boolean warehouseSet;
	
	public WarehouseController(Gui theView, WareHouseModel theModel) {
		
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.addWareHouseListener(new WarehouseListener());
		this.theView.addOrderListener(new OrderListener());

		warehouseSet  = false;
	}
	
	/**
	 * WarehouseListener listens to the "Submit Warehouse"-button in the GUI
	 * 
	 * @author ma
	 *
	 */
	class WarehouseListener implements ActionListener{

		/**
		 * When the user presses the "Submit Warehouse"-button in the GUI the
		 * WarehouseListener transfers the information from the GUI to the model. 
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			File warehouseTxt = null;
			

			warehouseTxt = theView.getWarehouse();
				
			
			warehouseSet = theModel.initWarehouse(warehouseTxt);
		}
		
	}
	/**
	 * OrderListener listens to the button, that submits the order and the alorithm in the GUI.
	 * When the button is clicked, the search is started and an answer is returned.
	 */
	class OrderListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent arg0) {

			boolean orderSet = true;

			if(!warehouseSet){
				theView.errorAlert("Please select the Warehouse first and submit it!");
				return;
			}
			
			
			try{
				orderSet = theModel.initOrder(theView.getOrder());


			} catch(InvalidOrderException e){
				theView.errorAlert("Your order had some "
				+ "unaccepted items: \n" + e.getInvalidItems());
			}

			if(!orderSet){
				theView.errorAlert("There seems to be a problem with your order. Maybe you did not select a file? Please check.");
				return;
			}

			

			theModel.startSearch(theView.getAlgo(), theView.getParam());
		

				theView.setSolution(theModel.getResult());



		}
		
	}
}
