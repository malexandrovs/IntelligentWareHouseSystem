import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import model.*;


public class WarehouseTest{
    public static void main(String[] args) {
        try{

            File warehouseTxt = new File("/Users/Dell/Documents/Files/University/3.Semester/MoAI/IntelligentWareHouseSystem-CurrentFinal/IntelligentWareHouseSystem-master/intelligentwarehousesystem/src/resources/problem1.txt");
            // File orderTxt = new File("/home/ma/git/IntelligentWareHouseSystem/intelligentwarehousesystem/src/resources/invalidOrder.txt");
            File orderTxt = new File("C:/Users/Dell/Documents/Files/University/3.Semester/MoAI/IntelligentWareHouseSystem-CurrentFinal/IntelligentWareHouseSystem-master/intelligentwarehousesystem/src/resources/order11.txt");

            BufferedReader br = new BufferedReader(new FileReader(orderTxt));



        System.out.println(warehouseTxt.exists());
        WareHouseModel warehouse = new WareHouseModel();

        boolean warehouseSet = warehouse.setWarehouse(warehouseTxt);
        System.out.println(warehouseSet);
        String[] order = br.readLine().split(" ");

        warehouse.initOrder(order);
        System.out.println("HillClimbing: ");
        warehouse.startSearch(0,4);

        System.out.println("simulatedAnnealing: ");
        warehouse.startSearch(1,4);

        System.out.println("Local Beam: ");
        warehouse.startSearch(2,4);

        System.out.println("randomRestartHillClimbing: ");
        warehouse.startSearch(3,4);

        System.out.println("firstChoiceHillClimbing: ");
        warehouse.startSearch(4,4);


        // int[] result = warehouse.getResult();

        // System.out.println();
        // for (int psu : result) {
        //     System.out.println(psu);
        // }

        String result = warehouse.getResult();

        System.out.println(result);
        } catch(IOException e){
            e.printStackTrace();
        } catch(InvalidOrderException e){
            System.out.println("Your order had some unaccepted items: \n" + e.getInvalidItems());
        }


    }
}
