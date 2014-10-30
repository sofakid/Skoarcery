

"killall scsynth".unixCmd;

(
"
mf 90 => )

.a       <_c#, _d, _e, _g#, _a, c#, d, e, g#, a> => @mine
.b  o~ forte               <_a, c#, d, e, g#, a> => @mine @bass => @instrument
.c ~o  pp  <0,3,5> => @detune <_a, _a, c#, e, a> => @mine
.d ~o  ppp <0,5,7> => @detune    <c#, e, _a, g#> => @mine @acid => @instrument
.s          @snare => @instrument
.k          @kick  => @instrument
.h     ppp  @hats  => @instrument

{! foo !! | )            o/.      ]]  oo/ ]] ]] oo/ }           |
          | ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     |
          | ].       ]]  oo/  ].      o/    ]       }           |
          | ]]. ]].  ]]  }            ]]. ]].  ]]   }           |
          | )            }            ]     ]       }           |
          | ]     ]      }            o/         ]       oo/ ]. |
!}

{! derp !! | }     )          }     )          |
           | }     )          }     ].      ]] |
           | }     )          }     ]]. ]]. ]] |
           | }     ]]. ]]. ]] }     ].      ]] |
!}

{! zorp<x> !!
 | )         )          |
 | )         ]    ]     |
 | ] ]  ]] ]] ]         |
 | )  }                 |
 | )    ]] oo/ ]        |
 | ] ]  ]] ]] oo/ ]]    |
!! !x.choose !}

,segno`

.a !zorp<!mine>.next
.b !zorp<!mine>.choose
.c !zorp<!mine>.choose
.d !zorp<!mine>.choose

.h  |: ] ] ] ] ] ] ] ]  |
.s  |: }   )   }   )   :| :| !foo.choose
.k  |:  ))      ))     :| :| !derp.next

D.S. ^^(;,;)^^

".skoar.play;
)


ArrayedCollection





(
"

mf 90 => )

.a       <_c#, _d, _e, _g#, _a, c#, d, e, g#, a> => @mine
.b o~  forte               <_a, c#, d, e, g#, a> => @mine @bass => @instrument
.c ~o  pp  <0,3,5> => @detune <_a, _a, c#, e, a> => @mine
.d ~o  ppp <0,5,7> => @detune    <c#, e, _a, g#> => @mine @acid => @instrument
.s          @snare => @instrument
.k          @kick  => @instrument
.h     ppp  @hats  => @instrument

{! foo !! | )            o/.      ]]  oo/ ]] ]] oo/ }           |
          | ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     |
          | ].       ]]  oo/  ].      o/    ]       }           |
          | ]]. ]].  ]]  }            ]]. ]].  ]]   }           |
          | )            }            ]     ]       }           |
          | ]     ]      }            o/         ]       oo/ ]. |
!}

{! derp !! | }     )            }     )           |
           | }     )            }     ].       ]] |
           | }     )            }     ]]. ]].  ]] |
           | }     ]]. ]].  ]]  }     ].       ]] |
!}

{! zorp<x> !!
 | )         )          |
 | )         ]    ]     |
 | ] ]  ]] ]] ]         |
 | )  }                 |
 | )    ]] oo/ ]        |
 | ] ]  ]] ]] oo/ ]]    |
!! !x.choose !}

,segno`

.a !zorp<!mine>.next
.b !zorp<!mine>.choose
.c !zorp<!mine>.choose
.d !zorp<!mine>.choose

.h  |  ] ] ] ] ] ] ] ]  |
.s  |: }   )   }   )   :| :| !foo.choose
.k  |:  ))      ))     :| :| !derp.next


D.S. ^^(;,;)^^

".skoar.play;
)





(
"
mf a ]] ]]
{! <x> !! f f f !! d !! e !}
{! zorp !!
)    )
)    ] ]
] ]  ]] ]] ]
!! <<_a,_c,e>, <a,c,e>, <_a,c#,_e,g>>.choose !}
 !zorp.next D.C.

".skoar.play;
)

nil.foo;
(
"
mf

5.rand
@bass => @instrument

<_a, c#, e>.choose ] o/ ]] ]] o/

|: g ]] ]] :|

D.C.

".skoar.play;
)
(
try {
    nil.foo;
} {
    | e |
    e.postln;
    e.throw;
};
)
Array
[0,1,2].add(7)

a = "w".findRegexp("\\d", 0);
a.dump

(
"
mf 90 => )

{! foo !!
 | )            o/.      ]]  oo/ ]] ]] oo/ }           |
 | ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     |
 | ].       ]]  oo/  ].      o/    ]       }           |
 | ]]. ]].  ]]  }            ]]. ]].  ]]   }           |
 | )            }            ]     ]       }           |
 | ]     ]      }            o/         ]       oo/ ]. |
!}

{! derp !!
 | }     )            }     )           |
 | }     )            }     ].       ]] |
 | }     )            }     ]]. ]].  ]] |
 | }     ]]. ]].  ]]  }     ].       ]] |
!}

.s  @snare => @instrument
.k  @kick  => @instrument
.h  @hats  => @instrument ppp

{! zorp !!
 | )         )          |
 | )         ]    ]     |
 | ] ]  ]] ]] ]         |
 | )  }                 |
 | )    ]] oo/ ]        |
 | ] ]  ]] ]] oo/ ]]    |
!! !mine.choose !}

.a <_c#, _d, _e, _g#, _a, c#, d, e, g#, a> => @mine
.b o~ forte <_a, c#, d, e, g#, a> => @mine
.c ~o pp <0,3,5> => @detune <_a, _a, c#, e, a> => @mine
.d ~o ppp <0,5,7> => @detune <c#, e, _a, g#> => @mine @bass => @instrument

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













