# ============
# Skoarmantics
# ============

#
# We went depth first so our children should be defined
#


def msg_chain_node(skoar, noad):
    pass


def beat(skoar, noad):
    noad.absorb_toke()
    noad.beat = noad.toke
    noad.is_beat = True


def meter_beat(skoar, noad):
    noad.absorb_toke()
    noad.beat = noad.toke


def listy(skoar, noad):
    from Skoarcery.SkoarPyon.lex import Toke_ListSep

    X = []

    for x in noad.children[1:-1]:
        if x.toke and isinstance(x.toke, Toke_ListSep):
            continue
        X.append(x)

    noad.replace_children(X)


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
    noad.replace_children(noad.children[1:-1])


def marker(skoar, noad):
    from Skoarcery.SkoarPyon.lex import Toke_Bars

    noad.absorb_toke()
    skoar.add_marker(noad)

    toke = noad.toke
    if isinstance(toke, Toke_Bars):
        if toke.pre_repeat > 0:
            noad.performer = (lambda x: x.jmp_colon(noad))


def noaty(skoar, noad):
    pass


def noat_literal(skoar, noad):
    from Skoarcery.SkoarPyon.lex import Toke_NamedNoat

    noat = noad.absorb_toke()
    noad.noat = noat

    if isinstance(noat, Toke_NamedNoat):
        noad.performer = (lambda x: x.noat_go(noat))


def noat_reference(skoar, noad):

    # TODO Symbol | CurNoat | listy
    pass



def pedally(skoar, noad):
    pass


