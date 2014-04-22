import unittest
from Skoarcery import langoids, tokens, nonterminals
from Skoarcery.langoids import Terminal


class MakeParseTable(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        langoids.compute_firsts()
        langoids.compute_follows()
        pass

    def tearDown(self):
        pass

    def test_make_table(self):
        from collections import defaultdict
        from Skoarcery.langoids import FIRST, FOLLOW
        from Skoarcery.tokens import Empty, EOF

        # M[ Nonterm, Term ] = Production
        M = defaultdict(dict)

        # (1) For each production A -> alpha
        #
        for A in nonterminals.nonterminals.values():
            for P in A.production_rules:

                alpha = P.production
                if P.derives_empty:
                    continue

                # (2)
                #
                for a in FIRST(alpha):
                    # (3)
                    if a == Empty:

                        for b in FOLLOW(A):
                            if isinstance(b, Terminal) and b != Empty:

                                X = M[A, b]

                                if X and not X.derives_empty:

                                    print("3) Grammar is not LL(1). Fuck.")

                                    print("X = {}\nP = {}\nA = {}\nb = {}".format(str(X), str(P), str(A), str(b)))
                                    #raise AssertionError("3) Grammar is not LL(1). Fuck.")

                                print("3) M[{:>16}, {:<16}] = {}".format(A.name, b.name, str(P)))
                                M[A, b] = P

                    # (2')
                    elif isinstance(a, Terminal):

                        X = M[A, a]

                        if X and not X.derives_empty:
                            print("2) Grammar is not LL(1). Fuck.")

                            print("X = {}\nP = {}\nA = {}\na = {}".format(str(X), str(P), str(A), str(a)))
                            #raise AssertionError("2)  Grammar is not LL(1). Fuck.")

                        print("2) M[{:>16}, {:<16}] = {}".format(A.name, a.name, str(P)))
                        M[A, a] = P






