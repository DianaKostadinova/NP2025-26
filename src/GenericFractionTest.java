import java.util.Scanner;
class ZeroDenominatorException extends  Exception{
    public ZeroDenominatorException(String message) {
        super(message);
    }
}
class GenericFraction<T extends Number,U extends  Number> {
    T numerator;
    U denominator;

    public  GenericFraction(T numerator,U denominator) throws ZeroDenominatorException{
        this.numerator=numerator;
        if (denominator.equals(0)){
            throw new ZeroDenominatorException("Denominator cannot be zero");
        }
        else this.denominator=denominator;
    }
    static double nzs(double a, double b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            double t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException{
        double a = this.numerator.doubleValue();
        double b = this.denominator.doubleValue();
        double c = gf.numerator.doubleValue();
        double d = gf.denominator.doubleValue();

        return new GenericFraction<>((a*d)+(b*c), (b*d));
    }
    double toDouble(){
        double a = this.numerator.doubleValue();
        double b = this.denominator.doubleValue();
        return a/b;
    }
    @Override
    public String toString() {
        double n = numerator.doubleValue();
        double d = denominator.doubleValue();
        double g =  nzs(n, d);
        return String.format("%.2f / %.2f", n / g, d / g);
    }
}
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
