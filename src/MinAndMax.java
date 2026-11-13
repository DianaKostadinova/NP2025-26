    import java.util.Comparator;
    import java.util.Scanner;
    class MinMax<T extends Comparable<T>> {
        T min;
        T max;
        int times;
        int mintimes;
        int maxtimes;
        public MinMax(){}
        void update(T element){
            times++;
            if (min == null){
                min = element;
                max = element;
                mintimes = 1;
                maxtimes = 1;
                return;
            }
            int cmpMin = min.compareTo(element);
            if(cmpMin<0){
                min = element;
                mintimes = 1;
            }else if (cmpMin == 0) {
               mintimes++;
            }
            int cmpMax = max.compareTo(element);
            if(cmpMax>  0){
                max = element;
                maxtimes = 1;
            } else if (cmpMax==0) {
                maxtimes++;

            }
        }
        public T min() {
            return min;
        }

        public T max() {
            return max;
        }
        private int compareOthers(){
            if (min.compareTo(max) == 0){
                return 0;
            }
            return times - (mintimes + maxtimes);
        }
        @Override
        public String toString() {
            return max + " " + min+ " " + compareOthers()+"\n";
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