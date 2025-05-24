import java.util.*;
//Kütüphane Yönetim Sistemi
/* Pattern Açıklaması: Bu sistem, kütüphane yönetimi için çeşitli tasarım desenlerini kullanarak kitap ekleme, kullanıcı ekleme ve arama işlemlerini gerçekleştiren bir uygulamadır.
 * Kısa Kod Açıklaması: Singleton, Factory Method, Adapter, Observer ve Command desenleri kullanılarak yapılandırılmıştır.
 * Neden Kullanıldığı: Kütüphane sisteminin esnekliğini artırmak ve kodun yeniden kullanılabilirliğini sağlamak için bu desenler kullanıldı.
 * Nasıl Kullanıldı: Her bir desen, belirli bir işlevi yerine getirmek için uygun sınıflar ve arayüzlerle uygulanmıştır.
 */
// Singleton Pattern: Tek bir Kütüphane veritabanı
/* Pattern Açıklaması: Singleton, bir sınıfın yalnızca tek bir örneğinin (instance) olmasını ve bu örneğe küresel erişim sağlanmasını garanti eder.
 * Kısa Kod Açıklaması: `LibraryDatabase` sınıfı, `getInstance()` metodu ile tek bir örnek oluşturur. `private` kurucu ve statik `instance` değişkeni ile kontrol edilir.
 * Neden Kullanıldığı: Kütüphane sisteminde tek bir veritabanı örneği olmasını sağlamak için kullanıldı. Böylece tüm işlemler aynı veri kaynağı üzerinde gerçekleşir.
 * Nasıl Kullanıldı: `LibraryDatabase` sınıfında statik bir `instance` değişkeni ve `getInstance()` metodu ile yalnızca bir örnek oluşturulur.
 */
class LibraryDatabase {
    private static LibraryDatabase instance;
    private List<Book> books;
    private List<User> users;
    private List<Observer> observers;

