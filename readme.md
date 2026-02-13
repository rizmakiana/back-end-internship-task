# Back-End Internship Task

Selamat datang di repository tugas magang Back-End. Proyek ini dibangun menggunakan Java dan memerlukan beberapa konfigurasi lokal sebelum dapat dijalankan dengan lancar.

---

## 🛠️ Prasyarat (Prerequisites)

Sebelum menjalankan aplikasi, pastikan PC Anda sudah terinstal:

1.  **Java 21**: Pastikan versi Java yang terinstal adalah JDK 21.
    * Cek dengan perintah: `java -version`
2.  **Typst Compiler**: Aplikasi ini menggunakan Typst untuk generate laporan.
    * Pastikan perintah `typst` bisa diakses via terminal.
    * Jika belum ada, silakan install melalui [Typst Official](https://github.com/typst/typst).
3.  **Database Engine**: (MySQL/PostgreSQL/Lainnya sesuai pilihan Anda).

---

## 🚀 Langkah-Langkah Instalasi

### 1. Setup Database
* Buat database baru di PC Anda.
* Buka folder `/sql` yang ada di dalam repository ini.
* **Eksekusi/Run** seluruh file SQL yang ada di dalam folder tersebut ke database Anda. File ini berisi data **Region** yang diperlukan oleh sistem.

### 2. Konfigurasi `application.properties`
Buka file konfigurasi di:
`src/main/resources/application.properties`

Sesuaikan pengaturan database sesuai dengan PC Anda:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/NAMA_DATABASE_ANDA
spring.datasource.username=USERNAME_DB_ANDA
spring.datasource.password=PASSWORD_DB_ANDA

# Pengaturan tambahan jika diperlukan
spring.jpa.hibernate.ddl-auto=update