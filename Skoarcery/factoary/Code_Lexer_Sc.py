import unittest
from Skoarcery import terminals, emissions


bs = "{"
be = "}"


class Code_Sc_Lexer(unittest.TestCase):

    def setUp(self):
        terminals.init()

    def exceptions(self):
        emissions.SC.code_raw(
"""
SkoarError : Exception {
    *new {
        | msg |
        ^super.new(msg)
    }
    errorString {
        ^"SKOAR'" ++ super.errorString;
    }
}
"""
        )

    def base_token(self):

        emissions.SC.wee_header("Abstract Token")
        emissions.SC.code_raw(
"""SkoarToke {

    var <lexeme;
    classvar <regex = nil;
    classvar <inspectable = false;

    *new {
        | s |
        ^super.new.init( s )
    }

    init {
        | s |
        lexeme = s;
    }

    // how many characters to burn from the buffer
    burn {^lexeme.size}

    // override and return nil for no match, new toke otherwise
    *match {}

    *match_toke {
        | buf, offs, toke_class |

        var o = buf.findRegexp(toke_class.regex, offs);

        if (o.size > 0) {
            ^SkoarToke.class.new(o[0][1]);
        };

        ^nil
    }

}


"""
        )

    def EOF_token(self):

        emissions.SC.wee_header("EOF is special")
        emissions.SC.code_raw(
"""{0} : SkoarToke {bs}
    classvar <regex = "$";

    burn {bs}
        | buf, offs |

        if (buf.size > offs) {bs}
            SkoarError("Tried to burn EOF when there's more input.").throw
        {be}

        ^0
    {be}

    *match {bs}
        | buf, offs |

        if (buf.size < offs) {bs}
            SkoarError("offset too large matching EOF").throw;
        {be};

        if (buf.size == offs) {bs}
            ^{0}("");
        {be};

        ^nil
    {be}
{be}

""".format(terminals.EOF.toker_name, bs=bs, be=be)
        )

    def whitespace_token(self):

        emissions.SC.wee_header("Whitespace is special")
        emissions.SC.code_raw(
"""{0} : SkoarToke {bs}
    classvar <regex = "{1}";

    *burn {bs}
        | buf, offs |

        var o = buf.findRegexp("{1}", offs);

        if (o.size > 0) {bs}
            ^o[0][1].size;
        {be};

        ^0
    {be}
{be}


""".format(terminals.Whitespace.toker_name, terminals.Whitespace.regex, bs=bs, be=be)
        )

    def typical_token(self, token):

        inspectable = "true" if token.name in terminals.inspectables else "false"

        emissions.SC.code_raw(
"""{0} : SkoarToke {bs}
    classvar <regex = "{1}";
    classvar <inspectable = {2};

    *match {bs}
        | buf, offs | ^SkoarToke.match_toke(buf, offs, {0}); {be}
{be}

""".format(token.toker_name, token.regex, inspectable, bs=bs, be=be)
        )

    def test_ScLexer(self):

        fd = open("../../SuperCollider/Klassy/lex.sc", mode="w")

        emissions.SC.fd = fd
        emissions.SC.file_header("lex", "Code_Sc_Lexer")

        self.exceptions()
        self.base_token()
        self.whitespace_token()
        self.EOF_token()

        emissions.SC.wee_header("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)


        fd.close()