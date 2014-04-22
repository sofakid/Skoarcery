
string_of_tokens = """
CurNoat
MeterS
MeterE
MeterSig
MeterKW
Portamento
Slur
Plus
Minus
ZedPlus
TwoPow
Int
Float
Str
ListS
ListE
ListSep
Carrots
LWing
RWing
Tuplet
Crotchets
Quavers
Caesura
DynPiano
DynForte
DynSFZ
DynFP
Quarters
Eighths
Env
AssOp
MsgOp
AccSharp
AccNatural
AccFlat
NoatName
NoatSharps
NoatFlats
BooleanOp
Choard
CondS
CondSep
CondE
MsgName
MsgNameWithArgs
Nosey
DaCap
DalSeg
Fine
Segno
Coda
Rep
DubRep
Goto
Symbol
Slash
TwoPower
String
<e>
EOF
Bars
Colon
Label
"""

#
#
#

list_of_names = None
tokens = None
Empty = None
EOF = None


def init():
    from Skoarcery.langoids import Terminal
    global string_of_tokens, list_of_names, tokens, EOF, Empty

    list_of_names = []
    tokens = dict()

    for token in string_of_tokens.split():
        list_of_names.append(token)

        tokens[token] = Terminal(token)

    Empty = tokens["<e>"]
    EOF = tokens["EOF"]