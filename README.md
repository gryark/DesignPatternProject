# Kütüphane Yönetim Sistemi -SİZİN İÇİN DETAYLI-

Bu proje, Java ile geliştirilmiş bir konsol uygulamasıdır ve bir kütüphane yönetim sistemini simüle eder. Kullanıcılar, kitap ekleme, kullanıcı kaydı ve kitap arama gibi işlemleri gerçekleştirebilir. Proje, **Singleton** tasarım kalıbını zorunlu olarak içerir ve ek olarak **Factory Method**, **Adapter**, **Facade**, **Observer** ve **Command** kalıplarını kullanır.

## Proje Genel Bakış
Kütüphane Yönetim Sistemi, kullanıcıların konsol üzerinden aşağıdaki işlemleri yapmasına olanak tanır:
- Roman veya ders kitabı ekleme
- Yeni veya eski sistemden kullanıcı ekleme
- Kitap arama (başlık veya yazara göre)
- Kayıtlı kullanıcıları listeleme

Sistem, **Creational**, **Structural** ve **Behavioral** kategorilerden toplam altı tasarım kalıbını entegre eder:
- **Creational**: Singleton, Factory Method
- **Structural**: Adapter, Facade
- **Behavioral**: Observer, Command

## Menü Seçenekleri ve Kapsanan Tasarım Kalıpları

Aşağıda, konsol menüsündeki her seçeneğin açıklaması ve kapsadığı tasarım kalıpları listelenmiştir.

### 1. Roman Ekle
- **Açıklama**: Kullanıcı, bir romanın başlığını ve yazarını girerek kütüphaneye ekler.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `addNovel` metodu, `LibraryDatabase.getInstance()` ile tekil veritabanı örneğine erişir.
  - **Factory Method**: `NovelFactory` kullanılarak `Novel` türünde bir kitap nesnesi oluşturulur.
  - **Observer**: Kitap eklendiğinde, `LibraryDatabase` içindeki `addBook` metodu `notifyObservers` ile kayıtlı kullanıcılara bildirim gönderir.
  - **Facade**: `LibraryFacade` sınıfı, roman ekleme işlemini basit bir arayüzle koordine eder.

### 2. Ders Kitabı Ekle
- **Açıklama**: Kullanıcı, bir ders kitabının başlığını ve yazarını girerek kütüphaneye ekler.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `addTextbook` metodu, `LibraryDatabase.getInstance()` ile tekil veritabanı örneğine erişir.
  - **Factory Method**: `TextbookFactory` kullanılarak `Textbook` türünde bir kitap nesnesi oluşturulur.
  - **Observer**: Kitap eklendiğinde, `LibraryDatabase` içindeki `addBook` metodu `notifyObservers` ile bildirim gönderir.
  - **Facade**: `LibraryFacade`, ders kitabı ekleme işlemini basit bir arayüzle yönetir.

### 3. Kullanıcı Ekle
- **Açıklama**: Kullanıcı, yeni bir kullanıcı adı ve ID'si girerek sisteme ekler.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `addUser` metodu, `LibraryDatabase.getInstance()` ile tekil veritabanına erişir.
  - **Observer**: Yeni kullanıcı eklendiğinde, `UserObserver` nesnesi `LibraryDatabase`'e eklenir ve bildirim alabilir.
  - **Facade**: `LibraryFacade`, kullanıcı ekleme işlemini basitleştirir.

### 4. Eski Kullanıcı Ekle
- **Açıklama**: Kullanıcı, eski sistemden bir kullanıcıyı (tam ad ve kod) girerek sisteme ekler.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `addLegacyUser` metodu, `LibraryDatabase.getInstance()` ile tekil veritabanına erişir.
  - **Adapter**: `UserAdapter`, eski `LegacyUser` nesnesini yeni `User` arayüzüne uyarlar.
  - **Observer**: Eski kullanıcı eklendiğinde, `UserObserver` nesnesi `LibraryDatabase`'e eklenir ve bildirim alabilir.
  - **Facade**: `LibraryFacade`, eski kullanıcı ekleme işlemini basit bir arayüzle koordine eder.

