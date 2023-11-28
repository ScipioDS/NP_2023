import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде
class ZeroDenominatorException extends Exception{
    ZeroDenominatorException(String msg){
        super(msg);
    }
}
class GenericFraction<T extends Number, U extends Number>{
    T numerator;
    U denominator;
    GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if (denominator.doubleValue() == 0){
            throw new ZeroDenominatorException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        GenericFraction<Double, Double> result = null;

        Double b = getDenominator() * gf.getDenominator();
        Double a = (getDenominator()*gf.getNumerator()) + (gf.getDenominator()*getNumerator());
        try {
            result = new GenericFraction<>(a, b);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public double getNumerator() {
        return numerator.doubleValue();
    }

    public double getDenominator() {
        return denominator.doubleValue();
    }
    public double toDouble(){
        return getNumerator() / getDenominator();
    }
    private double gcd(double n1, double n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }
    @Override
    public String toString() {
        double gcd = gcd(getNumerator(),getDenominator());
        return String.format("%.2f / %.2f",getNumerator()/gcd,getDenominator()/gcd);
    }
}