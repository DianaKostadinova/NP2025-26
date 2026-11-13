import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
}

class Time {
    private int hours;
    private int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    int getHours() {
        return hours;
    }

    int getMinutes() {
        return minutes;
    }
}

class TimeTable {
    List<Time> times;

    public TimeTable() {
        times = new ArrayList<>();
    }

    void readTimes(InputStream stream) throws UnsupportedFormatException, InvalidTimeException {
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            for (String token : line.split("\\s+")) {
                String[] parts;
                if (token.contains(":")) {
                    parts = token.split(":");
                } else if (token.contains(".")) {
                    parts = token.split("\\.");
                } else {
                    throw new UnsupportedFormatException(token);
                }

                int h = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                if (h < 0 || h > 23 || m < 0 || m > 59) {
                    throw new InvalidTimeException(token);
                }
                times.add(new Time(h, m));
            }
        }
    }

    void writeTimes(OutputStream stream, TimeFormat format) {
        PrintWriter pw = new PrintWriter(stream);
        List<Time> sorted = new ArrayList<>(times);
        sorted.sort(Comparator.comparingInt(Time::getHours)
                .thenComparingInt(Time::getMinutes));

        for (Time time : sorted) {
            int h = time.getHours();
            int m = time.getMinutes();
            if (format == TimeFormat.FORMAT_AMPM) {
                String part;
                if (h == 0) {
                    h = 12;
                    part = "AM";
                } else if (h == 12) {
                    part = "PM";
                } else if (h > 12) {
                    h -= 12;
                    part = "PM";
                } else {
                    part = "AM";
                }
                pw.println(String.format("%2d:%02d %s", h, m, part));
            } else {
                pw.println(String.format("%2d:%02d", h, m));
            }
        }
        pw.flush();
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