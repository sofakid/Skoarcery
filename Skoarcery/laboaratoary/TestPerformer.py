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

        skoar = apparatus.parse("| a ) b ) c ) | d ) e ) f ) g ) |")

        skoar.tinsel_and_balls()

        for x in skoar.get_pattern_gen():
            print("x: " + repr(x))