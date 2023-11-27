import java.util.Scanner;
class MinMax<T extends Comparable<T>>{
    T min;
    T max;
    int total;
    int minCount;
    int maxCount;
    MinMax(){
        total = 0;
        minCount = 0;
        maxCount = 0;
    }
    void update(T element){
        if(total == 0){
            max = element;
            min = element;
        }
        total++;
        if (element.compareTo(min)<0){
            min = element;
            minCount = 1;
        } else {
            if (element.compareTo(min)==0){
                minCount++;
            }
        }
        if (element.compareTo(max)>0){
            max = element;
            maxCount = 1;
        } else {
            if (element.compareTo(max)==0){
                maxCount++;
            }
        }
    }
    T max(){
        return max;
    }
    T min(){
        return min;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        T min = min();
        T max = max();
        stringBuilder.append(String.format("%s %s %d\n", min, max, total - (minCount+maxCount)));
        return stringBuilder.toString();
    }
}
public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}