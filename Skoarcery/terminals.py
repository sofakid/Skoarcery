src = """
#
# Skoar Tokes
#
# format (at very start of line): TokeName: regex
#
# If token carries information: TokeName*: regex
#   -  be sure an inspector for TokeName exists
#

<e>:         unused
EOF:         unused
Whitespace:  \\s*

Comment:        <[?](.|[\\n\\r])*?[?]>

MeterS:         <!
MeterE:         !>
TrebleClef:     G:|treble:
BassClef:       F:|bass:
AltoClef:       C:|alto:



CurNoat:        [$]
Portamento:     ~~~
Slur:           [+][+]

# careful not to match ottavas with end in (ma,mb,va,vb), or steal from floats
Int*:           (-)?(0|[1-9][0-9]*)(?![mv][ab]|\\.[0-9])
Float*:         (-)?(0|[1-9][0-9]*)\\.[0-9]+

ListS:          <(?![!=?])
ListE:          >
ListSep:        ,

# one or many ^ but don't eat ^^( which is cthulhu's left wing
Carrots*:       \\^+(?!\\^\\^[(])?
LWing:          \\^\\^[(]
RWing:          [)]\\^\\^

Tuplet*:        /\\d+(:\\d+)?|(du|tri|quadru)plets?|(quin|sex|sep|oc)tuplets?
Crotchets*:     [}]+
Quavers*:       o+/
Caesura:        //

# we can't allow f for forte as f is a noat, so we allow
#
#  forte fforte ffforte ff fff, but not f
#
#  for consisentecy, piano, ppiano, pppiano work too.
#
#  default velocity:
#    ppp (16), pp (32), p (48), mp (64), mf (80), f (96), ff (112), fff (127)

DynPiano*:       (mp|p+)(iano)?
DynForte*:       mf(orte)?|f+orte|ff+
DynSFZ:          sfz
DynFP:           fp

Quarters*:       [)]+\\.?
Eighths*:        \\]+\\.?
AssOp:            =>
MsgOp:            \\.
Soak:             [?]

AccSharp:         #|sharp
AccNatural:       nat
AccFlat:          flat

# VectorNoat*:      ~?([a-eg]|f(?![a-zA-Z_]))(#*|b*)~?
VectorNoat*:      [a-eg]|f(?![a-zA-Z_])
BooleanOp*:       ==|!=|<=|>=|in|nin|and|or|xor
Choard*:          (D(?!\\.[CS]\\.)|[ABCEFG])([Mm0-9]|sus|dim)*
CondS:            [{][?]
CondSep:          ;
CondE:            [?][}]
MsgName*:         [a-zA-Z_][a-zA-Z0-9_]*
MsgNameWithArgs*: [a-zA-Z_][a-zA-Z0-9_]*<

Nosey:            ,

DaCapo:           D\\.C\\.|Da Capo
DalSegno:         D\\.S\\.|Dal Segno
Fine:             fine
Segno*:           %S%(?:_[a-zA-Z_][a-zA-Z0-9_]*)*
Coda:             \\([+]\\)
Rep:              \\./\\.
DubRep:           /\\.\\|\\./
AlCoda:           al(la)? coda
AlSegno:          al segno
AlFine:           al fine


OttavaA:          8va|ottava (alta|sopra)|all' ottava
OttavaB:          8vb|ottava (bassa|sotto)

QuindicesimaA:    15ma|alla quindicesima
QuindicesimaB:    15mb|alla quindicesimb

Loco:             loco
Volta*:           \\[\\d+\\.\\]

Symbol*:          \\\\[a-zA-Z][a-zA-Z0-9]+
Slash:            /

# TODO: deal with \"
String*:          \'[^']*\'

Bars*:            :?[\\|]+:?

PedalDown:        Ped\\.
PedalUp:          [*]

"""

#
#
#

list_of_names = None
inspectables = None
tokens = None
Empty = None
EOF = None
Whitespace = None

odd_balls = None


def init():
    from Skoarcery.langoids import Terminal
    global src, list_of_names, tokens, EOF, Empty, Whitespace, odd_balls, inspectables

    list_of_names = []
    inspectables = []
    tokens = dict()

    for token_line in src.split("\n"):

        token_line = token_line.strip()
        if len(token_line) > 0 and not token_line.startswith("#"):

            (token, v, regex) = token_line.partition(":")

            token = token.strip()
            regex = "^" + regex.strip()

            if token.endswith("*"):
                token = token.rstrip("*")
                inspectables.append(token)

            list_of_names.append(token)

            tokens[token] = Terminal(token, regex)

    #print("# tokens initialized.")

    Empty = Terminal("<e>", None)
    EOF = Terminal("EOF", None)
    Whitespace = tokens["Whitespace"]

    odd_balls = {Empty, EOF, Whitespace}


