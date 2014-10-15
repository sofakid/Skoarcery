
SkoarVoice {
    var   skoar;        // global skoar
    var  <skoarboard;   //

    var  <name;         // name of voice as Symbol

    var   markers;      // list of markers (for gotos/repeats)
    var   codas;        // list of codas
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
        markers = List[];
        codas = List[];
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

        skoarboard.keysValuesDo {
            | k, v |

            e[k] = v;
        };

e.postln;

        ^e

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
"SETTING TEMPO".postln;
x.postln;
name.postln;
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

//"built choard: ".post; cur_noat.postln;

    }

    reload_curnoat {
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

    // save these in a list for jumping around in
    add_marker {
        | marker_noad |

        markers.add(marker_noad);
    }

    // save these in a list for jumping around in
    add_coda {
        | coda_noad |

        codas.add(coda_noad);
    }

    // find the start of the piece
    the_capo {
        var x = markers[0];

        if (x != nil) {
            ^x;
        }

        ^skoar.tree;
    }

    da_capo {
        | noad |

        var al_x = noad.children[1];

        noad.go_here_next(this.the_capo);

    }

    dal_segno {
        | noad |

        var al_x = noad.children[1];

        var n = markers.size;
        var j;

        j = block {
            | break |

            for (0, n - 1, {
                | i |

                if (noad == markers[i]) {
                    break.(i);
                };

            });
            SkoarError("couldn't find where we are in markers").throw;
        };

        // go backwards in list and find either a
        // post_repeat or the start
        block {
            | break |

            forBy(j - 1, 0, -1, {
                | i |

                var x = markers[i];
                var t = x.toke;

                if (t.isKindOf(Toke_Segno)) {
                    noad.go_here_next(x);
                    break.value;
                };
            });
            SkoarError("no segno %S% found.").throw;
        };

    }

    // starts at noad and goes backwards until it finds a |:
    jmp_colon {
        | noad |

        var toke = noad.toke;

        // find where we are in markers
        var n = markers.size;
        var j;

        j = block {
            | break |
            for (0, n - 1, {
                | i |

                if (noad == markers[i]) {
                    break.(i);
                };

            });
            SkoarError("couldn't find where we are in markers").throw;
        };

        // go backwards in list and find either a
        // post_repeat or the start
        block {
            | break |
            forBy(j - 1, 0, -1, {
                | i |

                var x = markers[i];
                var t = x.toke;

                if (t.isKindOf(Toke_Bars) && t.post_repeat) {
                    noad.go_here_next(x);
                    break.value;
                };
            });
            noad.go_here_next(this.the_capo);
        };
    }


}

