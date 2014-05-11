import unittest
from Skoarcery import terminals, emissions
from Skoarcery.factoary import schematics


class Code_Lexer_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()

    def imports(self):
        emissions.PY.raw(
"""
import re
import abc

class SubclassResponsibilityError(NotImplementedError):
    pass

"""
        )

    def base_token(self):

        schematics.skoarToke(emissions.PY)

    def EOF_token(self):

        emissions.PY.cmt_hdr("EOF is special")
        emissions.PY.raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"$")

    @staticmethod
    def burn(buf, offs):
        print("Burning EOF")
        if len(buf) > offs:
            raise Exception("Tried to burn EOF when there's more input.")

        return 0

    @staticmethod
    def match(buf, offs):

        if len(buf) <= offs:
            print("woot")
            assert len(buf) == offs
            return {0}("")

        return None


""".format(terminals.EOF.toker_name)
        )

    def whitespace_token(self):

        emissions.PY.cmt_hdr("Whitespace is special")
        emissions.PY.raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"{1}")

    @staticmethod
    def burn(buf, offs):

        match = {0}.regex.match(buf, offs)

        if match:
            return len(match.group(0))

        return 0


""".format(terminals.Whitespace.toker_name, terminals.Whitespace.regex)
        )

    def typical_token(self, token):
        schematics.typical_token(emissions.PY, token)

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
