import unittest
from Skoarcery import terminals, emissions


class Code_Sc_Lexer(unittest.TestCase):

    def setUp(self):
        terminals.init()

    def base_token(self):

        emissions.SC.wee_header("Abstract Token")

        emissions.SC.code_raw(
"""SkoarToke {

    var <buf;
    classvar <regex = nil;

    *new {
        | s |
        ^super.new.init( s )
    }

    init {
        | s |
        buf = s;
    }

    // how many characters to burn from the buffer
    burn {^buf.size}

    // override and return nil for no match, new toke otherwise
    *match {}

    *match_toke {
        | buf, offs, toke_class |

        var o = buf.findRegexp(toke_class.regex, offs);

        if (o.size > 0) {
            ^SkoarTokeclass.new(o[0][1]);
        }

        ^nil
    }

}


"""
        )

    def whitespace_token(self):

        emissions.SC.wee_header("Whitespace is special")

        reg = '"' + terminals.Whitespace.regex + '"'

        emissions.SC.code_raw(
"""Toke_Whitespace : SkoarToke {
classvar <regex = """ + reg + """;

    *burn {
        | buf, offs |

        var o = buf.findRegexp(""" + reg + """, offs);

        if (o.size > 0) {
            ^o[0][1].size;
        }

        ^0
    }
}


""")

    def typical_token(self, token):

        name = token.name
        regex = '"' + token.regex + '"'

        emissions.SC.code_raw(
"Toke_" + name + """ : SkoarToke {
    classvar <regex = """ + regex + """;
    *match {
        | buf, offs | ^SkoarToke.match_token(buf, offs, Toke_""" + name + """) }
}


"""
        )

    def test_ScLexer(self):

        emissions.SC.file_header("lex", "Code_Sc_Lexer")

        self.base_token()
        self.whitespace_token()

        emissions.SC.wee_header("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)

