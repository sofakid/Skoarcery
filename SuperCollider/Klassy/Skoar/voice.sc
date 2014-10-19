
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

     // x => y
    assign_symbol {
        | x, y |

        var k = y.val;
        var v = this.evaluate(x);

        skoarboard[k] = v;
    }

    evaluate {
        | x |
/*
        var desires = List[ "listy", Toke_Int, Toke_Float, Toke_Symbol,  ];
        var items = noad.collect(desires);

        var listy = Array.new(items.size - 1);

        items.do {
            | o |

            if (o.name == "listy") {
                hand.update(o.toke);
                cur_noat.add(hand.finger);

                // remove the handler which will overwrite cur_noat with each noat
                o.performer = nil;
            };

            if (o.toke.isKindOf(Toke_Int)) {
                cur_noat.add(o.toke.val);

                // remove the handler which will overwrite cur_noat with each noat
                o.performer = nil;
            };

        };*/
        // just find the toke for now
        var t = x.next_toke;

        ^t.val;
    }

    set_tempo {
        | bpm, toke |

        var x = bpm / 60 * toke.val;
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
        | noad |

        var desires = List[ Toke_Int, Toke_Float, "noat_literal" ];
        var items = noad.collect(desires);

        cur_noat = Array.new(items.size - 1);

        items.do {
            | o |

            if (o.name == "noat_literal") {
                hand.update(o.toke);
                cur_noat.add(hand.finger);

                // remove the handler which will overwrite cur_noat with each noat
                o.performer = nil;
            };

            if (o.toke.isKindOf(Toke_Int)) {
                cur_noat.add(o.toke.val);

                // remove the handler which will overwrite cur_noat with each noat
                o.performer = nil;
            };

        };

    }

    reload_on_beat {
        | noat |
    }

    noat_symbol {
        | noat |
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

