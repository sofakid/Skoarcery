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

    def test_carrots(self):
        FIRST = dragonsets.FIRST
        FOLLOW = dragonsets.FOLLOW

        Symbol = tokens.tokens["Symbol"]
        Carrots = tokens.tokens["Carrots"]

        optional_carrots = nonterminals.nonterminals["optional_carrots"]
        msg_chain_node = nonterminals.nonterminals["msg_chain_node"]

        self.assertSetEqual({Carrots, tokens.Empty}, FIRST(optional_carrots))

        self.assertSetEqual({Carrots, Symbol}, FIRST([optional_carrots, Symbol, msg_chain_node]))


