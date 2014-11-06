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
SynthDef.synthDefDir.postln
SynthDef.browse
(
SynthDef(\sawpulse, { |out, freq = 440, gate = 0.5, plfofreq = 6, mw = 0,
ffreq = 2000, rq = 0.3, freqlag = 0.05, amp = 1|
    var sig, plfo, fcurve;
    plfo = SinOsc.kr(plfofreq, mul:mw, add:1);
    freq = Lag.kr(freq, freqlag) * plfo;
    fcurve = EnvGen.kr(Env.adsr(0, 0.3, 0.1, 20), gate);
    fcurve = (fcurve - 1).madd(0.7, 1) * ffreq;
    sig = Mix.ar([Pulse.ar(freq, 0.9), Saw.ar(freq*1.007)]);
    sig = RLPF.ar(sig, fcurve, rq)
        * EnvGen.kr(Env.adsr(0.04, 0.2, 0.6, 0.1), gate, doneAction:2)
        * amp;
    Out.ar(out, sig ! 2)
}).add;

x = "

130 => )

.alice  @default => @instrument fff
.bob    @default => @instrument fff
.bass  @sawpulse => @instrument mp

{! intro !!

.bass o~~

.alice | _a# ))        o/. ]]  ]] ]] ] | ]. _g#  ]] _a# )        o/.  ]]  ]] ]] ] |
.bob   | _d  ))        o/. ]]  ]] ]] ] | _c ].   ]]     )        o/.  ]]  ]] ]] ] |
.bass  |  a# ) ]] ]] ] )       ]] ]] ] |  g# )          ]] ]] ]  )        ]] ]] ] |

.alice | ]. _g# ]] _a# )       o/. ]] ]] ]] ] | ]   _f ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
.bob   | _c# ]. ]]     )       o/. ]] ]] ]] ] | ] o~ a ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] | ~o
.bass  |  f# )         ]] ]] ] )      ]] ]] ] |  f )          )        )      g ]   a ] |

.bass ~~o

!}

{! melody_a !!

.alice | _a# ) _f )__          o/. _a# ]]  ]]   c ]]  d ]] d# ]] |
.bob   | _d  )    ]] ]] _c ] _d ].     ]]  ]] _d# ]] _f ]] _g ]] |

.alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
.bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

.alice |  a# ))                                  o/ a# ]  ]  g# ]]  f# ]] |
.bob   |  c# ]. _f# ]]  ]] _g# ]] _a# ]] c ]] c# ]. ]]    ]  c  ]] _a# ]] |

.alice | g# ].  f# ]]  f ))                      )               |
.bob   | c# ]. _g# ]]    ]] ]] _f# ]  _g# ]. ]]  ]] _f# ]] _g# ] |

.alice |  d# ] ]]  f ]]  f# ))                   f ] d#  ] |
.bob   | _f# ] ]] _f ]] _f# ] ]] _g# ]]  _a# ) _g# ] _f# ] |

.alice |  c# ] ]]  d# ]]  f ))                 d# ]  c# ] |
.bob   | _f  ] ]] _d# ]] _f ] ]] _f# ]] _g# ) _f# ] _d# ] |

.alice |  c ] ]]  d ]]  e ))                   g )     |
.bob   | _e ] ]] _d ]] _e ] ]] _g ] ]] _a ]] _a# ] c ] |

.alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] |
.bob   | _a ] o~  a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] | ~o

!}

.alice !intro !melody_a
.bob   !intro !melody_a

{! dum<x> !! !x ) ]] ]] ] ) ]] ]] ] !}
{! bum<x> !! !x ) ) ) ] ] !}
{! bassline !! o~~
!dum<a#>
!dum<g#>
!dum<f#>
!dum<c#>
!dum<b#>
!dum<a#>
!dum<c>
!bum<f>
~~o !}

.bass  !intro !bassline

".skoar;

)
x.play
(x = "

150 => )

{! intro !!

| _a# )) } o/ ]]  ]] ]] ]  |

!}

{! melody_a !!
| _a# ) _f )__ _f]. _a# ]] ]] c ]] d ]] d# ]] f )) o/ ] <?triplets f ] f# ] g#]?> f ] f#]] g#]] a# )) |
| o/ a#] ]  g#]] f#]] g#]. f#]] f)) ) d# ] ]] f]] f#)) f] d#] c#] ]] d#]] f)) d#] c#] c] ]] d]] e)) g) f] _f ]] ]] ] ]] ]] ] ]] ]] ] ] | !}

<? want just one octave up ?>
.mel1 mf 12 => @transpose !melody_a D.C.
.mel2 @sawpulse => @instrument o~ ,segno` !melody_a D.S.

.kick @kick => @instrument ) D.C.

.hats @hats => @instrument pp
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

{! dum<x> !! !x ] ] ] ] ] ] ] ] !}

{! bassline !! forte @sawpulse => @instrument o~~ ,segno`
!dum<a#>
!dum<g#>
!dum<f#>
!dum<c#>
!dum<b#>
!dum<a#>
!dum<c>
!dum<f>
D.S. !}

.arps forte @marimba => @instrument o~
.arps ,segno` | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] | D.S.

.bass !bassline


".skoar;
)

x.play;

(
"
.arps forte @marimba => @instrument o~
.arps ,segno` | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] | D.S.


".skoar.play;
)
Quarks.gui
Instr

(x =".arps | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] | _a# ]] d ]] f ]] a# ]] ]] f ]] d ]] _a# ]] |

{! bassfun !! <a#, g#, f#, c#, b, a#, c, f> !}
.
".skoar;
)
(
x.play;
)
Event
UnitTest.gui;

Server.killAll;

"killall scsynth".unixCmd

TestSkoarSanity.new.run

(
x = "
<a#, g#, f#, c#, b, a#, c, f>.next

{! dum<x> !! !x ] ] ] ] ] ] ] ] !}

{! bassline !! ff @sawpulse => @instrument o~
!dorf.next => @x !dum<!x>
~o D.C. !}



.bass !bassline
".skoar.play;
)

x.play

(

SynthDef(\marimba, {arg out=0, amp=1, t_trig=1, freq=100, rq=0.006;
	var env, signal;
	var rho, theta, b1, b2;
	b1 = 1.987 * 0.9889999999 * cos(0.09);
	b2 = 0.998057.neg;
	signal = SOS.ar(K2A.ar(t_trig), 0.3, 0.0, 0.0, b1, b2);
	signal = RHPF.ar(signal*0.8, freq, rq) + DelayC.ar(RHPF.ar(signal*0.9, freq*0.99999, rq*0.999), 0.02, 0.01223);
	signal = Decay2.ar(signal, 0.4, 0.3, signal);
	DetectSilence.ar(signal, 0.01, doneAction:2);
	Out.ar(out, signal*(amp*0.4)!2);
}).add;
)

(
Pdef(\test, Pbind(\instrument, \marimba, \midinote, Prand([[1,5], 2, [3, 5], 7, 9, 3],
    inf) + 48, \dur, 0.2)).play;

)

x = Pbind(\instrument, \marimba, \midinote, 48, \dur, 0.2)
x.patternpairs.dump

( 'instrument': 'marimba', 'dur': 1.0, \amp:1, 'midinote': 70.0 ).play
