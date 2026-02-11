#import "../config/config.typ": *

// 1. Tangkap path JSON dinamis dari Java (dataFile)
//    Gunakan fallback ke file lama lu buat ngetes manual di VS Code
#let raw_path = sys.inputs.at("dataFile", default: "../data/deposit.json")

// 2. Tambahkan "/" di depan path kalau datanya dari Java (data/...) 
//    supaya Typst mencarinya dari root project assets/typst/
#let final_path = if raw_path.starts-with("..") { raw_path } else { "/" + raw_path }

#show: report_style

// 3. Masukkan data JSON yang sudah dinamis ke fungsi laporan
#deposit_report(json(final_path))

#signature()