### 5. Kitap Ara
- **Açıklama**: Kullanıcı, bir anahtar kelime ve arama türü (başlık veya yazar) girerek kitapları arar.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `searchBooks` metodu, `LibraryDatabase.getInstance()` ile veritabanına erişir.
  - **Command**: `SearchCommand` arayüzü, `SearchByTitleCommand` veya `SearchByAuthorCommand` komutlarını kullanarak aramayı gerçekleştirir.
  - **Facade**: `LibraryFacade`, arama işlemini basit bir arayüzle sunar.

### 6. Kullanıcıları Listele
- **Açıklama**: Sisteme kayıtlı tüm kullanıcıları listeler.
- **Kapsanan Pattern'ler**:
  - **Singleton**: `LibraryFacade` içindeki `listUsers` metodu, `LibraryDatabase.getInstance()` ile veritabanına erişir.
  - **Adapter**: Listelenen kullanıcılar arasında eski sistemden uyarlanmış (`UserAdapter`) kullanıcılar da yer alabilir.
  - **Facade**: `LibraryFacade`, kullanıcı listeleme işlemini basit bir arayüzle sağlar.

## Özet Tablosu

| Menü Seçeneği            | Singleton | Factory Method | Adapter | Facade | Observer | Command |
|--------------------------|-----------|----------------|---------|--------|----------|---------|
| 1. Roman Ekle            | ✓         | ✓              |         | ✓      | ✓        |         |
| 2. Ders Kitabı Ekle      | ✓         | ✓              |         | ✓      | ✓        |         |
| 3. Kullanıcı Ekle        | ✓         |                |         | ✓      | ✓        |         |
| 4. Eski Kullanıcı Ekle   | ✓         |                | ✓       | ✓      | ✓        |         |
| 5. Kitap Ara             | ✓         |                |         | ✓      |          | ✓       |
| 6. Kullanıcıları Listele | ✓         |                | ✓       | ✓      |          |         |

## Genel Değerlendirme
- **Facade** ve **Singleton**, tüm menü seçeneklerinde yer alır çünkü `LibraryFacade` her işlemi koordine eder ve `LibraryDatabase` tekil bir örnek olarak kullanılır.
- **Factory Method**, sadece kitap ekleme işlemlerinde (Roman ve Ders Kitabı) devreye girer.
- **Adapter**, eski kullanıcı ekleme ve kullanıcı listeleme işlemlerinde kullanılır.
- **Observer**, kitap ekleme ve kullanıcı ekleme işlemlerinde bildirim mekanizması olarak çalışır.
- **Command**, yalnızca kitap arama işleminde kullanılır.

## Kurulum ve Kullanım
1. **Gereksinimler**:
   - Java Development Kit (JDK) yüklü olmalıdır.
2. **Kompilasyon**:
   ```bash
   javac LibraryManagementSystem.java
   ```
3. **Çalıştırma**:
   ```bash
   java LibraryManagementSystem
   ```
4. **Kullanım**:
   - Konsol menüsünden seçenekleri (1-7) seçerek işlemleri gerçekleştirin.
   - Örnek: Roman eklemek için `1` seçin, başlık ve yazar bilgilerini girin.

## Örnek Çalıştırma
```plaintext
Kütüphane Yönetim Sistemi
1. Roman Ekle
2. Ders Kitabı Ekle
3. Kullanıcı Ekle
4. Eski Kullanıcı Ekle
5. Kitap Ara
6. Kullanıcıları Listele
7. Çıkış
Bir seçenek seçin: 5
Arama anahtar kelimesini girin: 1984
Arama türü (baslik/yazar): baslik
Arama sonuçları:
1984, Yazar: George Orwell
```

## Notlar
- Proje, basit ve anlaşılır bir yapıya sahiptir.
- Ek özellikler veya özelleştirmeler için kodda değişiklik yapılabilir.
- Daha fazla bilgi veya destek için iletişime geçebilirsiniz.
