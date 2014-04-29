src = """
<e>:    unused
EOF:    unused
WS:             \\s*

Comment:        <[?]([.\\n]*(?![?]))[?]>

MeterS:         <!
MeterE:         !>
TrebleClef:     G:|treble:
BassClef:       F:|bass:
AltoClef:       C:|alto:



CurNoat:        \\$
Portamento:     ~~~
Slur:           \\+\\+
Int:            (\\+|-)?(0|[1-9][0-9]*)(?![mv][ab])
Float:          (\\+|-)?(0|[1-9][0-9]*)\\.[0-9]+

ListS:          <(?![!=?])
ListE:          >
ListSep:        ,
Carrots:        \\^+(?!\\^\\^\\()?
LWing:          \\^\\^[(]
RWing:          [)]\\^\\^
Tuplet:         /\\d+
Crotchets:      }+
Quavers:        o+/
Caesura:        //
DynPiano:       mp|p+
DynForte:       mf|f+
DynSFZ:         sfz
DynFP:          fp
Quarters:       [)]+\\.?
Eighths:        \\]+\\.?
AssOp:          =>
MsgOp:          \\.
AccSharp:       #|sharp
AccNatural:     nat
AccFlat:        flat

NoatSharps:     #
NoatFlats:      b
VectorNoat:     [a-g](#*|b*)
BooleanOp:      == | != | <= | >= | in | nin | and | or | xor
Choard:         (D(?!\\.[CS]\\.)|[ABCEFG])([Mm0-9]|sus|dim)*
CondS:          {
CondSep:        ;
CondE:          }
MsgName:         [a-zA-Z_][a-zA-Z0-9_]*
MsgNameWithArgs: [a-zA-Z_][a-zA-Z0-9_]*<

Nosey:          ,

DaCapo:         D\\.C\\.|Da Capo
DalSegno:       D\\.S\\.|Dal Segno
Fine:           fine
Segno:          %S%|al segno
Coda:           \\(\\+\\)
Rep:            \\./\\.
DubRep:         /\\.\\|\\./
AlCoda:         al(la)? coda
AlSegno:        al segno
AlFine:         al fine


OttavaA:        8va|ottava (alta|sopra)|all' ottava
OttavaB:        8vb|ottava (bassa|sotto)

QuindicesimaA:   15ma|alla quindicesima
QuindicesimaB:   15mb|alla quindicesimb

Loco:           loco
Volta:          \\[\\d+\\.\\]

Symbol:         \\\\[a-zA-Z][a-zA-Z0-9]+
Slash:          /
String:         \'[^']*\'
Bars:           :?[\\|]+:?

PedalDown:      Ped\\.
PedalUp:        \\*

"""

#
#
#

list_of_names = None
tokens = None
Empty = None
EOF = None
WS = None

odd_balls = None


def init():
    from Skoarcery.langoids import Terminal
    global src, list_of_names, tokens, EOF, Empty, WS, odd_balls

    list_of_names = []
    tokens = dict()

    for token_line in src.split("\n"):

        token_line = token_line.strip()
        if len(token_line) > 0:

            (token, v, regex) = token_line.partition(":")

            token = token.strip()
            regex = regex.strip()

            list_of_names.append(token)

            tokens[token] = Terminal(token, regex)

    #print("# tokens initialized.")

    Empty = Terminal("<e>", None)
    EOF = Terminal("EOF", None)
    WS = tokens["WS"]

    odd_balls = {Empty, EOF, WS}


