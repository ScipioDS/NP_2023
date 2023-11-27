package Risk;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class RiskTester2 {
    public static void main(String[] args) {
        Risk2 risk = new Risk2();
        risk.processAttacksData(System.in);
    }

}
class Risk2 {
    LinkedList<Integer> x_list;
    LinkedList<Integer> y_list;

    public Risk2() {
        x_list = new LinkedList<>();
        y_list = new LinkedList<>();
    }

    public int processAttacksData(InputStream is) {
        Scanner scanner = new Scanner(is);
        int num_success = 0;
        while (scanner.hasNextLine()) {
            String in_str = scanner.nextLine();
            String[] x = in_str.split(";")[0].split(" ");
            String[] y = in_str.split(";")[1].split(" ");

            for (String xi : x) {
                x_list.add(Integer.parseInt(xi));
            }
            for (String yi : y) {
                y_list.add(Integer.parseInt(yi));
            }
            x_list.sort(Collections.reverseOrder());
            y_list.sort(Collections.reverseOrder());

            int x_surviors = 0, y_surviors = 0;
            for (int i = 0; i < 3; i++) {
                if (x_list.get(i).compareTo(y_list.get(i)) < 0 || x_list.get(i).compareTo(y_list.get(i)) == 0) {
                    y_surviors++;
                } else {
                    x_surviors++;
                }
            }
            x_list.clear();
            y_list.clear();

            System.out.println(x_surviors + " " + y_surviors);
        }
        return num_success;
    }
}