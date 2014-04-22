import unittest
from Skoarcery import langoids, tokens


class FIRST(unittest.TestCase):

    def setUp(self):
        tokens.init()

    def tearDown(self):
        pass

    def tes_tokens_first(self):

        for token in tokens.tokens.values():

            first = token.first()

            self.assertIn(token, first, "token first wrong")
            self.assertEqual(1, len(first), "FIRST(token) should be {token}")

    def test_nonterminals_first(self):

        langoids.compute_firsts()

    def test_follow(self):
        langoids.compute_firsts()
        langoids.compute_follows()