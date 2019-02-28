package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import model.WareHouseModel;
import view.Gui;
/**
 * The WarehouseController connects the the GUI and the functionality of the program
 * according to the Model-View-Controller pattern.
 * 
 * It uses inner classes as ActionListeners for the actions that the user may perform
 * in the GUI.
 * @author ma 
 */
public class WarehouseController {

	private Gui theView;
	private WareHouseModel theModel;
	
	public WarehouseController(Gui theView, WareHouseModel theModel) {
		
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.addWareHouseListener(new WarehouseListener());
		this.theView.addOrderListener(new OrderListener());
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
			File warehouseTxt;
			
			try {
				warehouseTxt = theView.getWarehouse();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			theModel.setWarehouse(warehouseTxt);
		}
		
	}
	
	class OrderListener implements ActionListener{
//OrderReceiver is activated when user presses Button in the GUI to submit an Order.
//Order Receiver is given to the Button as an ActionListener
//		The GUI needs the following Methods:
		//public String getOrder();
		//public String getAlg();
		//public String getWareHousePath();
		
		//QUESTION: How to solve the Problem with local beam search and parallel Hill climbing (the need an additional parameter)
		//Possible solution: wrapper class for searchAlg Info.?
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			File orderTxt;
			int alg;
			int param;
			
			try {
				orderTxt = theView.getOrder();
				alg = theView.getAlgo();
				param = theView.getParam();
			}catch(Exception ex) {
				//TODO: Handle exception
			}
			
			try{
				theModel.initOrder(orderTxt);
			} catch(InvalidOrderException e){
				System.out.println("Your order had some "
								+ "unaccepted items: \n" + e.getInvalidItems());
			}

			theModel.startSearch(alg, param, orderTxt);
			
			try {
				theView.setSolution(theModel.getResult());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
}
