import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import SC


class Code_Parser_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()

    def test_sc_rdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.terminals import Empty

        fd = open("../../SuperCollider/Klassy/rdpp.sc", "w")
        SC.fd = fd

        # Header
        # Imports
        # class SkoarParseException
        # class SkoarParser:
        #     __init__
        #     fail
        self.code_start()

        SC.tab += 1
        N = nonterminals.nonterminals.values()

        # write each nonterminal as a function
        for A in N:

            R = A.production_rules

            #SC.cmt(str(A))
            SC.stmt(A.name + " {")
            SC.tab += 1
            SC.stmt("| parent |\n")

            if A.intermediate:
                SC.stmt("var noad = parent;")
            else:
                SC.stmt("var noad = SkoarNoad('" + A.name + "', None, parent);")

            SC.stmt("var desires = nil;\n")

            #SC.code_line("print('" + A.name + "')")

            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                desires = FIRST(alpha)

                if Empty in desires:
                    desires.discard(Empty)
                    desires.update(FOLLOW(A))

                SC.cmt(str(P))

                i = 0

                n = len(desires)
                SC.stmt("desires = [", end="")
                for toke in desires:
                    SC.raw(toke.toker_name + ".class")
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            SC.raw(",\n")
                            SC.stmt("           ", end="")
                        else:
                            SC.raw(", ")

                else:
                    SC.raw("];\n")

                SC.if_("toker.sees(desires)")

                #SC.print(str(P))

                for x in alpha:
                    if isinstance(x, Terminal):
                        SC.stmt('noad.add_toke("' + x.toker_name + '", toker.burn(' + x.toker_name + '));')

                        #SC.print("burning: " + x.name)
                    else:
                        if x.intermediate:
                            SC.stmt("this." + x.name + "(noad);")
                        else:
                            SC.stmt("noad.add_noad(this." + x.name + "(noad));")
                else:
                    SC.return_("noad")

                SC.stmt("};\n")

            if A.derives_empty:
                SC.cmt("<e>")
                #SC.print("burning empty")
                SC.return_("noad")

            else:
                SC.cmt("Error State")
                SC.stmt("this.fail;")
                SC.tab -= 1

            SC.stmt("}")
            SC.newline()

        SC.tab -= 1
        SC.stmt("}")

        fd.close()

    def code_start(self):

        SC.file_header("rdpp.sc", "Code_Parser_Sc - Create Recursive Descent Predictive Parser")
        SC.raw("""
SkoarParseException : Exception {

}

SkoarParser {

    var <runtime, <toker, <tab;

    *new {
        | runtime |
        ^super.new.init( runtime )
    }

    init {
        | runtime |

        runtime = runtime;
        toker = runtime.toker;
        tab = 0;
    }

    fail {
        toker.dump;
        SkoarParseException("Fail").throw;
    }

    //print {
    //    | line, end |
    //    (line ++ end).postln;
    //}

""")

