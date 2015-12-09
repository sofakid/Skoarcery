import unittest
from Skoarcery.factoary.Buildoar import Buildoar
from Skoarcery.factoary.Code_Lexer_Sc import Code_Lexer_Sc
from Skoarcery.factoary.Code_Parser_Sc import Code_Parser_Sc
from Skoarcery.laboaratoary.TestDragonSpells import DragonTests
from Skoarcery.laboaratoary.TestParseTable import Verify_LL_1
from Skoarcery.laboaratoary.TestNonterminals import TestNonterminals
from Skoarcery.laboaratoary.TestTerminals import TestTokens
from Skoarcery.laboaratoary.Test_Sclang import Test_Sclang, Test_Sclang_Sanity, Test_Sclang_Dev


class Build_Sc(Buildoar):

    def build(self):

        #
        # Grammar
        self.step("Test Grammar",
                  [TestTokens, TestNonterminals, Verify_LL_1, DragonTests])

        #
        # Lexer
        self.step("Build Lexer", Code_Lexer_Sc)

        # #
        # # Toke Inspector
        #
        # self.run_tests("Build toke inspector", Code_Lexer_Inspector_Sc))

        #
        # Parser
        self.step("Build Parser", Code_Parser_Sc)

        #
        # SuperCollider should compile class lib

    def sanity(self):
        self.step("Sanity Tests", Test_Sclang_Sanity)

    #
    # Apparatus
    #self.step("Sanity", [Test_Apparatus, ExamineParseTree, Test_Performer])

    def dev(self):
        self.step("Dev Tests", Test_Sclang_Dev)
        

#if __name__ == '__main__':
#    b = Build_Sc()
#    b.build()
    
