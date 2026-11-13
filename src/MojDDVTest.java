import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class AmountNotAllowedException extends   Exception{
    public  AmountNotAllowedException(int amount){
        super(String.format("Receipt with amount " + amount + " is not allowed to be scanned"));
    }
}

class Receipt{
    String id;
    int amount;
    double tax;
    public Receipt(String id, int amount, double tax){
        this.id = id;
        this.amount = amount;
        this.tax = tax;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }
    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f", id, amount, tax);
    }
}

class MojDDV{
    List<Receipt> receipts;
    public MojDDV(){
        receipts = new ArrayList<>();
    }

    int sumReceipts(String [] parts){
        int sum =0;
        for (int i=1; i<parts.length; i+=2){
            sum+=Integer.parseInt(parts[i]);
        }
        return sum;
    }
    double taxReturn(String [] parts){
        double sum =0;
        for (int i=1; i<parts.length; i+=2){
            double price=0;
            if (parts[i+1].equals("A")){
                price+=Double.parseDouble(parts[i])*0.18;
            }
            if (parts[i+1].equals("B")){
                price+=Double.parseDouble(parts[i])*0.05;
            }
            if (parts[i+1].equals("C")){
                price=0;
            }
            price*=0.15;
            sum+=price;
        }
        return sum;
    }

    void readRecords(InputStream is) {
        Scanner scanner =  new Scanner(is);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.isEmpty()){
                continue;
            }
            String [] parts = line.split(" ");
            String id = parts[0];
            int amount = sumReceipts(parts);
            double  tax = taxReturn(parts);
            try {
                if (amount > 30000) {
                    throw new AmountNotAllowedException(amount);
                }
                receipts.add(new Receipt(id, amount, tax));
            } catch (AmountNotAllowedException e){
                System.out.println(e.getMessage());
            }


        }
    }
    void printTaxReturns(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        for (Receipt receipt : receipts) {
            pw.println(receipt.toString());
        }
        pw.flush();
    }
    void printStatistics(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        double sum =0 ;
        int count = 0;
        double avg = 0;
        for (Receipt receipt : receipts) {
            sum+=receipt.getTax();
            count++;
            if(min>receipt.getTax()){
                min=receipt.getTax();
            }
            if(max<receipt.getTax()){
                max=receipt.getTax();
            }
        }
        avg =  sum/count;
        pw.println(String.format("min:\t%05.03f", min));
        pw.println(String.format("max:\t%05.03f", max));
        pw.println(String.format("sum:\t%05.03f", sum));
        pw.println(String.format("count:\t%-5d", count));
        pw.println(String.format("avg:\t%05.03f", avg));
        pw.flush();
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}