import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.pymp import apparatus


class Test_Apparatus(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()

    def test_apparatus(self):

        apparatus.parse("| c ) d ) ]] ]] ]] g ] |").decorate(loud=True)

        apparatus.parse("| mp c ) d ) %S% ]] | [1.] fff ]] fp ]] g p D.S. ] | [2.] <! 4/4 \sna !> <c,e,g> )) )) ) :|").decorate(loud=True)

    def test_multiline(self):

        apparatus.parse("""

        <! 120 => ) !>

        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|

        ) <? yay ?> )

        """).decorate(loud=True)

    def test_notes(self):

        apparatus.parse("| a ) b ) c ) | d ) e ) f ) g ) |").decorate(loud=True)

        apparatus.parse("| a ) a ) b ) | ++ ) e ) f ) ) g |").decorate(loud=True)

        apparatus.parse("| a c e ) bb ) c# ) | d## ) e ) ~f ) g~ ) |").decorate(loud=True)

    def test_misc(self):

        apparatus.parse("|ppp a ) b ) mp c ) | d mf ) // ) f $ ) g $  )  |").decorate(loud=True)

        apparatus.parse(" <3,4,5> 2 -3 +5 3.2 0.4").decorate(loud=True)

    def test_f(self):
        apparatus.parse("f ff fff ffff f# fp #f |").decorate(loud=True)

    def test_meter(self):

        apparatus.parse("<!!>").decorate(loud=True)
        apparatus.parse("<! 130 => )) !>").decorate(loud=True)
        apparatus.parse("<! 90 => forte 100 => fforte 127 => fff 4=>ppp!>").decorate(loud=True)
        apparatus.parse("<! !>").decorate(loud=True)
        apparatus.parse("<!!>").decorate(loud=True)

    def test_time_signatures(self):
        apparatus.parse("<! 4/4 9/8 2/2 !>").decorate(loud=True)
# someday        apparatus.parse("<! 3+4+5/4 !>").decorate(loud=True)

