import java.util.*;


class Book{
    String title;
    String category;
    float price;

    public Book(String title, String category, float price){
        this.title = title;
        this.category = category;
        this.price = price;
    }
    String getTitle(){
        return title;
    }
    String getCategory(){
        return category;
    }
    float getPrice(){
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", title, category, price);
    }
}

//class BookComparator implements Comparator<Book>{
//    @Override
//    public int compare(Book o1, Book o2) {
//        int result = o1.getTitle().compareTo(o2.getTitle());
//        if (result != 0) {
//            return  result;
//        }
//        return Float.compare(o1.getPrice(), o2.getPrice());
//    }
//}

class BookCollection{
   private  List<Book> books;
   public BookCollection(){
       this.books = new ArrayList<>();
   }
   public void addBook(Book book){
       this.books.add(book);
   }
   public void printByCategory(String category){
       List <Book> bookSorted = new ArrayList<>();
       for (Book book : this.books){
           String bookCategory =book.getCategory().toLowerCase();
             if(bookCategory.equals(category.toLowerCase())){
               bookSorted.add(book);
           }
       }
       bookSorted.sort((b1,b2) -> {
           int result = b1.getTitle().compareTo(b2.getTitle());
           if(result!=0){
               return result;
           }
           return Float.compare(b1.getPrice(), b2.getPrice());
       });
       for (Book book : bookSorted){
           System.out.println(book);
       }
   }
    public List<Book> getCheapestN(int n){
        books.sort((b1,b2) -> Float.compare(b1.getPrice(), b2.getPrice()));
        if (n >= books.size()){
            return books;
        }
        return books.subList(0, n);
    }
}

public class Aud4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(
            Scanner scanner,
            BookCollection collection
    ) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}