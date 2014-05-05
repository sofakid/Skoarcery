import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.pymp import apparatus


class ExamineParseTree(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()


    def test_fun(self):

        apparatus.parse("""
        | c ) d ) ]] ]] ]] g ] |

        | mp c ) d ) %S% ]] | [1.] fff ]] fp ]] g p D.S. ] | [2.] <! 4/4 \sna !> <c,e,g> )) )) ) :|

        <! 120 => ) !>

        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|
        | <a,c,e> ). ). ]]] ]]] ]]] |: f# ) ) ooo/ ]]] ooo/ ]] ]] :|

        | a ) b ) c ) | d ) e ) f ) g ) |

        """)

