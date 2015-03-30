import unittest

from Skoarcery import terminals, emissions, underskoar


class Code_Lexer_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()
        underskoar.init(emissions.PY)

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
        underskoar.skoarToke()

    def EOF_token(self):
        underskoar.EOF_token()

    def whitespace_token(self):
        underskoar.whitespace_token()

    def typical_token(self, token):
        underskoar.typical_token(token)

    def test_PY_lexer(self):

        fd = open("SkoarPyon/lex.py", mode="w")

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
