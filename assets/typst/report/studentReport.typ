// #import "../config/config.typ": *

// #show: report_style

// #set align(center)
// DAFTAR PESERTA DIDIK BARU

// TAHUN PELAJARAN 2025/2026

// #student_report(json("../data/student2.json"))
// #signature()


#import "../config/config.typ": *

#show: report_style

#set align(center)
DAFTAR PESERTA DIDIK BARU

TAHUN PELAJARAN 2025/2026

// --- BAGIAN MAGIC NYA DI SINI ---

// 1. Cek apakah ada kiriman 'dataFile' dari Java/CLI?
//    Kalau GAK ADA (lagi preview manual), pake default "../data/student2.json"
#let raw_path = sys.inputs.at("dataFile", default: "../data/student2.json")

// 2. Logic Path Handling (PENTING!)
//    Kalau path-nya dari Java (misal: "data/file-123.json"), kita harus tambahin "/" di depan
//    biar Typst bacanya dari ROOT project.
//    Kalau path-nya default (ada ".."), biarin aja relative.
#let final_path = if raw_path.starts-with("..") { raw_path } else { "/" + raw_path }

// 3. Panggil fungsi report pake data yang udah bener
#student_report(json(final_path))

// --------------------------------

#signature()