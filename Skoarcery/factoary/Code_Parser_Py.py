import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable, emissions
from Skoarcery.langoids import Terminal, Nonterminal


class Code_Parser_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()
        emissions.init()

    def test_PY_rdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.terminals import Empty

        fd = open("SkoarPyon/rdpp.py", "w")
        PY = emissions.PY
        PY.fd = fd

        # Header
        # Imports
        # class SkoarParseException
        # class SkoarParser:
        #     __init__
        #     fail
        self.code_start()

        PY.tab += 1
        N = nonterminals.nonterminals.values()

        # precompute desirables
        PY.method("init_desirables")
        for A in N:

            R = A.production_rules

            PY.nl()
            PY.cmt(str(A))

            # each production
            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                desires = FIRST(alpha)

                if Empty in desires:
                    desires.discard(Empty)
                    desires.update(FOLLOW(A))

                i = 0

                n = len(desires)
                PY.dict_set("self.desirables", str(P), "[", end="")
                for toke in desires:
                    PY.raw(toke.toker_name)
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            PY.raw(",\n")
                            PY.stmt("           ", end="")
                        else:
                            PY.raw(", ")

                else:
                    PY.raw("]\n")

        PY.end()

        # write each nonterminal as a function
        for A in N:

            R = A.production_rules

            #PY.cmt(str(A))
            PY.stmt("def " + A.name + "(self, parent):")
            PY.tab += 1
            PY.stmt("self.tab += 1")

            if A.intermediate:
                PY.var("noad", "parent")
            else:
                PY.var("noad", PY.v_new("SkoarNoad", PY.v_sym(A.name), "parent"))

            PY.nl()

            #PY.code_line("print('" + A.name + "')")

            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                PY.stmt("desires = " + PY.v_dict_get("self.desirables", str(P)))

                PY.if_("self.toker.sees(desires)")

                #PY.print(str(P))

                for x in alpha:
                    if isinstance(x, Terminal):
                        PY.stmt("noad.add_toke('" + x.toker_name + "', self.toker.burn(" + x.toker_name + "))")

                        #PY.print("burning: " + x.name)
                    else:
                        if x.intermediate:
                            PY.stmt("self." + x.name + "(noad)")
                        else:
                            PY.stmt("noad.add_noad(self." + x.name + "(noad))")
                else:
                    PY.return_("noad")
                    PY.tab -= 1
                    PY.nl()

            if A.derives_empty:
                PY.cmt("<e>")
                #PY.print("burning empty")
                PY.return_("noad")

            else:
                PY.cmt("Error State")
                PY.stmt("self.fail()")

            PY.tab -= 1
            PY.nl()

        PY.tab -= 1

        fd.close()

    def code_start(self):
        from Skoarcery.terminals import Empty

        PY = emissions.PY

        PY.file_header("rdpp", "PyRDPP - Create Recursive Descent Predictive Parser")
        s = "from Skoarcery.SkoarPyon.apparatus import SkoarNoad\n"\
            "from Skoarcery.SkoarPyon.lex import "
        T = terminals.tokens.values()
        n = len(T)
        i = 0
        for t in T:
            if t == Empty:
                n -= 1
                continue
            s += t.toker_name
            i += 1
            if i < n:
                if i % 5 == 0:
                    s += ", \\\n    "
                else:
                    s += ", "

        PY.raw(s + """


class SkoarParseException(Exception):
    pass


class SkoarParser:

    def __init__(self, runtime):
        self.runtime = runtime
        self.toker = runtime.toker
        self.tab = 0
        self.desirables = dict()
        self.init_desirables()

    def fail(self):
        self.toker.dump()
        raise SkoarParseException

    @property
    def tabby(self):
        if self.tab == 0:
            return ""

        return ("{:>" + str(self.tab * 2) + "}").format(" ")

    def print(self, line, end):
        print(self.tabby + line, end=end)


""")
