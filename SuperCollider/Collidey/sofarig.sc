SofaBtn {

    var <>lit;
    var <>label;
    var <>inst;

    // notes
    var <>notes;

    var <device;

    // default handlers just bubble up to device
    var <>noteOn = {
        | note, vel |
        device.noteOn(note, vel);
    }

    var <>noteOff = {
        | note |
        device.noteOff(note);
    }

    *new {
        | device, label |
        this.device = device
        this.label = label
        this.lit = False
    }
}


SofaMidi {

    var <>defaultVoicer;
    var <>inst;

    var <keys;

    var onProgramChange = { |program| };

    *new {
        ^super.new.init()
    }

    init {
        "You should override this".postln
    }

    *initClass {

        defaultVoicer = Voicer(2, i, [\env, Env.adsr(0.01, 0.2, 0.75, 0.1), \rq, 0.2]);

    }

    programChange { |program| ; }

    channels

}

QuNexus: SofaMidi {

    var <panel

    init {
        this.keys = [
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

        ]

        this.panel = [
            SofaBtn(this, "Togl A"),
            SofaBtn(this, "Velo B"),
            SofaBtn(this, "Preset"),
            SofaBtn(this, "Pres C"),
            SofaBtn(this, "Tilt D"),
            SofaBtn(this, "Bend"),
            SofaBtn(this, "Oct -"),
            SofaBtn(this, "Oct +")
        ]

    }

    eventNotes {

        this.keys do: {
            |k|
            k.noteOn = {
                | note, vel |
                (midinote: note, pan: 0, amp: vel/127).play
            }
        }
    }


    example {
        // This example illustrates connecting a Voicer, mapping global controls to
        // MIDI controllers, GUI interactivity and pitch bend.

        (
            i = Instr([\test, \miditest], {
                | freq = 440, gate = 0, env, pb = 1, ffreq = 1000, rq = 1 |

                var out, amp;

                amp = Latch.kr(gate, gate);	// velocity sensitivity
                out = EnvGen.kr(env, gate, doneAction:2) *
                RLPF.ar(Pulse.ar(freq * pb, 0.25, amp), ffreq, rq);

                [out,out]

            }, [\freq, \amp, nil, nil, \freq, \rq]);



            k = VoicerMIDISocket(0, v);
            k.addControl(1, \ffreq, 1000, \freq);   // filt. cutoff by mw
            k.addControl(\pb, \pb, 1, 3);	// 3-semitone bend

            v.gui;	// the controllers show up in the window, w/ visual feedback when you move the wheels
        )

        // when done
        v.free;
    }

    free     {
        voice.free
    }


}

SvOne: SofaMidi {

    *new { ^super.new }

}

(
 var evt;
 evt = Event.new;
 evt.play;
)



