import unittest
from Skoarcery import dragonsets, tokens, nonterminals


class Tokens(unittest.TestCase):

    def setUp(self):
        tokens.init()

    def tearDown(self):
        pass

    def tes_input(self):
        print("\n\n ==========--------( Tokens: Input )--------------------------------------------------------========\n\n")
        print(tokens.string_of_tokens)

    def test_sorted(self):
        print("\n\n ==========--------( Tokens: Created )--------------------------------------------------------========\n\n")
        L = tokens.list_of_names

        L.sort()

        i = 0
        for T in L:
            i += 1
            print("{:>16}".format(T), end=('\n' if ((i % 7) == 0) else ' '))

