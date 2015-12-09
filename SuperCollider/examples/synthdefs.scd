// these are supercollider instruments. Gathered from around.
// go see https://github.com/brunoruviaro/SynthDefs-for-Patterns for more.

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
}).add;

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
}).add;


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
}).add;

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
}).add;

SynthDef("PMCrotale", {
arg freq = 261, tone = 3, art = 1, amp = 0.8, pan = 0;
var env, out, mod;

env = Env.perc(0, art);
mod = 5 + (1/IRand(2, 6));

out = PMOsc.ar(freq, mod*freq,
	pmindex: EnvGen.kr(env, timeScale: art, levelScale: tone),
	mul: EnvGen.kr(env, timeScale: art, levelScale: 0.3));

out = Pan2.ar(out, pan);

out = out * EnvGen.kr(env, timeScale: 1.3*art,
	levelScale: Rand(0.1, 0.5), doneAction:2);
Out.ar(0, out*amp); //Out.ar(bus, out);

}).add;





SynthDef("kick3", {arg punch = 1, amp = 1;
    var freq = EnvGen.kr(Env([400, 66], [0.08], -3)),
	sig = Normalizer.ar(SinOsc.ar(freq, 0.5pi, punch).distort, 1) * amp
            * EnvGen.kr(Env([0, 1, 0.8, 0], [0.01, 0.1, 0.2]), doneAction: 2);
    Out.ar(0, sig ! 2);
}).add;



SynthDef("ringkick", {arg freq = 40, decay = 0.25, amp = 1;
	var snd;
	snd = Ringz.ar(
		in: LPF.ar(
			in: Impulse.ar(0),
			freq: 1000),
		freq: freq,
		decaytime: decay,
		mul: 7 * amp).tanh.sin*2;
	Out.ar(0, snd!2);
}).add;



SynthDef("beating", {arg freq = 440, amp = 0.1, art = 1;
	var env, snd1, snd2;
	env = EnvGen.ar(Env.perc(0.01, art), doneAction: 2);
	snd1 = SinOsc.ar(freq);
	snd2 = SinOsc.ar(Line.kr(freq+15, freq, art));
	Out.ar(0, Pan2.ar(Mix([snd1, snd2]), 0, amp*env))
}).add;

SynthDef("bass", { |freq = 440, gate = 1, amp = 0.5, slideTime = 0.17, ffreq = 1100, width = 0.15, detune = 1.005, preamp = 4|
    var sig, env;
	env = Env.adsr(0.01, 0.3, 0.4, 0.1);
    freq = Lag.kr(freq, slideTime);
    sig = Mix(VarSaw.ar([freq, freq * detune], 0, width, preamp)).distort;
	sig = sig * amp * EnvGen.kr(env, gate, doneAction: 2);
    sig = LPF.ar(sig, ffreq);
    Out.ar(0, sig ! 2)
}).add;

SynthDef("kik", { |basefreq = 50, ratio = 7, sweeptime = 0.05, preamp = 1, amp = 1, decay1 = 0.3, decay1L = 0.8, decay2 = 0.15, out|
    var fcurve = EnvGen.kr(Env([basefreq * ratio, basefreq], [sweeptime], \exp)),
        env = EnvGen.kr(Env([1, decay1L, 0], [decay1, decay2], -4), doneAction: 2),
        sig = SinOsc.ar(fcurve, 0.5pi, preamp).distort * env * amp;
    Out.ar(out, sig ! 2)
}).add;

SynthDef("kraftySnr", { |amp = 1, freq = 2000, rq = 3, decay = 0.3, pan, out|
	var sig = PinkNoise.ar(amp),
	env = EnvGen.kr(Env.perc(0.01, decay), doneAction: 2);
	sig = BPF.ar(sig, freq, rq, env);
	Out.ar(out, Pan2.ar(sig, pan))
}).add;

SynthDef("sillyvoice", { arg
	freq = 220,
	amp = 0.5,
	vibratoSpeed = 6,
	vibratoDepth = 4,
	vowel = 0,
	att = 0.01,
	rel = 0.1,
	lag = 1,
	gate = 1;

	var in, vibrato, env, va, ve, vi, vo, vu, snd;

	vibrato = SinOsc.kr(vibratoSpeed, mul: vibratoDepth);
	in = Saw.ar(Lag.kr(freq, lag) + vibrato);
	env = EnvGen.kr(Env.asr(att, 1, rel), gate, doneAction: 2);

	va = BBandPass.ar(
		in: in,
		freq: [ 600, 1040, 2250, 2450, 2750 ],
		bw: [ 0.1, 0.067307692307692, 0.048888888888889, 0.048979591836735, 0.047272727272727 ],
		mul: [ 1, 0.44668359215096, 0.35481338923358, 0.35481338923358, 0.1 ]);

	ve = BBandPass.ar(
		in: in,
		freq: [ 400, 1620, 2400, 2800, 3100 ] ,
		bw: [ 0.1, 0.049382716049383, 0.041666666666667, 0.042857142857143, 0.038709677419355 ],
		mul: [ 1, 0.25118864315096, 0.35481338923358, 0.25118864315096, 0.12589254117942 ]);

	vi = BBandPass.ar(
		in: in,
		freq: [ 250, 1750, 2600, 3050, 3340 ] ,
		bw: [ 0.24, 0.051428571428571, 0.038461538461538, 0.039344262295082, 0.035928143712575 ],
		mul: [ 1, 0.031622776601684, 0.15848931924611, 0.079432823472428, 0.03981071705535 ] );

	vo = BBandPass.ar(
		in: in,
		freq:[ 400, 750, 2400, 2600, 2900 ] ,
		bw: [ 0.1, 0.10666666666667, 0.041666666666667, 0.046153846153846, 0.041379310344828 ],
		mul: [ 1, 0.28183829312645, 0.089125093813375, 0.1, 0.01 ]);

	vu = BBandPass.ar(
		in: in,
		freq: [ 350, 600, 2400, 2675, 2950 ],
		bw: [ 0.11428571428571, 0.13333333333333, 0.041666666666667, 0.044859813084112, 0.040677966101695 ],
		mul: [ 1, 0.1, 0.025118864315096, 0.03981071705535, 0.015848931924611 ]);

	snd = SelectX.ar(Lag.kr(vowel, lag), [va, ve, vi, vo, vu]);
	snd = Mix.new(snd);
	Out.ar(0, snd!2 * env * amp);
}).add;

