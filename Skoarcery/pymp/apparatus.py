from Skoarcery.pymp.lex import Toke_Whitespace, Toke_EOF

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


class TreeNoad:

    def __init__(self, name, data, parent):
        self.parent = parent
        self.name = name
        self.data = data
        self.children = []

    def addToke(self, name, toke):
        self.children.append(TreeNoad(name, toke, self))

    def addNoad(self, noad):
        self.children.append(noad)

    def drawTree(self, tab=1):
        s = ("{:>" + str(tab) + "}{}\n").format(" ", self.name)
        for x in self.children:
            if x:
                s += x.drawTree(tab + 1)

        return s

def parse(src):

    from ..pymp import rdpp

    toker = Toker(src)
    parser = rdpp.SkoarParser(toker)
    root = parser.skoar(None)
    toker.eof()

    print("Parse Tree")
    print(root.drawTree())

