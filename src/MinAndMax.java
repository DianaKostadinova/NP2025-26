    import java.util.Scanner;

    class MinMax<T extends Comparable<T>> {
        private T min;
        private T max;
        private int total;      // how many elements went through update
        private int countMin;   // how many times the current min appeared
        private int countMax;   // how many times the current max appeared

        public MinMax() {
            min = null;
            max = null;
            total = 0;
            countMin = 0;
            countMax = 0;
        }

        void update(T element) {
            if (total == 0) {          // first element is both min and max
                min = element;
                max = element;
            }

            if (element.compareTo(min) < 0) {          // new, smaller min
                min = element;
                countMin = 1;
            } else if (element.compareTo(min) == 0) {  // same as current min
                countMin++;
            }

            if (element.compareTo(max) > 0) {          // new, bigger max
                max = element;
                countMax = 1;
            } else if (element.compareTo(max) == 0) {  // same as current max
                countMax++;
            }

            total++;
        }

        T min() {
            return min;
        }

        T max() {
            return max;
        }

        @Override
        public String toString() {
            int different;
            if (min.compareTo(max) == 0) {   // all elements were equal
                different = total - countMin;
            } else {
                different = total - countMin - countMax;
            }
            return min + " " + max + " " + different + "\n";
        }
    }

    public class MinAndMax {
        public static void main(String[] args) throws ClassNotFoundException {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            MinMax<String> strings = new MinMax<String>();
            for (int i = 0; i < n; ++i) {
                String s = scanner.next();
                strings.update(s);
            }
            System.out.println(strings);
            MinMax<Integer> ints = new MinMax<Integer>();
            for (int i = 0; i < n; ++i) {
                int x = scanner.nextInt();
                ints.update(x);
            }
            System.out.println(ints);
        }
    }