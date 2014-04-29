import unittest
from Skoarcery import dragonsets, tokens, nonterminals


class DragonTests(unittest.TestCase):

    def setUp(self):
        #
        #tokens.src = """
        #
        #Symbol:         \\\\[a-zA-Z][a-zA-Z0-9]+
        #Carrots:        \\^+(^\\^\\^\\()
        #
        #"""
        #
        #nonterminals.src = """
        #
        #optional_carrots : Carrots | <e>
        #stmt             : optional_carrots Symbol
        #
        #"""

        tokens.init()
        nonterminals.init()
        dragonsets.init(compute=False)
        dragonsets.compute_firsts()

    def tearDown(self):
        pass


