# Catalogue Movie

Project challenge dari dicoding dalam kelas Menjadi Android Developer Expert (MADE)
https://www.dicoding.com/academies/14/ . Big thanks to Google yang telah memberikan saya beasiswa
untuk dapat mengikuti kelas ini.

## Screen Shots

<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/stage-1/screenshots/home.png"
width="256">&nbsp;&nbsp;&nbsp;
<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/stage-1/screenshots/search.png"
width="256">&nbsp;&nbsp;&nbsp;
<img src="https://github.com/muhrahmatullah/Dicoding-Movie-Catalogue/blob/stage-1/screenshots/detail.png"
width="256">&nbsp;&nbsp;&nbsp;

### Persyaratan aplikasi

* [x] Terdapat fitur untuk mencari film
* [x] Halaman detail untuk menampilkan detail fim yang telah dipilih pada halaman list film.
* [x] Tampilan poster dari film.

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