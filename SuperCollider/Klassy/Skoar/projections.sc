
// -------------------------------------
// SkoarVoxer - A pattern for all voices
// -------------------------------------
SkoarVoxer {
    var skoar;
    var voicers;

    *new {
        | skr |
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
            voicers.add(SkoarVoicer.new(tree, v, skoar));
        };
    }

    eventStream {

        var voxen = List.new;

        voicers.do {
            | v |
            voxen.add(v.pfunk);
        };

        ^Ppar.new(voxen).asStream;
    }

    pfunk {
        var q = this.eventStream;
        ^Pfunc({q.next;});
    }

}

// ---------------------------------
// SkoarVoicer - pattern for a voice
// ---------------------------------
SkoarVoicer {

    var skoar;
    var noad;
    var voice;
    var minstrel;

    *new {
        | n, v, skr |
        ^super.new.init(n, v, skr);
    }

    init {
        | n, v, skr |
        noad = n;
        voice = v;
        skoar = skr;

        minstrel = SkoarMinstrel(voice.name, voice, skoar).asStream;
    }

    next {

        noad = minstrel.next;

        if (noad.isKindOf(SkoarNoad)) {
            noad.on_enter;
        };

        ^noad;
    }

    eventStream {
        ^Routine({
            var e = voice.event;

            // collect until we get a beat
            while {
                this.next;
                noad != nil
            } {
                // we're watching two voices, the conductoar, and us.

                // our voice
                if (noad.voice == voice) {

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

                // conductoar noads
                //   (unless this voice is the conductoar, where the above would happen)
                } {
                    if (noad.is_beat == true) {

                        e = voice.event;

                        e[\dur] = noad.toke.val;
                        e[\note] = \rest;

                        //" Firing event:".postln;
                        //e.postln;
                        e.yield;
                        e = voice.event;
                    };
                };
            };
voice.name.post; ": Done.".postln;
        });
    }

    pfunk {
        var q = this.eventStream;
        ^Pfunc({q.next;});
    }

}