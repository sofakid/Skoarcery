# tokens initialized.

import re
import abc



class SkoarToke:

    __metaclass__ = abc.ABCMeta

    regex = None

    def __init__(self, s):
        self.buf = s

    # how many characters to burn from the buffer
    def burn(self, *args):
        return len(self.buf)

    # override and return nil for no match, new toke otherwise
    @staticmethod
    @abc.abstractstaticmethod
    def match(buf, offs):
        raise NotImplementedError

    @staticmethod
    def match_toke(buf, offs, toke_class):

        o = buf.findRegexp(toke_class.regex, offs)

        if len(o) > 0:
            return toke_class.new(o[0][1])

        return None



class Toke_WS(SkoarToke):
    regex = r"^\s*"

    @staticmethod
    def burn(buf, offs):
        o = buf.findRegexp("^\s*", offs)

        if len(o.size) > 0:
            return len(o[0][1])

        return 0


class Toke_DynForte(SkoarToke):
    regex = r"mf|f+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DynForte)


class Toke_Label(SkoarToke):
    regex = r"[a-zA-Z][a-zA-Z0-9_]"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Label)


class Toke_ListS(SkoarToke):
    regex = r"<"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_ListS)


class Toke_MsgOp(SkoarToke):
    regex = r"\."

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MsgOp)


class Toke_Fine(SkoarToke):
    regex = r"fine"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Fine)


class Toke_MsgName(SkoarToke):
    regex = r"[a-zA-Z_][a-zA-Z0-9_]*"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MsgName)


class Toke_Quarters(SkoarToke):
    regex = r"\]+\.+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Quarters)


class Toke_DaCapo(SkoarToke):
    regex = r"D\.C\.|Da Capo"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DaCapo)


class Toke_Int(SkoarToke):
    regex = r"(+|-)?(0|[1-9)[0-9]+)"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Int)


class Toke_Loco(SkoarToke):
    regex = r"loco"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Loco)


class Toke_RWing(SkoarToke):
    regex = r"\)\^\^"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_RWing)


class Toke_NoatSharps(SkoarToke):
    regex = r"#"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_NoatSharps)


class Toke_AlSegno(SkoarToke):
    regex = r"al segno"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AlSegno)


class Toke_AccSharp(SkoarToke):
    regex = r"#|sharp"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AccSharp)


class Toke_Minus(SkoarToke):
    regex = r"-"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Minus)


class Toke_ListSep(SkoarToke):
    regex = r","

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_ListSep)


class Toke_PedalUp(SkoarToke):
    regex = r"*"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_PedalUp)


class Toke_Rep(SkoarToke):
    regex = r"\./\."

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Rep)


class Toke_Goto(SkoarToke):
    regex = r":"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Goto)


class Toke_DynPiano(SkoarToke):
    regex = r"mp|p+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DynPiano)


class Toke_DubRep(SkoarToke):
    regex = r"/\.\|\./"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DubRep)


class Toke_Slash(SkoarToke):
    regex = r"/"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Slash)


class Toke_ListE(SkoarToke):
    regex = r">"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_ListE)


class Toke_Symbol(SkoarToke):
    regex = r"\[a-zA-Z][a-zA-Z0-9]+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Symbol)


class Toke_Nosey(SkoarToke):
    regex = r","

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Nosey)


class Toke_CondGo(SkoarToke):
    regex = r"::"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_CondGo)


class Toke_Float(SkoarToke):
    regex = r"(+|-)?(0|[1-9)[0-9]+)\.[0-9]+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Float)


class Toke_Tuplet(SkoarToke):
    regex = r"/\d+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Tuplet)


class Toke_Coda(SkoarToke):
    regex = r"\(\+\)"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Coda)


class Toke_AlCoda(SkoarToke):
    regex = r"al(la)? coda"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AlCoda)


class Toke_BassClef(SkoarToke):
    regex = r"F:|bass:"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_BassClef)


class Toke_TrebleClef(SkoarToke):
    regex = r"G:|treble:"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_TrebleClef)


class Toke_CondSep(SkoarToke):
    regex = r";"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_CondSep)


class Toke_ZedPlus(SkoarToke):
    regex = r"[1-9][0-9]+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_ZedPlus)


