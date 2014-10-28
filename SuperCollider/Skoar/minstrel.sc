
SkoarMinstrel {

    var   skoar;
    var  <voice;
    var   conductoar;

    // array of branches, and an inverse map: branch -> index
    var   parts;
    var   parts_index;

    var  <colons_burned;
    var <>colon_seen;
    var <>segno_seen;
    var <>al_fine;

    var   skrp_iters;

    var   event_stream;

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

        skrp_iters = IdentityDictionary.new;

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

        event_stream = Routine({
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
                                };

                            } {
                                x.perform(this, nav);
                            };

                        });
                    });

                    running = false;
                };

                switch (nav_result)

                    {\nav_fine} { running = false; }

                    {\nav_coda} { j = 0; }

                    {\nav_da_capo} {
                        dst = parts[0].children[0];
                        j = 0;
                    }

                    {\nav_segno} {
                        dst = segno_seen;
                        j = parts_index[dst.branch];
                    }

                    {\nav_jump} {
                        dst = colon_seen;
                        if (dst == nil) {
                            dst = parts[0].children[0];
                            j = 0;
                        } {
                            j = parts_index[dst.branch];
                        };
                    };

            };

            ("Minstrel " ++ voice.name ++ " done.").postln;
        });

    }

    gosub {
        // skrp_args is the args to the skoarpion
        // msg_arr is an array like [\msg, arg1, arg2, arg3 ...]
        | label, nav, msg_arr, skrp_args |

        var skrp = skoar.skoarpions[label];
        var iter;
        var z;

        var f = {
            | x |
            x.perform(this, nav);
        };

        if (msg_arr == nil) {
            msg_arr = [\block];
        };

        "gosub:msg_arr: ".postln;
        msg_arr.dump;
        skrp_args.val.postln;


        iter = skrp_iters[label];
        if (iter == nil) {
            iter = skrp.iter;
            skrp_iters[label] = iter;
        };

        // current line
        voice.push_args(skrp.args, skrp_args);
        z = iter.performMsg(msg_arr);
        z.inorder(f, nil, skrp.stinger);
        voice.pop_args;
    }

    nextEvent {
        ^event_stream.next;
    }

    pfunk {
        ^Pfunc({this.nextEvent;});
    }

    reset_colons {
        colons_burned = Dictionary.new;
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
