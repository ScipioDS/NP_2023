import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}
class SubTime{
    int hrs;
    int min;
    int sec;
    int ms;
    SubTime(String input){
        String [] parts = input.split(":");
        hrs = Integer.parseInt(parts[0]);
        min = Integer.parseInt(parts[1]);
        String [] secparts = parts[2].split(",");
        sec = Integer.parseInt(secparts[0]);
        ms = Integer.parseInt(secparts[1]);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d,%03d",hrs,min,sec,ms);
    }
    public void shift(int num){
        ms += num;

        if(ms > 999) {
            ms %= 1000;
            sec++;
        } else if(ms < 0) {
            ms += 1000;
            sec--;
        }

        if(sec > 59) {
            sec -= 60;
            min++;
        } else if(sec < 0) {
            sec += 60;
            min--;
        }

        if(min > 59) {
            min -= 60;
            hrs++;
        } else if(min < 0) {
            min += 60;
            hrs--;
        }
    }
}
class Subtitle{
    int number;
    SubTime start;
    SubTime end;
    String text;

    public Subtitle(String line1, String line2, String line3) {
        number = Integer.parseInt(line1);
        start = new SubTime(line2.split(" --> ")[0]);
        end = new SubTime(line2.split(" --> ")[1]);
        text = line3;
    }

    @Override
    public String toString() {
        return String.format("%d\n%s --> %s\n%s",number,start,end,text);
    }
    public void shiftby(int num){
        start.shift(num);
        end.shift(num);
    }
}
class Subtitles{
    List<Subtitle> subtitles;
    Subtitles(){
        subtitles = new ArrayList<>();
    }
    public int loadSubtitles(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        int loaded = 0;
        while (scanner.hasNextLine()){
            loaded++;
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            String line3 = "";
            while (scanner.hasNext()){
                String text = scanner.nextLine();
                if (text.isEmpty()) break;
                line3+=text + "\n";
            }
            subtitles.add(new Subtitle(line1, line2, line3));
        }
        return loaded;
    }
    void print(){
        subtitles.stream().forEach(System.out::println);
    }
    void shift(int shiftby){
        subtitles.stream().forEach(sub -> sub.shiftby(shiftby));
    }
}
