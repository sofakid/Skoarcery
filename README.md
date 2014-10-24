Skoar and Skoarcery
===================

__Skoar__ is a high-level language for coding music.

It runs on [SuperCollider], a free and fantastic audio programming environment.

__Skoarcery__ is a set of tools to define, test, and build the Skoar language.

Examples
========

# MIDI example - [listen on soundcloud](https://soundcloud.com/lucas-cornelisse/windwaker-sv1)

    <? Zelda Wind Waker Theme - inspired by piano arrangement by Shinobu Amayake ?>

    @midi => @type
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


Skoar
=====

Skoar is a language for coding music.

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

Choards need work, but this is the intention:

    A Am A#m Asus2 Adim etc..

Or use lists of noats:

    <a,c,e> )  <a,c#,e> )

# octaving

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

    |,segno` c ]] D.S. al Coda  '............'  (+) | a) c) e) } |

Infinite repeats:

    <? from the top ?>
    | _a] c] e] | D.C. <? also accept Da Capo ?>

    <? from the segno ?>
    | _a] c] e] o/ | ,segno` _f] f] _f] o/ Dal Segno |


# data

We can set and get values from a dictionary local to the voice. Anything set here
will be copied into the resulting event every beat; which we can use to configure the voice.

    @smooth => @instrument

    <0,3,5> => @detune

# Skoarpions

The __Skoarpion__ is a flexible device; we can use it as a function, a sequence, a block, or a
source of chaos.

    !! name<args> !! head
      body
      ...
    !! stinger


Each time you call the skoarpion, `head` runs, then the `body`; the `stinger` runs before every
beat.

Example:

    !! zorp<derp> !!
     | )         )          |
     | )         ]    ]     |
     | ] ]  ]] ]] ]         |
     | )  }                 |
     | )    ]] oo/ ]        |
     | ] ]  ]] ]] oo/ ]]    |
    !! @derp.choose

    <? - this calls !zorp.choose, setting @derp to <_a, c#, e>
       - @derp.choose in the stinger, means it will pick one noat from @derp each beat.
       - the !zorp.choose will choose one line at random ?>
    !zorp<<_a, c#, e>>.choose

They can be used as a block of code with `.block`:

    !! alice !!
    ~o mp
    @acid => @instrument
    <0,5,7> => @detune
    <c#, e, _a, g#> => @favorites
    !!

    !! bob<x> !!
    o~~
    forte
    @x.value => @favorites
    @bass => @instrument
    !!

    .a !alice.block
    .b !bob<a,e>.block

    ...

You can cycle the lines in order with `.next` or backwards with `.last`

# other sequences

Arrays and arraylike things can be iterated like skoarpions.

    <c, e, g> => @food

    <? this sends .next to the array, does nothing useful. ?>
    @food.next )

    !food.next )   <? plays: c ?>
    !food.next )   <? plays: e ?>
    !food.last )   <? plays: c ?>
    !food.choose ) <? at random ?>

That is, the `!xxx` notation starts an interator that sticks around.

# messages

With messages you can work with the underlying objects (i.e. the SuperCollider objects)

    <? a random number between zero and five ?>
    5.rand => @food

    <? print foo to the post screen ?>
    'foo'.postln

    <? choose a random note and post it to the screen ?>
    <c,d,e,f,g,a,b>.choose.postln

Static methods can be called on underlying classes, just write them as symbols:

    @Array.fib<20,27,3> => @food

    @MyRediculousClass.new<'srsly', 2.1828> => @zagwaggler

    @zagwaggler.bringTheWub<'wub'>.wub.wub.wub.wub

# Cthulhu

You can wake Cthulhu, crashing the skoar.

    ^^(;,;)^^

Cthulhu can also make assertions.

    ^^(;@octave == 5;)^^

Install
=======

In SuperCollider's interpreter options, __include__ the folder `~/.../Skoar/SuperCollider/Klassy/Skoar` and
restart the interpreter

The lexical and syntactic analysers, `lex.sc` and `rdpp.sc` (ditto `.py`) are built with Skoarcery.
They are built and written to `.../SuperCollider/Klassy/Skoar`.

Currently the built code is checked in, you don't need to get Skoarcery working unless you want to
work on the language.

Skoarcery
=========

### [terminals.py]
- Tokens by convention are UpperCamelCase.

- Tokens are defined with regexes that have to work with both SuperCollider and Python.
All we do is recognise, no capture groups.
    - If tagged with \*, it means see appropriate toke_inspector for further token processing.
        - SuperCollider: [toke_inspector.sc]
        - Python:        [toke_inspector.py]


### [nonterminals.py]
- Defines an LL(1) grammar suitable for building recursive decent predictive parsers for skoar.

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

- [rdpp.sc] - Recursive descent predictive parser. Builds the parse tree.

### The Hand Coded Code - Mostly The Runtime

- [skoar.sc] - The skoar object you get from compiling your skoar. From here you get a pattern object and play it.
- [apparatus.sc] - The parse tree code. Noads, searching, iteration, etc.
- [voice.sc] - Voice object - each voice is performed by a minstrel.
- [minstrel.sc] - Minstrels are agents who read and perform their own voice of a skoar piece.
- [skoarpions.sc] - Implements the Skoarpion, our general purpose control-flow construct.
- [beaty.sc] - The code for beats and rests.
- [pitchy.sc] - The code for pitchy stuff. Noats, choards, etc.
- [toker.sc] - Toker for the parser.
- [skoarpuscles.sc] - Skoarpuscles.




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
[beaty.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/beaty.sc
[pitchy.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/pitchy.sc
[skoarpions.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoarpions.sc
[voice.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/voice.sc
[skoarpuscles.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoarpuscles.sc
[toker.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toker.sc

[SuperCollider]: http://supercollider.sourceforge.net/