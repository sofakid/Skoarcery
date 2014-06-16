Skoar and Skoarcery
===================


Skoar is a musical notation language, intended to add an expressive dimension to SuperCollider (and python),
modelled after western music notation.

Skoarcery is the compiler compiler that compiles skoar lexers and parsers for SuperCollider and Python.

Skoar
-----


Oversimplified, a skoar starts as a SuperCollider string, and is compiled into a parse tree. This tree
can then be traversed, behaving as a SuperCollider pattern.

    (

    // 28 days later
    var creepy = """
    @creepy => @instrument
    | ~~d ] a~~ ] ~~a ] a~~] :| :| :| :| :| :| :| :| :| :| :| :|
    """.pskoar;

    var koards = """
    <? take the octave down, yeah ugly ?> d~~
    | D ))) | a# ))) | F ))) | G ))) :|
    """.pskoar;

    var guitar = "|: c# ) ) d ) ) e ) ) f ) ) :| :| :|".pskoar;

    Ppar([creepy, koards, guitar]).play;

    )



Install Notes
-------------


map Klass folder to the SuperCollider extensions folder.

    ln -s ~/.../Skoar/SuperCollider/Klassy ~/Library/Application\ Support/SuperCollider/Extensions/Klassy

The lexical and syntactic analysers, lex.sc and rdpp.sc (ditto .py) are built with Skoarcery, and aren't
in the master branch. They are built and written to SuperCollider/Klassy.

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
- [emissions.py] - Implements Python and SuperCollider output tounges, see Underskoar.


### [laboaratoary]
- These are our unit tests.
- Test the grammar for LL(1), test that it compiles in sclang, test skoars, etc..

### [factoary]
- These are written as unit tests, they build our lexers and parsers.

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

Performance
-----------

It is entirely too early to be performance tuning Skoar, but some notes:

- We don't currently have precompiled regexes in SuperCollider.

- There is no statement terminator, we don't have semicolons, and the parse tree grows to the left quickly.
 I kept the main operations near the top of the grammar, and intermediate steps aren't added to the produced SkoarNoad
 tree, but the recursion building it is deep. It's those phrasey phrases.

    - In the meantime, if it becomes an issue, work with smaller skoars, and combine them.



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

