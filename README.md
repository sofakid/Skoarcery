Skoar and Skoarcery
===================


Skoar is a musical notation language, intended to add an expressive dimension to SuperCollider.

Skoarcery is the compiler compiler that compiles skoar lexers and parsers for SuperCollider and Python.


What's a Skoar?
---------------

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





Koadmap
-------

## [terminals.py]
- Tokens by convention are UpperCamelCase.

- Tokens are defined with regexes that have to work with both SuperCollider and Python.
All we do is recognise, no capture groups.
    - If tagged with \*, it means see appropriate toke_inspector for further token processing.
        - SuperCollider: [toke_inspector.sc]
        - Python:        [toke_inspector.py]


## [nonterminals.py]
- Defines an LL(1) grammar suitable for building recursive decent parsers for skoar.

- Nonterminals by convention are like_this

- \+ before a nonterminal indicates this is an intermediate step that can be skipped in the
constructed parse tree, it will not create a new skoarnode, instead appending its noads to
its parent's children list.

- \* after a nonterminal means there is corresponding semantic code for this, defined in:
    - SuperCollider: [skoarmantics.sc]
    - Python:        [skoarmantics.py]


[terminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/terminals.py
[nonterminals.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/nonterminals.py

[toke_inspector.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/toke_inspector.sc
[toke_inspector.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/toke_inspector.py

[skoarmantics.sc]: https://github.com/sofakid/Skoarcery/blob/master/SuperCollider/Klassy/Skoar/skoarmantics.sc
[skoarmantics.py]: https://github.com/sofakid/Skoarcery/blob/master/Skoarcery/pymp/skoarmantics.py


Performance
-----------

It is entirely too early to be performance tuning Skoar, but some notes:

- We don't currently have precompiled regexes in SuperCollider.

- There is no statement terminator, we don't have semicolons, and the parse tree grows to the left quickly.
 I kept the main operations near the top of the grammar, and intermediate steps aren't added to the produced SkoarNoad
 tree, but the recursion building it is deep. It's those phrasey phrases.

    - In the meantime, if it becomes and issue, work with smaller skoars, and combine them.

