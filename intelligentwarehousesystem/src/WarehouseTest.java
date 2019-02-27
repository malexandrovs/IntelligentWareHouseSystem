import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import model.*;

public class WarehouseTest{
    public static void main(String[] args) {
        try{

            File warehouseTxt = new File("/Users/Dell/Documents/Files/University/3.Semester/MoAI/IntelligentWareHouseSystem-CurrentFinal/IntelligentWareHouseSystem-master/intelligentwarehousesystem/src/resources/problem1.txt");
            File orderTxt = new File("C:/Users/Dell/Documents/Files/University/3.Semester/MoAI/IntelligentWareHouseSystem-CurrentFinal/IntelligentWareHouseSystem-master/intelligentwarehousesystem/src/resources/order11.txt");
            BufferedReader br = new BufferedReader(new FileReader(orderTxt));



        System.out.println(warehouseTxt.exists());
        WareHouseModel warehouse = new WareHouseModel();

        boolean warehouseSet = warehouse.setWarehouse(warehouseTxt);
        System.out.println(warehouseSet);
        String[] order = br.readLine().split(" ");

        warehouse.startSearch(0,-1, order);

        int[] result = warehouse.getResult();

        for (int psu : result) {
            System.out.print(psu);
        }
        System.out.print("\n");
        } catch(IOException e){
            e.printStackTrace();
        }


    }
}
