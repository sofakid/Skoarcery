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

.mel  | _a)   d] f#)   e] d)     c#]  | d)  _b] _g)  c#] _a] d] _b]       |
.mel  | c#)   _a] _g)   d] _b)    c#] | d)   e]] ]] f#] d] e] _a)   ] |
.mel  | _a)  d]] e]] f#)  e] d)  c#]  | d)  _b] _g)  c#] _a] d] _b]       |
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
SynthDef(\blips, {arg out = 0, freq = 25, numharm = 10, att = 0.01, rel = 1, amp = 0.1, pan = 0.5;
	var snd, env;
	env = Env.perc(att, rel, amp).kr(doneAction: 2);
	snd = LeakDC.ar(Mix(Blip.ar([freq, freq*1.01], numharm, env)));
	Out.ar(out, Pan2.ar(snd, pan));
}).add;


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
}).store;

// kick -------
// http://www.soundonsound.com/sos/jan02/articles/synthsecrets0102.asp
// increase mod_freq and mod_index for interesting electronic percussion

SynthDef(\kick,
	{ arg out = 0, freq = 50, mod_freq = 5, mod_index = 5, sustain = 0.4, amp = 0.8, beater_noise_level = 0.025;
	var pitch_contour, drum_osc, drum_lpf, drum_env;
	var beater_source, beater_hpf, beater_lpf, lpf_cutoff_contour, beater_env;
	var kick_mix;

	// hardcoding, otherwise skoar will set to the length of the noat ) ] ]]] etc
    sustain = 0.4;
    freq = 50;

	pitch_contour = Line.kr(freq*2, freq, 0.02);
	drum_osc = PMOsc.ar( pitch_contour,
            mod_freq,
            mod_index/1.3,
            mul: 1,
            add: 0);
	drum_lpf = LPF.ar(in: drum_osc, freq: 1000, mul: 1, add: 0);
	drum_env = drum_lpf * EnvGen.ar(Env.perc(0.005, sustain), 1.0, doneAction: 2);
	beater_source = WhiteNoise.ar(beater_noise_level);
	beater_hpf = HPF.ar(in: beater_source, freq: 500, mul: 1, add: 0);
	lpf_cutoff_contour = Line.kr(6000, 500, 0.03);
	beater_lpf = LPF.ar(in: beater_hpf, freq: lpf_cutoff_contour, mul: 1, add: 0);
	beater_env = beater_lpf * EnvGen.ar(Env.perc, 1.0, doneAction: 2);
	kick_mix = Mix.new([drum_env, beater_env]) * 2 * amp;
	Out.ar(out, [kick_mix, kick_mix])
}).store;


// snare -------
// http://www.soundonsound.com/sos/Mar02/articles/synthsecrets0302.asp
SynthDef(\snare,
	{arg out = 0, sustain = 0.1, drum_mode_level = 0.25,
	snare_level = 40, snare_tightness = 1000,
	freq = 405, amp = 0.8;
	var drum_mode_sin_1, drum_mode_sin_2, drum_mode_pmosc, drum_mode_mix, drum_mode_env;
	var snare_noise, snare_brf_1, snare_brf_2, snare_brf_3, snare_brf_4, snare_reson;
	var snare_env;
	var snare_drum_mix;

    sustain = 0.1;
    freq = 405;

	drum_mode_env = EnvGen.ar(Env.perc(0.005, sustain), 1.0, doneAction: 2);
	drum_mode_sin_1 = SinOsc.ar(freq*0.53, 0, drum_mode_env * 0.5);
	drum_mode_sin_2 = SinOsc.ar(freq, 0, drum_mode_env * 0.5);
	drum_mode_pmosc = PMOsc.ar(	Saw.ar(freq*0.85),
					184,
					0.5/1.3,
					mul: drum_mode_env*5,
					add: 0);
	drum_mode_mix = Mix.new([drum_mode_sin_1, drum_mode_sin_2, drum_mode_pmosc]) * drum_mode_level;

	snare_noise = LFNoise0.ar(20000, 0.1);
	snare_env = EnvGen.ar(Env.perc(0.005, sustain), 1.0, doneAction: 2);
	snare_brf_1 = BRF.ar(in: snare_noise, freq: 8000, mul: 0.5, rq: 0.1);
	snare_brf_2 = BRF.ar(in: snare_brf_1, freq: 5000, mul: 0.5, rq: 0.1);
	snare_brf_3 = BRF.ar(in: snare_brf_2, freq: 3600, mul: 0.5, rq: 0.1);
	snare_brf_4 = BRF.ar(in: snare_brf_3, freq: 2000, mul: snare_env, rq: 0.0001);
	snare_reson = Resonz.ar(snare_brf_4, snare_tightness, mul: snare_level) ;
	snare_drum_mix = Mix.new([drum_mode_mix, snare_reson]) * 5 * amp;
	Out.ar(out, [snare_drum_mix, snare_drum_mix]);
}).store;

