Skoar and Skoarcery
===================

"You realize everyone is going to think you are insane?" - miggles

Skoar is a musical notation language.

Skoarcery is the compiler compiler that compiles skoar lexers and parsers for [SuperCollider] and Python.

# Drumming example - [listen on soundcloud](https://soundcloud.com/lucas-cornelisse/beets)


    <? 4/4 time, 70 beats per min. No voice label on this line, all voices will read it ?>

    4/4 70 => )


    <? each line begins with a voice label, we are using three voices ?>
    .h  @hats  => @instrument
    .s  @snare => @instrument forte
    .k  @kick  => @instrument

    .h  | }}}     |
    .s  | } ) } ) |
    .k  | ) } ) } |


    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }            )            }             )           :|
    .k  |: )            o/.      ]]  oo/ ]] ]] oo/ }           :|

    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }            )            }             )           :|
    .k  |: ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     :|


    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]] ]]]  ]] ]]] ]]] ]] ]]  ]] ]] ]] ]] :|
    .s  |: }            )                 }                 ].       ]] :|
    .k  |: ]     ]] ]]  o/    ]] ]]       o/         ]      o/    ]     :|

    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]]] ]]] ]]] ]]] :|
    .s  |: }            )            }             ].            ]]      :|
    .k  |: ].       ]]  oo/  ].      o/    ]       }                     :|


    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }            )            }             ]]. ]].  ]] :|
    .k  |: ]]. ]].  ]]  }            ]]. ]].  ]]   }           :|

    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }            )            }             )           :|
    .k  |: ]]. ]].  ]]  o/.      ]]  ]] o/    ]]   }           :|


    .h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }            ]]. ]].  ]]  }             ].       ]] :|
    .k  |: )            }            ]     ]       }           :|

    .h  |: ]]] ]]] ]]] ]]] ]] ]]   ]] ]] ]] ]]] ]]]   ]] ]]] ]]] ]] ]]   ]] ]] ]] ]] :|
    .s  |: }                       )                  }                  )           :|
    .k  |: ]               ]       }                  o/         ]       oo/ ].      :|


    .h  | ))) |
    .s  | ))) |
    .k  | ))) |

    <? Special thanks to The Breakbeat Bible for the dope dubstepz.
       The synths are the SOS drums from the SuperCollider examples folder. ?>

# MIDI example - [listen on soundcloud](https://soundcloud.com/lucas-cornelisse/windwaker-sv1)

    <? Zelda Wind Waker Theme - inspired by piano arrangement by Shinobu Amayake ?>

    <? after compiling do: myskoar[\midiout] = MIDIOut(0); ?>
    @midi => @type

    <? key is Db, so i flat everything and sharp c & f ?>
    -1 => @transpose

    9/8 120 => ).
    mp

    | <_a,_c#,d> )). }. | d )). }. | <_a,_c#,d> )). }. | _a )). }. |

    | _a)   d] f#)   e] d)     c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)   _a] _g)   d] _b)    c#] | d)   e]] ]] f#] d] e] _a)   ] |
    | _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).      _a)   ] |
    | _a)  d]] e]] f#)  e] d)  c#] | e]] d].  _b] _g)  c#] _a] d] _b]  | c#)   _a] _g)   d] _b)    c#] | ]] d]] e]] ]] f#] d] e] _a) ] |
    | _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).       a)   ] |

    |: g] f#] e] f#)  d] c#] d] e] | a] d] ] g] d] ] f#)    ] | g] a] b] b] g] e] a)  a]   | g] f#] d] e] f#] e]] f#]] e)  ] |
    |  d] c#] d] e)   e] a] g] c#] | g] d] ] f#)  ] e] c#] e] | d] b] d] g] a] b] f# d] g] | e).    ] f#] e] a)           ] :|

    | <_a,_c#,d> )). }. | d )). }. | <_a,_c#,d> )). }. | <_a,_f#> )). _a ) ] |

This was played over MIDI to my Korg SV1.

# current state

This is alpha software. Most of the things described here are implemented. The python implementation needs alot of work
to bring it to where the SuperCollider one is.

Focus is on SuperCollider at the moment.

# why is everything named crazily?

SuperCollider already has well known objects called Score, Note, Chord, etc.. So here we use skoar, noat, choard, etc..

You thought I was just being cute?

Skoar
=====

Skoar is a mini language for describing music, it is compiled into a parse tree that can be traversed, implementing a
SuperCollider pattern.


    ("""

    ||: Am ]]] oo/ ]]]  | G oo/ ]]] ooo/ | F oo/ ]]] ooo/ | F ooo/ ]]] oo/ :||

    """.skoar.play;
    )

# beats

    ))) - whole noat
    )) - half noat
    ) - quarter noat
    ] - eighth noat
    ]] - sixteenth noat
    ]]] - thirty second noat

# rests

    }}} - whole rest
    }} - half rest
    } - quarter rest
    o/ - eighth rest
    oo/ - sixteenth rest
    ooo/ - thirty second rest

# dotted (x1.5 duration) beats

    ). - dotted quarter
    ]]. - dotted sixteenth
    o/. - dotted eighth rest

# staccatto

    .) - staccatto quarter
    .]]. - staccatto and dotted sixteenth

# noats

    <? you can use # or b after the noat to sharp or flat it. Also midi numbers. ?>

    || c ) d ) eb ) | f ]] ]] g ] ] g# ) | } 81.5 ) 83 ) ||

    <? you get two octaves to work with, prepend _ for the lower octave. ?>

    ||: d ) _a ) a ) _a ) :||

