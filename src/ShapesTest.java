import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }
        }
    }
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Scalable, Stackable {
    protected String id;
    protected Color color;

    Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    protected abstract char type();

    @Override
    public String toString() {
        return String.format("%c: %-5s%-10s%10.2f", type(), id, color, weight());
    }
}

class Circle extends Shape {
    private float radius;

    Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius * radius);
    }

    @Override
    protected char type() {
        return 'C';
    }
}

class Rectangle extends Shape {
    private float width;
    private float height;

    Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return width * height;
    }

    @Override
    protected char type() {
        return 'R';
    }
}

class Canvas {
    private final List<Shape> shapes = new ArrayList<>();

    private void insert(Shape shape) {
        int i = 0;
        while (i < shapes.size() && shapes.get(i).weight() >= shape.weight()) {
            i++;
        }
        shapes.add(i, shape);
    }

    public void add(String id, Color color, float radius) {
        insert(new Circle(id, color, radius));
    }

    public void add(String id, Color color, float width, float height) {
        insert(new Rectangle(id, color, width, height));
    }

    public void scale(String id, float scaleFactor) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).getId().equals(id)) {
                Shape s = shapes.remove(i);
                s.scale(scaleFactor);
                insert(s);
                return;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Shape s : shapes) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}