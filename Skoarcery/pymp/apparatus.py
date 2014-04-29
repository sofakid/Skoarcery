from Skoarcery.pymp.lex import Toke_WS, Toke_EOF
from ..pymp import lex, rdpp


class Toker:

    def __init__(self, src):
        self.buf = src
        self.offs = 0

        # lookahead token.
        self.ready = None

    def see(self, toke_class):
        if self.ready:
            if isinstance(self.ready, toke_class):
                return self.ready
        else:
            self.ready = toke_class.match(self.buf, self.offs)
            return self.ready

        return None

    def sees(self, tokes):

        for toke_class in tokes:
            X = self.see(toke_class)
            if X:
                return X

        return None

    def burn(self, toke_class):

        toke = self.ready

        if not toke:
            toke = self.see(toke_class)
        
        if toke and isinstance(toke, toke_class):
            #print("Burning " + self.ready.buf + " offs:" + str(self.offs))
            self.ready = None
            self.offs += toke.burn()
            self.offs += Toke_WS.burn(self.buf, self.offs)
            #print("Burnt offs:" + str(self.offs))
            return

        raise Exception("Tried to burn " + toke_class.__name__ + "but what we have is " + toke.__class__.__name__)


def parse(src):
    toker = Toker(src)
    parser = rdpp.SkoarParser(toker)
    parser.skoar()