SynthDef("plucking", {arg amp = 0.1, freq = 440, decay = 5, coef = 0.1;
var env, snd;
env = EnvGen.kr(Env.linen(0, decay, 0), doneAction: 2);
snd = Pluck.ar(
        in: WhiteNoise.ar(amp),
        trig: Impulse.kr(0),

        maxdelaytime: 0.1,
        delaytime: freq.reciprocal,
        decaytime: decay,
        coef: coef);
    Out.ar(0, [snd, snd]);
}).add;

SynthDef("defaultB", { arg out=0, freq=440, amp=0.1, pan=0, gate=1;
    var z;
    z = LPF.ar(
            Mix.new(VarSaw.ar(freq + [0, Rand(-0.4,0.0), Rand(0.0,0.4)], 0, 0.3)),
            XLine.kr(Rand(4000,5000), Rand(2500,3200), 1)
        ) * Linen.kr(gate, 0.01, 0.7, 0.3, 2);
    OffsetOut.ar(out, Pan2.ar(z, pan, amp));
}, [\ir]).add;

SynthDef("trig_demo", { |freq = 440, gate = 1, t_trig = 1|
    var env, sig;
	env = Decay2.kr(t_trig, 0.01, 0.1);
    sig = SinOsc.ar(freq, 0, env);
	sig = sig * Linen.kr(gate, 0.01, 0.1, 0.1, doneAction: 2);
    Out.ar(0, sig ! 2)
}).add;

SynthDef("marimba1", {arg freq = 440, amp = 0.4;
	var snd, env;
	env = Env.linen(0.015, 1, 0.5, amp).kr(doneAction: 2);
	snd = BPF.ar(Saw.ar(0), freq, 0.02);
	snd = BLowShelf.ar(snd, 220, 0.81, 6);
	snd = snd * env;
	Out.ar(0, Splay.ar(snd));
}).add;

SynthDef("trianglewavebells",{
	arg out = 0, pan = 0.0, freq = 440, amp = 1.0, gate = 1, att = 0.01, dec = 0.1, sus = 1, rel = 0.5, lforate = 10, lfowidth = 0.0, cutoff = 100, rq = 0.5;

	var osc1, osc2, vibrato, filter, env;
	vibrato = SinOsc.ar(lforate, Rand(0, 2.0));
	osc1 = Saw.ar(freq * (1.0 + (lfowidth * vibrato)), 0.75);
	osc2 = Mix(LFTri.ar((freq.cpsmidi + [11.9, 12.1]).midicps));
	filter = RHPF.ar((osc1 + (osc2 * 0.5)) * 0.5, cutoff, rq);
	env = EnvGen.ar(
		envelope: Env.adsr(att, dec, sus, rel, amp),
		gate: gate,
		doneAction: 2);
	Out.ar(out, Pan2.ar(filter * env, pan));
}).add;


SynthDef("organdonor",{
	arg out = 0, pan = 0.0, freq = 440, amp = 0.1, gate = 1, att = 0.01, dec = 0.5, sus = 1, rel = 0.5, lforate = 10, lfowidth = 0.01, cutoff = 100, rq = 0.5;

	var vibrato, pulse, filter, env;
	vibrato = SinOsc.ar(lforate, Rand(0, 2.0));
	// up octave, detune by 4 cents
	// 11.96.midiratio = 1.9953843530485
	// up octave and a half, detune up by 10 cents
	// 19.10.midiratio = 3.0139733629359
	freq = freq * [1, 1.9953843530485, 3.0139733629359];
	freq = freq * (1.0 + (lfowidth * vibrato));
	pulse = VarSaw.ar(
		freq: freq,
		iphase: Rand(0.0, 1.0) ! 3,
		width: Rand(0.3, 0.5) ! 3,
		mul: [1.0,0.7,0.3]);
	pulse = Mix(pulse);
	filter = RLPF.ar(pulse, cutoff, rq);
	env = EnvGen.ar(
		envelope: Env.adsr(att, dec, sus, rel, amp),
		gate: gate,
		doneAction: 2);
	Out.ar(out, Pan2.ar(filter * env, pan));
}).add;

SynthDef(\mario, { |out, amp=0.3, freq=440, att=0.001, sustain=0.3, rel=0.03, dur=1|
	var snd;
	snd = LFPulse.ar(freq)!2;
	snd = snd * EnvGen.ar(Env.linen(att, sustain * 0.5, rel), doneAction:2);
	OffsetOut.ar(out, snd*amp*0.5);
}).add;
