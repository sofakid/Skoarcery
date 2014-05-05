import unittest
from Skoarcery import dragonsets, terminals, nonterminals


class TestTokens(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()

    def tearDown(self):
        pass

    def tes_input(self):
        print("\n\n ==========--------( Tokens: Input )--------------------------------------------------------========\n\n")
        print(terminals.src)

    def test_sorted(self):
        print("\n\n ==========--------( Tokens: Created )--------------------------------------------------------========\n\n")
        L = terminals.list_of_names

        L.sort()

        i = 0
        for T in L:
            i += 1
            print("{:>16}".format(T), end=('\n' if ((i % 7) == 0) else ' '))

        print("\n")

    def test_untoked(self):
        T = terminals.tokens
        N = nonterminals.nonterminals

        unused = []

        skip = [terminals.Empty, terminals.EOF, terminals.Whitespace]

        for t in T.values():

            if t in skip:
                continue

            uses = []
            for n in N.values():
                uses.extend([p for p in n.production_rules if t in p.production])

            if len(uses) is 0:
                unused.append(t.name)

        if unused:
            print("-------( Untoked ) ------------\n")
            for untoked in unused:
                print(untoked)
            print("")

            self.fail("Untoked tokens: " + str(len(unused)))




