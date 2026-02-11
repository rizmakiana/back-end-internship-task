#import "../util/text.typ": *
#import "../config/config.typ": *

#let data = json("../data/mahastudent.json")

#for student in data {

  show: report_style
  
  bold_12("LEMBAR PENILAIAN KERJA PRAKTEK", center)
  
  [Mahasiswa di bawah ini:]
  grid(
    columns: 3,
    row-gutter: 10pt,
    column-gutter: 5pt,
    [Nama],[:],[#student.name],
    [NPM],[:],[#student.id],
    [Program Studi],[:],[Teknik Informatika],
    [Fakultas],[:],[Teknik dan Ilmu Komputer],
    [Universitas],[:],[Universitas Indraprasta PGRI]
  )
  
  [Telah melaksanakan Kerja Praktek selama 3 bulan, pada tanggal 3 November 2025 s.d. 3 Februari 2026 di perusahaan kami.]
  grid(
    columns: 3,
    row-gutter: 10pt,
    column-gutter: 5pt,
    [Nama Perusahaan],[:],[Sekolah Menengah Atas (SMA) Malahayati School],
    [Bidang Usaha],[;],[Pendidikan],
    [Alamat],[:],[Jl. Bima no 3 RT/RW 08/07, Cijantung, Jakarta Timur],
    [Topik Khusus],[:],[-],
  )
  
  // linebreak()
  
  [Hasil penilaian selama pelaksanaan KP adalah sebagai berikut:]
  grid(
    columns: (3.3em, 8.5em, auto, auto),
    row-gutter: 9pt,
    [],[Kehadiran],[:],[],
    [],[Kedisiplinan],[:],[],
    [],[Keaktivan],[:],[],
    [],[Penguasaan Materi],[:],[],
    [],[Nilai Akhir],[:],[]
  )
  [#h(3.3em)Demikian hasil Penelitian yang kami berikan, dan semoga dapat menjadikan perhatian]
  
  signature2()
  
  pagebreak()

}