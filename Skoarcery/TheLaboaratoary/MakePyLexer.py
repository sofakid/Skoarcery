import unittest
from Skoarcery import tokens


class MakePyLexer(unittest.TestCase):

    def setUp(self):
        tokens.init()

    def header(self):
        print(
"""
import re
import abc

"""
        )

    def base_token(self):
        print(
"""
class SkoarToke:

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

        o = buf.findRegexp(toke_class.regex, offs)

        if len(o) > 0:
            return toke_class.new(o[0][1])

        return None
"""
        )

    def whitespace_token(self):
        print(
"""

class Toke_WS(SkoarToke):
    regex = r"{}"

    @staticmethod
    def burn(buf, offs):
        o = buf.findRegexp("^\s*", offs)

        if len(o.size) > 0:
            return len(o[0][1])

        return 0
""".format(tokens.WS.regex)
        )

    def typical_token(self, token):
        print(
"""
class Toke_{0}(SkoarToke):
    regex = r"{1}"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_{0})
""".format(token.name, token.regex)
        )

    def test_PyLexer(self):

        self.header()
        self.base_token()
        self.whitespace_token()

        for token in tokens.tokens.values():
            if token not in tokens.odd_balls:
                self.typical_token(token)

