import unittest
from Skoarcery import dragonsets, tokens, nonterminals


class Tokens(unittest.TestCase):

    def setUp(self):
        tokens.init()

    def tearDown(self):
        pass

    def tes_input(self):
        print("\n\n ==========--------( Tokens: Input )--------------------------------------------------------========\n\n")
        print(tokens.src)

    def test_sorted(self):
        print("\n\n ==========--------( Tokens: Created )--------------------------------------------------------========\n\n")
        L = tokens.list_of_names

        L.sort()

        i = 0
        for T in L:
            i += 1
            print("{:>16}".format(T), end=('\n' if ((i % 7) == 0) else ' '))

        print("\n")

    def test_untoked(self):
        T = tokens.tokens
        N = nonterminals.nonterminals

        unused = []

        skip = [tokens.Empty, tokens.EOF, tokens.WS]

        for t in T.values():

            if t in skip:
                continue

            uses = []
            for n in N.values():
                uses.extend([p for p in n.production_rules if t in p.production])

            unused.append(t.name)

        if unused:
            print("-------( Untoked ) ------------\n")
            for untoked in unused:
                print(untoked)
            print("")

            self.fail("Untoked tokens: " + str(len(unused)))




