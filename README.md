Skoar and Skoarcery
===================

__Skoar__ is a high-level language for coding music.

It runs on [SuperCollider], a free and fantastic audio programming environment.

__Skoarcery__ is a set of tools to define, test, and build the Skoar language.

Example
=======

[listen on soundcloud](https://soundcloud.com/lucas-cornelisse/zelda-skoared)

    <? Zelda Theme - inspired by piano arrangement by Shinobu Amayake ?>

    130 => )

    .alice     <0,3,5> => @detune     [##      ] => @amp
    .bob       <0,3,5> => @detune     [##      ] => @amp
    .bass    @sawpulse => @instrument [###     ] => @amp  o~~~~
    .hats        @hats => @instrument [##      ] => @amp
    .snare      @snare => @instrument [##      ] => @amp
    .kick        @kick => @instrument [###     ] => @amp

    {! bassline_a !!
      <a#, g#, f#, c#, b, a#, c>.{: .) ]] ]] ] ) ) :}
      !bass_end<f>
    !}

    {! bassline_b !!
      <a#, g#, f#, f>.{: ) ]] ]] ] ) ) :}

      |: _e ]] _a# ]] c# ]  e ]] a# ]] ~o c# ] e ) } | f ) o~ _f ]] ]] ] ) } :|

      <b, a#, c>.{: ) ]] ]] ] ) ) :}
      !bass_end<f>
    !}

    {! bass_end<x>    !! !x ) ) ) ] ]     !}

    {! intro !!

      .hats  {: }}} :: 4 times :}
      .snare {: }}} :: 4 times :}
      .kick  {: }}} :: 4 times :}

      .alice | _a# ))        o/. ]]  ]] ]] ] |     ]. _g#  ]] _a# )        o/.  ]]  ]] ]] ] |
      .bob   | _d  ))        o/. ]]  ]] ]] ] | _c  ].      ]]     )        o/.  ]]  ]] ]] ] |
      .bass  |  a# ) ]] ]] ] )       ]] ]] ] |  g# )              ]] ]] ]  )        ]] ]] ] |

      .alice |     ]. _g# ]] _a# )       o/. ]] ]] ]] ] |   ]    _f ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bob   | _c# ].     ]]     )       o/. ]] ]] ]] ] |   ] o~ _a ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bass  |  f# )             ]] ]] ] )      ]] ]] ] | f )              )        )      g ]   a ] |
    !}

    {! melody_a !!

      .bass !bassline_a

      .hats  {: }}} :: 7 times :} | ] ] ] ] ] ] ] ] |
      .snare {: }}} :: 8 times :}
      .kick  {: }}} :: 8 times :}

      .alice | _a# ) _f )__          o/. _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ] _d ].     ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice |  a# ))                                  o/ a# ]  ]  g# ]]  f# ]] |
      .bob   |  c# ]. _f# ]]  ]] _g# ]] _a# ]] c ]] c# ]. ]]    ]  c  ]] _a# ]] |

      .alice | g# ].  f# ]]  f ))                      )               |
      .bob   | c# ]. _g# ]]    ]] ]] _f# ]  _g# ]. ]]  ]] _f# ]] _g# ] |

      .alice |  d# ] ]]  f ]]  f# ))                  f ] d#  ] |
      .bob   | _f# ] ]] _f ]] _f# ] ]] _g# ]] _a# ) _g# ] _f# ] |

      .alice |  c# ] ]]  d# ]]  f ))                 d# ]  c# ] |
      .bob   | _f  ] ]] _d# ]] _f ] ]] _f# ]] _g# ) _f# ] _d# ] |

      .alice |  c ] ]]  d ]]  e ))                      g )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _g ] ]] _a ]] ]] _a# ] c ] |

      .alice |  f ]     _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~  _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}


    {! melody_b !!

      .bass !bassline_b

      .hats  {: ] ] ] ] ] ] ] ]] ]] :: 12 times :}
      .kick  {: )   }   )   }       :: 12 times :}
      .snare {: }   )   }   )       :: 11 times :} | ] ]]  ]]  ] ]] ]]  ]  ] |

      .alice | _a# ) _f )             o/.  _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ]  _d ].      ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice | a# )). ~o c# ) | c  ) o~ a )) f  ) |  f# )).  a# ) | a )  f )) ) |
      .bob   | c# )).    e  ) | d# )    c )) _a ) | _b  )).  c# ) | c ) _a )) ) |

      .alice |  f# )). a# ) | a )  f )) d ) |  d# )).  f# ) |  f  )  c# )) _a# ) |
      .bob   | _b  )). c# ) | c ) _a ))   ) | _f# )). _b  ) | _a# ) _f  )) _c# ) |

      .alice |  c ] ]]  d ]]  e ))                        g  )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _f ]] _g ] ]] _a ]] _a# ] c ] |

      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}

    {! fill !!
      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |
      .snare |    ]       ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .hats  |    ]       ]      ] ]       ] ]       ]  ]    |
      .kick  |    )              }         )         }       |
      .bass !bass_end<f>
    !}

    !intro
    !melody_a
    !fill
    !melody_b
    !fill


