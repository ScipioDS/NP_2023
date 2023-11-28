import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
class F1Driver implements Comparable<F1Driver> {
    String name;
    List<String> laps;

    public F1Driver(String input) {
        this.laps = new ArrayList<>();
        String [] strings = input.split(" ");
        this.name = strings[0];
        for (int i = 1; i < strings.length; i++) {
            laps.add(strings[i]);
        }
    }
    void printName(){
        System.out.println(name);
    }
    int FastlapToMS(String string){
        int fmin = Integer.parseInt(string.split(":")[0]);
        int fsec = Integer.parseInt(string.split(":")[1]);
        int fms = Integer.parseInt(string.split(":")[2]);

        return fmin*60*1000 + fsec*1000 + fms;
    }
    String getFastestLap(){
        String fastest = laps.get(0);
        int fmin = Integer.parseInt(laps.get(0).split(":")[0]);
        int fsec = Integer.parseInt(laps.get(0).split(":")[1]);
        int fms = Integer.parseInt(laps.get(0).split(":")[2]);
        for (int i = 0; i < laps.size(); i++) {
            String [] s = laps.get(i).split(":");
            int min = Integer.parseInt(s[0]);
            int sec = Integer.parseInt(s[1]);
            int ms = Integer.parseInt(s[2]);

            if (fmin > min || (fmin>=min && fsec > sec) || (fmin>=min && fsec>=sec && fms > ms)){
                fastest = laps.get(i);
                fmin = min;
                fsec = sec;
                fms = ms;
            }
        }

        return fastest;
    }

    @Override
    public int compareTo(F1Driver o) {
        return FastlapToMS(getFastestLap()) - o.FastlapToMS(o.getFastestLap());
    }

    @Override
    public String toString() {
        return String.format("%-11s %s",name,getFastestLap());
    }
}
class F1Race {
    List<F1Driver> drivers;
    F1Race(){
        drivers = new ArrayList<>();
    }
    void readResults(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        drivers = bufferedReader.lines().map(i -> new F1Driver(i)).collect(Collectors.toList());

        bufferedReader.close();
    }
    void printSorted(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        List<F1Driver> driversSorted = drivers.stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < drivers.size(); i++) {
            printWriter.println(i+1 + ". " + driversSorted.get(i));
        }

        printWriter.flush();
    }
}