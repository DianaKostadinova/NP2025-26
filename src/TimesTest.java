import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}

class Time implements Comparable<Time> {
    int hours;
    int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public String format(TimeFormat format) {
        int h = hours;
        int m = minutes;
        String part;
        if (format == TimeFormat.FORMAT_24){
            return String.format("%02d:%02d", h, m);
        }
        if(h == 0){
            h+=12;
            part = "AM";
        } else if (h == 12){
            part = "PM";
        } else if (h<=12) {
            part = "AM";
        } else {
            part = "PM";
            h -= 12;
        }
        return String.format("%2d:%02d %s",  h, m, part);
    }
    @Override
    public int compareTo(Time o) {
        return Integer.compare(this.hours*60+this.minutes, o.hours*60+o.minutes);
    }
}

class TimeTable{
    private final List <Time> times;
    public TimeTable(){
        times = new ArrayList<>();
    }
    public void readTimes(InputStream inputStream) throws UnsupportedFormatException, InvalidTimeException {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            for (String token : line.split("\\s+")) {
                times.add(parse(token));
            }
        }
    }
    private Time parse (String token) throws  UnsupportedFormatException, InvalidTimeException{
        String [] parts;
        if (token.contains(":")) {
            parts = token.split(":");
        } else if (token.contains(".")) {
            parts = token.split("//.");
        } else {
            throw new UnsupportedFormatException(token);
        }
        if (parts.length != 2) {
            throw new UnsupportedFormatException(token);
        }
        return  new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

    }
    void writeTimes(OutputStream outputStream, TimeFormat timeFormat){

    }
}

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}