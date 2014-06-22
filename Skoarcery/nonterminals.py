
src = """

# Skoar Nonterminals
#
# like_this for nonterminals, LikeThis for terminals
#
# + before a nonterminal indicates this is an intermediate step that can be
# skipped in the constructed parse tree, it will not create a new skoarnode,
# instead appending its noads to its parent's children list.
#
# * after a nonterminal means there is corresponding semantic code for this,
# defined in skoarmantics.py

skoar   : phrases
+phrases: phrasey phrases | <e>
+phrasey: Comment | marker | meter | skoaroid | dal_goto | beat

marker*            : Segno | Fine | coda | Volta | Bars

coda*              : Coda optional_al_coda
optional_al_coda   : AlCoda | <e>

dal_goto*          : DaCapo al_x | DalSegno al_x
al_x               : AlCoda | AlSegno | AlFine | <e>

beat*              : Crotchets | Quavers | Quarters | Eighths | Slash
meter_beat*        : Crotchets | Quavers | Quarters | Eighths | Slash

meter*             : MeterS meter_stmts MeterE
+meter_stmts       : meter_stmt meter_stmts | <e>
meter_stmt         : Int meter_stmt_numbery | meteroid
+meter_stmt_numbery: meter_ass | meter_sig_prime
meter_ass*         : AssOp meter_ass_r
+meter_ass_r       : Symbol | meter_beat | dynamic
meter_sig_prime*   : Slash Int

meteroid           : meter_symbolic | clef
meter_symbolic*    : optional_carrots Symbol msg_chain_node
clef*              : TrebleClef | BassClef | AltoClef

listy*             : ListS listy_suffix
+listy_suffix      : listy_entries ListE
+listy_entries     : skoaroid moar_listy_entries
+moar_listy_entries: ListSep listy_entries | <e>

musical_keyword      : dynamic | ottavas | pedally | musical_keyword_misc
musical_keyword_misc*: Rep | DubRep | Portamento
pedally*             : PedalDown | PedalUp
ottavas*             : OctaveShift | OttavaA | OttavaB | QuindicesimaA | QuindicesimaB | Loco
dynamic*             : DynPiano | DynForte | DynSFZ | DynFP

acc              : AccSharp | AccNatural | AccFlat
accidentally*    : acc noaty

noaty*           : noat_literal | noat_reference
noat_literal*    : Choard | NamedNoat
noat_reference*  : Symbol | CurNoat | listy
nouny            : cthulhu | noaty | conditional | nouny_literal | accidentally | musical_keyword
+nouny_literal   : Tuplet | Caesura | Slur | Int | Float | String

skoaroid*        : nouny skoaroid_prime
+skoaroid_prime  : assignment skoaroid_prime | <e>

assignment*      : AssOp settable
+settable        : Caesura | CurNoat | Symbol | listy

optional_carrots*: Carrots | <e>
stmt*            : optional_carrots skoaroid msg_chain_node
optional_stmt    : stmt | <e>
msg_chain_node*  : optional_soak MsgOp msg msg_chain_node | <e>
msg*             : MsgNameWithArgs listy_suffix | MsgName | listy
optional_soak    : Soak | <e>

boolean*         : skoaroid BooleanOp skoaroid
conditional      : CondS optional_stmt CondSep boolean CondSep optional_stmt CondE
cthulhu*         : LWing CondSep cthulhu_prime
+cthulhu_prime   : boolean CondSep RWing | Nosey CondSep RWing



"""


SKOAR = None
nonterminals = None


def init():
    global nonterminals, SKOAR
    nonterminals = dict()

    from Skoarcery import terminals
    from Skoarcery.langoids import Nonterminal

    # create and track as they appear
    def hello(name):

        try:
            xoid = nonterminals[name]
        except KeyError:
            xoid = Nonterminal(name)
            nonterminals[name] = xoid

        return xoid

    for bnf_line in src.split("\n"):
        if len(bnf_line) == 0 or bnf_line.lstrip().startswith("#"):
            continue

        #print(bnf_line)
        a = bnf_line.split(":")

        name = a[0].strip()

        if name.startswith("+"):
            name = name.lstrip("+")
            intermediate = True
        else:
            intermediate = False

        if name.endswith("*"):
            name = name.rstrip("*")
            has_semantics = True
        else:
            has_semantics = False

        for production in a[1].split("|"):

            p = []
            for langoid in production.split():

                if len(langoid) == 0:
                    continue

                toke = terminals.tokens.get(langoid)

                if toke:
                    p.append(toke)
                else:

                    if langoid[0].isupper():
                        raise Exception("Unknown token " + langoid)

                    X = hello(langoid)

                    p.append(X)

            X = hello(name)
            X.intermediate = intermediate
            X.has_semantics = has_semantics
            X.add_production(p)

    SKOAR = nonterminals["skoar"]

    print("nonterminals initialized.")
