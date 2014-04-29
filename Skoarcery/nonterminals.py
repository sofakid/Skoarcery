
src = """
skoar   : phrases
phrases : phrasey phrases | <e>

phrasey : Comment | markers | meter | skoaroid | dal_goto | beat

markers        : Segno | Fine | coda | measure_marker
measure_marker : Bars opt_volta
opt_volta      : Volta | <e>

beat : Crotchets | Quavers | Quarters | Eighths | Slash

meter              : MeterS meter_stmts MeterE
meter_stmts        : meter_stmt meter_stmts | <e>
meter_stmt         : Int meter_stmt_numbery | meteroid | comment
meter_stmt_numbery : AssOp meter_ass_r | meter_sig_prime
meter_ass_r        : Symbol | beat | dynamic
meter_sig_prime    : Slash Int

meteroid           : optional_carrots Symbol msg_chain_node | clef
clef               : TrebleClef | BassClef | AltoClef

listy               : ListS listy_suffix
listy_suffix        : listy_entries ListE
listy_entries       : skoaroid moar_listy_entries
moar_listy_entries  : ListSep listy_entries | <e>

musical_keyword      : dynamic | ottavas | musical_keyword_misc
musical_keyword_misc : PedalDown | PedalUp | Rep | DubRep | Portamento
ottavas              : OttavaA | OttavaB | QuindicesimaA | QuindicesimaB | Loco
dynamic              : DynPiano | DynForte | DynSFZ | DynFP

acc              : AccSharp | AccNatural | AccFlat
accidentally     : acc noaty
sharps_or_flats  : NoatSharps | NoatFlats | <e>
noat             : VectorNoat sharps_or_flats

noaty            : Choard | Symbol | CurNoat | noat | listy | conditional
nouny            : cthulhu | noaty | nouny_literal | accidentally | musical_keyword
nouny_literal    : Tuplet | Caesura | Slur | Int | Float | String

skoaroid         : nouny skoaroid_prime
skoaroid_prime   : assignment skoaroid_prime | <e>

assignment       : AssOp settable
settable         : Caesura | CurNoat | Symbol | listy

optional_carrots : Carrots | <e>
stmt             : optional_carrots skoaroid msg_chain_node
optional_stmt    : stmt | <e>
msg_chain_node   : MsgOp msg msg_chain_node | <e>
msg              : MsgNameWithArgs listy_suffix | MsgName | listy

boolean          : skoaroid BooleanOp skoaroid
conditional      : CondS optional_stmt CondSep boolean CondSep optional_stmt CondE
cthulhu          : LWing CondSep cthulhu_prime
cthulhu_prime    : boolean CondSep RWing | Nosey CondSep RWing

dal_goto         : DaCapo al_whatnow | DalSegno al_whatnow
al_whatnow       : AlCoda | AlSegno | AlFine | <e>
coda             : Coda optional_al_coda
optional_al_coda : AlCoda | <e>


"""



SKOAR = None
nonterminals = None


def init():
    global nonterminals, SKOAR
    nonterminals = dict()

    from Skoarcery import tokens
    from Skoarcery.langoids import Nonterminal

    def o_hai_have_we_met(name):

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

                    X = o_hai_have_we_met(langoid)

                    p.append(X)

            X = o_hai_have_we_met(name)

            X.add_production(p)

            #print(repr(X))

    SKOAR = nonterminals["skoar"]

    print("nonterminals initialized.")
