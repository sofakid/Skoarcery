import unittest

from Skoarcery import terminals, emissions
from Skoarcery.schematics import lexer


class Code_Lexer_Inspector_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()

    def imports(self):
        emissions.PY.raw(
"""
import re
import abc


"""
        )

    def typical_token(self, token):
        emissions.PY.raw(
"""def {0}(toke):
    toke.val = 0


""".format(token.toker_name, token.regex)
        )

    def test_PyInspector(self):

        fd = open("../pymp/toke_inspector_template.py", mode="w")

        PY = emissions.PY
        PY.fd = fd
        PY.file_header("toke_inspector", "Code_Lexer_Inspector_Py")

        self.imports()

        emissions.PY.cmt_hdr("Value Tokes")
        for name in terminals.inspectables:
            token = terminals.tokens[name]
            lexer.tokeInspector(PY, token)

        fd.close()
