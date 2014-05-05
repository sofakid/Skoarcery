from collections import OrderedDict, UserDict
from Skoarcery.pymp import toke_inspector
from Skoarcery.pymp.lex import Toke_Whitespace, Toke_EOF, SkoarToke


class Toker:

    def __init__(I, code):
        I.skoarse = code
        I.am_here = 0
        I.saw = None

    def see(I, want):
        if I.saw:
            if isinstance(I.saw, want):
                return I.saw
        else:
            I.am_here += Toke_Whitespace.burn(I.skoarse, I.am_here)
            I.saw = want.match(I.skoarse, I.am_here)
            return I.saw

        return None

    def sees(I, wants):

        for want in wants:
            X = I.see(want)
            if X:
                return X

        return None

    def burn(I, want):

        toke = I.saw

        if toke is None:
            toke = I.see(want)

        if isinstance(toke, want):
            I.saw = None
            I.am_here += toke.burn()
            I.am_here += Toke_Whitespace.burn(I.skoarse, I.am_here)
            return toke

        raise Exception("I tried to burn " + want.__name__ + ", but what I saw is " + toke.__class__.__name__)

    def eof(I):
        try:
            Toke_EOF.burn(I.skoarse, I.am_here)
        except:
            I.dump()
            raise

    def dump(I):
        print("\nToker Dump")
        print("here   : " + str(I.am_here))
        print("saw    : " + str(I.saw))
        print("skoarse: " + I.skoarse[0:I.am_here] + "_$_" + I.skoarse[I.am_here:-1])


class SkoarNoad:

    def __init__(self, name, data, parent):
        self.parent = parent
        self.name = name
        self.data = data
        self.children = []

        if isinstance(data, SkoarToke):
            self.inspectable = data.__class__.inspectable
        else:
            self.inspectable = False


    def add_toke(self, name, toke):
        self.children.append(SkoarNoad(name, toke, self))

    def add_noad(self, noad):
        self.children.append(noad)

    def draw_tree(self, tab=1):
        s = ("{:>" + str(tab) + "}{}\n").format(" ", self.name)
        for x in self.children:
            if x:
                s += x.draw_tree(tab + 1)

        return s

    def visit(self, f):

        for x in self.children:
            if x:
                x.visit(f)

        f(self)

    def nextBeat(self):

        duration = 0


class SkoarMarkers(OrderedDict):
    pass


class Skoar:

    def __init__(self, skoarse):
        from ..pymp import rdpp
        self.skoarse = skoarse
        self.tree = None
        self.toker = Toker(self.skoarse)
        self.parser = rdpp.SkoarParser(self)
        self.markers = SkoarMarkers()

        self.control_stack = []

    def parse(self):
        self.tree = self.parser.skoar(None)
        self.toker.eof()

    def tinsel_and_balls(self):

        def inspect(x):
            try:
                toke_inspector.__dict__[x.name](x.data)

                print("decorated " + x.name + ":")
                for k, v in x.data.__dict__.items():
                    print("    " + k + ": " + str(v))
                print("")

            except KeyError:
                # ignore
                pass

        self.tree.visit(inspect)


def parse(src):

    skoar = Skoar(src)
    skoar.parse()

    return skoar


class SymboalTable(UserDict):
    pass


class Skoarmantics:
    pass

