# Kütüphane Yönetim Sistemi - Sınav Cevabı

**Soru**: Projenizi yazılı olarak açıklayın, hangi tasarım kalıplarını kullandınız ve nasıl uyguladınız?

**Cevap**:

Projem, Java ile yazılmış bir konsol uygulaması olan Kütüphane Yönetim Sistemi’dir. Bu sistem, bir kütüphanede kitap ekleme, kullanıcı kaydetme ve kitap arama gibi işlemleri yapmamızı sağlıyor. Kullanıcılar konsolda bir menüden seçim yaparak bu işlemleri gerçekleştiriyor. Proje, ödevde istenen **Singleton** tasarım kalıbını zorunlu olarak kullanıyor ve ayrıca **Factory Method**, **Adapter**, **Facade**, **Observer** ve **Command** kalıplarını içeriyor. Bunları basitçe açıklamak gerekirse.

- **Singleton**: Sistemde tek bir veritabanı olsun istedim. `LibraryDatabase` sınıfını Singleton yaptım, böylece sadece bir tane veritabanı örneği oluyor. `getInstance()` metoduyla bu örneğe erişiliyor. Bu sayede kitaplar ve kullanıcılar hep aynı yerde tutuluyor, karışıklık olmuyor.

- **Factory Method**: Kitap eklerken iki tür kitap var: roman ve ders kitabı. `BookFactory` adında bir arayüz oluşturdum. `NovelFactory` ve `TextbookFactory` sınıfları, sırasıyla roman ve ders kitabı üretiyor. Böylece menüde “Roman Ekle” veya “Ders Kitabı Ekle” seçildiğinde, doğru kitap türü otomatik oluşturuluyor.

- **Adapter**: Eski bir kullanıcı sisteminden veri almak gerekiyordu. Eski sistemde kullanıcılar `LegacyUser` sınıfıyla tutuluyordu, ama yeni sistem `User` arayüzünü kullanıyor. `UserAdapter` sınıfıyla eski kullanıcıları yeni sisteme uydurdum. Mesela, menüde “Eski Kullanıcı Ekle” seçeneği bu adaptörü kullanıyor.

- **Observer**: Kitap eklendiğinde kullanıcılara haber vermek istedim. `LibraryDatabase`’e bir bildirim sistemi ekledim. `UserObserver` sınıfı, yeni kitap eklendiğinde mesaj alıyor. Örneğin, menüde roman eklediğimde, kayıtlı kullanıcılara “Yeni kitap eklendi” bildirimi gidiyor.

- **Command**: Kitap arama işlemini esnek yapmak için Command kalıbını kullandım. `SearchCommand` arayüzüyle, başlık (`SearchByTitleCommand`) veya yazara (`SearchByAuthorCommand`) göre arama komutları oluşturdum. Menüde “Kitap Ara” seçeneği seçildiğinde, kullanıcı “başlık” veya “yazar” seçip aramayı çalıştırıyor.
-  **Facade**: Sistemin tüm işlemlerini kolaylaştırmak için `LibraryFacade` sınıfı yazdım. Bu sınıf, kitap ekleme, kullanıcı ekleme gibi işlemleri tek bir yerden yönetiyor. Konsol menüsündeki her seçenek, bu sınıfın metodlarını çağırıyor, böylece karmaşık kodları kullanıcıdan gizliyorum.

**Nasıl Çalışıyor?** Programı çalıştırınca bir menü çıkıyor: roman/ders kitabı ekleme, yeni/eski kullanıcı ekleme, kitap arama ve kullanıcıları listeleme. Mesela, “Roman Ekle” seçersem, başlık ve yazar giriyorum, sistem romanı kaydediyor ve kullanıcılara bildirim gönderiyor. Arama yaparken, başlık veya yazara göre seçim yapıyorum, sistem ilgili komutu çalıştırıp sonuçları listeliyor. Tüm işlemler `LibraryFacade` üzerinden kolayca yapılıyor.

Bu kalıpları kullanmak, kodu düzenli ve anlaşılır yaptı. Singleton veri tutarlılığını sağladı, Factory Method farklı kitap türlerini eklememi kolaylaştırdı, Adapter eski verileri entegre etti, Facade karmaşıklığı azalttı, Observer kullanılara bildirimleri otomatikleştirdi ve Command aramaları esnek hale getirdi. 
