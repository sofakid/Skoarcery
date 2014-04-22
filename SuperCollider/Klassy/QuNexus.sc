QuNexus : SofaMidi {

    var <panel;

    init {
        keys = [
            SofaBtn(this, "0"),
            SofaBtn(this, "10"),
            SofaBtn(this, "1"),
            SofaBtn(this, "11"),
            SofaBtn(this, "2"),

            SofaBtn(this, "3"),
            SofaBtn(this, "12"),
            SofaBtn(this, "4"),
            SofaBtn(this, "Channel"),
            SofaBtn(this, "5"),
            SofaBtn(this, "Rotate"),
            SofaBtn(this, "6"),

            SofaBtn(this, "7"),
            SofaBtn(this, "CoMA"),
            SofaBtn(this, "8"),
            SofaBtn(this, "Xpose"),
            SofaBtn(this, "9"),

            SofaBtn(this, "Notes"),
            SofaBtn(this, "Rec"),
            SofaBtn(this, "CC"),
            SofaBtn(this, "Stop"),
            SofaBtn(this, "Bend"),
            SofaBtn(this, "Play"),
            SofaBtn(this, "AftTch"),

            SofaBtn(this, "ChnPres")

        ];

        panel = [
            SofaBtn(this, "Togl A"),
            SofaBtn(this, "Velo B"),
            SofaBtn(this, "Preset"),
            SofaBtn(this, "Pres C"),
            SofaBtn(this, "Tilt D"),
            SofaBtn(this, "Bend"),
            SofaBtn(this, "Oct -"),
            SofaBtn(this, "Oct +")
        ];

        this.eventNotes();

    }

    eventNotes {

        this.keys do: {
            |k|
            k.noteOn = {
                | note, vel |
                (freq: note, pan: 0, amp: vel/127).play
            }
        }

    }

    //
    // example {
    //     // This example illustrates connecting a Voicer, mapping global controls to
    //     // MIDI controllers, GUI interactivity and pitch bend.
    //
    //     (
    //         i = Instr([\test, \miditest], {
    //             | freq = 440, gate = 0, env, pb = 1, ffreq = 1000, rq = 1 |
    //
    //             var out, amp;
    //
    //             amp = Latch.kr(gate, gate);	// velocity sensitivity
    //             out = EnvGen.kr(env, gate, doneAction:2) *
    //             RLPF.ar(Pulse.ar(freq * pb, 0.25, amp), ffreq, rq);
    //
    //             [out,out]
    //
    //         }, [\freq, \amp, nil, nil, \freq, \rq]);
    //
    //
    //
    //         k = VoicerMIDISocket(0, this.defaultVoicer);
    //         k.addControl(1, \ffreq, 1000, \freq);   // filt. cutoff by mw
    //         k.addControl(\pb, \pb, 1, 3);	// 3-semitone bend
    //
    //
    //     )
    // }

    // free {
    //     voice.free
    // }


}



