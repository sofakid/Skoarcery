import unittest

from Skoarcery import terminals, emissions
from Skoarcery.schematics import lexer


class Code_Lexer_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()
        lexer.init(emissions.PY)

    def imports(self):
        emissions.PY.raw(
"""
import re
import abc


class SubclassResponsibilityError(NotImplementedError):
    pass


class SkoarError(AssertionError):
    pass


"""
        )

    def base_token(self):
        lexer.skoarToke()

    def EOF_token(self):
        lexer.EOF_token()

    def whitespace_token(self):
        lexer.whitespace_token()

    def typical_token(self, token):
        lexer.typical_token(token)

    def test_PyLexer(self):

        fd = open("../pymp/lex.py", mode="w")

        emissions.PY.fd = fd

        emissions.PY.file_header("lex", "Code_Py_Lexer")

        self.imports()
        self.base_token()
        self.whitespace_token()
        self.EOF_token()

        emissions.PY.cmt_hdr("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)

        fd.close()
