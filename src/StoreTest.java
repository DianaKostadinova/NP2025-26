        import java.io.*;
        import java.nio.charset.StandardCharsets;
        import java.util.*;
        import java.util.stream.Collectors;
        class InvalidProductException extends Exception {
            public InvalidProductException(String line) {
                super("Invalid product: " + line);
            }
        }

        class CategoryNotFoundException extends Exception {
            public CategoryNotFoundException(String category) {
                super("Category " + category + " not found");
            }
        }

        abstract class Product implements Comparable<Product> {
            private final int id;
            private final String name;
            private final String category;
            private final double basePrice;

            public Product(int id, String name, String category, double basePrice) {
                this.id = id;
                this.name = name;
                this.category = category;
                this.basePrice = basePrice;
            }

            public int getId() { return id; }
            public String getName() { return name; }
            public String getCategory() { return category; }
            public double getBasePrice() { return basePrice; }

            public abstract double getFinalPrice();
            public abstract char getTypeCode();

            @Override
            public int compareTo(Product other) {
                int c = Double.compare(getFinalPrice(), other.getFinalPrice());
                if (c != 0) return c;
                c = name.compareTo(other.name);
                if (c != 0) return c;
                return Integer.compare(id, other.id);
            }

            @Override
            public String toString() {
                return String.format("%c %-5d %-15s %-12s %10.2f",
                        getTypeCode(), id, name, category, getFinalPrice());
            }

            public static Product createProduct(String line) throws InvalidProductException {
                String[] parts = line.split(";", -1);
                if (parts.length != 6) {
                    throw new InvalidProductException(line);
                }
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                String type = parts[0];


                try {
                    if (!type.equals("P") && !type.equals("D")) {
                        throw new InvalidProductException(line);
                    }
                    int id = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String category = parts[3];
                    double basePrice = Double.parseDouble(parts[4]);
                    if (basePrice < 0) {
                        throw new InvalidProductException(line);
                    }

                    if (type.equals("P")) {
                        double weight = Double.parseDouble(parts[5]);
                        if (weight <= 0) {
                            throw new InvalidProductException(line);
                        }
                        return new PhysicalProduct(id, name, category, basePrice, weight);
                    } else {
                        int sizeMb = Integer.parseInt(parts[5]);
                        if (sizeMb <= 0) {
                            throw new InvalidProductException(line);
                        }
                        return new DigitalProduct(id, name, category, basePrice, sizeMb);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidProductException(line);
                }
            }
        }

        class PhysicalProduct extends Product {
            private final double weight;

            public PhysicalProduct(int id, String name, String category, double basePrice, double weight) {
                super(id, name, category, basePrice);
                this.weight = weight;
            }

            public double getWeight() { return weight; }

            public double getShippingCost() {
                if (weight <= 1) return 60;
                return 60 + 30 * (weight - 1);
            }

            @Override
            public double getFinalPrice() {
                return getBasePrice() + getShippingCost();
            }

            @Override
            public char getTypeCode() { return 'P'; }

            @Override
            public String toString() {
                return super.toString() + String.format(" | %.2f kg", weight);
            }
        }

        class DigitalProduct extends Product {
            private final int sizeMb;

            public DigitalProduct(int id, String name, String category, double basePrice, int sizeMb) {
                super(id, name, category, basePrice);
                this.sizeMb = sizeMb;
            }

            public int getSizeMb() { return sizeMb; }

            @Override
            public double getFinalPrice() {
                return getBasePrice() * 0.9;
            }

            @Override
            public char getTypeCode() { return 'D'; }

            @Override
            public String toString() {
                return super.toString() + String.format(" | %d MB", sizeMb);
            }
        }
        class Store {
            List <Product> products;
            public Store() {
                products = new ArrayList<>();
            }
            private int invalidLines = 0;

            public int readProducts(InputStream is) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                int read=0;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    if (line.trim().equals("COMMANDS")) break;
                    try {
                        products.add(Product.createProduct(line));
                        read++;
                    } catch (InvalidProductException e) {
                        invalidLines++;
                    }
                }
                return read;
            }

            public int getInvalidLines() {
                return invalidLines;
            }

            public void printSorted(OutputStream os) {
               PrintWriter pw = new PrintWriter(os);
               products.sort(Product::compareTo);
               for(Product product:  products) {
                   pw.println(product);
               }
               pw.flush();
            }


            public void printByCategory(OutputStream os, String category) throws CategoryNotFoundException {
                List <Product> sorted = new ArrayList<>();
                for (Product product: products){
                    if(product.getCategory().equals(category)) {
                        sorted.add(product);
                    }
                }
                if (sorted.isEmpty()) {
                    throw new CategoryNotFoundException(category);
                }
                Comparator <Product> cmp = Comparator.comparing(Product::getName,String.CASE_INSENSITIVE_ORDER  )
                        .thenComparing(Comparator.comparing(Product::getFinalPrice,Comparator.reverseOrder()))
                        .thenComparing(Comparator.comparing(Product::getId));
                PrintWriter pw = new PrintWriter(os);
                sorted.sort(cmp);
                for(Product product: sorted) {
                    pw.println(product);
                }
                pw.flush();
            }

            public void printStatistics(OutputStream os) {
                int count = 0;
                int pCount = 0;
                int dCount = 0;
                for (Product product: products) {
                    count++;
                    if(product.getTypeCode() == 'P'){
                        pCount++;
                    }
                    if(product.getTypeCode() == 'D'){
                        dCount++;
                    }
                }
                PrintWriter pw = new PrintWriter(os);
                if (products.isEmpty()) {
                    pw.println("No products");
                    pw.flush();
                    return;
                }

                pw.println("Total: "+count + " (physical: " + pCount + ", digital: " + dCount+")");
                pw.println("Cheapest: "+products.get(0).toString());
                pw.println("Most expensive: "+products.get(products.size()-1).toString());
                double sum=0;
                for(Product product: products) {
                    sum+=product.getFinalPrice();
                }
                pw.println("Average price: " +String.format("%.2f", sum/products.size()));
                pw.flush();
            }
        }

        public class StoreTest {
            public static void main(String[] args) throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                StringBuilder productLines = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null && !line.trim().equals("COMMANDS")) {
                    productLines.append(line).append("\n");
                }

                Store store = new Store();
                int read = store.readProducts(
                        new ByteArrayInputStream(productLines.toString().getBytes(StandardCharsets.UTF_8)));
                System.out.println("Read products: " + read);
                System.out.println("Invalid lines: " + store.getInvalidLines());

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split("\\s+");
                    System.out.println("=== " + line + " ===");
                    switch (parts[0]) {
                        case "SORTED":
                            store.printSorted(System.out);
                            break;
                        case "CATEGORY":
                            try {
                                store.printByCategory(System.out, parts[1]);
                            } catch (CategoryNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "STATS":
                            store.printStatistics(System.out);
                            break;
                        default:
                            System.out.println("Unknown command");
                    }
                }
            }
        }
