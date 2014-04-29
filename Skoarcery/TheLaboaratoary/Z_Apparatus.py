import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.pymp import apparatus


class Test_Apparatus(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()


    def test_apparatus(self):

        apparatus.parse("| c ) d ) ]] ]] ]] g ]")

        apparatus.parse("| c ) d ) ]] | ]] ]] g ] | <! 4/4 \sna !> <c,e,g> )) )) ) :|")

