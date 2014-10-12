
// -------------------------------------
// SkoarVoxer - A pattern for all voices
// -------------------------------------
SkoarVoxer {
    var skoar;
    var voicers;

    *new {
        | skr |
        "fleep".postln;
        ^super.new.init(skr);
    }

    init {
        | skr |
        var tree = nil;

        skoar = skr;
        tree = skoar.tree;

        voicers = List.new;

        skoar.voices.do {
            | v |
            voicers.add(SkoarVoicer.new(tree, v));
        };
    }

    eventStream {

        var voxen = List.new;

        voicers.do {
            | v |
            voxen.add(v.pfunk);
        };
"bleefs".postln;
        ^Ppar.new(voxen).asStream;

    }

    pfunk {
        var q = this.eventStream;
        ^Pfunc({q.next;});
    }

}

// -----------------------------------
// SkoarVoicer - A pattern for a voice
// -----------------------------------
SkoarVoicer {

    var noad;
    var voice;

    *new {
        | n, v |
        ^super.new.init(n, v);
    }

    init {
        | n, v |
        noad = n;
        voice = v;
    }

    next {
        var x = nil;

        x = noad.next_item();

        if (x.isKindOf(SkoarNoad)) {
            noad = x;
            x.on_enter;
        };

        ^x;
    }

    routine {
        ^Routine({
             loop {
                this.next.yield;
             };
        });
    }

    eventStream {
        ^Routine({
            var e = voice.event;
            var noad = nil;

            // collect until we get a beat
            while {
                noad = this.next;
                noad.notNil;
            } {

                noad.action;

                if (noad.is_beat == true) {

                    e = voice.event;

                    e[\dur] = noad.toke.val;

                    if (noad.is_rest == true) {
                        e[\note] = \rest;
                    } {
                        if (voice.cur_noat != nil) {
                            if (e[\type] == \instr) {
                                e[\note] = voice.cur_noat;
                            } {
                                e[\midinote] = voice.cur_noat;
                            };
                        };
                    };

                    //" Firing event:".postln;
                    //e.postln;
                    e.yield;
                    e = voice.event;
                };

                if (noad.toke.isKindOf(Toke_Int)) {
                    e[\degree] = noad.toke.val;
                };

            };

        });
    }

    pfunk {
        var q = this.eventStream;
        ^Pfunc({q.next;});
    }

}