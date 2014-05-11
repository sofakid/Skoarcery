import unittest


class Buildoar(unittest.TestCase):

    def step(self, msg, list_of_steps, assert_success=True):
        from unittest import TestSuite as TS, makeSuite as sweeten, TextTestRunner as Runner

        sweet = TS()

        if not isinstance(list_of_steps, list):
            list_of_steps = [list_of_steps]

        for test in list_of_steps:
            sweet.addTest(sweeten(test))

        result = Runner().run(sweet)

        if assert_success:
            self.assertTrue(result.wasSuccessful(), msg)
            print(msg + ": [ OK ]")


if __name__ == '__main__':
    unittest.main()
