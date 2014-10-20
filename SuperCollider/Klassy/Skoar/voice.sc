
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
        var v = x.val;

        ("@" ++ k ++ " = ").post; v.dump;
        skoarboard[k] = v;
    }

    set_tempo {
        | bpm, val |

        var x = bpm / 60 * val.val;
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
"noat_go ".post; noat.dump;

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

        var items = noad.val;

        cur_noat = Array.new(items.size - 1);

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

