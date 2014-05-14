from Skoarcery import emissions
from Skoarcery.pymp.lex import Toke_Whitespace, Toke_EOF

py_imports = """from collections import OrderedDict, UserDict
from Skoarcery.pymp import toke_inspector, skoarmantics
from Skoarcery.pymp.lex import Toke_Whitespace, Toke_EOF, SkoarToke
"""


_ = _____ = _________ = _____________ = _________________ = _____________________ = None

# -------
# Symbols
# -------
Toker_ = "Toker"
burn_whitespace_ = "Toke_Whitespace.burn"
code_ = "code"
saw_ = "saw"
see_ = "see"
sees_ = "sees"
wants_ = "wants"
want_ = "want"
x_ = "x"
toke_ = "toke"

skoar_ = "skoar"
noad_ = "noad"
burn_ = "burn"
match_ = "match"
buf_ = "buf"
offs_ = "offs"
toke_class_ = "toke_class"
match_toke_ = "match_toke"
s_ = "s"
SkoarError_ = "SkoarError"
SubclassResponsibilityError_ = "SubclassResponsibilityError"


#
# configure schematics to output a language
# implemented by one of the tongues in emissions
def init(tongue):
    global _, _____, _________, _____________, _________________, _____________________
    assert tongue in emissions.tongues
    _ = _____ = _________ = _____________ = _________________ = _____________________ = tongue


def burn_whitespace(buf, offs):
    return burn_whitespace_ + "(" + _.v_attr(buf) + ", " + _.v_attr(offs) + ")"


def want_match(buf, offs):
    return want_ + "." + match_ + "(" + _.v_attr(buf) + ", " + _.v_attr(offs) + ")"


def toker():
    _.class_(Toker_)
    _____.attr_var("", buf_)
    _____.attr_var("", offs_)
    _____.attr_var("", saw_)

    _____.constructor(code_)
    _________.ass(_.v_attr(buf_), code_)
    _________.ass(_.v_attr(offs_), "0")
    _________.ass(_.v_attr(saw_), _.null)
    _____.end()

    _____.method(see_, want_)
    _________.if_(_.v_attr(saw_))
    _____________.if_(_.v_isinstance(_.v_attr(saw_), want_))
    _________________.return_(_.v_attr(saw_))
    _____________.else_()
    _________________.incr(_.v_attr(saw_), burn_whitespace(buf_, offs_))
    _________________.ass(_.v_attr(saw_), want_match(buf_, offs_))
    _____________.end()
    _____________.return_(_.v_attr(saw_))
    _________.end()
    _________.return_(_.null)
    _____.end()

    _____.method(sees_, wants_)
    _________.var(x_, _.null)
    _________.for_(want_, wants_)
    _____________.ass(x_, _.self_dot(see_, want_))
    _____________.if_(x_)
    _________________.return_(x_)
    _____________.end()
    _________.end()
    _________.return_(_.null)
    _____.end()

    _____.method(burn_, want_)
    _________.ass(toke_, _.v_attr(saw_))


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
        self.performer = lambda x: None
        self.j = 0
        self.parent = parent
        self.i = i  # position in parent
        self.n = 0  # number of children
        self.name = name
        self.toke = toke
        self.children = []
        self.is_beat = False
        self.next_jmp = None

        if isinstance(toke, SkoarToke):
            self.inspectable = toke.__class__.inspectable
        else:
            self.inspectable = False

    # ------------------
    # shrinking the tree
    # ------------------
    def replace_children(self, X):
        self.children = X
        self.recount_children()

    def recount_children(self):
        i = 0
        n = 0

        for x in self.children:
            if isinstance(x, SkoarNoad):
                x.i = i
            i += 1
            n += 1

        self.n = n

    # ----------------
    # growing the tree
    # ----------------
    def add_noad(self, noad):
        self.children.append(noad)
        noad.i = self.n
        self.n += 1

    def add_toke(self, name, toke):
        self.children.append(SkoarNoad(name, toke, self, self.n))
        self.n += 1

    def absorb_toke(self):
        if self.n == 1:

            x = self.children.pop()
            self.n = 0

            if isinstance(x, SkoarNoad) and x.toke:
                self.toke = x.toke
            else:
                self.toke = x

        return self.toke

    # ----------------
    # showing the tree
    # ----------------
    def draw_tree(self, tab=1):
        s = ("{:>" + str(tab) + "}{}\n").format(" ", self.name)
        for x in self.children:
            if x:
                s += x.draw_tree(tab + 1)

        return s

    # -----------------
    # Climbing the Tree
    # -----------------
    def depth_visit(self, f):

        for x in self.children:
            if x:
                x.depth_visit(f)

        f(self)

    def next_item(self):

        if self.next_jmp:
            return self.next_jmp

        if self.j == self.n:
            if self.parent is None:
                raise StopIteration

            return self.parent.next_item()

        n = self.children[self.j]
        self.j += 1
        return n

    def go_here_next(self, noad):
        self.next_jmp = noad
        noad.parent.j = noad.i

    # -------------------
    # performing the tree
    # -------------------
    def on_enter(self):
        self.j = 0

    def action(self):
        self.performer()