More examples: [examples.md]

Skoar
=====

Skoar is a language for coding music, combining a grand-staff-like notation
with a flexible programming notation.

# beats and rests

    __beats_______________   __rests_______________

    ))) - whole               }}} - whole
    ))  - half                 }} - quarter
    )   - quarter               } - quarter
    ]   - eighth               o/ - eighth
    ]]  - sixteenth           oo/ - sixteenth
    ]]] - thirty secondth    ooo/ - thirthy secondth


# fancy beats - dotted, staccato, ties

    ).  - dotted quarter
    ]]. - dotted sixteenth
    o/. - dotted eighth rest

    .)   - staccato quarter
    .]]. - staccato and dotted sixteenth

    )__  - quarter with a tie (ties to the next beat)
    )__. - dotted quarter with a tie

# noats

We call them __noats__, not __notes__, you see, __notes__ are already things;
nor are these __noats__ the nearly named __noads__, which are also totally things..

    <? use # or b after the noat to sharp or flat it. Or use scale degree numbers. ?>

    c ) d ) eb ) f ]] ]] g ] ] g# )

    <? you get two octaves to work with, prepend _ for the lower octave. ?>

    <_c, _d, _e, _f, _g, _a, _b, c, d, e, f, g, a, b>.choose ]]

# choards

Choards don't work yet, this is the intention:

    A Am A#m Asus2 Adim etc..

But we can use lists of noats:

    <_a,c,e> )  <_a,c#,e> )

# changing the octave

    <? up one octave ?>    <? down one ?>
    ~o                     o~
    8va                    8vb
    ottava alta            ottava bassa

    <? up two ?>           <? down two ?>
    ~~o                    o~~
    15ma                   15mb
    alla quindicesima

# dynamics

Have to use the full word `forte`, `f` is a noat.

    fff ffforte ppp pppiano piano mp mf ff pp p sfp

# repeats

Colons:

    |: _a ]]] c ]]] e ]]] :| g ]]] ooo/ ]]] :|

Segnos and Codas:

    | _a ) c ) e ) | ,segno` ) ]] ]] e ]] | f D.S. al fine ) ) ) fine

    | ,segno` c ]] e ]] (+) ]] ]] D.S. al Coda '.......'  (+) | a) c) e) } |

