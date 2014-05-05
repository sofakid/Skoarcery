import unittest
from Skoarcery import dragonsets, terminals, nonterminals


class DragonTests(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        dragonsets.init(compute=False)
        dragonsets.compute_firsts()

    def tearDown(self):
        pass

    def test_tokens_first(self):

        print("\n\n ==========--------( Terminal FIRST sets )-------------------------===========\n")

        for token in terminals.tokens.values():

            first = dragonsets.FIRST(token)

            self.assertIn(token, first, "token first wrong")
            self.assertEqual(1, len(first), "FIRST(token) should be {token}")

    def test_nonterminals_first(self):

        print("\n\n ==========--------( Nonterminal FIRST sets )--------------------------------------------------------========\n\n")

        for N in nonterminals.nonterminals.values():
            X = dragonsets.FIRST(N)

            self.assertGreater(len(X), 0, "Nonterminal: " + str(N))
            print("{:>50} {}".format(str(N), str(X)))

    def test_follow(self):

        print("\n\n ==========--------( FOLLOW sets )--------------------------------------------------------========\n\n")

        dragonsets.compute_follows()

        X = list()
        for K in dragonsets.FOLLOW.D.keys():
            X.append(K)

        X.sort()

        for K in X:
            FK = dragonsets.FOLLOW(K)

            self.assertGreater(len(FK), 0)

            print("{:>50} {}".format(str(K), str(FK)))


    def test_carrots(self):
        FIRST = dragonsets.FIRST
        FOLLOW = dragonsets.FOLLOW

        Symbol = terminals.tokens["Symbol"]
        Carrots = terminals.tokens["Carrots"]

        optional_carrots = nonterminals.nonterminals["optional_carrots"]
        msg_chain_node = nonterminals.nonterminals["msg_chain_node"]


        X = FIRST(optional_carrots)

        self.assertSetEqual({Carrots, terminals.Empty}, X)

        X = FIRST([optional_carrots, Symbol, msg_chain_node])

        print("FIRST([optional_carrots, Symbol, msg_chain_node]): " + repr(X))
        self.assertSetEqual({Carrots, Symbol}, X)

