
SkoarVoice {
    var   skoar;        // global skoar
    var  <skoarboard;   //

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

        skoarboard = IdentityDictionary.new;

        hand = Hand.new;
        cur_noat = nil;
    }

    put {
        | k, v |
        skoarboard[k] = v;
    }

    at {
        | k |
        ^skoarboard[k];
    }

    event {
        var e = (type: \note);

        ^skoarboard.transformEvent(e);
    }

    assign_incr {
        | x_val, y |

        var y_toke = y.toke;

        if (y_toke.isKindOf(Toke_Symbol)) {
            y.val = SkoarValueSymbol(y_toke.val);
            this.incr_symbol(x_val, y.val);
        };

        if (y_toke.isKindOf(Toke_Quarters) || y_toke.isKindOf(Toke_Eighths)) {
            this.incr_tempo(x_val, y_toke);
        };
    }

    assign_decr {
        | x_val, y |

        var y_toke = y.toke;

        if (y_toke.isKindOf(Toke_Symbol)) {
            y.val = SkoarValueSymbol(y_toke.val);
            this.decr_symbol(x_val, y.val);
        };

        if (y_toke.isKindOf(Toke_Quarters) || y_toke.isKindOf(Toke_Eighths)) {
            this.decr_tempo(x_val, y_toke);
        };
    }

    assign_set {
        | x_val, y |

        var y_toke = y.toke;

        if (y_toke.isKindOf(Toke_Symbol)) {
            y.val = SkoarValueSymbol(y_toke.val);
            this.assign_symbol(x_val, y.val);
        };

        if (y_toke.isKindOf(Toke_Quarters) || y_toke.isKindOf(Toke_Eighths)) {
            this.set_tempo(x_val, y_toke);
        };

    }

     // x => y
    incr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = skoarboard[k] + v;

        //("@" ++ k ++ " <= ").post; v.dump;
        skoarboard[k] = v;
    }

    decr_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        v = skoarboard[k] - v;

        //("@" ++ k ++ " <= ").post; v.dump;
        skoarboard[k] = v;
    }

    assign_symbol {
        | x, y |
        var k = y.val;
        var v = x.flatten;

        //("@" ++ k ++ " <= ").post; v.dump;
        skoarboard[k] = v;
    }


    incr_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        var y = skoarboard[\tempo] + x;
        skoarboard[\tempo] = y;
    }

    decr_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        var y = skoarboard[\tempo] - x;
        skoarboard[\tempo] = y;
    }

    set_tempo {
        | bpm, beat |

        var x = bpm.flatten / 60 * beat.val;
        skoarboard[\tempo] = x;
    }

    dynamic {
        | toke |

        if (toke.isKindOf(Toke_DynPiano) || toke.isKindOf(Toke_DynForte)) {
            skoarboard[\amp] = toke.val / 127;
        };
    }

    noat_go {
        | noat |

        hand.update(noat);
        cur_noat = hand.finger;
    }

    choard_go {
        | noat |

        hand.choard(noat);
        cur_noat = hand.finger;
    }

    choard_listy {
        | items |

        cur_noat = Array.new(items.size);

        items.do {
            | o |

            if (o.isKindOf(SkoarValueNoat)) {
                hand.update(o.val);
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

