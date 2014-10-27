



(
"
mf 90 => )

!! foo !!
 | )            o/.      ]]  oo/ ]] ]] oo/ }           |
 | ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     |
 | ].       ]]  oo/  ].      o/    ]       }           |
 | ]]. ]].  ]]  }            ]]. ]].  ]]   }           |
 | )            }            ]     ]       }           |
 | ]     ]      }            o/         ]       oo/ ]. |
!!

!! derp !!
 | }     )            }     )           |
 | }     )            }     ].       ]] |
 | }     )            }     ]]. ]].  ]] |
 | }     ]]. ]].  ]]  }     ].       ]] |
!!

.s  @snare => @instrument
.k  @kick  => @instrument
.h  @hats  => @instrument ppp

!! zorp !!
 | )         )          |
 | )         ]    ]     |
 | ] ]  ]] ]] ]         |
 | )  }                 |
 | )    ]] oo/ ]        |
 | ] ]  ]] ]] oo/ ]]    |
!! @mine.choose

.a <_c#, _d, _e, _g#, _a, c#, d, e, g#, a> => @mine
.b o~ forte <_a, c#, d, e, g#, a> => @mine
.c ~o pp <0,3,5> => @detune <_a, _a, c#, e, a> => @mine
.d ~o ppp <0,5,7> => @detune <c#, e, _a, g#> => @mine @acid => @instrument

,segno`

.a !zorp.next
.b !zorp.choose
.c !zorp.choose
.d !zorp.choose

.h  |  ] ] ] ] ] ] ] ]  |
.s  |: }   )   }   )   :| :| !foo.choose
.k  |:  ))      ))     :| :| !derp.next

D.S. ^^(;,;)^^

".skoar.play;
)









(
"
mf a ]] ]]

!! zorp !! 'foo'
)    )
)    ] ]
] ]  ]] ]] ]
!! <<_a,_c,e>, <a,c,e>, <_a,c#,_e,g>>.choose

,segno` !zorp.next D.S.

".skoar.play;
)


(
"
mf

5.rand

<_a, c#, e>.choose ] o/ ]] ]] o/

D.C.

".skoar.play;
)


[0,1,2].add(7)














