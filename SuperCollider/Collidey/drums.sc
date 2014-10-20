
// Skoar drums, almost completely from DrumSynths.scd, modified to work better skoarwise.

// SOS Drums by Renick Bell, renick_at_gmail.com
// recipes from Gordon Reid in his Sound on Sound articles
//
// kick -------
// http://www.soundonsound.com/sos/jan02/articles/synthsecrets0102.asp
// increase mod_freq and mod_index for interesting electronic percussion
(
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

// SOStom -------
// http://www.soundonsound.com/sos/Mar02/articles/synthsecrets0302.asp
SynthDef(\tom,
	{arg out = 0, sustain = 0.4, drum_mode_level = 0.25,
	freq = 90, drum_timbre = 1.0, amp = 0.8;
	var drum_mode_sin_1, drum_mode_sin_2, drum_mode_pmosc, drum_mode_mix, drum_mode_env;
	var stick_noise, stick_env;
	var drum_reson, tom_mix;

    sustain = 0.4;
    freq = 90;

	drum_mode_env = EnvGen.ar(Env.perc(0.005, sustain), 1.0, doneAction: 2);
	drum_mode_sin_1 = SinOsc.ar(freq*0.8, 0, drum_mode_env * 0.5);
	drum_mode_sin_2 = SinOsc.ar(freq, 0, drum_mode_env * 0.5);
	drum_mode_pmosc = PMOsc.ar(	Saw.ar(freq*0.9),
								freq*0.85,
								drum_timbre/1.3,
								mul: drum_mode_env*5,
								add: 0);
	drum_mode_mix = Mix.new([drum_mode_sin_1, drum_mode_sin_2, drum_mode_pmosc]) * drum_mode_level;
	stick_noise = Crackle.ar(2.01, 1);
	stick_env = EnvGen.ar(Env.perc(0.005, 0.01), 1.0) * 3;
	tom_mix = Mix.new([drum_mode_mix, stick_env]) * 2 * amp;
	Out.ar(out, [tom_mix, tom_mix]);
}).store;

)

(
x = """

4/4 240 => )

.s  @snare => @instrument
.k  @kick  => @instrument
.h  @hats  => @instrument ppp

.h  | ] ] ] ] ] ] ] ] :|
.s  | }   )   }   )   :|
.k  | ))      ))      :|

""".skoar;

)

x.pskoar.play;

(
try {
    SkoarJumpException(0).throw;
} { | e |
    e.postProtectedBacktrace;
}
)
(
x = """

4/4 240 => )

.s  @snare => @instrument mf
.k  @kick  => @instrument
.h  @hats  => @instrument ppp

.h  | ] ] ] ] ] ] ] ] |
.s  | }   )   }   )   |
.k  | ))      ))      |

220 => )

.h  | ] ] ] ] ] ] ] ] |
.s  | }   )   }   )   |
.k  | ))      ))      |

190 => )

.h  |  ] ] ] ] ] ] ] ] |
.s  |: }   )   }   )   |
.k  |  ))      ))      |

160 => )

.h  |: ] ] ] ] ] ] ] ] |
.s  |  }   )   ] ] ] ] |
.k  |  ))      )   )  :|

120 => )

.h  |  ] ] ] ] ] ] ] ] |
.s  |  }   )   }   )   |
.k  |: )   ] ]  ) )    |

.h  | ] ] ] ] ] ] ] ]  |
.s  | }   ] ] }   ] ] :|
.k  | ))      ))       |

.h  | ] ] ] ] ] ] ] ] |
.s  | }   )   }   )   |
.k  | ))      ))      |

.h mf  | ]] ]] ]] ]]  ] ]  ]] ]] ]  ]] ]] ] :|
.s     | }            )    }        )        |
.k     | ))                ))                |

.h ppp | ] ] ] ] ] ] ] ]   :|
.s ff  | )   )   ] ] )  mf :|
.k     | ))      ))        :|

""".skoar;

)
x.pskoar.play;
(
x = """
<? Special thanks to The Breakbeat Bible for the dope dubstepz.
   The synths are the SOS drums from the SuperCollider examples folder. ?>

4/4 90 => )

!! hatz !!
 | ]] oo/ ]]] ]]] ]]   ]] ]] oo/ ]]  ]] ]] ]] ]]   ]] ]] ]] ]] |
 | ]] oo/ ]] ]]] ]]]  ]] ]] oo/ ]]  ]] ]] ]] ]]   ]] ]] ]] ]] |
 | fine |
!!

!! foo !!
 | )            o/.      ]]  oo/ ]] ]] oo/ }           |
 | ]]. ]].  ]]  o/    ]] ]]  }             o/    ]     |
 | ].       ]]  oo/  ].      o/    ]       }           |
 | ]]. ]].  ]]  }            ]]. ]].  ]]   }           |
 | )            }            ]     ]       }           |
 | ]     ]      }            o/         ]       oo/ ]. |
!!

!! derp !! !zorp
 | }     )            }     )                 |
 | }     )            }     !zorp ].       ]] |
 | }     )            }     !zorp ]]. ]].  ]] |
 | }     ]]. ]].  ]]  }     ].       ]]       |
 | fine |
!!

!! zorp !!
 | ~o Am    |
 | F o~     |
 | F        |
 | G        |
!!

.d @default => @instrument
.s  @snare => @instrument
.k  @kick  => @instrument
.h  @hats  => @instrument ppp

.h  | }}}     |
.s  | } ) } ) |
.k  | ) } ) } |
.d  | Am ) |

,segno`

.h  |: ]] ]] ]] ]]  ]] ]] ]] ]]  ]] ]] ]] ]]   ]] ]] ]] ]] :| !hatz :|
.s  | !derp |
.k  | !foo  |
.d  | !derp |

D.S. al fine

""".skoar;
)
x.play;

s.score.dump
y = x.asStream;
y.next


"killall scsynth".unixCmd;

x;


(
var hats = """
.hats  @hats  => @instrument ppp
.hats  || ] ] ] ] ] ] ] ] || D.C.
""".pskoar;

var kick = """
.kick  @kick  => @instrument fff
.kick  || ))      ))      || D.C.
""".pskoar;

var snare = """
@snare => @instrument mp
|| }   )   }   )   || D.C.
""".pskoar;

p = Ppar([hats,kick]);
q = Ppar([p,snare]);
q.play;

)

(
x = """
kick:  @kick  => @instrument 32 => @freq
kick:  || ))      ))      || D.C.
""".skoar.pvoice(\kick);

y = x.asStream;
)
x.play;
y.next;

(
var hats = """
|| a ] ] ] ] ] ] ] ] || D.C.
""".pskoar.play;
//
// var kick = """
// kick:  @kick  => @instrument fff
// kick:  || ))      ))      || D.C.
// """.pskoar;
//
// var snare = """
// snare: @snare => @instrument mp
// snare: || }   )   }   )   || D.C.
// """.pskoar;

//p = Ppar([hats,kick]);
// q = Ppar([p,snare]);
// p.dump;

)

(
var a, b;
a = Pbind(\note, Pseq([7, 4, 0], 4), \dur, Pseq([1, 0.5, 1.5], inf));
b = Pbind(\note, Pseq([5, 10, 12], 4), \dur, 1);
Ppar([ a, b ]).play;
)