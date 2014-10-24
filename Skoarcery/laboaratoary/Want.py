import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.SkoarPyon import apparatus


class Want(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()


    def test_drums(self):
        apparatus.parse("| !b,hh ) !sn ) ]] c!b,hh ]] ]] ] |")


    def test_syncopation(self):
        apparatus.parse("| c ) c 'stress this note:' .) ]] c!b,hh ]] ]] ] |")


    def test_ties(self):
        apparatus.parse("| b )__ c) d) e __)   |")
        apparatus.parse("| b ))__ c] d) e __]. |")
        apparatus.parse("| b )__ c] d) e __]]. |")
        apparatus.parse("| b )__ c] d) e __].  |")
        apparatus.parse("| b )__ c] d) e __].  |")


    def test_ties_and_stress(self):
        apparatus.parse("| b )__ c.) d) e .__)  |")
        apparatus.parse("| b )__ c) d) e __.)  |")


    def test_voicings(self):
        apparatus.parse("""
            | 'root position' ooo) '1st inv' oo_o) '2nd_inv' o_oo)  |
        """)


    def test_octave_shift(self):
        apparatus.parse("| ~~o) o~~) ~ooo) oo_o) :|")


    def test_complex_time_sig(self):
        apparatus.parse("<! 3+4+5/4 !>")

