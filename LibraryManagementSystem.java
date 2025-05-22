import java.util.*;
//--------------------------------------------------------------------
// Singleton Pattern: Kütüphane veritabanı
/* Pattern Açıklaması: Singleton, bir sınıfın yalnızca tek bir örneğinin (instance) olmasını ve bu örneğe küresel erişim sağlanmasını garanti eder.
 * Kısa Kod Açıklaması: `LibraryDatabase` sınıfı, `getInstance()` metodu ile tek bir örnek oluşturur. `private` kurucu ve statik `instance` değişkeni ile kontrol edilir.
 * Neden Kullanıldığı: Kütüphane sisteminde tek bir veritabanı örneği olmasını sağlamak için kullanıldı. Böylece tüm işlemler (kitap ve kullanıcı yönetimi) aynı veri kaynağı üzerinde gerçekleşir ve veri tutarlılığı korunur.
 * Nasıl Kullanıldı: `LibraryDatabase` sınıfında statik bir `instance` değişkeni ve `getInstance()` metodu ile yalnızca bir örnek oluşturulur. Diğer sınıflar bu örneğe erişir.
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
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
//--------------------------------------------------------------------
// Creational Pattern: Factory Method
/* Pattern Açıklaması: Factory Method, nesne oluşturma işlemini bir fabrika sınıfına veya metoduna devrederek nesne üretimini soyutlar.
 * Kısa Kod Açıklaması: `BookFactory` arayüzü ve `NovelFactory`, `TextbookFactory` sınıfları, farklı kitap türleri (`Novel`, `Textbook`) oluşturur.
 * Neden Kullanıldığı: Farklı kitap türlerinin (roman, ders kitabı) oluşturulmasını standartlaştırmak ve istemci kodun nesne oluşturma detaylarından bağımsız olmasını sağlamak için kullanıldı.
 * Nasıl Kullanıldı: `NovelFactory` ve `TextbookFactory` kullanılarak ilgili kitap türleri oluşturulur. İstemci, hangi tür kitabın oluşturulacağını fabrika aracılığıyla belirler.
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
//--------------------------------------------------------------------
// Structural Pattern: Adapter
/* Pattern Açıklaması: Adapter, uyumsuz arayüzleri birbiriyle çalışabilir hale getirir. Eski bir sistemi yeni bir sisteme uyarlar.
 * Kısa Kod Açıklaması: `UserAdapter`, eski `LegacyUser` sınıfını yeni `User` arayüzüne uyarlar. `LegacyUser`’ın `getLegacyInfo()` metodu, `User`’ın `getUserInfo()` metoduna dönüştürülür.
 * Neden Kullanıldığı: Eski kullanıcı veri sistemini (`LegacyUser`) yeni sisteme entegre etmek için kullanıldı. Böylece eski veriler, yeni sistemle uyumlu hale getirildi.
 * Nasıl Kullanıldı: `UserAdapter`, `LegacyUser` nesnesini sarar ve `User` arayüzünü implemente ederek eski sistemi yeni sisteme bağlar.
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

//--------------------------------------------------------------------
// Behavioral Pattern: Observer
/* Pattern Açıklaması: Observer, bir nesnedeki değişiklikleri bağımlı nesnelere otomatik olarak bildirir.
 * Kısa Kod Açıklaması: `LibraryDatabase` içinde `observers` listesi tutulur ve kitap eklendiğinde `notifyObservers` ile `UserObserver` nesnelerine bildirim gönderilir.
 * Neden Kullanıldığı: Kitap eklendiğinde kullanıcıların otomatik olarak bilgilendirilmesi için kullanıldı. Böylece sistem, değişiklikleri dinamik olarak bildirir.
 * Nasıl Kullanıldı: `UserObserver` sınıfı, `Observer` arayüzünü implemente eder ve `LibraryDatabase`’e kayıtlı kullanıcılara bildirim gönderir.
 */
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
//-------------------------------------------------------------------
// Behavioral Pattern: Strategy
/* Pattern Açıklaması: Strategy, bir algoritma ailesini tanımlar ve bu algoritmaları değiştirilebilir hale getirir. İstemci, çalışma zamanında stratejiyi seçer.
 * Kısa Kod Açıklaması: `SearchStrategy` arayüzü, `SearchByTitle` ve `SearchByAuthor` sınıflarıyla kitap aramayı farklı şekillerde gerçekleştirir.
 * Neden Kullanıldığı: Kitapları farklı kriterlere (başlık veya yazar) göre aramak için esnek bir yapı sağlamak amacıyla kullanıldı. Kullanıcı, arama türünü seçebilir.
 * Nasıl Kullanıldı: `LibraryFacade` içinde, kullanıcıdan alınan girdiye göre (`baslik` veya `yazar`) uygun strateji seçilir ve arama yapılır.
 */
interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}

class SearchByTitle implements SearchStrategy {
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}

class SearchByAuthor implements SearchStrategy {
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}
//--------------------------------------------------------------------
// Structural Pattern: Facade
/* Pattern Açıklaması: Facade, karmaşık bir alt sistemi basit bir arayüzle sararak kullanımı kolaylaştırır.
 * Kısa Kod Açıklaması: `LibraryFacade` sınıfı, kitap ekleme, kullanıcı ekleme ve arama gibi işlemleri tek bir arayüzde toplar.
 * Neden Kullanıldığı: Kütüphane sistemindeki karmaşık işlemleri (veritabanı erişimi, fabrika kullanımı, bildirimler) tek bir basit arayüzle yönetmek için kullanıldı. Kullanıcı, alt sistem detaylarını bilmeden işlem yapabilir.
 * Nasıl Kullanıldı: `LibraryFacade`, tüm işlemleri koordine eder ve istemciye (konsol uygulamasına) sade bir API sunar.
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

    public void searchBooks(String keyword, String strategyType) {
        SearchStrategy strategy = strategyType.equalsIgnoreCase("baslik") ?
                new SearchByTitle() : new SearchByAuthor();
        List<Book> results = strategy.search(database.getBooks(), keyword);
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
                    String strategy = scanner.nextLine();
                    library.searchBooks(keyword, strategy);
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