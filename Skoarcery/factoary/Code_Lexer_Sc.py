import unittest
from Skoarcery import terminals, emissions
from Skoarcery.factoary import schematics

bs = "{"
be = "}"


class Code_Lexer_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()

    def exceptions(self):

        SC = emissions.SC
        SC.cmt_hdr("SkoarException")
        SC.class_("SkoarError", "Exception")

        SC.static_method("new", "msg")
        SC.return_("super.new(msg)")
        SC.end_block()

        SC.static_method("errorString")
        SC.return_('"SKOAR" ++ super.errorString')
        SC.end_block()

        SC.end_block()

    def base_token(self):

        schematics.skoarToke(emissions.SC)

    def EOF_token(self):

        emissions.SC.cmt_hdr("EOF is special")
        emissions.SC.raw(
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

        emissions.SC.cmt_hdr("Whitespace is special")
        emissions.SC.raw(
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
        schematics.typical_token(emissions.SC, token)

    def test_ScLexer(self):

        fd = open("../../SuperCollider/Klassy/lex.sc", mode="w")

        emissions.SC.fd = fd
        emissions.SC.file_header("lex", "Code_Sc_Lexer")

        self.exceptions()
        self.base_token()
        self.whitespace_token()
        self.EOF_token()

        emissions.SC.cmt_hdr("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)

        fd.close()