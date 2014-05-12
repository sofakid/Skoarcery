import unittest
from Skoarcery import terminals, emissions
from Skoarcery.factoary import schematics

bs = "{"
be = "}"


class Code_Lexer_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        emissions.init()
        schematics.init(emissions.SC)

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
        schematics.skoarToke()

    def EOF_token(self):
        schematics.EOF_token()

    def whitespace_token(self):
        schematics.whitespace_token()

    def typical_token(self, token):
        schematics.typical_token(token)

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