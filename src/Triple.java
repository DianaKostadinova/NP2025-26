//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//
//    class Triple<T extends Number> {
//        T a;
//        T b;
//        T c;
//        List <T> list;
//        public Triple(T a,T b, T c ) {
//            this.a = a;
//            this.b = b;
//            this.c = c;
//            list = new ArrayList<>();
//        }
//
//        double max(){
//            double max = Math.max(a.doubleValue(),b.doubleValue());
//            return Math.max(max, c.doubleValue());
//        }
//        double avarage(){
//            double avg = a.doubleValue()+b.doubleValue()+c.doubleValue();
//            return avg/3;
//        }
//        void sort() {
//            list.add(a);
//            list.add(b);
//            list.add(c);
//
//            list.sort(Comparator.comparingDouble(Number::doubleValue));
//        }
//        @Override
//        public String toString() {
//            return String.format("%.2f %.2f %.2f",
//                    list.get(0).doubleValue(),
//                    list.get(1).doubleValue(),
//                    list.get(2).doubleValue());
//        }
//    }
//
//public class TripleTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int a = scanner.nextInt();
//        int b = scanner.nextInt();
//        int c = scanner.nextInt();
//        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
//        System.out.printf("%.2f\n", tInt.max());
//        System.out.printf("%.2f\n", tInt.avarage());
//        tInt.sort();
//        System.out.println(tInt);
//        float fa = scanner.nextFloat();
//        float fb = scanner.nextFloat();
//        float fc = scanner.nextFloat();
//        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
//        System.out.printf("%.2f\n", tFloat.max());
//        System.out.printf("%.2f\n", tFloat.avarage());
//        tFloat.sort();
//        System.out.println(tFloat);
//        double da = scanner.nextDouble();
//        double db = scanner.nextDouble();
//        double dc = scanner.nextDouble();
//        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
//        System.out.printf("%.2f\n", tDouble.max());
//        System.out.printf("%.2f\n", tDouble.avarage());
//        tDouble.sort();
//        System.out.println(tDouble);
//    }
//}
//// vasiot kod ovde
//// class Triple
//
//
