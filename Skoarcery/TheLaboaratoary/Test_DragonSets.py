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

    # optional_carrots_is_fucked.FIRST([optional_carrots,Symbol,msg_chain_node]) is just carrots
    def test_carrots(self):
        FIRST = dragonsets.FIRST
        FOLLOW = dragonsets.FOLLOW

        Symbol = tokens.tokens["Symbol"]
        Carrots = tokens.tokens["Carrots"]

        optional_carrots = nonterminals.nonterminals["optional_carrots"]
        msg_chain_node = nonterminals.nonterminals["msg_chain_node"]


        print((str(FIRST(optional_carrots))))

        print((str(FIRST([optional_carrots, Symbol, msg_chain_node]))))

