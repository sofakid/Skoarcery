
import unittest
from subprocess import Popen, PIPE
import subprocess


class Test_Sclang(unittest.TestCase):

    home = "/p/supercollider/build/Install/SuperCollider/SuperCollider.app/Contents/Resources/"
    sclang = home + "sclang"

    testing_home = "/Users/lucas/PycharmProjects/Skoar/SuperCollider/testing/runtests.scd"

    def print(self, msg):
        print("sclang: " + msg, end="")

    def exec(self, scd_code_file=None):

        class_lib_compiled = False
        class_lib_inited = False
        error_seen = False

        a_cmd = [Test_Sclang.sclang, "-D"]

        if scd_code_file:
            a_cmd.append(scd_code_file)

        print(str(a_cmd))

        proc = Popen(a_cmd, stdout=PIPE, stderr=subprocess.STDOUT)
        while proc.poll() is None:
            b = proc.stdout.readline()
            line = str(b, encoding="utf-8")
            self.print(line)

            #
            # yay
            if line.startswith("Class tree inited in"):
                class_lib_inited = True

            if line.startswith("compile done"):
                class_lib_compiled = True

            #
            # nay
            if line.startswith("-----------------------------------"):
                error_seen = True
                break

            #
            # complete
            if line.find("Welcome to SuperCollider") != -1:
                break

        self.assertFalse(error_seen, "Errors compiling class library.")
        self.assertTrue(class_lib_compiled, "Class library didn't compile.")
        self.assertTrue(class_lib_inited, "Class libarary did initialize.")

        tests_passed = False
        while proc.poll() is None:
            b = proc.stdout.readline()
            line = str(b, encoding="utf-8")
            self.print(line)

            #
            # yay
            if line.startswith("SKOAR PASS"):
                tests_passed = True
                break

            #
            # nay
            if line.startswith("SKOAR FAIL"):
                tests_passed = False
                break

        try:
            proc.terminate()
        finally:
            proc.stdout.close()

        self.assertTrue(tests_passed, "scland unit tests failed.")

    def test_sanity(self):
        self.exec(Test_Sclang.testing_home)
