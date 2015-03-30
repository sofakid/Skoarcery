from imp import reload
import unittest
from Skoarcery.factoary.Buildoar import Buildoar
from Skoarcery.factoary.Code_Lexer_Py import Code_Lexer_Py
from Skoarcery.factoary.Code_Parser_Py import Code_Parser_Py
from Skoarcery.laboaratoary.TestDragonSpells import DragonTests
from Skoarcery.laboaratoary.ExamineParseTree import ExamineParseTree
from Skoarcery.laboaratoary.TestParseTable import Verify_LL_1
from Skoarcery.laboaratoary.TestNonterminals import TestNonterminals
from Skoarcery.laboaratoary.TestPerformer import Test_Performer
from Skoarcery.laboaratoary.TestTerminals import TestTokens
from Skoarcery.laboaratoary.TestApparatus import Test_Apparatus


class Build_Pymp(Buildoar):

    def tes_Build_Skoar_Python(self):

        #
        # Grammar
        self.step("Test Grammar",
                  [TestTokens, TestNonterminals, Verify_LL_1, DragonTests])

        #
        # Lexer
        self.step("Build Lexer", Code_Lexer_Py)

        from Skoarcery.SkoarPyon import lex
        reload(lex)

        # #
        # # Toke Inspector
        # self.step(Code_Lexer_Inspector_Py)
        #
        # from Skoarcery.pymp import toke_inspector
        # reload(toke_inspector)

        #
        # Parser
        self.step("Build Parser", Code_Parser_Py)

        from Skoarcery.SkoarPyon import rdpp
        reload(rdpp)

        #
        # Apparatus
        self.step("Sanity",
                  [Test_Apparatus, ExamineParseTree, Test_Performer])

if __name__ == '__main__':
    unittest.main()
