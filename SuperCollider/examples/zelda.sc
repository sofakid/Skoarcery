(

//MIDIClient.init;
//MIDIClient.destinations;

b = "
<? Zelda Wind Waker Theme - inspired by piano arrangement by Shinobu Amayake ?>

<? Let's play the skoar on a midi device ?>
<?!MIDIOut.new<0> => @midiout
@midi => @type?>


<? key is Db, so i flat everything and sharp c & f ?>
-1 => @transpose

9/8 120 => ).

mf

.bass ,segno`
.bass | <_a,_c#,d> )))__ o/ | d )))__ o/ | <_a,_c#,d> )))__ o/ | _a )))__ o/ | D.S.

.mel  | ottava alta }}} o/  | }}} o/     | }}} o/              |    }}} o/   |

.mel  | _a)   d] f#)   e] d)     c#] | d)  _b] _g)  c#] _a] d] _b]       |
.mel  | c#)   _a] _g)   d] _b)    c#] | d)   e]] ]] f#] d] e] _a)   ] |
.mel  | _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       |
.mel  | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).      _a)   ] |

.mel  | _a)  d]] e]] f#)  e] d)  c#] | e]] d].  _b] _g)  c#] _a] d] _b]  | c#)   _a] _g)   d] _b)    c#] | ]] d]] e]] ]] f#] d] e] _a) ] |
.mel  | _a)  d]] e]] f#)  e] d)  c#] | d)  _b] _g)  c#] _a] d] _b]       | c#)  d] e)  f#]] ]] g] e] c#] | d)   oo/ e]] d).       a)   ] |

.mel  |: g] f#] e] f#)  d] c#] d] e] | a] d] ] g] d] ] f#)    ] | g] a] b] b] g] e] a)  a]   | g] f#] d] e] f#] e]] f#]] e)  ] |
.mel  |  d] c#] d] e)   e] a] g] c#] | g] d] ] f#)  ] e] c#] e] | d] b] d] g] a] b] f# d] g] | e).    ] f#] e] a)           ] :|

.mel  | <_a,_c#,d> )). }. | d )). }. | <_a,_c#,d> )). }. | <_a,_f#> )). _a ) ] | ^^(;,;)^^

".skoar;






)
(
b.play;
)


(x = "

150 => )
fff

{! melody_a !!
| _a# ) _f )__ _f]. _a# ]] ]] c ]] d ]] d# ]] f )) o/ ] <?triplets f ] f# ] g#]?> f ] f#]] g#]] a# )) |
| o/ a#] ]  g#]] f#]] g#]. f#]] f)) ) d# ] ]] f]] f#)) f] d#] c#] ]] d#]] f)) d#] c#] c] ]] d]] e)) g) f] _f ]] ]] ] ]] ]] ] ]] ]] ] ] | !}

<? want just one octave up ?>
.mel1 12 => @transpose @smooth => @instrument !melody_a D.C.
.mel2 <0,-3,5> => @detune !melody_a D.C.

.kick @kick => @instrument ) D.C.

.hats @hats => @instrument
.hats | ] ] ] ] ] ] ] ]] ]] | D.C.

.snare @snare => @instrument
.snare | } ) } ) | D.C.

{! dorf !!
a#
g#
f#
c#
b
a#
c
f
!}

{! dum<x> !! !x ] ] ] ] ] ] ] ] !! !}

{! bassline !! @bass => @instrument o~
!dum<a#>
!dum<g#>
!dum<f#>
!dum<c#>
!dum<b#>
!dum<a#>
!dum<c>
!dum<f>
~o D.C. !}

.args @bass => @instrument ppp
.arps | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] | D.C.

.bass !bassline


".skoar;
)

x.play;

(x =".arps | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] |

{! bassfun !! <a#, g#, f#, c#, b, a#, c, f> !}
.
".skoar;
)
(
x.play;
)


Server.killAll;

"killall scsynth".unixCmd