
SkoarMinstrel {

    var   skoar;
    var  <koar;
    var  <all_voice;

    var   event_stream;

    *new {
        | nom, k, skr |
        "new SkoarMinstrel: ".post; nom.postln;
        ^super.new.init(nom, k, skr);
    }

    init {
        | nom, k, skr |
        var skoarpion;

        skoar = skr;
        koar = k;
        all_voice = skr.all_voice;

        skoarpion = skoar.tree.next_skoarpuscle.val;

        event_stream = Routine({
            var running = true;
            var nav_result = nil;

            while {running == true} {

                nav_result = block {
                    | nav |
                    koar.do_skoarpion(skoarpion, this, nav, nil, nil);
                    nav.(\nav_done);
                };

                switch (nav_result)

                    {\nav_done} {
                        running = false;
                    }

                    {\nav_fine} { running = false; }

                    {\nav_da_capo} {
                        "Da Capo time.".postln;
                        // do nothing, will enter skoarpion again
                    }

                    {\nav_jump} {
                        // do nothing, will enter skoarpion again
                    }

                    {
                        SkoarError("Unhandled nav: " ++ nav_result).throw;
                    };

            };

            ("Minstrel " ++ koar.name ++ " done.").postln;
        });

    }

    nextEvent {
        ^event_stream.next;
    }

    pfunk {
        ^Pfunc({this.nextEvent;});
    }

    reset_colons {
        koar.state_put(\colons_burned, Dictionary.new;);
    }

}

// ----------------------------------
// Skoarchestra - A band of minstrels
// ----------------------------------
Skoarchestra {

    var minstrels;

    *new {
        | skoar |
        ^super.new.init(skoar);
    }

    init {
        | skoar |
        minstrels = List.new;

        if (skoar.voices.size == 1) {
            minstrels.add(SkoarMinstrel.new(\all, skoar.all_voice, skoar));
        } {
            skoar.voices.do {
                | v |
                if (v != skoar.all_voice) {
                    minstrels.add(SkoarMinstrel.new(v.name, v, skoar));
                };
            };
        };
    }

    eventStream {
        var funkStreams = List.new;

        minstrels.do {
            | m |
            funkStreams.add(m.pfunk);
        };

        ^Ppar.new(funkStreams).asStream;
    }

    pfunk {
        var x = this.eventStream;

        ^Pfunc({x.next;});
    }

}