class Toke_MeterS(SkoarToke):
    regex = r"<!"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MeterS)


class Toke_Segno(SkoarToke):
    regex = r"%S%|al segno"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Segno)


class Toke_AssOp(SkoarToke):
    regex = r"=>"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AssOp)


class Toke_Slur(SkoarToke):
    regex = r"++"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Slur)


class Toke_Bars(SkoarToke):
    regex = r"[\|]+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Bars)


class Toke_Crotchets(SkoarToke):
    regex = r"}+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Crotchets)


class Toke_EOF(SkoarToke):
    regex = r"unused"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_EOF)


class Toke_LWing(SkoarToke):
    regex = r"\^\^\("

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_LWing)


class Toke_Plus(SkoarToke):
    regex = r"\+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Plus)


class Toke_String(SkoarToke):
    regex = r"'[^']*[^\]'"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_String)


class Toke_WS(SkoarToke):
    regex = r"^\s*"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_WS)


class Toke_Portamento(SkoarToke):
    regex = r"~~~"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Portamento)


class Toke_DynSFZ(SkoarToke):
    regex = r"sfz"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DynSFZ)


class Toke_QuindicesimaA(SkoarToke):
    regex = r"15ma|alla quindicesima"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_QuindicesimaA)


class Toke_Eighths(SkoarToke):
    regex = r"\]+\.+"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Eighths)


class Toke_VectorNoat(SkoarToke):
    regex = r"[a-g]#*|b*"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_VectorNoat)


class Toke_Alto(SkoarToke):
    regex = r"C:|alto:"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Alto)


class Toke_<e>(SkoarToke):
    regex = r"unused"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_<e>)


class Toke_Carrots(SkoarToke):
    regex = r"\^+(^\^\^\()"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Carrots)


class Toke_OttavaB(SkoarToke):
    regex = r"8v?b|ottava (bassa|sotto)"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_OttavaB)


class Toke_PedalDown(SkoarToke):
    regex = r"Ped\."

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_PedalDown)


class Toke_Volta(SkoarToke):
    regex = r"\[\d+\.]"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Volta)


class Toke_AccNatural(SkoarToke):
    regex = r"nat"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AccNatural)


class Toke_CurNoat(SkoarToke):
    regex = r"\$"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_CurNoat)


class Toke_CondS(SkoarToke):
    regex = r"{"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_CondS)


class Toke_OttavaA(SkoarToke):
    regex = r"8v?a|ottava (alta|sopra)|all' ottava"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_OttavaA)


class Toke_Choard(SkoarToke):
    regex = r"[A-G]([Mm0-9]|sus|dim)*"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Choard)


class Toke_MsgNameWithArgs(SkoarToke):
    regex = r"[a-zA-Z_][a-zA-Z0-9_]*<"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MsgNameWithArgs)


class Toke_BooleanOp(SkoarToke):
    regex = r"== | != | <= | >= | in | nin | and | or | xor"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_BooleanOp)


class Toke_AlFine(SkoarToke):
    regex = r"al fine"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AlFine)


class Toke_CondE(SkoarToke):
    regex = r"}"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_CondE)


class Toke_DynFP(SkoarToke):
    regex = r"fp"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DynFP)


class Toke_Caesura(SkoarToke):
    regex = r"//"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Caesura)


class Toke_Colon(SkoarToke):
    regex = r":"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Colon)


class Toke_Quavers(SkoarToke):
    regex = r"o+/"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_Quavers)


class Toke_AccFlat(SkoarToke):
    regex = r"flat"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_AccFlat)


class Toke_QuindicesimaB(SkoarToke):
    regex = r"15mb|alla quindicesimb"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_QuindicesimaB)


class Toke_DalSegno(SkoarToke):
    regex = r"D\.S\.|Dal Segno"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_DalSegno)


class Toke_MeterSig(SkoarToke):
    regex = r"(\d+(\+\d)+/\d)"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MeterSig)


class Toke_NoatFlats(SkoarToke):
    regex = r"b"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_NoatFlats)


class Toke_MeterE(SkoarToke):
    regex = r"!>"

    @staticmethod
    def match(buf, offs):
        return SkoarToke.match_toke(buf, offs, Toke_MeterE)