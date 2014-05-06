# ==============
# toke_inspector
# ==============
#
# Here we pick the values out of the tokens
# and set its attributes appropriately

import re


def Toke_Comment(toke):
    toke.value = toke.lexeme[2:-2]


def Toke_Int(toke):
    toke.value = int(toke.lexeme)


def Toke_Float(toke):
    toke.value = float(toke.lexeme)


def Toke_Carrots(toke):
    toke.value = len(toke.lexeme)


def Toke_Tuplet(toke):
    toke.value = 0


def Toke_Crotchets(toke):
    toke.is_rest = True
    toke.value = len(toke.lexeme)


def Toke_Quavers(toke):
    toke.is_rest = True
    toke.value = len(toke.lexeme) - 1


def Toke_DynPiano(toke):
    toke.value = 0


def Toke_DynForte(toke):
    toke.value = 0


def Toke_Quarters(toke):
    toke.is_rest = False
    toke.value = len(toke.lexeme)


def Toke_Eighths(toke):
    toke.is_rest = False
    toke.value = len(toke.lexeme)


vector_noat_regex = re.compile(r"(~?)([a-g])(?:(#*)|(b*))(~?)")


def Toke_VectorNoat(toke):
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
    toke.value = toke.lexeme


def Toke_Choard(toke):
    toke.value = toke.lexeme


def Toke_MsgName(toke):
    toke.value = toke.lexeme


def Toke_MsgNameWithArgs(toke):
    toke.value = toke.lexeme.rstrip("<")


def Toke_Volta(toke):
    toke.value = int(toke.lexeme.strip("[.]"))


def Toke_Symbol(toke):
    toke.value = toke.lexeme[1:]


def Toke_Segno(toke):
    a = toke.lexeme.split("_")
    if len(a) > 1:
        toke.label = a[1]
    else:
        toke.label = ""


def Toke_String(toke):
    toke.value = toke.lexeme[1:-1]


def Toke_Bars(toke):
    toke.pre_repeat = toke.lexeme.startswith(":")
    toke.post_repeat = toke.lexeme.endswith(":")



