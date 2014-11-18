(
"
{: _a, _c, c, _e, e, _a :: ]]] ooo/ :}

<_a, _c, c, _e, e, _a>.{: ]]] ooo/ :}


".skoar.play;
)

(
"
.h  @hats => @instrument ]] D.C.
.s @snare => @instrument } ) D.C.
.k  @kick => @instrument ) D.C.

.a {: _a, _c, c, _e, e, _a :: ]]] ooo/ :}

.a {? 10.rand <= 5 ?? 0 ]] ]] ?? 4 ] ?} ]


.a {? 10.rand == 5 ?? a ] ] ] ] ]] ] ]] ?? ] c] }} ?}

.a {? 10.rand >= 5 ?? e] ] ] ] ]] ] ]] ?? }}
      10.rand <= 6 ?? ] ] ] f] ]] ] ]] ?? } )
      11.rand <= 3 ?? ] c] }} ?}

".skoar.play;
)

ArrayedCollection
(
"{? 5 <= 10.rand ?? a] ?? c] ?}".skoar.play;
)


("{? 10.rand >= 5 ?? ] ] ] o/ ]] ] ]] ?? }}
     10.rand <= 6 ?? ] ] ]  ] ]] ] ]] ?? } )
11.rand <= 3 ?? ] ] }} ?} D.C.".skoar.play;
)


("
120 => )

.hats   @hats => @instrument
.snare @snare => @instrument
.kick   @kick => @instrument

.hats  |: ] ] ] ] ] ] ] ]] ]] :| :| :| :| :| :| :| :| :| :| :|
.snare |: } ) } ) :| :| :| :| :| :| :| :| :| :| ] ]] ]]  ] ]] ]]  ] ]] ]]  ]  ] |
.kick  |: ) } ) } :| :| :| :| :| :| :| :| :| :| :|

".skoar.play)



(
"
,segno`
{! f !!
|: a ]  |
|  c# ]] ]]
|  b ] | fine D.S. al fine !}

!f
".skoar.draw_skoarpions.play;
)

(
"
{! f<x> !! !x ] 2 ] 4 ]!}

!f<4>
".skoar.draw_skoarpions.play;
)