// hats -------
// http://www.soundonsound.com/sos/Jun02/articles/synthsecrets0602.asp
SynthDef(\hats,
	{arg out = 0, freq = 6000, sustain = 0.1, amp = 0.8;
	var root_cymbal, root_cymbal_square, root_cymbal_pmosc;
	var initial_bpf_contour, initial_bpf, initial_env;
	var body_hpf, body_env;
	var cymbal_mix;

    amp = amp * 0.5;
    sustain = 0.1;
    freq = 6000;

	root_cymbal_square = Pulse.ar(freq, 0.5, mul: 1);
	root_cymbal_pmosc = PMOsc.ar(root_cymbal_square,
					[freq*1.34, freq*2.405, freq*3.09, freq*1.309],
					[310/1.3, 26/0.5, 11/3.4, 0.72772],
					mul: 1,
					add: 0);
	root_cymbal = Mix.new(root_cymbal_pmosc);
	initial_bpf_contour = Line.kr(15000, 9000, 0.1);
	initial_env = EnvGen.ar(Env.perc(0.005, 0.1), 1.0);
	initial_bpf = BPF.ar(root_cymbal, initial_bpf_contour, mul:initial_env);
	body_env = EnvGen.ar(Env.perc(0.005, sustain, 1, -2), 1.0, doneAction: 2);
	body_hpf = HPF.ar(in: root_cymbal, freq: Line.kr(9000, 12000, sustain),mul: body_env, add: 0);
	cymbal_mix = Mix.new([initial_bpf, body_hpf]) * amp;
	Out.ar(out, [cymbal_mix, cymbal_mix])
}).store;

x = "

