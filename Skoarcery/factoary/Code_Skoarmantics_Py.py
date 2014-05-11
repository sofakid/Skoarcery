import unittest
from Skoarcery import terminals, emissions, nonterminals


class Code_Skoarmantics_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()


    def imports(self):
        emissions.PY.raw(
"""



"""
        )

    def skoarmantic(self, X):
        emissions.PY.raw(
"""def {0}(skoar, noad):
    pass


""".format(X.name)
        )

    def test_PySkoarmantics(self):

        fd = open("../pymp/skoarmantics_template.py", mode="w")

        emissions.PY.fd = fd

        emissions.PY.file_header("Skoarmantics", "Code_Skoarmantics_Py")

        self.imports()

        emissions.PY.cmt_hdr("")
        for X in nonterminals.nonterminals.values():
            if X.has_semantics:
                self.skoarmantic(X)

        fd.close()
