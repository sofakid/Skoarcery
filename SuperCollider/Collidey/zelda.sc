(

var bass = """
<! 9/8 120 => ). !>

}}} }}}

<? sorry i don't have Key yet, it's Db ?>
-1 => @transpose

@smooth => @instrument
pp

|| <_a,_c,d> )). }. | d )). }. | <_a,_c,d> )). }. | _a )). }. D.C. ||

""".pskoar;

var melody = """
<! 9/8 120 => ). !>

}}} }}}

<? sorry i don't have Key yet, it's Db ?>
-1 => @transpose

mp

<? intro ?>
| d )). }. | e )). }. | d )). }. | _a )). ) ]] |

%S%
| _a)  d] f)  e] d)  c] | d)  _b] _g)  c] _a] d] _b] | c)  _a] _g)  d] _b)  c] | d)  e]  f] d] e] _a)  _a] |

D.S.

""".pskoar;

Ppar([bass, melody]).play;


)