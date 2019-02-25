package intelligentwarehousesystem.src;
import controller.WarehouseController;
import model.WareHouseModel;
import view.WareHouseView;
/**
 * The Intelligent Warehouse System takes in information about a warehouse containing PSUs.
 * Each PSU has a set of items stored on it.
 * 
 * When the user makes an order the system calculates the minimal amount of PSUs that carry
 * the ordered items.
 * 
 * The system is designed according to the Model-View-Controller pattern.
 * 
 * This class contains the main method. It initializes:
 *  the GUI
 *  the model - the part of the program that does all calculations
 *  the controller - the part that connects the two upper parts.
 * @author ma
 *
 */
public class IntelligentWareHouseSystem {

	public static void main(String[] args) {
		WareHouseView theView = new WareHouseView();
		WareHouseModel theModel = new WareHouseModel();
		WarehouseController theController = new WarehouseController(theView, theModel);
		
		theView.setVisible(true);
				
	}
}
