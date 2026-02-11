#let letterhead() = {
  show <h1>: set text(size: 12pt)
  show <h2>: set text(size: 14pt, weight: "bold")
  show <h3>: set text(red, weight: "bold")
  show <h4>: set text(size: 9pt)

  grid(
    columns: (100pt, auto),
    rows: (auto, auto),
    image("../assets/image/malahayati.png", width: 85%),
    [
      #set align(center)
      #set par(leading: 1em, spacing: 1em)
      
      #[YAYASAN BINA BERSAMA INDOMAS]  \ 
      #[SEKOLAH MENENGAH ATAS (SMA) MALAHAYATI] <h2>\
      #[TERAKREDITASI "A"] <h3>\
      #[JL. Bima No. 3 (Gongseng Raya) Cijantung Pasar Rebo, Jakarta Timur Telp. (021) 870 1744\
      website : #link("http://malahayatiislamicschool.sch.id")
    ] <h4>
    ]
  )
  line(length: 100%, stroke: 2pt + black)
}

#let student_report(data) = {
  table(
    columns: (auto, 70pt, 120pt, 1fr),
    inset: (x, y) => if y == 0 { 10pt } else { 7pt },
    align: (x, y) => if y == 0 { center + horizon } else { left + horizon },
    stroke: 0.5pt + gray,
    
    fill: (x, y) => if y == 0 { gray.lighten(80%) }, 
    table.header([*No.*], [*No. Peserta*], [*Nama Siswa*], [*Tempat/Tanggal Lahir*]),
    
    ..data.enumerate().map(((index, item)) => (
      [#(index + 1)],
      [#item.id],
      [#item.name],
      [#item.birthPlace, #item.birthDate],
    )
  )
  .flatten()
  )
}



#let report_style(body) = {
  set text(font: "Liberation Serif", size: 12pt)  
  set page(margin: (top: 1cm, bottom: auto, x: auto))
  set par(spacing: 1.5em, leading: 1.5em)
  letterhead()
  body 
} 

#let classroom_report(data) = {
  table(
    columns: (auto, 80pt, 150pt, auto, 90pt),
    inset: (x, y) => if y == 0 { 10pt } else { 7pt },
    align: (x, y) => if y == 0 { center + horizon } else { left + horizon },
    stroke: 0.5pt + gray,
    
    fill: (x, y) => if y == 0 { gray.lighten(80%) }, 
    table.header([*No.*], [*Kode Kelas*], [*Jurusan*], [*Tingkat Kelas*], [*Nama Kelas*]),
    
    ..data.enumerate().map(((index, item)) => (
      [#(index + 1)],
      [#item.code],
      [#item.department],
      [#item.grade],
      [#item.section]
    )).flatten()
  )
}

#let deposit_report(data) = {
  
  // BAGIAN 1: HEADER INFO SISWA
  grid(
    columns: (50pt, 10pt, auto),
    row-gutter: 1em,
    [Nama], [:], [#data.name],
    [NIS], [:], [#data.id],
    // [Kelas], [:], [#data.kelas]
  ) 
  
  v(1em) // Memberi jarak sedikit sebelum tabel

  // BAGIAN 2: TABEL TRANSAKSI
  table(
    columns: (30pt, 80pt, 150pt, 97pt, 97pt),
    inset: (x, y) => if y == 0 { 10pt } else { 7pt },
    align: (x, y) => if y == 0 { center + horizon } else { left + horizon },
    stroke: 0.5pt + gray,
    
    fill: (x, y) => if y == 0 { gray.lighten(80%) }, 
    table.header([*No.*], [*Tanggal*],[*No. Transaksi*], [*Deposit*], [*Penarikan*]),
    
    // Perbaikan: Loop ke dalam 'data.transaksi', bukan 'data' root
    ..data.transactions.enumerate().map(((index, item)) => (
      [#(index + 1)],
      [#item.date],
      [#item.reference],
      
      // Logika: Jika isi "-", tampilkan "-" saja. Jika angka, tambah "Rp."
      if str(item.deposit) == "-" { [-] } else { [Rp. #item.deposit] },
      if str(item.withdrawal) == "-" { [-] } else { [Rp. #item.withdrawal] }
    )).flatten()
  )

  // BAGIAN 3: TOTAL (Footer Tabel)
  // Menggunakan inset yang sama dengan tabel (7pt) agar teks lurus
  grid(
    columns: (260pt, 97pt, 97pt), 
    inset: 7pt, 
    align: (right, left, left), // Label kanan, angka kiri (mengikuti style tabel)
    [*Total : *], 
    [*Rp. #data.totalDeposit*], 
    [*Rp. #data.totalWithdrawal*]
  )

  v(1em)

  // BAGIAN 4: SISA TABUNGAN
  grid(
    columns: (auto, auto, auto),
    column-gutter:  10pt,
    [*Sisa Tabungan*], [*:*], [*Rp. #data.currentDeposit*]
  )
}



#let payment_report(data) = {
  
  columns(2, gutter: 45pt,)[ 
    #grid(
      columns: (50pt, 10pt, auto),
      row-gutter: 1em,
      [Nama], [:], [#data.name],
      [NIS], [:], [#data.student_id],
      [Kelas], [:], [#data.classroom]
    ) 

    #colbreak()
    
    #grid(
      columns: (100pt, 10pt, auto),
      row-gutter: 1em,
      [Tanggal], [:], [#data.date],
      [No. Transaki], [:], [#data.transactionReference],
    ) 
  ]
  
  v(1em)

  table(
    columns: (32pt, 300pt, 120pt),
    inset: (x, y) => if y == 0 { 10pt } else { 7pt },
    align: (x, y) => if y == 0 { center + horizon } else { left + horizon },
    stroke: 0.5pt + gray,
    
    fill: (x, y) => if y == 0 { gray.lighten(80%) }, 
    table.header([*No.*], [*Tagihan*],[*Jumlah*]),
    
    ..data.transaction.enumerate().map(((index, item)) => (
      [#(index + 1)],
      [#item.name],
      [Rp. #item.amount],
    )).flatten()
  )
  
  grid(
    columns: (332pt, 120pt), 
    inset: 7pt, 
    align: (right, center), // Label kanan, angka kiri (mengikuti style tabel)
    [*Total : *], [*Rp. #data.totalTransaction*], 
  )
  v(1em)
}

#let months = (
  "Januari",
  "Februari",
  "Maret",
  "April",
  "Mei",
  "Juni",
  "Juli",
  "Agustus",
  "September",
  "Oktober",
  "November",
  "Desember"
)

#let days = (
  "Senin",
  "Selasa",
  "Rabu",
  "Kamis",
  "Jum'at",
  "Sabtu",
  "Minggu"
)

#let signature() = {
  align(right)[
    #block(inset: (right: 10pt), align(left)[
      #let today = datetime.today(offset: +7)
      #let today_month = months.at(today.month() - 1)
      #let current_day = days.at(today.weekday() - 1)
      
      Jakarta, #current_day #today.day() #today_month #today.year() #h(1.5em)

      #h(1.5em)Kepala SMA Malahayati
    
    
      #v(2cm) // Jarak untuk tanda tangan
    
      #h(1em) Alip Arodabiro, S.Pd., M.Pd 
    ])
  ]
}

#let signature2() = {
  align(right)[
    #block(inset: (right: 10pt), align(left)[
      #let today = datetime.today(offset: +7)
      #let today_month = months.at(today.month() - 1)
      #let current_day = days.at(today.weekday() - 1)
      
      Jakarta, #box(width: 8em, repeat[.])

    
    
      #v(2cm) // Jarak untuk tanda tangan
    
      #h(1em) Alip Arodabiro, S.Pd., M.Pd  \
      #h(1.5em)Kepala SMA Malahayati
      
    ])
  ]
}
