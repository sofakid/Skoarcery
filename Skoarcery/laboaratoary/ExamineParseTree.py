import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY
from Skoarcery.pymp import apparatus


class ExamineParseTree(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()


    def test_big(self):

        src = """
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

        | a ) b ) c ) | d ) e ) f ) g ) %S%_food |

        """

        print("Skoarse Code")
        print("------------")
        print(src)

        skoar = apparatus.parse(src)

        print("\nParse Tree")
        print("----------")


        print(skoar.tree.draw_tree())
        skoar.tinsel_and_balls()

        print(skoar.tree.draw_tree())


    def test_small(self):

        src = """
        <! 4/4 120 => ) !>
        | mp c ) d ) %S% ]] piano |: f# ) ) ooo/ ]]] ooo/ ]] ]]  fine          :|
        | [1.] fff ]] fp ]] g ]   | [2.] <! 3/4 !> <c,e,g> )) )) ) D.S. al fine |
        """

        print("Skoarse Code")
        print("------------")
        print(src)

        skoar = apparatus.parse(src)

        print("\nParse Tree")
        print("----------")


        print(skoar.tree.draw_tree())

        skoar.tinsel_and_balls()

        print(skoar.tree.draw_tree())



