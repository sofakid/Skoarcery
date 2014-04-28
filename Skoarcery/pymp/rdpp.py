# ================================================================================================================
# rdpp.py - Generated by PyRDPP - Recursive Descent Predictive Parser on 2014-04-27 20:45:36.556233 for Python 3.4
# ================================================================================================================
from Skoarcery.pymp.lex import Toke_OttavaA, Toke_ListE, Toke_Slur, Toke_BassClef, Toke_PedalUp, \
    Toke_CondS, Toke_Float, Toke_Fine, Toke_OttavaB, Toke_Portamento, \
    Toke_Quavers, Toke_ListS, Toke_Segno, Toke_RWing, Toke_ListSep, \
    Toke_PedalDown, Toke_Loco, Toke_CondE, Toke_AssOp, Toke_CondSep, \
    Toke_String, Toke_Eighths, Toke_MsgNameWithArgs, Toke_DynPiano, Toke_Quarters, \
    Toke_Crotchets, Toke_Carrots, Toke_Caesura, Toke_Tuplet, Toke_Choard, \
    Toke_Bars, Toke_AlFine, Toke_MsgName, Toke_CurNoat, Toke_Rep, \
    Toke_LWing, Toke_ZedPlus, Toke_VectorNoat, Toke_Int, Toke_AccFlat, \
    Toke_AlCoda, Toke_DalSegno, Toke_AltoClef, Toke_MeterSig, Toke_AccSharp, \
    Toke_TrebleClef, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_Slash, \
    Toke_DubRep, Toke_MsgOp, Toke_NoatSharps, Toke_WS, Toke_BooleanOp, \
    Toke_DynFP, Toke_Coda, Toke_NoatFlats, Toke_AccNatural, Toke_EOF, \
    Toke_DaCapo, Toke_Symbol, Toke_DynSFZ, Toke_DynForte, Toke_Nosey, \
    Toke_Volta, Toke_MeterS, Toke_MeterE, Toke_AlSegno


class SkoarParseException(Exception):
    pass


