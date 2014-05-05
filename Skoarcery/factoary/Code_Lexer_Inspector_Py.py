import unittest
from Skoarcery import terminals, emissions


class Code_Lexer_Inspector_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()

    def imports(self):
        emissions.PY.code_raw(
"""
import re
import abc


"""
        )

    def typical_token(self, token):
        emissions.PY.code_raw(
"""def {0}(toke):
    toke.value = 0


""".format(token.toker_name, token.regex)
        )

    def test_PyInspector(self):

        fd = open("../pymp/toke_inspector_template.py", mode="w")

        emissions.PY.fd = fd

        emissions.PY.file_header("toke_inspector", "Code_Lexer_Inspector_Py")

        self.imports()

        emissions.PY.wee_header("Value Tokes")
        for name in terminals.inspectables:
            token = terminals.tokens[name]
            self.typical_token(token)

        fd.close()
