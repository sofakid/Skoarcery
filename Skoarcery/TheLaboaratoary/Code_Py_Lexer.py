import unittest
from Skoarcery import tokens, emissions


class Code_Py_Lexer(unittest.TestCase):

    def setUp(self):
        tokens.init()

    def imports(self):
        emissions.PY.code_raw(
"""
import re
import abc


"""
        )

    def base_token(self):

        emissions.PY.wee_header("Abstract Token")

        emissions.PY.code_raw(
"""class SkoarToke:

    __metaclass__ = abc.ABCMeta

    regex = None

    def __init__(self, s):
        self.buf = s

    # how many characters to burn from the buffer
    def burn(self, *args):
        return len(self.buf)

    # override and return nil for no match, new toke otherwise
    @staticmethod
    @abc.abstractstaticmethod
    def match(buf, offs):
        raise NotImplementedError

    @staticmethod
    def match_toke(buf, offs, toke_class):

        match = toke_class.regex.match(buf, offs)

        if match:
            print("\\n\\n" + toke_class.__name__ + ": MATCH: {" + match.group(0) + "}")
            return toke_class(match.group(0))

        return None


"""
        )

    def eof_token(self):

        emissions.PY.wee_header("EOF is special")

        emissions.PY.code_raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"$")

    @staticmethod
    def burn(buf, offs):
        if len(buf) > offs:
            raise Exception("Tried to burn EOF when there's more input.")

        return 0

    @staticmethod
    def match(buf, offs):

        if len(buf) <= offs:
            assert len(buf) == offs
            return {0}("")

        return None


""".format(tokens.EOF.toker_name)
        )

    def whitespace_token(self):

        emissions.PY.wee_header("Whitespace is special")

        emissions.PY.code_raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"{1}")

    @staticmethod
    def burn(buf, offs):

        match = {0}.regex.match(buf, offs)

        if match:
            return len(match.group(0))

        return 0


""".format(tokens.WS.toker_name, tokens.WS.regex)
        )

    def typical_token(self, token):
        emissions.PY.code_raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"{1}")

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, {0})


""".format(token.toker_name, token.regex)
        )

    def test_PyLexer(self):

        fd = open("../pymp/lex.py", mode="w")

        emissions.PY.fd = fd

        emissions.PY.file_header("lex", "Code_Py_Lexer")

        self.imports()
        self.base_token()
        self.whitespace_token()
        self.eof_token()

        emissions.PY.wee_header("Everyday Tokes")
        for token in tokens.tokens.values():
            if token not in tokens.odd_balls:
                self.typical_token(token)

        fd.close()
