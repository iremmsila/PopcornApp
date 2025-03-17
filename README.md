![image](https://github.com/user-attachments/assets/e3ddef7e-bb1a-4333-9e74-7b80ebbe77a7)
projeye başlamadan önce figma ile yol haritası çizilmiş, ekranların kabataslak olarak nasıl görüneceği belirlenmiş ve bu tasarımdan büyük ölçüde sapılmadan uygulama son halini bulmuştur.

Jetpack Compose ve Kotlin teknolojileri kullanılmıştır.
Dinamik renklendirme ile uygulamam light mode ve dark mode gibi seçeneklere kolayca uyum sağlayabilmektedir.
![image](https://github.com/user-attachments/assets/64239fb0-0523-4d60-bada-e91d9513c6ec)

Uygulamama horizontal pager özelliği ekleyerek, trend filmleri dinamik bir şekilde ekranda göstermeye başladım. Bu özellik, kullanıcıların filmler arasında yatay kaydırarak geçiş yapabilmesini sağladı. Trend filmleri her bir kategori için yatay bir şekilde sıralayarak, kullanıcıların film seçimlerini daha eğlenceli ve kolay hale getirdim.
![image](https://github.com/user-attachments/assets/b2f4cedb-3175-4a48-ac50-65d3d95fe59a)

o	Filmlerin detay bilgilerini gösteren bir ekran tasarladım. Bu ekranda, her bir filmin başlığı, açıklaması, çıkış tarihi, oyuncular ve yönetmen gibi detayları Realm veritabanından çekiyorum.
o	Detay ekranındaki veriler, Realm veritabanında saklanan verilerle dinamik olarak güncelleniyor. Bu sayede çevrimdışı kullanımda da kullanıcılar detaylı bilgilere erişebiliyor.
![image](https://github.com/user-attachments/assets/8396c5a9-256d-4906-a91c-01e7d40e7582)
![image](https://github.com/user-attachments/assets/774c86df-47c2-4518-aceb-c7b71c79036f)

o	Ana ekrana "Daha Fazla" butonu ekledim. Kullanıcı bu butona tıkladığında, o kategori altındaki tüm filmler veritabanından veya API'den çekilip gösterildi.
o	Bu özellik, kullanıcıların her kategoriye ait film seçeneklerini görmek için daha fazla sayfa gezmelerine gerek kalmadan, ihtiyaç duyduklarında tüm içeriğe hızlıca ulaşmalarını sağladı.
o	Veritabanı ve API Entegrasyonu: "Daha Fazla" butonuna tıklama işlemi, mevcut veritabanındaki ya da API'deki verilerin yüklenmesine olanak tanıyacak şekilde tasarlandı. Eğer veriler zaten yüklenmişse, mevcut veritabanındaki içerikler çekildi. Eğer veriler güncellenmesi gereken verilerse, API'den çekilmeye devam etti.
![image](https://github.com/user-attachments/assets/a7fe9861-0c8e-48cd-b30b-21ff3dc6ec6b)



