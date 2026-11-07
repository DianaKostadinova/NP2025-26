import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Car {
    String manufacturer;
    String model;
    int price;
    float power;

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", power=" + power +
                '}';
    }
}
    class CarCollection{
        List<Car> cars;
        public CarCollection() {
            this.cars = new ArrayList<>();
        }
        public void addCar(Car car){
            this.cars.add(car);
        }
        public void sortByPrice(boolean ascending){
            cars.sort((c1,c2) -> {
                int result = Integer.compare(c1.getPrice(), c2.getPrice());
                if (result != -0) {
                    return ascending ? result : -result;
                }
                int result2 = Float.compare(c1.getPower(), c2.getPower());
                return ascending ? result2 : -result2;

            });
           for (Car car : this.cars) {
               System.out.println(car);
            }
        }
        public List <Car> filterByManufacturer(String manufacturer) {
           return cars.stream()
                    .filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                    .sorted(Comparator.comparing(Car::getModel))
                    .collect(Collectors.toList());
        }
        public List getList(){
            return cars;
        }

    }

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            if (parts.length == 1)
                return line;

            String manufacturer = parts[0];
            String model = parts[1];
            int price = Integer.parseInt(parts[2]);
            float power = Float.parseFloat(parts[3]);
            cc.addCar(new Car(manufacturer, model, price, power));
        }
        return "";
    }
}