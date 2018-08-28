# Catalogue Movie

Project challenge dari dicoding dalam kelas Menjadi Android Developer Expert (MADE)
https://www.dicoding.com/academies/14/ . Big thanks to Google yang telah memberikan saya beasiswa
untuk dapat mengikuti kelas ini.

## Screenshots

<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/master/screenshots/home.png"
width="256">&nbsp;&nbsp;&nbsp;
<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/master/screenshots/search.png"
width="256">&nbsp;&nbsp;&nbsp;
<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/master/screenshots/detail.png"
width="256">&nbsp;&nbsp;&nbsp;

### Persyaratan aplikasi

* [x] Terdapat fitur untuk mencari film
* [x] Halaman detail untuk menampilkan detail fim yang telah dipilih pada halaman list film.
* [x] Tampilan poster dari film.
* [x] Navigasi untuk halaman upcoming, now playing, dan search.
* [x] Menggunakan recyclerview untuk menampilkan data bisa berupa list, atau card.
* [x] Halaman setting untuk mengganti bahasa atau localization. Aplikasi harus mendukung bahasa Indonesia dan bahasa Inggris.
* [x] Semua fungsi dari fitur project sebelumnya (Submission Project Catalogue Movie) harus tetap berjalan.
* [x] Tombol pada detail untuk menambahkan film favorit.
* [x] Halaman untuk menampilkan list movie favorit.
* [x] Menggunakan contentprovider.
* [x] Membuat aplikasi baru yaitu aplikasi favorit (boleh dengan menggunakan module baru) untuk mengakses list favorit.
* [x] Tambahkan widget favorit movie dengan menggunakan StackWidget.
* [x] Menambahkan scheduler untuk reminder
* [x] Harus bisa menyesuaikan orientasi portrait dan landscape.
* [x] Harus bisa menjaga data yang sudah dimuat / ditampilkan.


### Petunjuk menjalankan source code aplikasi

Untuk menjalankan source code aplikasi ini, anda perlu registrasi API KEY dari www.themoviedb.org
kemudian memasukkan API KEY yang telah didapat ke dalam file ***utils/UtilsConstant.java***

```
public final static String API_KEY = "Paste API KEY anda disini";
```

Kemudian hapus baris berikut pada file ***build.gradle***

```
buildConfigField 'String', "ApiKey", Catalogue_Movie_ApiKey
```

## Author

* **Muhammad Rahmatullah**

Jangan lupa untuk follow dan ★ ya :)
