
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

skoar              : branches
+branches          : branch branches | <e>

branch*            : optional_voice phrases Newline | skoarpion Newline
+optional_voice    : Voice | <e>

+phrases           : phrasey phrases | <e>
+phrasey           : Comment | marker | Meter | stmt | dal_goto | beat

marker*            : Segno | Fine | coda | Volta | Bars

coda*              : Coda optional_al_coda
optional_al_coda   : AlCoda | <e>

dal_goto*          : DaCapo al_x | DalSegno al_x
al_x*              : AlCoda | AlSegno | AlFine | <e>

beat*              : Crotchets | Quavers | Quarters | Eighths | Slash

skoarpion*         : SkoarpionSep SkoarpionName SkoarpionSep skoarpion_lines SkoarpionSep stinger
+skoarpion_lines   : skoarpion_line skoarpion_lines | <e>
skoarpion_line*    : phrases Newline
stinger            : phrases

listy*             : ListS listy_suffix
+listy_suffix      : listy_entries ListE
+listy_entries     : skoaroid moar_listy_entries
+moar_listy_entries: ListSep listy_entries | Newline | <e>

musical_keyword      : dynamic | ottavas | pedally | musical_keyword_misc
musical_keyword_misc*: Rep | Portamento | Carrot
pedally*             : PedalDown | PedalUp
ottavas*             : OctaveShift | OttavaA | OttavaB | QuindicesimaA | QuindicesimaB | Loco
dynamic*             : DynPiano | DynForte | DynSFZ | DynFP

nouny*           : cthulhu | conditional | nouny_literal | musical_keyword | listy
+nouny_literal   : Tuplet | Caesura | Slur | Int | Float | String | SkoarpionRef | Choard | NamedNoat | Symbol | OnBeat

skoaroid*        : nouny skoaroid_prime
+skoaroid_prime  : assignment skoaroid_prime | <e>

assignment*      : AssOp settable
+settable        : Caesura | OnBeat | Symbol | listy | Quarters | Eighths

stmt*            : skoaroid msg_chain_node
optional_stmt    : stmt | Newline | <e>
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
