import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets
from Skoarcery.langoids import Terminal


class MakeParseTable(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        pass

    def tearDown(self):
        pass

    # Dragon Spell: 4.4 Construction of a predictive parsing table
    def test_make_table(self):
        from collections import defaultdict
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.tokens import Empty, EOF

        # M[ Nonterm, Term ] = Production
        M = defaultdict(dict)

        duplicates = 0
        # (1) For each production A -> alpha
        #
        print("for each production P = A -> alpha:")
        for A in nonterminals.nonterminals.values():
            M[A] = []
            for P in A.production_rules:

                alpha = P.production
                print("\n    P = " + str(P))

                #
                # (2)
                #
                print("    for a in FIRST(alpha):")
                for a in FIRST(alpha):

                    if a != Empty:
                        X = M[A, a]

                        if X:
                            print("")
                            print("        ####  Grammar is not LL(1). Fuck. ####-----------------------")
                            print("")
                            print("        M[{}, {}]:".format(A.name, b.name))
                            print("            " + str(X))
                            print("            " + str(P))
                            print("")

                            #print("X = {}\nP = {}\nA = {}\na = {}".format(str(X), str(P), str(A), str(a)))
                            duplicates += 1

                        print("        M[A,a] = M[{}, {}] = P".format(A.name, a.name))
                        M[A, a] = P

                    # (3)
                    else:

                        print("        <e>, so for b in FOLLOW(A):")
                        for b in FOLLOW(A):
                            X = M[A, b]

                            if X:

                                print("")
                                print("            ####  Grammar is not LL(1). Fuck. ####-----------------------")
                                print("")
                                print("            M[{}, {}]:".format(A.name, b.name))
                                print("                " + str(X))
                                print("                " + str(P))
                                print("")

                                #print("X = {}\nP = {}\nA = {}\nb = {}".format(str(X), str(P), str(A), str(b)))
                                #raise AssertionError("3) Grammar is not LL(1). Fuck.")
                                duplicates += 1

                            print("            M[A,b] = M[{}, {}] = P".format(A.name, b.name))
                            M[A, b] = P

        self.assertTrue(duplicates is 0, str(duplicates) + " duplicate entries: Grammar is not LL(1).")
