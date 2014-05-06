from collections import OrderedDict, UserDict
from Skoarcery.pymp import toke_inspector, skoarmantics
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

    def __init__(self, name, toke, parent, i=0):
        self.parent = parent
        self.i = i  # position in parent
        self.n = 0  # number of children
        self.name = name
        self.toke = toke
        self.children = []

        if isinstance(toke, SkoarToke):
            self.inspectable = toke.__class__.inspectable
        else:
            self.inspectable = False

    def add_toke(self, name, toke):
        self.children.append(SkoarNoad(name, toke, self, self.n))
        self.n += 1

    def add_noad(self, noad):

        self.children.append(noad)
        noad.i = self.n
        self.n += 1



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


class Skoar:

    def __init__(self, skoarse):
        from ..pymp import rdpp
        self.skoarse = skoarse
        self.tree = None
        self.toker = Toker(self.skoarse)
        self.parser = rdpp.SkoarParser(self)
        self.markers = []

        self.control_stack = []

        self.cur_noat = None
        self.noat_direction = 1

    def parse(self):
        self.tree = self.parser.skoar(None)
        self.toker.eof()

    def tinsel_and_balls(self):

        def inspect(x):

            # tokens*
            if x.toke:
                try:
                    # run the function x.name, pass the token
                    toke_inspector.__dict__[x.name](x.toke)

                    print("decorated toke " + x.name + ":")
                    for k, v in x.toke.__dict__.items():
                        print("    " + k + ": " + str(v))
                    print("")
                except KeyError:
                    pass

            # nonterminals*
            else:
                try:
                    # run the function, pass the noad (not the nonterminal)
                    y = skoarmantics.__dict__[x.name]
                    y(self, x)

                    print("decorated noad " + x.name + ":")
                    for k, v in x.__dict__.items():
                        if k not in ["children", "parent", "toke", "inspectable"]:
                            print("    " + k + ": " + repr(v))
                    print("")
                except KeyError:
                    pass

        self.tree.visit(inspect)

    def noat_go(self, noat):

        # find direction and if we're moving
        move = True
        if noat.up:
            self.noat_direction = 1

        elif noat.down:
            self.noat_direction = -1

        elif noat == self.cur_noat:
            move = False

        # move if we do
        if move and self.noat_direction > 0:
            print("up to ", end="")

        elif move and self.noat_direction < 0:
            print("down to ", end="")

        print(noat.letter, end="")

        for i in range(0, noat.sharps):
            print("#", end="")

        for i in range(0, noat.flats):
            print("b", end="")

    # save these in a list for jumping around in
    def add_marker(self, marker_noad):
        self.markers.append(marker_noad)



def parse(src):

    skoar = Skoar(src)
    skoar.parse()

    return skoar


class SymboalTable(UserDict):
    pass