# choards

Choards need work, but this is the intention:

    A Am A#m Asus2 Adim etc..

Or use lists of noats:

    | <a,c,e> ) <81, c#, e> ) |

# octaving

    <? up one octave ?>
    ~o
    8va
    ottava alta

    <? up two ?>
    ~~o
    15ma
    alla quindicesima

    <? down one ?>
    o~
    8vb
    ottava bassa

    <? down two ?>
    o~~
    15mb

# dynamics

Have to use the full word `forte`, `f` is a noat.

    fff ffforte ppp pppiano piano mp mf ff pp p

# repeats

Colons

    |: _a ]]] c ]]] e ]]] :|: g ]]] ooo/ ]]] :|

Segnos and Codas:

    | _a ) c ) e ) | ,segno` ) ]] ]] e ]] | f D.S. al fine ) ) ) fine

Infinite repeats:

    <? from the top ?>
    | _a] c] e] | D.C. <? also accept Da Capo ?>

    <? from the segno ?>
    | _a] c] e] o/ | ,segno` _f] f] _f] o/ Dal Segno |

# data assignment

We use SuperCollider Symbols, but with a `@` instead of a `\`, and use a dictionary.

    @smooth => @instrument
    <0,3,5> => @detune
    |: a ) c ) e ) :|


# cthulhu

You can wake cthulhu, crashing the skoar..

    <? crashes ?>
    | a ) ^^(;,;)^^ |

    <? ensure Dal Segno is working ?>
    |: a ]] ]] Dal Segno ^^(;,;)^^ ,segno` ) ) :|


Cthulhu can assert stuff too, you have to stick it right in his face, he's sleeping.

    ^^(;@octave == 5;)^^

Or save him to wake later with any message.

    ^^(;,;)^^ => @foo

    @foo.anything


Install
=======

map Klassy folder to the SuperCollider extensions folder.

    ln -s ~/.../Skoar/SuperCollider/Klassy ~/Library/Application\ Support/SuperCollider/Extensions/Klassy

The lexical and syntactic analysers, lex.sc and rdpp.sc (ditto .py) are built with Skoarcery.
They are built and written to SuperCollider/Klassy.

Currently the built code is checked in, so it should work after mapping the folder (and restarting SuperCollider).

Skoarcery Koadmap
=================

### [terminals.py]
- Tokens by convention are UpperCamelCase.

- Tokens are defined with regexes that have to work with both SuperCollider and Python.
All we do is recognise, no capture groups.
    - If tagged with \*, it means see appropriate toke_inspector for further token processing.
        - SuperCollider: [toke_inspector.sc]
        - Python:        [toke_inspector.py]


### [nonterminals.py]
- Defines an LL(1) grammar suitable for building recursive decent parsers for skoar.

- Nonterminals by convention are like_this

- \+ before a nonterminal indicates this is an intermediate step that can be skipped in the
constructed parse tree, it will not create a new skoarnode, instead appending its noads to
its parent's children list.

- \* after a nonterminal means there is corresponding semantic code for this, defined in:
    - SuperCollider: [skoarmantics.sc]
    - Python:        [skoarmantics.py]


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

- [rdpp.sc] - Recursive decsent predictive parser. Builds the parse tree.

### The Hand Coded Code - Mostly The Runtime

- [skoar.sc] - The skoar object you get from compiling your skoar. From here you get a pattern object and play it.
- [apparatus.sc] - The parse tree code. Noads, searching, iteration, etc.
- [voice.sc] - Voice object - each voice is performed by a minstrel.
- [minstrel.sc] - Minstrels are agents who read and perform their own voice of a skoar piece.
- [projections.sc] - Produce SuperCollider Pattern objects from the minstrels' labours.
- [pitchy.sc] - The code for pitchy stuff. Noats, choards, etc.
- [toker.sc] - The guy the parser deals with when it wants tokes.


Dev Environment
===============

I use PyCharm. The "builds" are done throught the unittest interface.

PyCharm also works with the SC textmate bundle.



[terminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/terminals.py
[nonterminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/nonterminals.py

[toke_inspector.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toke_inspector.sc
[toke_inspector.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/toke_inspector.py

[skoarmantics.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoarmantics.sc
[skoarmantics.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/skoarmantics.py

[langoids.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/langoids.py
[dragonsets.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/dragonsets.py
[emissions.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/emissions.py
[underskoar.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/underskoar.py

[laboaratoary]: https://github.com/sofakid/Skoarcery/tree/master/Skoarcery/laboaratoary/
[factoary]: https://github.com/sofakid/Skoarcery/tree/master/Skoarcery/factoary/

[Build_Sc.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Build_Sc.py
[Code_Lexer_Py.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Lexer_Py.py
[Code_Lexer_Sc.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Lexer_Sc.py
[Code_Parser_Py.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Parser_Py.py
[Code_Parser_Sc.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/factoary/Code_Parser_Sc.py

[lex.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/lex.sc
[rdpp.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/rdpp.sc
[apparatus.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/apparatus.sc
[skoar.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoar.sc
[minstrel.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/minstrel.sc
[pitchy.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/pitchy.sc
[projections.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/projections.sc
[voice.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/voice.sc
[toker.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toker.sc

[SuperCollider]: http://supercollider.sourceforge.net/