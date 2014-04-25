import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets
from Skoarcery.langoids import Terminal


class PyRDPP(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()

    def test_pyrdpp(self):

        pass
