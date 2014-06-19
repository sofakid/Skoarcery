("""

<? 4/4  60 => ) ?>

|| d ) c ] ] |: b ]]] d ]]]
f ]]]. a ]]]]. :| } | g ]] b ]] g ]] d ]]  | a ]] ]] o/ e ). ]]. }} :||



""".pskoar.play;
)


("""

<? 4/4  100 => ) ?>

| d ) c ] ] |:  b ]]] d ]]] f ]]]. a ]]]]. :| } | g ]] b ]] g ]] d ]]  | a ]] ]] o/ e ). ]]. :|


| forte d# ]] ]] c ]] ]]. ]]] | ]] ]] ooo/ b# ) ooo/ ]. d ) |

fine

| mp c ) d ) %S% ]] | [1.] fff ]] fp ]] g p D.S. ] | [2.] <c,e,g> )) )) ) |

D.C. al fine





""".pskoar.play;
)

String

(

var guitar = """
@smooth => @instrument
|: a ]]] f~ ]]] d ]]] :| :| :| :| :| :| :||: a# ]]] f~ ]]] d ]]] :| :| :| :| :| :|  a# ]]] d ]]] f ]]] :| """.pskoar;

var smooth = """
@smooth => @instrument
|: Am ) ) | D ) :|""".pskoar;

Ppar([guitar,smooth]).play;
)

