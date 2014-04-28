import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY


class PyRDPP(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()

    def test_pyrdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.tokens import Empty


        fd = open("../pymp/rdpp.py", "w")
        PY.fd = fd

        self.code_start()

        PY.tab += 1
        order = sorted(nonterminals.nonterminals.values(),
                       key=lambda x: len(x.production_rules) * 10 +
                                     len(x.production_rules[0].production))

        for A in order:

            R = A.production_rules

            #PY.cmt(str(A))
            PY.code_line("def " + A.name + "(self):")
            PY.tab += 1
            #PY.code_line("print('" + A.name + "')")

            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                desires = FIRST(alpha)

                if Empty in desires:
                    desires.discard(Empty)
                    desires.update(FOLLOW(P))

                PY.cmt(str(P))

                i = 0

                n = len(desires)
                PY.code_line("desires = [", end="")
                for toke in desires:
                    PY.code_raw(toke.toker_name)
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            PY.code_raw(",\n")
                            PY.code_line("           ", end="")
                        else:
                            PY.code_raw(", ")

                else:
                    PY.code_raw("]\n")

                PY.code_line("if self.toker.sees(desires):")

                PY.tab += 1
                for x in alpha:
                    if isinstance(x, Terminal):
                        PY.code_line("self.toker.burn(" + x.toker_name + ")")
                    else:
                        PY.code_line("self." + x.name + "()")
                else:
                    PY.code_line("return\n")
                PY.tab -= 1

            if A.derives_empty:
                PY.cmt("<e>")
                PY.code_line("return")

            else:
                PY.cmt("Error State")
                PY.code_line("self.fail()")

            PY.tab -= 1
            PY.newline()

        PY.tab -= 1

        fd.close()

    def code_start(self):
        from Skoarcery.tokens import Empty

        PY.file_header("rdpp.py", "PyRDPP - Create Recursive Descent Predictive Parser")

        s = "from Skoarcery.pymp.lex import "
        T = tokens.tokens.values()
        n = len(T)
        i = 0
        for t in T:
            if t == Empty:
                n -= 1
                continue
            s += t.toker_name
            i += 1
            if i != n:
                if i % 5 == 0:
                    s += ", \\\n    "
                else:
                    s += ", "

        PY.code_raw(s + """


class SkoarParseException(Exception):
    pass


class SkoarParser:

    def __init__(self, toker):
        self.toker = toker

    def fail(self):
        raise SkoarParseException

""")
