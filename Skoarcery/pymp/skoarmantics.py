# ============
# Skoarmantics
# ============


# We went depth first so our children should be defined
from Skoarcery.pymp.lex import Toke_VectorNoat


def msg_chain_node(skoar, noad):
    pass


def beat(skoar, noad):
    x = noad.children[0].toke
    noad.children = []
    noad.beat = x


def meter_beat(skoar, noad):
    x = noad.children[0].toke
    noad.children = []
    noad.beat = x


def listy(skoar, noad):
    pass


def clef(skoar, noad):
    pass


def meter_symbolic(skoar, noad):
    pass


def stmt(skoar, noad):
    pass


def musical_keyword_misc(skoar, noad):
    pass


def coda(skoar, noad):
    pass


def meter_ass(skoar, noad):
    pass


def assignment(skoar, noad):
    pass


def accidentally(skoar, noad):
    pass


def boolean(skoar, noad):
    pass


def ottavas(skoar, noad):
    pass


def skoaroid(skoar, noad):
    pass


def msg(skoar, noad):
    pass


def dal_goto(skoar, noad):
    pass


def cthulhu(skoar, noad):
    pass


def dynamic(skoar, noad):
    pass


def optional_carrots(skoar, noad):
    pass


def meter_sig_prime(skoar, noad):
    pass


def meter(skoar, noad):

    # trim start and end tokens
    noad.children = noad.children[1:-2]


def markers(skoar, noad):
    skoar.add_marker(noad)



def noaty(skoar, noad):
    pass


def noat_literal(skoar, noad):
    noat = noad.children[0].toke

    noad.noat = noat
    noad.children = []

    if isinstance(noat, Toke_VectorNoat):
        noad.performer = (lambda: skoar.noat_go(noat))


def noat_reference(skoar, noad):

    # TODO Symbol | CurNoat | listy
    pass



def pedally(skoar, noad):
    pass


