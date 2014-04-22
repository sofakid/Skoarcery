
src = """

skoar : phrases

phrases :  meter phrases | stmt phrases | measure_marker phrases | <e>

measure_marker : opt_goto Bars opt_cometo

opt_goto  : goto   | <e>
opt_cometo: cometo | <e>

goto   : Colon optional_labels
cometo : optional_labels Colon

optional_labels : Label moar_labels | <e>

moar_labels: ListSep Label moar_labels | <e>

meter_sig : complex_meter Slash TwoPower

complex_meter : ZedPlus moar_complex

moar_complex : Plus complex_meter | <e>

meter : MeterS meter_stmts MeterE

meter_stmts : meter_stmt meter_stmts | <e>

meter_stmt : meter_sig | beat AssOp ZedPlus | dynamic AssOp ZedPlus | meteroid

meteroid : optional_carrots Symbol msg_chain_node | <e>

dynamic : DynPiano | DynForte | DynSFZ | DynFP

listy : ListS listy_suffix

listy_suffix: listy_entries ListE

listy_entries : skoaroid moar_listy_entries

moar_listy_entries : ListSep listy_entries | <e>

optional_carrots: Carrots | <e>


noaty : Choard | Symbol | CurNoat | noat | listy | conditional

skoaroid : Int | Float | String | Env | Tuplet | Caesura | noaty | accidentally | assignment | beat | cthulhu | dynamic

beat : Crotchets | Quavers | Quarters | Eighths

assignment : settable AssOp skoaroid | Symbol AssOp meter

settable : Env | listy | Caesura | CurNoat | Symbol

acc : AccSharp | AccNatural | AccFlat

accidentally : acc noaty

sharps_or_flats : NoatSharps | NoatFlats | <e>

vector : Int | <e>

noat : vector NoatName sharps_or_flats

boolean : skoaroid BooleanOp skoaroid

stmt: optional_carrots skoaroid msg_chain_node

optional_stmt : stmt | <e>

msg_chain_node : MsgOp msg msg_chain_node | <e>

msg : MsgNameWithArgs listy_suffix | MsgName | listy

conditional : CondS optional_stmt CondSep boolean CondSep optional_stmt CondE

cthulhu : LWing CondSep boolean CondSep RWing

"""


#
#
#
#
#
#
#
#
#

nonterminals = None
SKOAR = None


def init():
    global nonterminals, SKOAR
    nonterminals = dict()

    from Skoarcery import tokens
    from Skoarcery.langoids import Nonterminal

    #noinspection PyPep8Naming
    def hajimemashite(name):

        try:
            N = nonterminals[name]
        except KeyError:
            N = Nonterminal(name)
            nonterminals[name] = N

        return N

    for bnf_line in src.split("\n"):
        if len(bnf_line) > 0:
            #print(bnf_line)
            a = bnf_line.split(":")

            name = a[0].strip()

            for production in a[1].split("|"):

                p = []
                for langoid in production.split():

                    if len(langoid) == 0:
                        continue

                    toke = tokens.tokens.get(langoid)

                    if toke:
                        p.append(toke)
                    else:

                        if langoid[0].isupper():
                            raise Exception("Unknown token " + langoid)

                        N = hajimemashite(langoid)

                        p.append(N)

                N = hajimemashite(name)

                N.add_production(p)

            #print(repr(N))

    SKOAR = nonterminals["skoar"]

