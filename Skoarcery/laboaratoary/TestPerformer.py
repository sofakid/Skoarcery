import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.pymp import apparatus


class Test_Performer(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()


    def test_notes(self):

        skoar = apparatus.parse(
            "| a ) b )) c ))) | d ] e ]] f ]]] g ] ] |")

        skoar.decorate()

        for x, y in skoar.get_pattern_gen():
            print("x: " + str(x) + ", " + str(y))

    def test_more(self):

        skoar = apparatus.parse(
            """| mp c ) d ) %S% ]] | [1.] fff ]] fp ]] g p D.S. ]
               | [2.] <! 4/4 \sna !> <c,e,g> )) )) ) :|""")
        skoar.decorate()

        for x, y in skoar.get_pattern_gen():
            print("x: " + str(x) + ", " + str(y))
