

// =============
// SkoarIterator
// =============
SkoarIterator {

    var noad;
    var skoar;

    *new {
        | skr |
        ^super.new.init(skr);
    }

    init {
        | skr |
        noad = skr.tree;
        skoar = skr;
    }

    next {
        var x = noad.next_item();

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
            var e = skoar.event;
            var noad = nil;

            // collect until we get a beat
            while {
                noad = this.next;
                noad.notNil;
            } {

                noad.action;

                if (noad.is_beat == true) {

                    e = skoar.event;

                    e[\dur] = noad.toke.val;

                    if (noad.is_rest == true) {
                        e[\note] = \rest;
                    } {
                        if (skoar.cur_noat != nil) {
                            if (e[\type] == \instr) {
                                e[\note] = skoar.cur_noat;
                            } {
                                e[\midinote] = skoar.cur_noat;
                            };
                        };
                    };

                    //" Firing event:".postln;
                    //e.postln;
                    e.yield;
                    e = skoar.event;
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