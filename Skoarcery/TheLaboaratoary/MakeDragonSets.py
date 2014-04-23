import unittest
from Skoarcery import dragonsets, tokens, nonterminals


class FIRST(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        dragonsets.init(compute=False)
        dragonsets.compute_firsts()

    def tearDown(self):
        pass

    def test_tokens_first(self):

        print("\n\n ==========--------( Terminal FIRST sets )-------------------------===========\n")

        for token in tokens.tokens.values():

            first = dragonsets.FIRST(token)

            self.assertIn(token, first, "token first wrong")
            self.assertEqual(1, len(first), "FIRST(token) should be {token}")

    def test_nonterminals_first(self):

        print("\n\n ==========--------( Nonterminal FIRST sets )--------------------------------------------------------========\n\n")

        X = list()
        #for K in iter(dragonsets.FIRST.D.keys()):
        for K in iter(nonterminals.nonterminals.keys()):
            if K[0].islower():
                X.append(K)

        X.sort()

        for K in X:
            print("{:>50} {}".format(str(K), str(dragonsets.FIRST(K))))

    def test_follow(self):

        print("\n\n ==========--------( FOLLOW sets )--------------------------------------------------------========\n\n")

        dragonsets.compute_follows()

        X = list()
        for K in iter(dragonsets.FOLLOW.D.keys()):
            X.append(K)

        X.sort()

        for K in X:
            FK = dragonsets.FOLLOW(K)

            s = "-" if len(FK) == 0 else str(FK)

            print("{:>50} {}".format(str(K), s))