Infinite repeats:

    <? from the top ?>
    | _a] c] e] | D.C. <? also accept Da Capo ?>

    <? from the segno ?>
    | _a] c] e] o/ | ,segno` _f] f] _f] o/ Dal Segno |

Voltas:

    |: c )) ) | [1.] _a] c] a]] e]] :| [2.]  ]] ]] _c) ||

# variables

We can set and get values from a dictionary local to the voice. Anything set here
will be copied into the resulting event every beat; which we can use to configure the voice.

    <? names of things start with @ ?>
    @smooth => @instrument
    <0,3,5> => @detune

    <? to lookup the values, we use ! in place of @.... more on ! below.. ?>
    a# => @foo
    !foo )

# loops

Very much like a do-while:

    {: ]] oo/ ]] ]] :: !x <= !y :} 

You can send a loop to an array as a message to implement a foreach loop:

    <_a, _c, c, _e, e, _a>.{: ] ]] ]] :}

If you also put a boolean condition, it will keep foreaching while the condition is true.
    
    <_a, _c, c, _e, e, _a>.{: ] ]] ]] :: !groovy == 5 :}

You get a monotonic `!i` that starts at `0` and is incremented _just before_ the test.
 
    <? executes 8 times ?>
    {: <_a, _c, _e, a>.choose ]] ]] ] :: !i <= 8:}

But, that's far too engineery. You can just write:

    {: <_a, _c, _e, a>.choose ]] ]] ] :: 8 times :}
    
# conditionals

An if example:

    {? !x == !y ?? ]]] ?}

An if with else example:

    {? !x == !y ?? ]]] ?? ooo/ ?}
    
# Skoarpions

The __Skoarpion__ is a flexible device; we can use it as a function or a sequence.

    {! name<args> !!
      body
      ...
    !}

Let's make a function:

    {! zorp<x> !!
     | )         }             |
     | )         ]] oo/ ]      |
     | ]    ]    ]] ]]  ]      |
     | ]    ]    ]] ]]  oo/ ]] |
    !}

    <? - this calls !zorp.choose, setting @x to <_a, c#, e>
       - the !zorp.choose will choose one line at random ?>
    !zorp<<_a, c#, e>>.choose

You can cycle the lines in order with `.next` or backwards with `.last`

    <_a, c#, e> => @A

    !zorp<!A.choose>.next   <? plays | ) }        | with a random noat ?>
    !zorp<!A.choose>.next   <? plays | ) ]] oo/ ] | with a random noat ?>
    !zorp<!A.choose>.last   <? plays | ) }        | with a random noat ?>

Skoarpions normally have scope, but they can be inlined with `.inline`, which can be convenient:

    {! alice !! ~o mp
      <c#, e, _a, g#> => @favorites
              <0,5,7> => @detune
                @acid => @instrument
    !}

    {! bob<x> !! o~~ forte
      @bass => @instrument
         !x => @favorites
    !}

    {! <x> !! <0, 4, !x> => @detune !} => @charlie

    .a !alice.inline
    .b !bob<<a,e>>.inline
    .c !charlie<<5,7,9>.choose>.inline
    ...

# other sequences

Arrays and arraylike things can be iterated like skoarpions.

    <c, e, g> => @food

    !food.next )   <? plays: c ?>
    !food.next )   <? plays: e ?>
    !food.last )   <? plays: c ?>
    !food.choose ) <? at random ?>

# messages

With messages you can work with the underlying objects (i.e. the SuperCollider objects)

    <? save into @food, a random number between zero and five ?>
    5.rand => @food

    <? print foo to the post screen ?>
    'foo'.postln

    <? choose a random note and post it to the screen ?>
    <c,d,e,f,g,a,b>.choose.postln

Static methods can be called on underlying classes, dereference with `!` and send the message:

    !Array.fib<20,27,3> => @food

    !MyRediculousClass.new<'srsly', 2.1828> => @zagwaggler

    !zagwaggler.bringTheWub<'wub'>.wub.wub.wub.wub

# Cthulhu

You can wake Cthulhu, crashing the skoar.

    ^^(;,;)^^


Cthulhu can also make assertions.

    ^^(;!octave == 5;)^^


Install
=======

You need the very latest [SuperCollider 3.7](http://supercollider.github.io/download.html)

You just need to point SuperCollider at the Skoar folder (that you git cloned) and you're set.

In SuperCollider's interpreter options, __include__ the folder `~/.../Skoar/SuperCollider/Skoar` and
restart the interpreter

The lexical and syntactic analysers, `lex.sc` and `rdpp.sc` (ditto `.py`) are built with Skoarcery.
They are built and written to `.../SuperCollider/Skoar`.

Currently the built code is checked in, you don't need to get Skoarcery working unless you want to
work on the language.



Skoarcery
=========

### [terminals.py]
- Tokens by convention are UpperCamelCase.

- Tokens are defined with regexes that have to work with both SuperCollider and Python.
All we do is recognise, no capture groups.

- \* after a terminal means there are values we need to pick out of the lexeme: [decorating.sc]

### [nonterminals.py]
- Defines an LL(1) grammar suitable for building recursive decent predictive parsers for skoar.

- Nonterminals by convention are like_this

- \+ before a nonterminal indicates this is an intermediate step that can be skipped in the
constructed parse tree, it will not create a new skoarnode, instead appending its noads to
its parent's children list.

- \* after a nonterminal means there is corresponding semantic code for this: [decorating.sc]


### Misc Skoarcery

- [langoids.py] - Terminal, Nonterminal, Production objects,
- [dragonsets.py] - FIRST and FOLLOW sets, from the Dragon Book.
- [emissions.py] - Implements Python and SuperCollider coding.
- [underskoar.py] - Templates for lexer code


### [laboaratoary]
- These are our unit tests.
- Test the grammar for LL(1), test that it compiles in sclang, test skoars, etc..

### [factoary]
- These are written as unit tests, they build our lexers and parsers. Done this way because we
generate some information, test it, build on it, test that ...

- The important one at the moment is [Build_Sc.py], it will run tests,
build files, run more tests, etc.. it builds Skoar. This one builds Skoar.

- [Code_Lexer_Py.py], [Code_Lexer_Sc.py] - Build lex.py, lex.sc
- [Code_Parser_Py.py], [Code_Parser_Sc.py] - Build rdpp.py, rdpp.sc


### Skoar Code
### The Generated Code

- [lex.sc] - Lexical analyser, defines classes for each token, extending SkoarToke.
    - in Skoar, we call them Tokes, in Skoarcery, they are Terminals, or tokens.

- [rdpp.sc] - Recursive descent predictive parser. Builds the parse tree.

### The Hand Coded Code - Mostly The Runtime

- [skoar.sc] - The skoar object you get from compiling your skoar. From here you get a pattern object and play it.
- [apparatus.sc] - The parse tree code. Noads, searching, iteration, etc.
- [decorating.sc] - Second stage, decorate the parse tree.
- [koar.sc] - Each voice is performed on a koar by a minstrel.
- [minstrel.sc] - Minstrels are agents who read and perform their own voice of a skoar piece.
- [skoarpions.sc] - Implements the Skoarpion, our general purpose control-flow construct.
- [beaty.sc] - The code for beats and rests.
- [pitchy.sc] - The code for pitchy stuff. Noats, choards, etc.
- [toker.sc] - Toker for the parser.
- [skoarpuscles.sc] - Skoarpuscles, the thingy things are all skoarpuscles.




[terminals.py]:    https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/terminals.py
[nonterminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/nonterminals.py

[toke_inspector.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/toke_inspector.sc
[toke_inspector.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/toke_inspector.py


[langoids.py]:   https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/langoids.py
[dragonsets.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/dragonsets.py
[emissions.py]:  https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/emissions.py
[underskoar.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/underskoar.py

[laboaratoary]:  https://github.com/sofakid/Skoarcery/tree/master/Skoarcery/laboaratoary/
[factoary]:      https://github.com/sofakid/Skoarcery/tree/master/Skoarcery/factoary/

[Build_Sc.py]:       https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Build_Sc.py
[Code_Lexer_Py.py]:  https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Lexer_Py.py
[Code_Lexer_Sc.py]:  https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Lexer_Sc.py
[Code_Parser_Py.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Parser_Py.py
[Code_Parser_Sc.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Parser_Sc.py

[lex.sc]:          https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/lex.sc
[rdpp.sc]:         https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/rdpp.sc
[apparatus.sc]:    https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/apparatus.sc
[decorating.sc]:   https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/decorating.sc
[skoar.sc]:        https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/skoar.sc
[minstrel.sc]:     https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/minstrel.sc
[beaty.sc]:        https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/beaty.sc
[pitchy.sc]:       https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/pitchy.sc
[skoarpions.sc]:   https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/skoarpions.sc
[koar.sc]:         https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/koar.sc
[skoarpuscles.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/skoarpuscles.sc
[toker.sc]:        https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Skoar/toker.sc

[SuperCollider]: https://github.com/supercollider/supercollider
[examples.md]:   https://github.com/sofakid/Skoarcery/blob/master/examples.md