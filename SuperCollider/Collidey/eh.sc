

(
SynthDef(\rachel, {
    | gate=1, out=0, freq=440, amp=0.4, pan=0, ar=1, dr=1 |

    var audio;

    // sin city
    audio = SinOsc.ar(freq, 0, amp);

    // linen envelope
    audio = audio * Linen.kr(
        gate: gate,
        attackTime: ar,
        susLevel: 1,
        releaseTime: dr,
        doneAction:  2
    );

    // a new stereoscopic sound spectacular
    audio = Pan2.ar(in: audio, pos: pan);

    OffsetOut.ar(
        bus: out,
        channelsArray: audio
    );

}).add;
)

(
(
instrument: \rachel,
midinote:82
).play;
)


(
p = Pbind(*[
    instrument:  \default,
    detune: [0, 3, 5],

    freq:   Pseq( (1..11) * 100, 4 * 5 * 7),
    db:     Pseq( [-20, -40, -30, -40], inf),
    pan:    Pseq( [-1, 0, 1, 0], inf),
    dur:    Pseq( [0.2, 0.2, 0.2, 0.2,  0.4, 0.4,  0.8], inf),
    legato: Pseq( [2, 0.5, 0.75, 0.5, 0.25], inf)

]);

p.play;
)

(degree: [-3, 0, 2], sustain: 2, db: [-20, -20, -10]).play;

(degree: [-3, 0, 2], sustain: 2, db: [-20, -10, -20]).play;

(degree: [-3, 2, 4], sustain: 2, detune: [0,0,0, 3,3,3, 5,5,5, 7,7,7, 80,70,30] ).play;
(degree: [-3, 0, 2], sustain: 2, db: [-20, -20, -10]).play;


// Bass drum
(var start_freq = 300,
    end_freq = 50,
    duration = 1.6,
    start_array, end_array,
    freq_env, amp_env;

start_array = [start_freq, start_freq];
end_array = [end_freq, end_freq];

{SinOsc.ar(
    freq: Line.kr(start_array, end_array, 0.04),
	mul: Line.kr(0.7, 0.0, duration))}.play
)

(
play({
    var fundamental, partials, out, offset;
    fundamental = 100; // fundamental frequency
    partials = 20; // number of partials per channel

    // start of oscil daisy chain
    offset = SinOsc.kr(1/60, 0.5pi, mul: 0.03, add: 0.01);
    out = 0.0;
    partials.do({ arg i; var p;
        p = fundamental * (i+1); // freq of partial
        // p = exprand(20, 3000); // variation
        out = FSinOsc.ar(
            p,
            0,
            max(0, // clip negative amplitudes to zero
                LFNoise1.kr(
                    6 + [4.0.rand2, 4.0.rand2], // amplitude rate
                    0.02, // amplitude scale
                    offset // amplitude offset
                )
            ),
            out
        )
    });
    out
    // * EnvGen.kr(Env.perc(0, 4), Impulse.kr(1/4)) // variation
})
)