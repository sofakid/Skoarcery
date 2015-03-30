# ==============
# toke_inspector
# ==============
#
# Here we pick the vals out of the tokens
# and set its attributes appropriately
from math import ldexp

import re


def Toke_Int(toke):
    toke.val = int(toke.lexeme)


def Toke_Float(toke):
    toke.val = float(toke.lexeme)


def Toke_Carrots(toke):
    toke.val = len(toke.lexeme)


def Toke_Tuplet(toke):
    toke.val = 0


def Toke_Crotchets(toke):
    toke.is_rest = True
    toke.val = 2 ** len(toke.lexeme)


def Toke_Quavers(toke):
    toke.is_rest = True
    # len("oo/")
    toke.val = ldexp(1, -(len(toke.lexeme) - 1))


def Toke_DynPiano(toke):
    toke.val = 0


def Toke_DynForte(toke):
    toke.val = 0


def Toke_Quarters(toke):
    toke.is_rest = False
    toke.val = 2 ** (len(toke.lexeme) - 1)


def Toke_Eighths(toke):
    toke.is_rest = False
    toke.val = ldexp(1, -len(toke.lexeme))


vector_noat_regex = re.compile(r"(~?)([a-g])(?:(#*)|(b*))(~?)")


def Toke_NamedNoat(toke):
    s = toke.lexeme

    r = vector_noat_regex.search(s)

    if r.group(1):
        toke.up = True

    toke.letter = r.group(2)

    sharps = r.group(3)
    if sharps:
        toke.sharps = len(sharps)

    flats = r.group(4)
    if flats:
        toke.flats = len(flats)

    if r.group(5):
        if toke.up:
            raise AssertionError("Can't noat up and down: " + s)
        toke.down = True


def Toke_BooleanOp(toke):
    toke.val = toke.lexeme


def Toke_Choard(toke):
    toke.val = toke.lexeme


def Toke_MsgName(toke):
    toke.val = toke.lexeme


def Toke_MsgNameWithArgs(toke):
    toke.val = toke.lexeme.rstrip("<")


def Toke_Volta(toke):
    toke.val = int(toke.lexeme.strip("[.]"))


def Toke_Symbol(toke):
    toke.val = toke.lexeme[1:]


def Toke_Segno(toke):
    a = toke.lexeme.split("_")
    if len(a) > 1:
        toke.label = a[1]
    else:
        toke.label = ""


def Toke_String(toke):
    toke.val = toke.lexeme[1:-1]


def Toke_Bars(toke):
    toke.pre_repeat = toke.lexeme.startswith(":")
    toke.post_repeat = toke.lexeme.endswith(":")
    toke.unspent = True



