
SkoarKoar {
    var   skoar;        // global skoar
    var  <skoarboard;   //
    var  <stack;        // stack of vars visible to the skoar code
    var  <state_stack;  // stack of vars invisible to the skoar code

    var  <name;         // name of voice as Symbol

    var <>cur_noat;
    var   hand;

    *new {
        | skr, nom |
        ^super.new.init(skr, nom);
    }

    init {
        | skr, nom |

        skoar = skr;
        name = nom;

        stack = List[];
        state_stack = List[];
        skoarboard = IdentityDictionary.new;
        stack.add(skoarboard);

        hand = Hand.new;
        cur_noat = nil;
    }

    assign_incr {
        | x, y |

        if (y.isKindOf(SkoarpuscleSymbol)) {
            this.incr_symbol(x, y);
        };

        if (y.isKindOf(SkoarpuscleBeat)) {
            this.incr_tempo(x, y);
        };
    }

    assign_decr {
        | x, y |

        if (y.isKindOf(SkoarpuscleSymbol)) {
            this.decr_symbol(x, y);
        };

        if (y.isKindOf(SkoarpuscleBeat)) {
            this.decr_tempo(x, y);
        };
    }

    assign_set {
        | x, y |

        if (y.isKindOf(SkoarpuscleSymbol)) {
            this.assign_symbol(x, y);
        };

        if (y.isKindOf(SkoarpuscleBeat)) {
            this.set_tempo(x, y);
        };

    }

    // x +> y
    incr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = this[k] + v;

        //("@" ++ k ++ " <= ").post; v.dump;
        this[k] = v;
    }

    // x -> y
    decr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = this[k] - v;

        //("@" ++ k ++ " <= ").post; v.dump;
        this[k] = v;
    }

    // x => y
    assign_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        //("@" ++ k ++ " <= ").post; x.postln; v.dump;
        this[k] = v;
    }

    incr_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        var y = this[\tempo] + x;
        this[\tempo] = y;
    }

    decr_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        var y = this[\tempo] - x;
        this[\tempo] = y;
    }

    set_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        this[\tempo] = x;
    }

    dynamic {
        | skoarpuscle |

        this[\amp] = skoarpuscle.amp;
    }

    noat_go {
        | x |

        hand.update(x);
        cur_noat = hand.finger;
    }

    choard_go {
        | x |

        hand.choard(x);
        cur_noat = hand.finger;
    }

    choard_listy {
        | items |

        cur_noat = Array.new(items.size);

        items.do {
            | o |

            if (o.isKindOf(SkoarpuscleNoat)) {
                hand.update(o);
                cur_noat.add(hand.finger);
            } {
                o = o.as_noat;
                if (o != nil) {
                    cur_noat.add(o);
                };
            };

        };

    }

    pedal_up {
    }

    pedal_down {
    }

    octave_shift {
        | x |
        hand.octave = hand.octave + x;
    }

    // ---------------------
    // State and scope stuff
    // ---------------------
    put {
        | k, v |
        this.top_args[k] = v;
    }

    at {
        | k |
        var out = nil;

        stack.reverseDo {
            | skrb |
            out = skrb[k];
            if (out != nil) {
                ^out;
            };
        };

        ^out;
    }

    state_put {
        | k, v |
        state_stack[state_stack.size - 1].put(k, v);
    }

    state_at {
        | k |
        var out = nil;

        state_stack.reverseDo {
            | skrb |
            out = skrb[k];
            if (out != nil) {
                ^out;
            };
        };

        ^out;
    }

    event {
        var e = (type: \note);

        stack.do {
            | skrb |
            e = skrb.transformEvent(e);
        }

        ^e
    }

    push_args {
        | args_def, args |
        var skrb = IdentityDictionary.new;
        var i = 0;

        if (args_def.isKindOf(SkoarpuscleArgs)) {
            args_def.val.do {
                | k |
                k = k.val;
                skrb[k] = args.val[i];
                i = i + 1;
            };
        };

        stack.add(skrb);
    }

    pop_args {
        stack.pop;

        if (stack.size == 0) {
            "Stack underflow. This means trouble. What are you doing?".postln;
            stack.add(skoarboard);
        };
    }

    top_args {
        ^stack[stack.size - 1];
    }

    do_skoarpion {
        | skoarpion, minstrel, up_nav, msg_arr, skrp_args |

        var dst;
        var nav_result;
        var iter;
        var running = true;

        var state = IdentityDictionary.new;
        var skoarpion_iters = IdentityDictionary.new;

        state_stack.add(state);

        state[\colons_burned] = Dictionary.new;
        state[\al_fine] = false;
        state[\skoarpion_iters] = skoarpion_iters;

        if (skoarpion.isKindOf(Skoarpion) == false) {
            "This isn't a skoarpion: ".post; skoarpion.postln;
            ^nil;
        };

        this.push_args(skoarpion.args, skrp_args);

        if (skoarpion.name != nil) {

            iter = this.state_at(\skoarpion_iters)[skoarpion.name];

            // start a new one if we haven't seen it
            if (iter == nil) {
                iter = skoarpion.iter(name);
                skoarpion_iters[skoarpion.name] = iter;
            };
        } {
            iter = skoarpion.iter;
        };

        // default behaviour (when unmessaged)
        if (msg_arr == nil) {
            msg_arr = [\block];
        };

        dst = iter.performMsg(msg_arr);

        // -----------------
        // alright let's go!
        // -----------------
        while {running == true} {

            nav_result = block {
                | nav |
                dst.go_from_here(
                    dst.address,
                    minstrel.koar,
                    {   | x |
                        x.perform(minstrel, nav); },
                    skoarpion.stinger);
                nav.(\nav_done);
            };

            switch (nav_result)

                {\nav_done} {
                    running = false;
                }

                {\nav_fine} {
                    up_nav.(\nav_fine);
                }

                {\nav_coda} {}

                {\nav_da_capo} {
                    up_nav.(\nav_da_capo);
                }

                {\nav_segno} {
                    dst = this.state_at(\segno_seen);

                    if (dst == nil) {
                        up_nav.(\nav_segno);
                    };

                }

                {\nav_jump} {
                    dst = this.state_at(\colon_seen);

                    if (dst == nil) {
                        up_nav.(\nav_jump);
                    };

                };

        };
        this.pop_args;
        state_stack.pop;
    }
}
