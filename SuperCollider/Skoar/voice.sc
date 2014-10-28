
SkoarVoice {
    var   skoar;        // global skoar
    var  <skoarboard;   //
    var   stack;

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
        skoarboard = IdentityDictionary.new;
        stack.add(skoarboard);

        hand = Hand.new;
        cur_noat = nil;
    }

    push_args {
        | args_def, args |
        var skrb = IdentityDictionary.new;
        var i = 0;

        "push_args".postln;
        args_def.dump;
        args_def.val.do {
            | k |
            k = k.val;
            "k:".post; k.postln;
            skrb[k] = args.val[i];
            i = i + 1;
        };

        stack.add(skrb);
    }

    pop_args {
        stack.pop;

        if (stack.size == 0) {
            stack.add(skoarboard);
        };
    }

    top_args {
        ^stack[stack.size];
    }

    put {
        | k, v |
        this.top_args[k] = v;
    }

    at {
        | k |
        var out = nil;

        stack.do {
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

        "assign_set.".post; x.post; y.postln;
        if (y.isKindOf(SkoarpuscleSymbol)) {
            "bloop".postln;
            this.assign_symbol(x, y);
            "bloosp".postln;
        };

        if (y.isKindOf(SkoarpuscleBeat)) {
            this.set_tempo(x, y);
        };

    }

     // x => y
    incr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = this[k] + v;

        //("@" ++ k ++ " <= ").post; v.dump;
        this[k] = v;
    }

    decr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = this[k] - v;

        //("@" ++ k ++ " <= ").post; v.dump;
        this[k] = v;
    }

    assign_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        ("@" ++ k ++ " <= ").post; x.postln; v.dump;
        this.put(k,v);
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
        | toke |

        if (toke.isKindOf(Toke_DynPiano) || toke.isKindOf(Toke_DynForte)) {
            this[\amp] = toke.val / 127;
        };
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

}

