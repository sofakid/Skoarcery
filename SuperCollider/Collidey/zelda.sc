(

var intro_melody = """
<! 9/8 120 => ). !> -1 => @transpose mp

| d )). }. | e )). }. | d )). }. | _a )). ) ]] |

""".pskoar;

var intro_bass = """
<! 9/8 120 => ). !> -1 => @transpose pp

| <_a,_c#,d> )). }. | d )). }. | <_a,_c#,d> )). }. | _a )). }. |

""".pskoar;

var bass = """
<! 9/8 120 => ). !> -1 => @transpose ppp

|: <_a,_c#,d> )). }. | d )). }. | <_a,_c#,d> )). }. | _a )). }. :| :| :|

""".pskoar;

var melody_A = """
<! 9/8 120 => ). !> -1 => @transpose mp

| _a)   d] f#)   e] d)     c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)   _a] _g)   d] _b)    c#] | d)   e]] ]] f#] d] e] _a)   ] |
| _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).      _a)   ] |
| _a)  d]] e]] f#)  e] d)  c#] | e]] d].  _b] _g)  c#] _a] d] _b]  | c#)   _a] _g)   d] _b)    c#] | ]] d]] e]] ]] f#] d] e] _a) ] |
| _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).       a)   ] |
""".pskoar;

var melody_B = """
<! 9/8 120 => ). !> -1 => @transpose mp
|: g] f#] e] f#)  d] c#] d] e] | a] d] ] g] d] ] f#)    ] | g] a] b] b] g] e] a)  a]   | g] f#] d] e] f#] e]] f#]] e)  ] |
|  d] c#] d] e)   e] a] g] c#] | g] d] ] f#)  ] e] c#] e] | d] b] d] g] a] b] f# d] g] | e).    ] f#] e] a)           ] :|

""".pskoar;

//Ptpar([Pseq([intro_melody, melody_A, melody_B]), Pseq([intro_bass, bass, bass])]).play;
Ppar([Pseq([intro_melody, melody_A, melody_B]), Pseq([intro_bass, bass, bass])]).play;


"=================== here =================== ".postln;

//Ppar([bass, melody]).render("/Users/lucas/Desktop/zww.aif");


)

(
"""
<! 9/8 120 => ). !> -1 => @transpose mp

| _a)   d] f#)   e] d)     c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)   _a] _g)   d] _b)    c#] | d)   e]] ]] f#] d] e] _a)   ] |
| _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).      _a)   ] |
| _a)  d]] e]] f#)  e] d)  c#] | e]] d].  _b] _g)  c#] _a] d] _b]  | c#)   _a] _g)   d] _b)    c#] | ]] d]] e]] ]] f#] d] e] _a) ] |
| _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).       a)   ] |

mf

|: g] f#] e] f#)  d] c#] d] e] | a] d] ] g] d] ] f#)    ] | g] a] b] b] g] e] a)  a]   | g] f#] d] e] f#] e]] f#]] e)  ] |
|  d] c#] d] e)   e] a] g] c#] | g] d] ] f#)  ] e] c#] e] | d] b] d] g] a] b] f# d] g] | e).    ] f#] e] a)           ] :|

D.C.

""".pskoar.play;
)


windwaker.play
"killall scsynth".unixCmd