class SkoarParser:

    def __init__(self, toker):
        self.toker = toker

    def fail(self):
        raise SkoarParseException

    # skoar
    def skoar(self):
        # skoar -> phrases
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Fine, Toke_Portamento, Toke_Slash, Toke_ListS,
                   Toke_Segno, Toke_PedalDown, Toke_Loco, Toke_Eighths, Toke_Quarters,
                   Toke_Crotchets, Toke_Caesura, Toke_Tuplet, Toke_Choard, Toke_Bars,
                   Toke_CurNoat, Toke_Rep, Toke_LWing, Toke_VectorNoat, Toke_Int,
                   Toke_AccFlat, Toke_DalSegno, Toke_AccSharp, Toke_DynPiano, Toke_QuindicesimaB,
                   Toke_QuindicesimaA, Toke_Quavers, Toke_DubRep, Toke_DynFP, Toke_Coda,
                   Toke_AccNatural, Toke_EOF, Toke_DaCapo, Toke_DynSFZ, Toke_DynForte,
                   Toke_MeterS, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.phrases()
            return

        # Error State
        self.fail()

    # listy
    def listy(self):
        # listy -> ListS listy_suffix
        desires = [Toke_ListS]
        if self.toker.sees(desires):
            self.toker.burn(Toke_ListS)
            self.listy_suffix()
            return

        # Error State
        self.fail()

    # meter_stmt_numbery
    def meter_stmt_numbery(self):
        # meter_stmt_numbery -> AssOp meter_ass_r
        desires = [Toke_AssOp]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AssOp)
            self.meter_ass_r()
            return

        # Error State
        self.fail()

    # coda
    def coda(self):
        # coda -> Coda optional_al_coda
        desires = [Toke_Coda]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Coda)
            self.optional_al_coda()
            return

        # Error State
        self.fail()

    # listy_entries
    def listy_entries(self):
        # listy_entries -> skoaroid moar_listy_entries
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.skoaroid()
            self.moar_listy_entries()
            return

        # Error State
        self.fail()

    # assignment
    def assignment(self):
        # assignment -> AssOp settable
        desires = [Toke_AssOp]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AssOp)
            self.settable()
            return

        # Error State
        self.fail()

    # listy_suffix
    def listy_suffix(self):
        # listy_suffix -> listy_entries ListE
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.listy_entries()
            self.toker.burn(Toke_ListE)
            return

        # Error State
        self.fail()

    # skoaroid
    def skoaroid(self):
        # skoaroid -> nouny skoaroid_prime
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.nouny()
            self.skoaroid_prime()
            return

        # Error State
        self.fail()

    # skoaroid_prime
    def skoaroid_prime(self):
        # skoaroid_prime -> assignment skoaroid_prime
        desires = [Toke_AssOp]
        if self.toker.sees(desires):
            self.assignment()
            self.skoaroid_prime()
            return

        # Error State
        self.fail()

    # accidentally
    def accidentally(self):
        # accidentally -> acc noaty
        desires = [Toke_AccNatural, Toke_AccSharp, Toke_AccFlat]
        if self.toker.sees(desires):
            self.acc()
            self.noaty()
            return

        # Error State
        self.fail()

    # noat
    def noat(self):
        # noat -> VectorNoat sharps_or_flats
        desires = [Toke_VectorNoat]
        if self.toker.sees(desires):
            self.toker.burn(Toke_VectorNoat)
            self.sharps_or_flats()
            return

        # Error State
        self.fail()

    # measure_marker
    def measure_marker(self):
        # measure_marker -> Bars opt_volta
        desires = [Toke_Bars]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Bars)
            self.opt_volta()
            return

        # Error State
        self.fail()

    # boolean
    def boolean(self):
        # boolean -> skoaroid BooleanOp skoaroid
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.skoaroid()
            self.toker.burn(Toke_BooleanOp)
            self.skoaroid()
            return

        # Error State
        self.fail()

    # stmt
    def stmt(self):
        # stmt -> optional_carrots skoaroid msg_chain_node
        desires = [Toke_Carrots]
        if self.toker.sees(desires):
            self.optional_carrots()
            self.skoaroid()
            self.msg_chain_node()
            return

        # Error State
        self.fail()

    # cthulhu
    def cthulhu(self):
        # cthulhu -> LWing CondSep cthulhu_prime
        desires = [Toke_LWing]
        if self.toker.sees(desires):
            self.toker.burn(Toke_LWing)
            self.toker.burn(Toke_CondSep)
            self.cthulhu_prime()
            return

        # Error State
        self.fail()

    # meter
    def meter(self):
        # meter -> MeterS meter_stmts MeterE
        desires = [Toke_MeterS]
        if self.toker.sees(desires):
            self.toker.burn(Toke_MeterS)
            self.meter_stmts()
            self.toker.burn(Toke_MeterE)
            return

        # Error State
        self.fail()

    # conditional
    def conditional(self):
        # conditional -> CondS optional_stmt CondSep boolean CondSep optional_stmt CondE
        desires = [Toke_CondS]
        if self.toker.sees(desires):
            self.toker.burn(Toke_CondS)
            self.optional_stmt()
            self.toker.burn(Toke_CondSep)
            self.boolean()
            self.toker.burn(Toke_CondSep)
            self.optional_stmt()
            self.toker.burn(Toke_CondE)
            return

        # Error State
        self.fail()

    # optional_al_coda
    def optional_al_coda(self):
        # optional_al_coda -> AlCoda
        desires = [Toke_AlCoda]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AlCoda)
            return

        # <e>
        return


    # optional_carrots
    def optional_carrots(self):
        # optional_carrots -> Carrots
        desires = [Toke_Carrots]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Carrots)
            return

        # <e>
        return


    # opt_volta
    def opt_volta(self):
        # opt_volta -> Volta
        desires = [Toke_Volta]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Volta)
            return

        # <e>
        return


    # optional_stmt
    def optional_stmt(self):
        # optional_stmt -> stmt
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_Carrots, Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat,
                   Toke_Rep, Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat,
                   Toke_AccSharp, Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep,
                   Toke_DynFP, Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String,
                   Toke_Symbol]
        if self.toker.sees(desires):
            self.stmt()
            return

        # <e>
        return


    # phrases
    def phrases(self):
        # phrases -> markers phrases
        desires = [Toke_Bars, Toke_Segno, Toke_Fine, Toke_Coda]
        if self.toker.sees(desires):
            self.markers()
            self.phrases()
            return

        # phrases -> phrasey
        desires = [Toke_Slur, Toke_CondS, Toke_Eighths, Toke_Quarters, Toke_Crotchets,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_VectorNoat, Toke_Int, Toke_DalSegno, Toke_DynPiano, Toke_Quavers,
                   Toke_DubRep, Toke_Loco, Toke_DaCapo, Toke_DynSFZ, Toke_DynForte,
                   Toke_MeterS, Toke_Symbol, Toke_OttavaA, Toke_OttavaB, Toke_AccNatural,
                   Toke_PedalUp, Toke_Float, Toke_Portamento, Toke_Slash, Toke_ListS,
                   Toke_Caesura, Toke_LWing, Toke_AccFlat, Toke_AccSharp, Toke_QuindicesimaB,
                   Toke_QuindicesimaA, Toke_DynFP, Toke_EOF, Toke_String]
        if self.toker.sees(desires):
            self.phrasey()
            return

        # Error State
        self.fail()

    # moar_listy_entries
    def moar_listy_entries(self):
        # moar_listy_entries -> ListSep listy_entries
        desires = [Toke_ListSep]
        if self.toker.sees(desires):
            self.toker.burn(Toke_ListSep)
            self.listy_entries()
            return

        # <e>
        return


    # dal_goto
    def dal_goto(self):
        # dal_goto -> DaCapo al_whatnow
        desires = [Toke_DaCapo]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DaCapo)
            self.al_whatnow()
            return

        # dal_goto -> DalSegno al_whatnow
        desires = [Toke_DalSegno]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DalSegno)
            self.al_whatnow()
            return

        # Error State
        self.fail()

    # meter_stmts
    def meter_stmts(self):
        # meter_stmts -> meter_stmt meter_stmts
        desires = [Toke_ZedPlus, Toke_BassClef, Toke_Carrots, Toke_AltoClef, Toke_MeterSig,
                   Toke_TrebleClef, Toke_Symbol]
        if self.toker.sees(desires):
            self.meter_stmt()
            self.meter_stmts()
            return

        # <e>
        return


    # msg_chain_node
    def msg_chain_node(self):
        # msg_chain_node -> MsgOp msg msg_chain_node
        desires = [Toke_MsgOp]
        if self.toker.sees(desires):
            self.toker.burn(Toke_MsgOp)
            self.msg()
            self.msg_chain_node()
            return

        # <e>
        return


    # cthulhu_prime
    def cthulhu_prime(self):
        # cthulhu_prime -> boolean CondSep RWing
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.boolean()
            self.toker.burn(Toke_CondSep)
            self.toker.burn(Toke_RWing)
            return

        # cthulhu_prime -> Nosey CondSep RWing
        desires = [Toke_Nosey]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Nosey)
            self.toker.burn(Toke_CondSep)
            self.toker.burn(Toke_RWing)
            return

        # Error State
        self.fail()

    # meteroid
    def meteroid(self):
        # meteroid -> optional_carrots Symbol msg_chain_node
        desires = [Toke_Carrots]
        if self.toker.sees(desires):
            self.optional_carrots()
            self.toker.burn(Toke_Symbol)
            self.msg_chain_node()
            return

        # meteroid -> clef
        desires = [Toke_AltoClef, Toke_BassClef, Toke_TrebleClef]
        if self.toker.sees(desires):
            self.clef()
            return

        # Error State
        self.fail()

    # meter_ass_r
    def meter_ass_r(self):
        # meter_ass_r -> Symbol
        desires = [Toke_Symbol]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Symbol)
            return

        # meter_ass_r -> beat
        desires = [Toke_Slash, Toke_Eighths, Toke_Quarters, Toke_Crotchets, Toke_Quavers]
        if self.toker.sees(desires):
            self.beat()
            return

        # meter_ass_r -> dynamic
        desires = [Toke_DynFP, Toke_DynSFZ, Toke_DynForte, Toke_DynPiano]
        if self.toker.sees(desires):
            self.dynamic()
            return

        # Error State
        self.fail()

    # sharps_or_flats
    def sharps_or_flats(self):
        # sharps_or_flats -> NoatSharps
        desires = [Toke_NoatSharps]
        if self.toker.sees(desires):
            self.toker.burn(Toke_NoatSharps)
            return

        # sharps_or_flats -> NoatFlats
        desires = [Toke_NoatFlats]
        if self.toker.sees(desires):
            self.toker.burn(Toke_NoatFlats)
            return

        # <e>
        return


    # clef
    def clef(self):
        # clef -> TrebleClef
        desires = [Toke_TrebleClef]
        if self.toker.sees(desires):
            self.toker.burn(Toke_TrebleClef)
            return

        # clef -> BassClef
        desires = [Toke_BassClef]
        if self.toker.sees(desires):
            self.toker.burn(Toke_BassClef)
            return

        # clef -> AltoClef
        desires = [Toke_AltoClef]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AltoClef)
            return

        # Error State
        self.fail()

    # al_whatnow
    def al_whatnow(self):
        # al_whatnow -> AlCoda
        desires = [Toke_AlCoda]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AlCoda)
            return

        # al_whatnow -> AlSegno
        desires = [Toke_AlSegno]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AlSegno)
            return

        # al_whatnow -> AlFine
        desires = [Toke_AlFine]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AlFine)
            return

        # Error State
        self.fail()

    # musical_keyword
    def musical_keyword(self):
        # musical_keyword -> dynamic
        desires = [Toke_DynFP, Toke_DynSFZ, Toke_DynForte, Toke_DynPiano]
        if self.toker.sees(desires):
            self.dynamic()
            return

        # musical_keyword -> ottavas
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Loco, Toke_QuindicesimaB, Toke_QuindicesimaA]
        if self.toker.sees(desires):
            self.ottavas()
            return

        # musical_keyword -> musical_keyword_misc
        desires = [Toke_Rep, Toke_DubRep, Toke_PedalUp, Toke_Portamento, Toke_PedalDown]
        if self.toker.sees(desires):
            self.musical_keyword_misc()
            return

        # Error State
        self.fail()

    # acc
    def acc(self):
        # acc -> AccSharp
        desires = [Toke_AccSharp]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AccSharp)
            return

        # acc -> AccNatural
        desires = [Toke_AccNatural]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AccNatural)
            return

        # acc -> AccFlat
        desires = [Toke_AccFlat]
        if self.toker.sees(desires):
            self.toker.burn(Toke_AccFlat)
            return

        # Error State
        self.fail()

    # meter_stmt
    def meter_stmt(self):
        # meter_stmt -> ZedPlus meter_stmt_numbery
        desires = [Toke_ZedPlus]
        if self.toker.sees(desires):
            self.toker.burn(Toke_ZedPlus)
            self.meter_stmt_numbery()
            return

        # meter_stmt -> meteroid
        desires = [Toke_BassClef, Toke_Carrots, Toke_AltoClef, Toke_TrebleClef, Toke_Symbol]
        if self.toker.sees(desires):
            self.meteroid()
            return

        # meter_stmt -> MeterSig
        desires = [Toke_MeterSig]
        if self.toker.sees(desires):
            self.toker.burn(Toke_MeterSig)
            return

        # Error State
        self.fail()

    # msg
    def msg(self):
        # msg -> MsgNameWithArgs listy_suffix
        desires = [Toke_MsgNameWithArgs]
        if self.toker.sees(desires):
            self.toker.burn(Toke_MsgNameWithArgs)
            self.listy_suffix()
            return

        # msg -> MsgName
        desires = [Toke_MsgName]
        if self.toker.sees(desires):
            self.toker.burn(Toke_MsgName)
            return

        # msg -> listy
        desires = [Toke_ListS]
        if self.toker.sees(desires):
            self.listy()
            return

        # Error State
        self.fail()

    # dynamic
    def dynamic(self):
        # dynamic -> DynPiano
        desires = [Toke_DynPiano]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DynPiano)
            return

        # dynamic -> DynForte
        desires = [Toke_DynForte]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DynForte)
            return

        # dynamic -> DynSFZ
        desires = [Toke_DynSFZ]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DynSFZ)
            return

        # dynamic -> DynFP
        desires = [Toke_DynFP]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DynFP)
            return

        # Error State
        self.fail()

    # settable
    def settable(self):
        # settable -> Caesura
        desires = [Toke_Caesura]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Caesura)
            return

        # settable -> CurNoat
        desires = [Toke_CurNoat]
        if self.toker.sees(desires):
            self.toker.burn(Toke_CurNoat)
            return

        # settable -> Symbol
        desires = [Toke_Symbol]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Symbol)
            return

        # settable -> listy
        desires = [Toke_ListS]
        if self.toker.sees(desires):
            self.listy()
            return

        # Error State
        self.fail()

    # markers
    def markers(self):
        # markers -> Segno
        desires = [Toke_Segno]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Segno)
            return

        # markers -> Fine
        desires = [Toke_Fine]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Fine)
            return

        # markers -> coda
        desires = [Toke_Coda]
        if self.toker.sees(desires):
            self.coda()
            return

        # markers -> measure_marker
        desires = [Toke_Bars]
        if self.toker.sees(desires):
            self.measure_marker()
            return

        # Error State
        self.fail()

    # phrasey
    def phrasey(self):
        # phrasey -> meter
        desires = [Toke_MeterS]
        if self.toker.sees(desires):
            self.meter()
            return

        # phrasey -> skoaroid
        desires = [Toke_OttavaA, Toke_OttavaB, Toke_Slur, Toke_PedalUp, Toke_CondS,
                   Toke_Float, Toke_Portamento, Toke_ListS, Toke_AccNatural, Toke_Caesura,
                   Toke_PedalDown, Toke_Tuplet, Toke_Choard, Toke_CurNoat, Toke_Rep,
                   Toke_LWing, Toke_VectorNoat, Toke_Int, Toke_AccFlat, Toke_AccSharp,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_Loco, Toke_DynSFZ, Toke_DynForte, Toke_String, Toke_Symbol]
        if self.toker.sees(desires):
            self.skoaroid()
            return

        # phrasey -> dal_goto
        desires = [Toke_DalSegno, Toke_DaCapo]
        if self.toker.sees(desires):
            self.dal_goto()
            return

        # phrasey -> beat
        desires = [Toke_Slash, Toke_Eighths, Toke_Quarters, Toke_Crotchets, Toke_Quavers]
        if self.toker.sees(desires):
            self.beat()
            return

        # <e>
        return


    # nouny
    def nouny(self):
        # nouny -> cthulhu
        desires = [Toke_LWing]
        if self.toker.sees(desires):
            self.cthulhu()
            return

        # nouny -> noaty
        desires = [Toke_VectorNoat, Toke_CondS, Toke_Choard, Toke_ListS, Toke_CurNoat,
                   Toke_Symbol]
        if self.toker.sees(desires):
            self.noaty()
            return

        # nouny -> nouny_literal
        desires = [Toke_Slur, Toke_Int, Toke_Float, Toke_Caesura, Toke_Tuplet,
                   Toke_String]
        if self.toker.sees(desires):
            self.nouny_literal()
            return

        # nouny -> accidentally
        desires = [Toke_AccNatural, Toke_AccSharp, Toke_AccFlat]
        if self.toker.sees(desires):
            self.accidentally()
            return

        # nouny -> musical_keyword
        desires = [Toke_Rep, Toke_OttavaA, Toke_OttavaB, Toke_PedalUp, Toke_Portamento,
                   Toke_DynPiano, Toke_QuindicesimaB, Toke_QuindicesimaA, Toke_DubRep, Toke_DynFP,
                   Toke_PedalDown, Toke_DynSFZ, Toke_DynForte, Toke_Loco]
        if self.toker.sees(desires):
            self.musical_keyword()
            return

        # Error State
        self.fail()

    # musical_keyword_misc
    def musical_keyword_misc(self):
        # musical_keyword_misc -> PedalDown
        desires = [Toke_PedalDown]
        if self.toker.sees(desires):
            self.toker.burn(Toke_PedalDown)
            return

        # musical_keyword_misc -> PedalUp
        desires = [Toke_PedalUp]
        if self.toker.sees(desires):
            self.toker.burn(Toke_PedalUp)
            return

        # musical_keyword_misc -> Rep
        desires = [Toke_Rep]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Rep)
            return

        # musical_keyword_misc -> DubRep
        desires = [Toke_DubRep]
        if self.toker.sees(desires):
            self.toker.burn(Toke_DubRep)
            return

        # musical_keyword_misc -> Portamento
        desires = [Toke_Portamento]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Portamento)
            return

        # Error State
        self.fail()

    # beat
    def beat(self):
        # beat -> Crotchets
        desires = [Toke_Crotchets]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Crotchets)
            return

        # beat -> Quavers
        desires = [Toke_Quavers]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Quavers)
            return

        # beat -> Quarters
        desires = [Toke_Quarters]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Quarters)
            return

        # beat -> Eighths
        desires = [Toke_Eighths]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Eighths)
            return

        # beat -> Slash
        desires = [Toke_Slash]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Slash)
            return

        # Error State
        self.fail()

    # ottavas
    def ottavas(self):
        # ottavas -> OttavaA
        desires = [Toke_OttavaA]
        if self.toker.sees(desires):
            self.toker.burn(Toke_OttavaA)
            return

        # ottavas -> OttavaB
        desires = [Toke_OttavaB]
        if self.toker.sees(desires):
            self.toker.burn(Toke_OttavaB)
            return

        # ottavas -> QuindicesimaA
        desires = [Toke_QuindicesimaA]
        if self.toker.sees(desires):
            self.toker.burn(Toke_QuindicesimaA)
            return

        # ottavas -> QuindicesimaB
        desires = [Toke_QuindicesimaB]
        if self.toker.sees(desires):
            self.toker.burn(Toke_QuindicesimaB)
            return

        # ottavas -> Loco
        desires = [Toke_Loco]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Loco)
            return

        # Error State
        self.fail()

    # nouny_literal
    def nouny_literal(self):
        # nouny_literal -> Int
        desires = [Toke_Int]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Int)
            return

        # nouny_literal -> Float
        desires = [Toke_Float]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Float)
            return

        # nouny_literal -> String
        desires = [Toke_String]
        if self.toker.sees(desires):
            self.toker.burn(Toke_String)
            return

        # nouny_literal -> Tuplet
        desires = [Toke_Tuplet]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Tuplet)
            return

        # nouny_literal -> Caesura
        desires = [Toke_Caesura]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Caesura)
            return

        # nouny_literal -> Slur
        desires = [Toke_Slur]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Slur)
            return

        # Error State
        self.fail()

    # noaty
    def noaty(self):
        # noaty -> Choard
        desires = [Toke_Choard]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Choard)
            return

        # noaty -> Symbol
        desires = [Toke_Symbol]
        if self.toker.sees(desires):
            self.toker.burn(Toke_Symbol)
            return

        # noaty -> CurNoat
        desires = [Toke_CurNoat]
        if self.toker.sees(desires):
            self.toker.burn(Toke_CurNoat)
            return

        # noaty -> noat
        desires = [Toke_VectorNoat]
        if self.toker.sees(desires):
            self.noat()
            return

        # noaty -> listy
        desires = [Toke_ListS]
        if self.toker.sees(desires):
            self.listy()
            return

        # noaty -> conditional
        desires = [Toke_CondS]
        if self.toker.sees(desires):
            self.conditional()
            return

        # Error State
        self.fail()
