import java.time.LocalDate;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.MIN;
        System.out.println(date.compareTo(date1)<0);
    }
}
