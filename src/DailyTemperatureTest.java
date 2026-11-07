import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.io.InputStream;
class DailyMeasurement{
    int dayOfYear;
    List<String> temperatures = new ArrayList<>();
    public DailyMeasurement(int dayOfYear, List<String> temperatures) {
        this.dayOfYear=dayOfYear;
        this.temperatures=temperatures;
    }
    int getDayOfYear(){
        return dayOfYear;
    }
    List<String> getTemperatures(){
        return temperatures;
    }
}
class DailyTemperatures{
    List <DailyMeasurement> days;
    public DailyTemperatures(){
        this.days=new ArrayList<>();
    }
    public void readTemperatures(InputStream in){
        Scanner scanner= new Scanner(in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if (line.isEmpty()){
                continue;
            }
            String [] parts = line.split("\\s");
            int day = Integer.parseInt(parts[0]);
            List<String> temperatures = new ArrayList<>();
            for (int i = 1; i < parts.length; i++){
                temperatures.add(parts[i]);
            }
            days.add(new DailyMeasurement(day,temperatures));
        }
    }
    public void writeDailyStats(OutputStream outputStream, char scale){
        PrintWriter pw = new PrintWriter(outputStream);
        days.sort((d1,d2) ->
            Integer.compare(d1.getDayOfYear(),d2.getDayOfYear()));

            for (DailyMeasurement dm: days){
                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;

                double sum = 0;
                int count = 0;

                for (String t: dm.getTemperatures()){
                    char unit = t.charAt(t.length()-1);
                    double value = Integer.parseInt(t.substring(0,t.length()-1));

                    if (unit == 'C' && scale == 'F'){
                        value = value*9/5 +32;
                    } else if (unit == 'F' && scale == 'C'){
                        value = (value - 32) *5/9;
                    }

                    min =Math.min(min,value);
                    max = Math.max(max,value);
                    sum+= value;
                    count++;
                }
                double avg = sum/count;
                pw.printf("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c%n",
                        dm.getDayOfYear(), count, min, scale, max, scale, avg, scale);
            }
            pw.flush();
    }

}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}