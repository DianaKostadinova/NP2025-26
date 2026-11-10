import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException  extends Exception{
    public NonExistingItemException(int id){
        super("Item with id " + id + " doesn't exist");
    }
}

abstract class Archive {
    int id;
    Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    abstract String open(Date date);
}

class LockedArchive extends Archive{
    Date dateToOpen;
    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }
    @Override
    String open(Date date) {
        if (date.before(dateToOpen)) {
            return String.format("Item %d cannot be opened before %s", id, dateToOpen);
        }
        return String.format("Item %d opened at %s", id, date);
    }
}
class SpecialArchive extends Archive{
    int maxOpen;
    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
    }
    private int timesOpened = 0;

    @Override
    String open(Date date) {
        if (timesOpened >= maxOpen) {
            return String.format("Item %d cannot be opened more than %d times", id, maxOpen);
        }
        timesOpened++;
        return String.format("Item %d opened at %s", id, date);
    }

}

class ArchiveStore{
    private final List<Archive> archives;
    List<String> messages = new ArrayList<>();
    public ArchiveStore(){
        archives = new ArrayList<>();
    }
    public void archiveItem(Archive item, Date date){
        archives.add(item);
        item.dateArchived = date;
        messages.add(String.format("Item %d archived at %s", item.id, date));
    }
    public void openItem(int id, Date date) throws NonExistingItemException {
        Archive item = archives.stream()
                .filter(a -> a.id == id)
                .findFirst()
                .orElseThrow(() -> new NonExistingItemException(id));

        messages.add(item.open(date));
    }

    String getLog() {
        return String.join("\n", messages);
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}
