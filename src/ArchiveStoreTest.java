
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception{
    NonExistingItemException(String msg){
        super(msg);
    }
}
class Archive{
    int id;
    LocalDate dateArchived;

    public Archive(int id) {
        this.id = id;
        this.dateArchived = LocalDate.MIN;
    }

    public int getId() {
        return id;
    }
    public String getDate(){
        return String.valueOf(dateArchived);
    }
    public String openArchive(LocalDate date){
        return String.format("Item %d opened at %s",id,date);
    }
    protected void setDateArchived(LocalDate dateArchived){
        this.dateArchived = dateArchived;
    }
}

class LockedArchive extends Archive{
    LocalDate dateToOpen;
    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String openArchive(LocalDate date) {
        if (date.compareTo(dateToOpen)<0){
            return String.format("Item %d cannot be opened before %s",id,dateToOpen);
        } else {
            return String.format("Item %d opened at %s",id,date);
        }
    }
}

class SpecialArchive extends Archive{
    int maxOpen;
    int numOpenings;
    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.numOpenings = 0;
    }

    @Override
    public String openArchive(LocalDate date) {
        if (numOpenings >= maxOpen){
            return String.format("Item %d cannot be opened more than %d times",id,maxOpen);
        } else {
            numOpenings++;
            return String.format("Item %d opened at %s",id,date);
        }
    }
}

class ArchiveStore{
    List<Archive> archives;
    List<String> logs;
    ArchiveStore(){
        this.archives = new ArrayList<>();
        this.logs = new ArrayList<>();
    }
    public void archiveItem(Archive item, LocalDate date){
        archives.add(item);
        int i = archives.indexOf(item);
        archives.get(i).setDateArchived(date);
        logs.add(String.format("Item %d archived at %s",archives.get(i).getId(),archives.get(i).getDate())+"\n");
    }
    public void openItem(int id, LocalDate date) throws NonExistingItemException{
        int count = (int) archives.stream().filter(i -> i.getId()==id).count();
        if (count==0){
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist",id));
        }
        int l = 0;
        for (int i = 0; i < archives.size(); i++) {
            if (archives.get(i).getId() == id){
                l = i;
                break;
            }
        }
        logs.add(archives.get(l).openArchive(date)+"\n");

    }
    public String getLog(){
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < logs.size(); i++) {
            log.append(logs.get(i));
        }
        return log.toString();
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
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