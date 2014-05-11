import unittest
from Skoarcery import terminals, emissions


class Code_Lexer_Py(unittest.TestCase):

    def setUp(self):
        terminals.init()

    def imports(self):
        emissions.PY.raw(
"""
import re
import abc


"""
        )

    def base_token(self):

        emissions.PY._wee_header("Abstract Token")
        emissions.PY.raw(
"""
    @staticmethod
    def match_toke(buf, offs, toke_class):

        match = toke_class.regex.match(buf, offs)

        if match:
            #print("\\n" + toke_class.__name__ + ": MATCH: {" + match.group(0) + "}")
            return toke_class(match.group(0))

        return None


"""
        )

        PY = emissions.PY
        PY._wee_header("Abstract Token")

        PY._abstract_class("SkoarToke")
        PY._var("<", "lexeme")
        PY._classvar("<", "regex", PY.null)
        PY._classvar("<", "inspectable", PY.false)

        PY._constructor("s")
        PY.code_line("lexeme = s")
        PY._end_block()

        PY._cmt("how many characters to burn from the buffer")
        PY._method("burn")
        PY._return(PY.length("lexeme"))
        PY._end_block()

        PY._cmt("override and return " + PY.null + " for no match, new toke otherwise")
        PY._abstract_static_method("match")
        PY._exception("NotImplementedError")
        PY._end_block()

        PY._cmt("match requested toke")
        PY._static_method("match_toke", "buf", "offs", "toke_class")
        PY.code_line("match = toke_class.regex.match(buf, offs)")

        PY._if(PY.length("o") + " > 0")
        PY._return("toke_class(match.group(0))")
        PY._end_if()

        PY._return(PY.null)
        PY._end_block()

        PY._end_block()

    def EOF_token(self):

        emissions.PY._wee_header("EOF is special")
        emissions.PY.raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"$")

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

        emissions.PY._wee_header("Whitespace is special")
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
        emissions.PY.raw(
"""class {0}(SkoarToke):
    regex = re.compile(r"{1}")
    inspectable = {2}

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, {0})


""".format(token.toker_name, token.regex, token.name in terminals.inspectables)
        )

    def test_PyLexer(self):

        fd = open("../pymp/lex.py", mode="w")

        emissions.PY.fd = fd

        emissions.PY._file_header("lex", "Code_Py_Lexer")

        self.imports()
        self.base_token()
        self.whitespace_token()
        self.EOF_token()

        emissions.PY._wee_header("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)

        fd.close()
