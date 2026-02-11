#import "../config/config.typ": *

#show: report_style

#set align(center)
DAFTAR KELAS SMA MALAHAYATI 

TAHUN PELAJARAN 2025/2026
#let raw_path = sys.inputs.at("dataFile", default: "../data/student2.json")

// 2. Logic Path Handling (PENTING!)
//    Kalau path-nya dari Java (misal: "data/file-123.json"), kita harus tambahin "/" di depan
//    biar Typst bacanya dari ROOT project.
//    Kalau path-nya default (ada ".."), biarin aja relative.
#let final_path = if raw_path.starts-with("..") { raw_path } else { "/" + raw_path }

#classroom_report(json(final_path))
#signature()
