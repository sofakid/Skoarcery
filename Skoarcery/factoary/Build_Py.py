from imp import reload
import unittest
from Skoarcery.factoary.Code_Lexer_Py import Code_Lexer_Py
from Skoarcery.factoary.Code_Parser_Py import Code_Parser_Py
from Skoarcery.laboaratoary.TestDragonSpells import DragonTests
from Skoarcery.laboaratoary.ExamineParseTree import ExamineParseTree
from Skoarcery.laboaratoary.TestParseTable import Verify_LL_1
from Skoarcery.laboaratoary.TestNonterminals import TestNonterminals
from Skoarcery.laboaratoary.TestTerminals import TestTokens
from Skoarcery.laboaratoary.TestApparatus import Test_Apparatus


class Build_All_Py(unittest.TestCase):

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_all_steps(self):

        from unittest import TestSuite as TS, makeSuite as sweeten, TextTestRunner as Runner

        #
        # Grammar
        sweet = TS()

        for test in [TestTokens, TestNonterminals, Verify_LL_1, DragonTests, ]:
            sweet.addTest(sweeten(test))

        Runner().run(sweet)

        #
        # Lexer
        sweet = TS()
        sweet.addTest(sweeten(Code_Lexer_Py))
        Runner().run(sweet)

        from Skoarcery.pymp import lex
        reload(lex)

        #
        # Parser
        sweet = TS()
        sweet.addTest(sweeten(Code_Parser_Py))
        Runner().run(sweet)

        from Skoarcery.pymp import rdpp
        reload(rdpp)

        #
        # Apparatus
        sweet = TS()
        for test in [Test_Apparatus, ExamineParseTree]:
            sweet.addTest(sweeten(test))
        Runner().run(sweet)

if __name__ == '__main__':
    unittest.main()
