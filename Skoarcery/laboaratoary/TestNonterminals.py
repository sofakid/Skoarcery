import unittest
from Skoarcery import dragonsets, terminals, nonterminals


class TestNonterminals(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()

    def tearDown(self):
        pass


    def test_unused(self):
        T = terminals.tokens
        N = nonterminals.nonterminals

        unused = []

        skip = [N["skoar"]]

        for x in N.values():

            if x in skip:
                continue

            # should be on the right side of some production
            uses = False
            for n in N.values():
                uses |= 0 < len([p for p in n.production_rules if x in p.production])

            if not uses:
                unused.append(x.name)

        if unused:
            print("-------( Unused Nonterminals ) ------------\n")
            for untoked in unused:
                print(untoked)
            print("")

            self.fail("Unused Nonterminals: " + str(len(unused)))



    def test_undefined(self):
        T = terminals.tokens
        N = nonterminals.nonterminals

        undefined = set()

        # verify everything on the right side is defined
        for x in N.values():

            for p in x.production_rules:

                for y in p.production:

                    if not (y in N.values() or y in T.values()):

                        undefined.add(y)

        if len(undefined) > 0:
            print("-------( Undefined langoids in productions ) ------------\n")
            for x in undefined:
                print(x.name)
            print("")

            self.fail("Unused Nonterminals: " + str(len(undefined)))




