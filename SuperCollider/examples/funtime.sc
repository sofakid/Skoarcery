

"killall scsynth".unixCmd;

(
"
mf 90 => )

.a       <_c#, _d, _e, _g#, _a, c#, d, e, g#, a> => @mine
.b  o~~ forte               <_a, c#, d, e, g#, a> => @mine
.c ~o  pp  <0,3,5> => @detune <_a, _a, c#, e, a> => @mine
.d ~o  ppp <0,5,7> => @detune    <c#, e, _a, g#> => @mine
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

.h  |: ] ] ] ] ] ] ] ] |
.s  |: }   )   }   )   | | !foo.choose
.k  |:  ))      ))     | | !derp.next

D.C. ^^(;,;)^^

".skoar.play;
)


ArrayedCollection

Symbol




(
"

mf 90 => )
4/4
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
!! !x.choose 'zoob'.postln !}

,segno`

.a !zorp<!mine>.next ]
.b !zorp<!mine>.choose ]
.c !zorp<!mine>.choose ]
.d !zorp<!mine>.choose ]

.h  |  ] ] ] ] ] ] ] ]  |
.s  |: }   )   }   )   :| :| !foo.choose
.k  |:  ))      ))     :| :| !derp.next


D.S. ^^(;,;)^^

".skoar.play;
)

Array
debug

(
var s = "
fff
.d {! zorp<x> !!
.d !x ) |: c ] ] e ) :|
c )
!!
'food'
!}
)
.d !zorp<_a> f ] !zorp<c>.next f ] !zorp<e>.next f ] !zorp<c>.next f ]
]]
".skoar.play;
)

(
"
mf

5.rand
@krgn_gen_varpulse => @instrument

120 => )

.a |: _c ] _a ]  c ] e ] g ] ] a ] |
.b |: _a ]  c ]  e ]               |
.c |: _g ] _e ] _a ] c ]   ]       |
.d |:   o/ _a ]                    |


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

IdentityDictionary
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
.d ~o ppp <0,5,7> => @detune <c#, e, _a, g#> => @mine @krgn_gen_sbass => @instrument

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

f = ")".skoar.pskoar;

f.nextFunc.value

Event

UnitTest.gui







