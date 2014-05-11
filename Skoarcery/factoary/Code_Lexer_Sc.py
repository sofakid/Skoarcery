import unittest
from Skoarcery import terminals, emissions


bs = "{"
be = "}"


class Code_Lexer_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()

    def exceptions(self):

        SC = emissions.SC
        SC._wee_header("SkoarException")
        SC._class("SkoarError", "Exception")

        SC._static_method("new", "msg")
        SC._return("super.new(msg)")
        SC._end_block()

        SC._static_method("errorString")
        SC._return('"SKOAR" ++ super.errorString')
        SC._end_block()

        SC._end_block()

    def base_token(self):

        SC = emissions.SC
        SC._wee_header("Abstract Token")

        SC._abstract_class("SkoarToke")
        SC._var("<", "lexeme")
        SC._classvar("<", "regex", SC.null)
        SC._classvar("<", "inspectable", SC.false)
        SC._newline()
        SC._constructor("s")
        SC.code_line("lexeme = s")
        SC._end_block()

        SC._cmt("how many characters to burn from the buffer")
        SC._method("burn")
        SC._return(SC.length("lexeme"))
        SC._end_block()

        SC._cmt("override and return " + SC.null + " for no match, new toke otherwise")
        SC._abstract_static_method("match")
        SC._end_block()

        SC._cmt("match requested toke")
        SC._static_method("match_toke", "buf", "offs", "toke_class")
        SC.code_line("var o = buf.findRegexp(toke_class.regex, offs)")

        SC._if(SC.length("o") + " > 0")
        SC._return("SkoarToke.class.new(o[0][1])")
        SC._end_if()

        SC._return(SC.null)
        SC._end_block()

        SC._end_block()

    def EOF_token(self):

        emissions.SC._wee_header("EOF is special")
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

        emissions.SC._wee_header("Whitespace is special")
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

        SC = emissions.SC
        inspectable = SC.true if token.name in terminals.inspectables else SC.false

        SC._class(token.toker_name, "SkoarToke")

        SC._classvar("<", "regex", '"' + token.regex + '"')
        SC._classvar("<", "inspectable", inspectable)
        SC._newline()

        SC._static_method("match", "buf", "offs")
        SC._return("SkoarToke.match_toke(buf, offs, " + token.toker_name + ")")
        SC._end_block()

        SC._end_block()

    def test_ScLexer(self):

        fd = open("../../SuperCollider/Klassy/lex.sc", mode="w")

        emissions.SC.fd = fd
        emissions.SC._file_header("lex", "Code_Sc_Lexer")

        self.exceptions()
        self.base_token()
        self.whitespace_token()
        self.EOF_token()

        emissions.SC._wee_header("Everyday Tokes")
        for token in terminals.tokens.values():
            if token not in terminals.odd_balls:
                self.typical_token(token)

        fd.close()