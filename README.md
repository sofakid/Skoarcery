Skoar
=====



Install Notes
-------------

map Klass folder to the Supercollider extensions

    ln -s ~/PycharmProjects/Skoar/SuperCollider/Klassy ~/Library/Application\ Support/SuperCollider/Extensions/Klassy


Talk to me like a compiler geek
-------------------------------


## [terminals]
- Tokens are defined with regexes that have to work with both SuperCollider and Python.

- Tokens by convention are UpperCamelCase.

- All we do with these regexes is recognise.
    - If tagged with \*, it means see appropriate toke_inspector for further token processing.
        - SuperCollider: [toke_inspector.sc]
        - Python:        [toke_inspector.py]



## [nonterminals]
- Defines an LL(1) grammar suitable for building recursive decent parsers for skoar.

- Nonterminals by convention are like_this

- \+ before a nonterminal indicates this is an intermediate step that can be skipped in the
constructed parse tree, it will not create a new skoarnode, instead appending its noads to
its parent's children list.

- \* after a nonterminal means there is corresponding semantic code for this, defined in skoarmantics (.py or .sc)

[terminals]: https://github.com/sofakid/skoar/blob/master/Skoarcery/terminals.py
[nonterminals]: https://github.com/sofakid/skoar/blob/master/Skoarcery/nonterminals.py
[toke_inspector.sc]: https://github.com/sofakid/skoar/blob/master/SuperCollider/Klassy/Skoar/skoarmantics.sc
[toke_inspector.py]: https://github.com/sofakid/skoar/blob/master/Pymp/skoarmantics.py

