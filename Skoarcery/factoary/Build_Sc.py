from imp import reload
import unittest
#from Skoarcery.factoary.Code_Lexer_Inspector_Py import Code_Lexer_Inspector_Py
from Skoarcery.factoary.Code_Lexer_Sc import Code_Lexer_Sc
from Skoarcery.factoary.Code_Parser_Sc import Code_Parser_Sc
from Skoarcery.laboaratoary.TestDragonSpells import DragonTests
from Skoarcery.laboaratoary.ExamineParseTree import ExamineParseTree
from Skoarcery.laboaratoary.TestParseTable import Verify_LL_1
from Skoarcery.laboaratoary.TestNonterminals import TestNonterminals
from Skoarcery.laboaratoary.TestPerformer import Test_Performer
from Skoarcery.laboaratoary.TestTerminals import TestTokens
from Skoarcery.laboaratoary.TestApparatus import Test_Apparatus
from Skoarcery.laboaratoary.Test_Sclang import Test_Sclang


class Build_All_Sc(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def run_tests(self, msg, list_of_tests):
        from unittest import TestSuite as TS, makeSuite as sweeten, TextTestRunner as Runner

        sweet = TS()

        if isinstance(list_of_tests, unittest.TestCase):
            list_of_tests = [list_of_tests]

        for test in list_of_tests:
            sweet.addTest(sweeten(test))

        self.assertTrue(Runner().run(sweet).wasSuccessful(), msg)

    def test_all_steps(self):

        #
        # Grammar
        self.run_tests("Test Grammar",
                       [TestTokens, TestNonterminals, Verify_LL_1, DragonTests])

        #
        # Lexer
        self.run_tests("Build Lexer", Code_Lexer_Sc)

        # #
        # # Toke Inspector
        #
        # self.run_tests("Build toke inspector", Code_Lexer_Inspector_Sc))

        #
        # Parser
        self.run_tests("Build Parser", Code_Parser_Sc)

        #
        # SuperCollider should compile class lib
        self.run_tests("Compile Parser", Test_Sclang)

        from Skoarcery.pymp import rdpp
        reload(rdpp)

        #
        # Apparatus
        self.run_tests("Sanity", [Test_Apparatus, ExamineParseTree, Test_Performer])


if __name__ == '__main__':
    unittest.main()
