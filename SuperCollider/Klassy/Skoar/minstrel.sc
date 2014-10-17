
SkoarMinstrel {

    var   skoar;
    var  <voice;
    var   conductoar;

    // array of branches, and an inverse map: branch -> index
    var   parts;
    var   parts_index;

    var <>colons_burned;
    var <>colon_seen;
    var <>segno_seen;
    var <>al_fine;

    var noad_stream;


    *new {
        | nom, v, skr |

        "new SkoarMinstrel: ".post; nom.postln;
        ^super.new.init(nom, v, skr);
    }

    init {
        | nom, v, skr |

        var i = 0;
        var f;

        skoar = skr;
        voice = v;
        conductoar = skr.conductoar;
        parts = List[];
        parts_index = Dictionary.new;
        colons_burned = Dictionary.new;

        // when set true, we will halt at a Toke_Fine
        al_fine = false;

        // collect minstrel's lines and conductoar's lines (branches on the trunk)
        skoar.tree.children.do {
            | line |

            var vi = line.voice;

            if ((vi == voice) || (vi == conductoar)) {
                parts_index[line] = i;
                parts.add(line);
                i = i + 1;
            };
        };


        noad_stream = Routine({
            var n = parts.size - 1;
            var j = 0;
            var dst = nil;
            var running = true;
            var nav_result = nil;

            while {running} {

                nav_result = block {
                    | nav |

                    for ( j, n, {
                        | i |

                        parts[i].inorder({
                            | x |

                            if (dst != nil) {
                                if (x == dst) {
                                    dst = nil;

                                    x.perform(this, nav);
                                    x.yield;
                                };

                            } {
                                x.perform(this, nav);
                                x.yield;
                            };

                        });
                    });

                    running = false;
                };

                switch (nav_result)

                    {\nav_fine} { running = false; }

                    {\nav_coda} { j = 0; }

                    {\nav_da_capo} { j = 0; }

                    {\nav_segno} {
                        dst = segno_seen;
                        j = parts_index[dst.branch];
                        colons_burned = Dictionary.new;
                    }

                    {\nav_jump} {
                        dst = colon_seen;
                        if (dst == nil) {
                            j = 0;
                        } {
                            j = parts_index[dst.branch];
                        };
                    };

            };
        });

    }

    // goes along, configuring a new event, which it returns when it finds a beat.
    nextEvent {
            var e = nil;
            var noad = nil;

            while {
                noad = noad_stream.next;
                noad != nil;
            } {

                if (noad.is_beat == true) {

                    // create an event with everything we've collected up until now
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

                    ^e;
                };

            };
voice.name.post; ": Done.".postln;
    }

    pfunk {
        ^Pfunc({this.nextEvent;});
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
            minstrels.add(SkoarMinstrel.new(\conductoar, skoar.conductoar, skoar));
        } {
            skoar.voices.do {
                | v |
                if (v != skoar.conductoar) {
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
