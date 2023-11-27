package Risk;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class RiskTester1 {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}
class Risk{
    LinkedList<Integer> x_list;
    LinkedList<Integer> y_list;
    public Risk(){
        x_list = new LinkedList<>();
        y_list = new LinkedList<>();
    }
    public int processAttacksData (InputStream is){
        Scanner scanner = new Scanner(is);
        int num_success = 0;
        while (scanner.hasNextLine()){
            String in_str = scanner.nextLine();
            String [] x = in_str.split(";")[0].split(" ");
            String [] y = in_str.split(";")[1].split(" ");

            for ( String xi : x){
                x_list.add(Integer.parseInt(xi));
            }
            for ( String yi : y){
                y_list.add(Integer.parseInt(yi));
            }
            x_list.sort(Collections.reverseOrder());
            y_list.sort(Collections.reverseOrder());

            boolean flag = true;
            for (int i = 0; i < 3; i++) {
                if (x_list.get(i).compareTo(y_list.get(i))<0 || x_list.get(i).compareTo(y_list.get(i))==0){
                    flag = false;
                    break;
                }
            }
            if (flag){
                num_success++;
            }
            x_list.clear();
            y_list.clear();
        }
        return num_success;
    }
}