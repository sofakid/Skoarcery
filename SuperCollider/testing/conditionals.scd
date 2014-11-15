
(
"
.h  @hats => @instrument ]] D.C.
.s @snare => @instrument } ) D.C.
.k  @kick => @instrument ) D.C.

<? .a <_a, _c, c, _e, e, _a> {! ]]] ooo/ !} ?>

.a {? 10.rand <= 5 ?? 0 ]] ]] :: 4 ] ?} ]


.a {? 10.rand == 5 ?? ] ] ] ] ]] ] ]] :: ] ] }} ?}

.a {? 10.rand >= 5 ?? ] ] ] ] ]] ] ]] :: }}
      10.rand <= 6 ?? ] ] ] ] ]] ] ]] :: } )
      11.rand <= 3 ?? ] ] }} ?}

".skoar.play;
)


(
"
{? 4.rand <= 6 ?? a :: c ?} ] D.C.
".skoar.draw_skoarpions.play;
)

Exception.debug = false;