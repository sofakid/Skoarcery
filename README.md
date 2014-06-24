Skoar and Skoarcery
===================

"You realize everyone is going to think you are insane?" - miggles

Skoar is a musical notation language.

Skoarcery is the compiler compiler that compiles skoar lexers and parsers for SuperCollider and Python.


Skoar
-----

Skoar is a mini language, it is compiled into a parse tree that can be traversed, implementing a SuperCollider pattern.


    ("""
    ||: Am ]]] oo/ ]]]  | G oo/ ]]] ooo/ | F oo/ ]]] ooo/ | F ooo/ ]]] oo/ :||
    """.pskoar.play;
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

# dottedness

    ). - dotted quarter
    ]]. - dotted sixteenth
    o/. - dotted eighth rest

# noats

    <? you can use # or b after the noat to sharp or flat it. Also midi numbers. ?>

    || c ) d ) eb ) | f ]] ]] g ] ] g# ) | } 81.5 ) 83 ) ||

    <? you get two octaves to work with, prepend _ for the lower octave ?>

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

Segnos `%S%` and Codas `(+)`:

    | _a ) c ) e ) | %S% ) ]] ]] e ]] | f D.S. al fine ) ) ) fine

Infinite repeats:

    <? from the top ?>
    | _a] c] e] | D.C. <? also accept Da Capo ?>

    <? from the segno ?>
    | _a] c] e] o/ | %S% _f] f] _f] o/ Dal Segno |

# data

We use SuperCollider Symbols, but with a `@` instead of a `\`, and use a dictionary.

    @smooth => @instrument
    <0,3,5> => @detune
    |: a ) c ) e ) :|

# messages

    @sna.foo<3>

# cthulhu

You can wake cthulhu, crashing the skoar..

    <? crashes ?>
    | a ) ^^(;,;)^^ |

    <? ensure Dal Segno is working ?>
    |: a ]] ]] Dal Segno ^^(;,;)^^ %S% ) ) :|


Cthulhu can assert stuff too, you have to stick it right in his face, he's sleeping.

    ^^(;@octave == 5;)^^

Or save him to wake later with any message.

    ^^(;,;)^^ => @foo

    @foo.anything

# moar

Lots more stuff in the grammar, and more coming. Conditons, functions, meter stuff, validation of number of
 beats per measure, taking values from other patterns...

Install
-------

map Klass folder to the SuperCollider extensions folder.

    ln -s ~/.../Skoar/SuperCollider/Klassy ~/Library/Application\ Support/SuperCollider/Extensions/Klassy

The lexical and syntactic analysers, lex.sc and rdpp.sc (ditto .py) are built with Skoarcery.
They are built and written to SuperCollider/Klassy.

If you don't want to build, i'm checking in built versions to the built branch. Just switch branches.

    git checkout built


Skoarcery Koadmap
-----------------

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


### Generated Code

- [lex.sc] - Lexical analyser, defines classes for each token, extending SkoarToke.
    - in Skoar, we call them Tokes, in Skoarcery, they are Terminals, or tokens.

- [rdpp.sc] - Recursive decsent predictive parser. Builds the parse tree.

### Nongenerated Runtime Code

- [apparatus.sc] - Stuff to do with the parse tree, and iterators of it. Lots in here.
- [toker.sc] - The guy the parser deals with when it wants tokes.


Dev Environment
---------------

I use PyCharm. The "builds" are done throught the unittest interface.

PyCharm also works with the SC textmate bundle.


Performance
-----------

It is entirely too early to be performance tuning Skoar, but some notes:

- We don't currently have precompiled regexes in SuperCollider.

[terminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/terminals.py
[nonterminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/nonterminals.py

[toke_inspector.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toke_inspector.sc
[toke_inspector.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/toke_inspector.py

[skoarmantics.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoarmantics.sc
[skoarmantics.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/skoarmantics.py

[langoids.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/langoids.py
[dragonsets.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/dragonsets.py
[emissions.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/emissions.py

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
[toker.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toker.sc

