#import "../util/text.typ": *
#import "../config/config.typ": *

#let data = json("../data/mahastudent.json")

#for student in data {
  
  show: report_style

  set align(center)


  bold_12("LEMBAR PENGESAHAN", center)
  linebreak()
  linebreak()
  bold_12("LAPORAN KERJA PRAKTIK", center)
  [BIDANG: TATA USAHA]

  linebreak()
  linebreak()
  [DI SEKOLAH MENENGAH ATAS (SMA) MALAHAYATI SCHOOL \ ]

  [PADA 3 NOVEMBER 2025 - 3 FEBRUARI 2026]

  linebreak()
  linebreak()

  [Disusun oleh:]
  linebreak()

  grid(
    columns: (auto, 15pt, auto),
    align: (auto,auto, left),
    row-gutter: 15pt,
    [Nama],[:],[#student.name],
    [NPM],[:],[#student.id]
  )

  linebreak()
  linebreak()

  columns(2, gutter: 8pt)[
    Mengetahui,
    #linebreak()
    #linebreak()
    #linebreak()
    #linebreak()
    Alip Arodabiro S.Pd, M.Pd.
  
    #colbreak()
  
    Pembimbing,
    #linebreak()
    #linebreak()
    #linebreak()
    #linebreak()
    Nurhijah Nellywati
    
  ]
}