<? Zelda Theme - inspired by piano arrangement by Shinobu Amayake ?>

    130 => )

    .alice    @default => @instrument mp
    .bob      @default => @instrument mp
    .bass    @sawpulse => @instrument mp o~~~~
    .hats        @hats => @instrument pp
    .snare      @snare => @instrument mp
    .kick        @kick => @instrument mp

    {! four_bars_rest  !! }}}}}  !}
    {! eight_bars_rest !! }}}}}} !}

    {! bass_end<x>    !! !x ) ) ) ] ]     !}
    {! bass_climb     !! | _e ]] _a# ]] c# ]  e ]] a# ]] ~o c# ] e ) } | f ) o~ _f ]] ]] ] ) } | !}

    {! bassline_a !!
      <a#, g#, f#, c#, b, a#, c>.{: .) ]] ]] ] ) ) :}
      !bass_end<f>
    !}

    {! bassline_b !!
      <a#, g#, f#, f>.{: ) ]] ]] ] ) ) :}

      !bass_climb !bass_climb

      <b, a#, c     >.{: ) ]] ]] ] ) ) :} !bass_end<f>
    !}

    {! intro !!

      .hats  !four_bars_rest
      .snare !four_bars_rest
      .kick  !four_bars_rest

      .alice | _a# ))        o/. ]]  ]] ]] ] |     ]. _g#  ]] _a# )        o/.  ]]  ]] ]] ] |
      .bob   | _d  ))        o/. ]]  ]] ]] ] | _c  ].      ]]     )        o/.  ]]  ]] ]] ] |
      .bass  |  a# ) ]] ]] ] )       ]] ]] ] |  g# )              ]] ]] ]  )        ]] ]] ] |

      .alice |     ]. _g# ]] _a# )       o/. ]] ]] ]] ] |   ]    _f ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bob   | _c# ].     ]]     )       o/. ]] ]] ]] ] |   ] o~ _a ]] ]]  ] ]] ]]  ] ]] ]]  ]     ] |
      .bass  |  f# )             ]] ]] ] )      ]] ]] ] | f )              )        )      g ]   a ] |
    !}

    {! melody_a !! .bass !bassline_a

      .alice | _a# ) _f )__          o/. _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ] _d ].     ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice |  a# ))                                  o/ a# ]  ]  g# ]]  f# ]] |
      .bob   |  c# ]. _f# ]]  ]] _g# ]] _a# ]] c ]] c# ]. ]]    ]  c  ]] _a# ]] |

      .alice | g# ].  f# ]]  f ))                      )               |
      .bob   | c# ]. _g# ]]    ]] ]] _f# ]  _g# ]. ]]  ]] _f# ]] _g# ] |

      .alice |  d# ] ]]  f ]]  f# ))                  f ] d#  ] |
      .bob   | _f# ] ]] _f ]] _f# ] ]] _g# ]] _a# ) _g# ] _f# ] |

      .alice |  c# ] ]]  d# ]]  f ))                 d# ]  c# ] |
      .bob   | _f  ] ]] _d# ]] _f ] ]] _f# ]] _g# ) _f# ] _d# ] |

      .alice |  c ] ]]  d ]]  e ))                      g )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _g ] ]] _a ]] ]] _a# ] c ] |

      .alice |  f ]     _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~  _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}

    {! melody_b !! .bass !bassline_b

      .alice | _a# ) _f )             o/.  _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ]  _d ].      ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice | a# )). ~o c# ) | c  ) o~ a )) f  ) |  f# )).  a# ) | a )  f )) ) |
      .bob   | c# )).    e  ) | d# )    c )) _a ) | _b  )).  c# ) | c ) _a )) ) |

      .alice |  f# )). a# ) | a )  f )) d ) |  d# )).  f# ) |  f  )  c# )) _a# ) |
      .bob   | _b  )). c# ) | c ) _a ))   ) | _f# )). _b  ) | _a# ) _f  )) _c# ) |

      .alice |  c ] ]]  d ]]  e ))                        g  )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _f ]] _g ] ]] _a ]] _a# ] c ] |

      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}

    {! fill !!
      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |
      .snare |    ]       ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .hats  |    ]       ]      ] ]       ] ]       ]  ]    |
      .kick  |    )              }         )         }       |
      .bass !bass_end<f>
    !}

    {! drums !!
      .hats  {: ] ] ] ] ] ] ] ]] ]] :: $.i <= 11 :}
      .kick  {: ) } ) } :: $.i <= 11 :}
      .snare {: } ) } ) :: $.i <= 10 :} | ] ]] ]]  ] ]] ]]  ] ]] ]]  ]  ] |
    !}

    !intro !melody_a

    .kick !eight_bars_rest
    .hats !four_bars_rest }}} }}} }}} ] ] ] ] ] ] ] ]
    .snare !eight_bars_rest

    !fill !melody_b !drums !fill

".skoar;
)

x.play;
(
"
{! melody_b !!

      .alice | _a# ) _f )             o/.  _a# ]]  ]]   c ]]  d ]] d# ]] |
      .bob   | _d  )    ]] ]] _c ]  _d ].      ]]  ]] _d# ]] _f ]] _g ]] |

      .alice |  f  ))                             o/ ]   f ]  f# ]] g# ]] |
      .bob   | _g# ]. _a# ]] ]] c ]] d ]] d# ]] f )    _g# ] _a# ]] c  ]] |

      .alice | a# )). ~o c# ) | c  ) o~ a )) f  ) |  f# )).  a# ) | a )  f )) ) |
      .bob   | c# )).    e  ) | d# )    c )) _a ) | _b  )).  c# ) | c ) _a )) ) |

      .alice |  f# )). a# ) | a )  f )) d ) |  d# )).  f# ) |  f  )  c# )) _a# ) |
      .bob   | _b  )). c# ) | c ) _a ))   ) | _f# )). _b  ) | _a# ) _f  )) _c# ) |

      .alice |  c ] ]]  d ]]  e ))                        g  )     |
      .bob   | _e ] ]] _d ]] _e ] ]] _f ]] _g ] ]] _a ]] _a# ] c ] |

      .alice |  f ]    _f ]] ]]  ] ]] ]]   ] ]] ]]   ]  ]    |
      .bob   | _a ] o~ _a ]] ]]  ] ]] ]]   ] ]] ]]   ]  ] ~o |

    !}.block
".skoar.play;
)
