package intelligentwarehousesystem.src.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.WareHouseModel;
import view.WareHouseView;
/**
 * The WarehouseController connects the the GUI and the functionality of the program
 * according to the Model-View-Controller pattern.
 * 
 * It uses inner classes as ActionListeners for the actions that the user may perform
 * in the GUI.
 * @author ma 
 */
public class WarehouseController {

	private WareHouseView theView;
	private WareHouseModel theModel;
	
	public WarehouseController(WareHouseView theView, WareHouseModel theModel) {
		
		this.theView = theView;
		this.theModel = theModel;
		
		this.theView.addWareHouseListener(new WarehouseListener());
		this.theView.addOrderReceiver(new OrderReceiver());
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
			String warehousePath = "";
			
			try {
				warehousePath = theView.getWarehousePath();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			theModel.setWarehouse(warehousePath);
		}
		
	}
	
	class OrderReceiver implements ActionListener{
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
			String order = "";
			SearchAlg alg; //Better a String, because the view gives me only a string. 
						   //How to get an Alg-Object from A string?
			
			try {
				order = theView.getOrder();
				alg = theView.getAlg(); //See above
			}catch(Exception ex) {
				//TODO: Handle exception
			}
			
			theModel.startSearch(alg, order);
			
			try {
				theView.displayResult(theModel.getResult());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
}
