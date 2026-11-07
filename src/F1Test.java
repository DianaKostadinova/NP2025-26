import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class Driver{
    String name;
    List<String> times;
    public Driver(String name, List<String> times){
        this.name = name;
        this.times = times;
    }
    double formatTime(String time){
        String [] times = time.split(":");
        double sum =0;
        sum = Double.parseDouble(times[0])*60+Double.parseDouble(times[1])+(Double.parseDouble(times[2]))/1000;
        return sum;

    }

    String getBestLap() {
        String best = times.get(0);
        for (String t : times) {
            if (formatTime(t) < formatTime(best)) {
                best = t;
            }
        }
        return best;
    }

    double getBestTime() {
        return formatTime(getBestLap());
    }

    String getName(){
        return name;
    }
    List<String> getTimes(){
        return times;
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, getBestLap());
    }
}
class F1Race{
    List<Driver> drivers;
    public F1Race(){
        drivers = new ArrayList<>();
    }

    void readResults(InputStream in)     {
        Scanner input = new Scanner(in);
        while(input.hasNextLine()){
            String line = input.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }
            String [] parts =  line.split("\\s");
            String name =  parts[0];
            List<String> times = new ArrayList<>();
            for (int i = 1; i < parts.length; i++){
                times.add(parts[i]);
            }
            drivers.add(new Driver(name, times));
        }
    }
    void printSorted(OutputStream out){
        PrintWriter pw = new PrintWriter(out);
        drivers.sort((d1,d2) -> Double.compare(d1.getBestTime(), d2.getBestTime()));
        int i=1;
        for (Driver d : drivers){
            pw.println(i + ". " + d);
            i++;
        }
        pw.flush();
    }
}

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }
}