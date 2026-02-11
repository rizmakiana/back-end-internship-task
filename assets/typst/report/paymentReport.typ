#import "../config/config.typ": *

#set page(background: rotate(24deg,
  text(150pt, fill: rgb("FFCBC4"))[
    *LUNAS*
  ]
))

#show: report_style

#payment_report(json("../data/payment.json"))
#signature()