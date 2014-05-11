
import unittest
from subprocess import Popen, PIPE
import subprocess


class Test_Sclang(unittest.TestCase):

    home = "/Applications/SuperCollider/SuperCollider.app/Contents/Resources/"
    path = home + "sclang"

    def print(self, msg):
        print("sclang: " + msg, end="")

    def test_exec(self):

        class_lib_compiled = False
        class_lib_inited = False
        error_seen = False

        proc = Popen([Test_Sclang.path, "-D"], stdout=PIPE, stderr=subprocess.STDOUT)
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
            if line.find("ERROR") != -1:
                error_seen = True
                break

            #
            # complete
            if line.startswith("Welcome to SuperCollider"):
                break

        try:
            proc.terminate()
        finally:
            proc.stdout.close()

        # assert afterwards to see the whole output
        self.assertFalse(error_seen, "Errors compiling class library.")
        self.assertTrue(class_lib_compiled, "Class library didn't compile.")
        self.assertTrue(class_lib_inited, "Class libarary did initialize.")

