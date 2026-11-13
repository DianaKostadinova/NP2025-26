import java.io.InputStream;
import java.util.Scanner;

class ShapesApplication{
    double maxArea;
    ShapesApplication(double maxArea){
        this.maxArea = maxArea;
    }
    void readCanvases(InputStream is){
        Scanner scanner = new Scanner(is);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.isEmpty()) continue;
        }
    }
}
public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}