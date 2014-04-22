Server.killAll;

MIDIIn.connectAll

a = MIDIFunc.cc({arg ...args; args.postln}, 1); // match cc 1
a.free; // cleanup

b = MIDIFunc.cc({arg ...args; args.postln}, 1, 1); // match cc1, chan 1
b.free; // cleanup

c = MIDIFunc.cc({arg ...args; args.postln}, (1..127)); // match cc 1-10
c.free; // cleanup

n = MIDIFunc.noteOn({|; args.postln}); // match any noteOn
n.free; // cleanup

MIDIIn.doNoteOnAction(1, 1, 64, 64); // spoof a note on
MIDIIn.doControlAction(1, 1, 1, 64); // spoof a cc
MIDIIn.doControlAction(1, 1, 9, 64);
MIDIIn.doControlAction(1, 10, 1, 64);



//noteHandler = MIDIFunc.new({|val, num, chan, src|;

(
var notes, on, off;

MIDIClient.init;
MIDIIn.connectAll;

notes = Array.newClear(128);    // array has one slot per possible MIDI note

on = MIDIFunc.noteOn({ |veloc, num, chan, src| notes[num] = Synth(\default, [\freq, num.midicps, \amp, veloc * 0.00315]);
});

off = MIDIFunc.noteOff({ |veloc, num, chan, src|
    notes[num].release;
});

q = { on.free; off.free; };
)

q.value


