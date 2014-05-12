import unittest
from Skoarcery import terminals, emissions, nonterminals


class Code_Skoarmantics_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        emissions.init()


    def imports(self):
        emissions.PY.raw(
"""



"""
        )

    def symbols(self):
        _ = _____ = _________ = _____________ = _________________ = _____________________ = emissions.PY

        _.cmt_hdr("Symbols")
        _.stmt(_.v_ass("skoar_", _.v_str("skoar")))
        _.stmt(_.v_ass("noad_", _.v_str("noad")))
        _.stmt(_.v_ass("noat_", _.v_str("noat")))
        _.stmt(_.v_ass("toke_", _.v_str("toke")))
        _.stmt(_.v_ass("node_absorb_toke_", _.v_str("noad.absorb_toke()")))
        _.stmt(_.v_ass("toke_", _.v_str("toke")))

        for X in nonterminals.nonterminals.values():
            if X.has_semantics:
                _.stmt(_.v_ass(X.name + "_", _.v_str(X.name)))

        _.nl()

    def test_PySkoarmantics(self):

        fd = open("../schematics/semantics_template.py", mode="w")

        PY = emissions.PY
        _ = _____ = _________ = _____________ = _________________ = _____________________ = PY
        _.fd = fd
        _.file_header("Semantics", "Code_Skoarmantics_Py")

        self.imports()

        self.symbols()
        _.cmt_hdr("semantic functions")

        for X in nonterminals.nonterminals.values():
            if X.has_semantics:
                name = X.name
                _.function(name)
                _____.stmt("_.function(" + name + "_, skoar_, noad_)")
                _____.stmt("_____.stmt(node_absorb_toke_)")
                _____.stmt("_.end()")
                _.end()

        fd.close()