    private LibraryDatabase() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static LibraryDatabase getInstance() {
        if (instance == null) {
            instance = new LibraryDatabase();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers("Yeni kitap eklendi: " + book.getTitle());
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    // Observer Pattern: Bildirim sistemi
    /* Pattern Açıklaması: Observer, bir nesnedeki değişiklikleri bağımlı nesnelere otomatik olarak bildirir.
     * Kısa Kod Açıklaması: `observers` listesi tutulur ve kitap eklendiğinde `notifyObservers` ile bildirim gönderilir.
     * Neden Kullanıldığı: Kitap eklendiğinde kullanıcıların otomatik olarak bilgilendirilmesi için kullanıldı.
     * Nasıl Kullanıldı: `UserObserver` sınıfı, `Observer` arayüzünü implemente eder ve bildirimleri alır.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// Creational Pattern: Factory Method
/* Pattern Açıklaması: Factory Method, nesne oluşturma işlemini bir fabrika sınıfına devrederek nesne üretimini soyutlar.
 * Kısa Kod Açıklaması: `BookFactory` arayüzü ve `NovelFactory`, `TextbookFactory` sınıfları, farklı kitap türleri oluşturur.
 * Neden Kullanıldığı: Farklı kitap türlerinin (roman, ders kitabı) oluşturulmasını standartlaştırmak için kullanıldı.
 * Nasıl Kullanıldı: `LibraryFacade` içinde `NovelFactory` ve `TextbookFactory` kullanılarak kitaplar oluşturulur.
 */
abstract class Book {
    protected String title;
    protected String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

class Novel extends Book {
    public Novel(String title, String author) {
        super(title, author);
    }
}

class Textbook extends Book {
    public Textbook(String title, String author) {
        super(title, author);
    }
}

interface BookFactory {
    Book createBook(String title, String author);
}

class NovelFactory implements BookFactory {
    public Book createBook(String title, String author) {
        return new Novel(title, author);
    }
}

class TextbookFactory implements BookFactory {
    public Book createBook(String title, String author) {
        return new Textbook(title, author);
    }
}

// Structural Pattern: Adapter
/* Pattern Açıklaması: Adapter, uyumsuz arayüzleri birbiriyle çalışabilir hale getirir.
 * Kısa Kod Açıklaması: `UserAdapter`, eski `LegacyUser` sınıfını yeni `User` arayüzüne uyarlar.
 * Neden Kullanıldığı: Eski kullanıcı veri sistemini yeni sisteme entegre etmek için kullanıldı.
 * Nasıl Kullanıldı: `UserAdapter`, `LegacyUser` nesnesini sarar ve `User` arayüzünü implemente eder.
 */
interface User {
    String getUserInfo();
}

class NewUser implements User {
    private String name;
    private String id;

    public NewUser(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getUserInfo() {
        return "Kullanıcı: " + name + ", ID: " + id;
    }
}

class LegacyUser {
    private String fullName;
    private int userCode;

    public LegacyUser(String fullName, int userCode) {
        this.fullName = fullName;
        this.userCode = userCode;
    }

    public String getLegacyInfo() {
        return fullName + " (" + userCode + ")";
    }
}

class UserAdapter implements User {
    private LegacyUser legacyUser;

    public UserAdapter(LegacyUser legacyUser) {
        this.legacyUser = legacyUser;
    }

    public String getUserInfo() {
        return "Uyarlanmış Kullanıcı: " + legacyUser.getLegacyInfo();
    }
}

// Behavioral Pattern: Observer
interface Observer {
    void update(String message);
}

class UserObserver implements Observer {
    private String userId;

    public UserObserver(String userId) {
        this.userId = userId;
    }

    public void update(String message) {
        System.out.println("Kullanıcı " + userId + " için bildirim: " + message);
    }
}

// Behavioral Pattern: Command
/* Pattern Açıklaması: Command, bir işlemi bir nesne olarak kapsüller ve bu işlemi parametreleştirerek çalıştırılmasını sağlar.
 * Kısa Kod Açıklaması: `SearchCommand` arayüzü, `SearchByTitleCommand` ve `SearchByAuthorCommand` sınıflarıyla kitap aramayı kapsüller.
 * Neden Kullanıldığı: Kitap arama işlemlerini (başlık veya yazar) bağımsız komutlar olarak düzenlemek ve esnek bir şekilde çalıştırmak için kullanıldı.
 * Nasıl Kullanıldı: `LibraryFacade` içinde, kullanıcı girdisine göre uygun komut seçilir ve `execute` metodu ile arama yapılır.
 */
interface SearchCommand {
    List<Book> execute(List<Book> books, String keyword);
}

class SearchByTitleCommand implements SearchCommand {
    public List<Book> execute(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}

class SearchByAuthorCommand implements SearchCommand {
    public List<Book> execute(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}

// Structural Pattern: Facade
/* Pattern Açıklaması: Facade, karmaşık bir alt sistemi basit bir arayüzle sararak kullanımı kolaylaştırır.
 * Kısa Kod Açıklaması: `LibraryFacade` sınıfı, kitap ekleme, kullanıcı ekleme ve arama gibi işlemleri tek bir arayüzde toplar.
 * Neden Kullanıldığı: Kütüphane sistemindeki karmaşık işlemleri tek bir basit arayüzle yönetmek için kullanıldı.
 * Nasıl Kullanıldı: `LibraryFacade`, tüm işlemleri koordine eder ve istemciye sade bir API sunar.
 */
class LibraryFacade {
    private LibraryDatabase database;
    private BookFactory novelFactory;
    private BookFactory textbookFactory;

    public LibraryFacade() {
        database = LibraryDatabase.getInstance();
        novelFactory = new NovelFactory();
        textbookFactory = new TextbookFactory();
    }

    public void addNovel(String title, String author) {
        Book book = novelFactory.createBook(title, author);
        database.addBook(book);
    }

    public void addTextbook(String title, String author) {
        Book book = textbookFactory.createBook(title, author);
        database.addBook(book);
    }

    public void addUser(String name, String id) {
        User user = new NewUser(name, id);
        database.addUser(user);
        database.addObserver(new UserObserver(id));
    }

    public void addLegacyUser(String fullName, int userCode) {
        User user = new UserAdapter(new LegacyUser(fullName, userCode));
        database.addUser(user);
        database.addObserver(new UserObserver("Eski-" + userCode));
    }

    public void searchBooks(String keyword, String commandType) {
        SearchCommand command = commandType.equalsIgnoreCase("baslik") ?
                new SearchByTitleCommand() : new SearchByAuthorCommand();
        List<Book> results = command.execute(database.getBooks(), keyword);
        System.out.println("Arama sonuçları:");
        for (Book book : results) {
            System.out.println(book.getTitle() + ", Yazar: " + book.getAuthor());
        }
    }

    public void listUsers() {
        System.out.println("Kullanıcılar:");
        for (User user : database.getUsers()) {
            System.out.println(user.getUserInfo());
        }
    }
}

// Ana konsol uygulaması
public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryFacade library = new LibraryFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nKütüphane Yönetim Sistemi");
            System.out.println("1. Roman Ekle");
            System.out.println("2. Ders Kitabı Ekle");
            System.out.println("3. Kullanıcı Ekle");
            System.out.println("4. Eski Kullanıcı Ekle");
            System.out.println("5. Kitap Ara");
            System.out.println("6. Kullanıcıları Listele");
            System.out.println("7. Çıkış");
            System.out.print("Bir seçenek seçin: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            if (choice == 7) break;

            switch (choice) {
                case 1:
                    System.out.print("Roman başlığını girin: ");
                    String novelTitle = scanner.nextLine();
                    System.out.print("Yazarı girin: ");
                    String novelAuthor = scanner.nextLine();
                    library.addNovel(novelTitle, novelAuthor);
                    break;
                case 2:
                    System.out.print("Ders kitabı başlığını girin: ");
                    String textbookTitle = scanner.nextLine();
                    System.out.print("Yazarı girin: ");
                    String textbookAuthor = scanner.nextLine();
                    library.addTextbook(textbookTitle, textbookAuthor);
                    break;
                case 3:
                    System.out.print("Kullanıcı adını girin: ");
                    String userName = scanner.nextLine();
                    System.out.print("Kullanıcı ID'sini girin: ");
                    String userId = scanner.nextLine();
                    library.addUser(userName, userId);
                    break;
                case 4:
                    System.out.print("Eski kullanıcı tam adını girin: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Eski kullanıcı kodunu girin: ");
                    int userCode = scanner.nextInt();
                    library.addLegacyUser(fullName, userCode);
                    break;
                case 5:
                    System.out.print("Arama anahtar kelimesini girin: ");
                    String keyword = scanner.nextLine();
                    System.out.print("Arama türü (baslik/yazar): ");
                    String commandType = scanner.nextLine();
                    library.searchBooks(keyword, commandType);
                    break;
                case 6:
                    library.listUsers();
                    break;
                default:
                    System.out.println("Geçersiz seçenek!");
            }
        }
        scanner.close();
    }
}