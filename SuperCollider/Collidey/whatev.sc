
(
a = Pbind(\x, Pseq([1, 2, 3]), \y, Prand([100, 300, 200], inf), \zzz, 99);
x = a.asStream;
)

x.next(()); // pass in an event ()
x.next(());
x.next(());
x.next(()); // end: nil


().play


// sound examples

// using the default synth def
Pbind(\freq, Pexprand(lo:220, hi:2200), \dur, 0.1).play;
Pbind(\freq, Prand([300, 500, 231.2, 399.2], inf), \dur, Prand([0.1, 0.3], inf)).play;

Pbind(\freq, Prand([1, 1.2, 2, 2.5, 3, 4], inf) * 200, \dur, 0.1).play;


(
SynthDef(\acid, { arg out, freq = 1000, gate = 1, pan = 1, cut = 4000, rez = 0.8, amp = 1;
    Out.ar(out,
        Pan2.ar(
            RLPF.ar(
                Pulse.ar(freq, 0.05),
            cut, rez),
        pan) * EnvGen.kr(Env.linen(0.01, 1, 0.3), gate, amp, doneAction:2);
    )
}).add;
)

(
Pbind(\instrument, \acid, \dur, Pseq([1, 0.25, 0.25, 0.25, 0.25], inf), \root, -12,
    \degree, Pseq([0, 3, 5, 7, 9, 11, 5, 1], inf), \pan, Pfunc({1.0.rand2}),
    \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}), \amp, 0.2).play;
)


(
Pseq([
    Pbind(\instrument, \acid, \dur, Pseq([0.25, 0.5, 0.25], 4), \root, -24,
        \degree, Pseq([0, 3, 5, 7, 9, 11, 5, 1], inf), \pan, Pfunc({1.0.rand2}),
        \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}), \amp, 0.2),

    Pbind(\instrument, \acid, \dur, Pseq([0.25], 6), \root, -24, \degree, Pseq([18, 17, 11, 9], inf),
        \pan, Pfunc({1.0.rand2}), \cut, 1500, \rez, Pfunc({0.7.rand +0.3}), \amp, 0.16)

], inf).play;
)

(
Prand([
    Pbind(\instrument, \acid, \dur, Pseq([0.25, 0.5, 0.25], 4), \root, -24,
        \degree, Pseq([0, 3, 5, 7, 9, 11, 5, 1], inf), \pan, Pfunc({1.0.rand2}),
        \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}),
        \amp, 0.2),

    Pbind(\instrument, \acid, \dur, Pseq([0.25], 6), \root, -24, \degree, Pseq([18, 17, 11, 9], inf),
        \pan, Pfunc({1.0.rand2}), \cut, 1500, \rez, Pfunc({0.7.rand +0.3}), \amp, 0.16)

], inf).play;
)
(
Pdef(\buckyball).play;
)

(
Pdef(\buckyball, Pbind(\instrument, \acid, \dur, Pseq([0.25, 0.5, 0.25], inf), \root, [-24, -17],
        \degree, Pseq([0, [0,3,5], 5, 7, 9, 11, [5, 17], 1], inf), \pan, Pfunc({[1.0.rand2, 1.0.rand2]}),
    \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}), \amp, [0.15, 0.22]));
)
(
Pdef(\buckyball, Pbind(\instrument, \acid, \dur, Pseq([0.25, 0.5, 0.25], inf), \root, [-24, -17],
    \degree, Pseq([0b, 3b, 5b, 7b, 9b, 11b, 5b, 0b], inf), \pan, Pfunc({1.0.rand2}), //notice the flats
    \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}), \amp, 0.2));
)

//stop the Pdef
Pdef(\buckyball).stop;

//start the Pdef
Pdef(\buckyball).resume;

//removing the Pdef
Pdef.remove(\buckyball);


(
// efx synthdef- dig the timing on the delay and the pbind. :-P
SynthDef(\pbindefx, { arg out, in, time1=0.25, time2=0.5;
    var audio, efx;
    audio = In.ar([20, 21], 2);
    efx=CombN.ar(audio, 0.5, [time1, time2], 10, 1, audio);
    Out.ar(out, efx);
}).add;

// create efx synth
a = Synth.after(1, \pbindefx);

// if you don't like the beats change to 0.4, 0.24
//a.set(\time1, 0.4, \time2, 0.24);

SynthDef(\acid, { arg out, freq = 1000, gate = 1, pan = 0, cut = 4000, rez = 0.8, amp = 1;
    Out.ar(out,
        Pan2.ar(
            RLPF.ar(
                Pulse.ar(freq, 0.05),
            cut, rez),
        pan) * EnvGen.kr(Env.linen(0.02, 1, 0.3), gate, amp, doneAction:2);
    )
}).add;
)

(
Pbind(\instrument, \acid, \out, 20, \dur, Pseq([0.25, 0.5, 0.25], inf), \root, [-24, -17],
    \degree, Pseq([0, 3, 5, 7, 9, 11, 5, 1], inf), \pan, Pfunc({1.0.rand2}),
    \cut, Pxrand([1000, 500, 2000, 300], inf), \rez, Pfunc({0.7.rand +0.3}), \amp, 0.12).play;
)

a.set(\time1, 0.4, \time2, 0.24);

(
r = Routine({
    [60, 72, 71, 67, 69, 71, 72, 60, 69, 67].do({ |midi| midi.yield });
});

while { (m = r.next).notNil } { m.postln };)

(
var midi, dur;
midi = Pseq([60, 72, 71, 67, 69, 71, 72, 60, 69, 67], 1).asStream;
dur = Pseq([2, 2, 1, 0.5, 0.5, 1, 1, 2, 2, 3], 1).asStream;

SynthDef(\smooth, { |freq = 440, sustain = 1, amp = 0.5|
    var sig;
    sig = SinOsc.ar(freq, 0, amp) * EnvGen.kr(Env.linen(0.05, sustain, 0.1), doneAction: 2);
    Out.ar(0, sig ! 2)
}).add;

r = Task({
    var delta;
    while {
        delta = dur.next;
        delta.notNil
    } {
        Synth(\smooth, [freq: midi.next.midicps, sustain: delta]);
        delta.yield;
    }
}).play(quant: TempoClock.default.beats + 1.0);
)

(
var midi, dur;
midi = Pseq([60, 72, 71, 67, 69, 71, 72, 60, 69, 67], inf).asStream;
dur = Pseq([2, 2, 1, 0.5, 0.5, 1, 1, 2, 2, 4], inf).asStream;

SynthDef(\smooth, { |freq = 440, sustain = 1, amp = 0.5|
    var sig;
    sig = SinOsc.ar(freq, 0, amp) * EnvGen.kr(Env.linen(0.05, sustain, 0.1), doneAction: 2);
    Out.ar(0, sig ! 2)
}).add;

r = Task({
    var delta;
    while {
        delta = dur.next;
        delta.notNil
    } {
        delta = delta * 0.3;
        Synth(\rachel, [freq: midi.next.midicps, sustain: delta * 0.9]);
        delta.yield;
    }
}).play(quant: TempoClock.default.beats + 1.0);
)