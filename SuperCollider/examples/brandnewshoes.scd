(x = "
80 => )

<G, @minorPentatonic> => @key
5 => @transpose

.melody @bass => @instrument pp o~
.bass @bass => @instrument o~~ mp
.wah @sillyvoice => @instrument o~~~ mf

.tom @tom => @instrument ~o pp
.kik @kik => @instrument ~~o pp
.snare @kraftySnr => @instrument ff

0

.wah           {: }}} ))) :: 2 times :} {: ))) :: 20 times :}
.bass      oo/ {: ] ] :: 20 times :}
.tom           {: ] ] :: 20 times :} {: {? 4 times ?? o/ ]] oo/ ?? ] ] ?} :: 80 times :}
.snare  }}}}   {: {? 5 times ?? o/ ] ?? ]] fff oo/ ]] ]] ff ?} :: 60 times :}

.kik }}}} {: ] ] :: 7 times :}
.kik      {: {? 20 times ?? ] ]] ]] ?? oo/. ]]] ] oo/. ]]] ] oo/. ]]] ] oo/. ]]] ] ?} :: 70 times :}

.bass {: {? 10 times ?? <0,0,2,3>.choose ] ] ?? <<0,1,0,1,0,1,0,1>,<0,1,0,2,0,3,5,5>>.choose.{: ]]] :} ?} :: 76 times :}

.melody } ] } oo/ ]] ] }}}
.melody {: 2.rand <$, $+1, $+2.rand>.{: $ => @x {? 3 times ?? !x ]] ]] $+2 ]] ]] $+3 ]] ]] ?? !x ]] ]] ]] o/ ?} :} :: 3 times :}

.melody {: 3.rand <$, $+1, $+2.rand>.{: $ => @x {? 3 times ?? !x ] oo/ ]] ]] oo/ ]] oo/ ?? !x ] ] ] oo/ $+1 ]] ?} :} :: 6 times :}

.melody {: 3.rand <$, $+1, $+2.rand>.{: $ => @x {? 3 times ?? !x ]] ]] $+2 ]] ]] $+3 ]] ]] ?? !x ]] ]]  ]] ]]  ]] ]] ?} :} :: 3 times :}

.melody <0,1,2,3,4,5,6,7,8>.{: ]]] :} ooo/
.melody {: 3.rand + 2 <$, $+1, $+2>.{: $ => @x {? 3 times ?? !x ]] ]] $+2 ]] ]] $+3 ]] ]] ?? !x ]] ]]  ]] ]] ]] ]] ?} :} :: 3 times :}

.melody {: 3.rand + 6 <$, $+1.rand+1, $+3>.{: $ => @x {? 3 times ?? !x ]]] ]]] ]] o/ ]]] ]]] ]] ?? !x ]] ]] o/ ]] ]] ?} :} :: 3 times :}

.melody <0,1,2,3,4,5,6,7,8,9,10>.{: ]]] :} ooo/ )

".skoar.play;
)