class SkoarIterator:

    def __init__(self, skoar):
        self.noad = skoar.tree

    def __iter__(self):
        return self

    def __next__(self):
        x = self.noad.next_item()

        if isinstance(x, SkoarNoad):
            self.noad = x
            x.on_enter()

        return x


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

    def decorate(self, loud=False):

        debug = (lambda x: print(x)) if loud else (lambda x: x)

        def inspect(x):

            # tokens*
            if x.toke:
                try:
                    # run the function x.name, pass the token
                    toke_inspector.__dict__[x.name](x.toke)

                    debug("decorated toke " + x.name + ":")
                    for k, v in x.toke.__dict__.items():
                        debug("    " + k + ": " + str(v))
                    debug("")
                except KeyError:
                    pass

            # nonterminals*
            else:
                try:
                    # run the function, pass the noad (not the nonterminal)
                    y = skoarmantics.__dict__[x.name]
                    y(self, x)

                    debug("decorated noad " + x.name + ":")
                    for k, v in x.__dict__.items():
                        if k not in ["children", "parent", "toke", "inspectable"]:
                            debug("    " + k + ": " + repr(v))
                    debug("")
                except KeyError:
                    pass

        self.tree.depth_visit(inspect)

    def get_pattern_gen(self):
        for x in SkoarIterator(self):
            if isinstance(x, SkoarNoad):

                # run performance handler
                x.performer(self)

                if x.is_beat:
                    yield [self.cur_noat.lexeme, x.beat.val]

    # ----
    # misc
    # ----
    def noat_go(self, noat):

        self.cur_noat = noat

        # # find direction and if we're moving
        # move = True
        # if noat.up:
        #     self.noat_direction = 1
        #
        # elif noat.down:
        #     self.noat_direction = -1
        #
        # elif noat == self.cur_noat:
        #     move = False
        #
        # # move if we do
        # if move and self.noat_direction > 0:
        #     print("up to ", end="")
        #
        # elif move and self.noat_direction < 0:
        #     print("down to ", end="")
        #
        # print(noat.letter, end="")
        #
        # for i in range(0, noat.sharps):
        #     print("#", end="")
        #
        # for i in range(0, noat.flats):
        #     print("b", end="")

    # save these in a list for jumping around in
    def add_marker(self, marker_noad):
        self.markers.append(marker_noad)

    def jmp_colon(self, noad):
        from Skoarcery.pymp.lex import Toke_Bars
        toke = noad.toke

        if toke.unspent:
            # spend it
            toke.unspent = False
            print("here")
            # find where we are in self.markers
            j = 0
            n = len(self.markers)
            for i in range(0, n):
                t = self.markers[i]
                if noad == t:
                    j = i
                    break
            else:
                raise AssertionError("couldn't find where we are in markers")

            # go backwards in list and find either a
            # post_repeat or the start
            for i in range(j - 1, 0, -1):
                x = self.markers[i]
                t = x.toke

                if isinstance(t, Toke_Bars) and t.post_repeat:
                    noad.go_here_next(x)
                    break
            else:
                noad.go_here_next(self.markers[0])


def parse(src):

    skoar = Skoar(src)
    skoar.parse()

    return skoar


class SymboalTable(UserDict):
    pass
