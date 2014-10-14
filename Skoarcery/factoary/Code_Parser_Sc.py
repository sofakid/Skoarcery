import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable, emissions
from Skoarcery.langoids import Terminal, Nonterminal


class Code_Parser_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()
        emissions.init()

    def test_sc_rdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.terminals import Empty

        fd = open("../../SuperCollider/Klassy/Skoar/rdpp.sc", "w")
        SC = emissions.SC
        SC.fd = fd

        # Header
        # Imports
        # class SkoarParseException
        # class SkoarParser:
        #     init
        #     fail
        self.code_start()

        SC.tab += 1
        N = nonterminals.nonterminals.values()

        # precompute desirables
        SC.method("init_desirables")
        for A in N:

            R = A.production_rules

            SC.nl()
            SC.cmt(str(A))

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
                SC.dict_set("desirables", str(P), "[", end="")
                for toke in desires:
                    SC.raw(toke.toker_name)
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            SC.raw(",\n")
                            SC.stmt("           ", end="")
                        else:
                            SC.raw(", ")

                else:
                    SC.raw("]);\n")

        SC.end()

        # write each nonterminal as a function
        for A in N:

            R = A.production_rules

            #SC.cmt(str(A))
            SC.method(A.name, "parent")

            if A.intermediate:
                SC.var("noad", "parent")
            else:
                SC.var("noad", SC.v_new("SkoarNoad", SC.v_str(A.name), SC.null, "parent"))

            SC.var("desires", SC.null)
            SC.nl()

            # each production
            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                SC.stmt("desires = " + SC.v_dict_get("desirables", str(P)))

                SC.cmt(str(P))

                SC.if_("toker.sees(desires) != " + SC.null)

                #SC.print(str(P))

                for x in alpha:
                    if isinstance(x, Terminal):
                        SC.stmt('noad.add_toke(' + SC.v_str(x.toker_name) + ', toker.burn(' + x.toker_name + '))')

                        #SC.print("burning: " + x.name)
                    else:
                        if x.intermediate:
                            SC.stmt(SC.this + "." + x.name + "(noad)")
                        else:
                            SC.stmt("noad.add_noad(this." + x.name + "(noad))")
                else:
                    SC.return_("noad")

                SC.end_if()

            if A.derives_empty:
                SC.cmt("<e>")
                #SC.print("burning empty")
                SC.return_("noad")

            else:
                SC.cmt("Error State")
                SC.stmt("this.fail")

            SC.end()

        SC.end()

        fd.close()

    def code_start(self):
        SC = emissions.SC
        SC.file_header("rdpp.sc", "Code_Parser_Sc - Create Recursive Descent Predictive Parser")
        SC.raw("""
SkoarParseException : Exception {

}

SkoarParser {

    var <runtime, <toker, <tab, desirables;

    *new {
        | runtime |
        ^super.new.init( runtime )
    }

    init {
        | runtime |

        runtime = runtime;
        toker = runtime.toker;
        tab = 0;
        desirables = IdentityDictionary();
        this.init_desirables();
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

