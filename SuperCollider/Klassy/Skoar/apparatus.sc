
// =========
// The Toker
// =========

Toker {
    var skoarse;
    var i_am_here;
    var i_saw;

    *new {
        | code |
        ^super.new.init( code );
    }

    init {
        | code |
        skoarse = code;
        i_am_here = 0;
        i_saw = nil;
    }

    see {
        | want |

        if (i_saw != nil) {
            if (i_saw.isKindOf(want)) {
                ^i_saw
            }
        } {
            i_am_here = i_am_here + Toke_Whitespace.burn(skoarse, i_am_here);
            i_saw = want.match(skoarse, i_am_here);
            ^i_saw;
        }

        ^nil;
    }

    sees {
        | wants |

        var x = block {
            | break |

            wants.do {
                | want |

                x = this.see(want);
                if (x != nil) {
                    break.(x);
                };
            };

            break.(nil);
        };
        ^x;
    }

    burn {
        | want |

        var toke = i_saw;

        if (toke == nil) {
            toke = this.see(want);
        };

        if (toke.isKindOf(want)) {
            i_saw = nil;
            i_am_here = i_am_here + toke.burn;
            i_am_here = i_am_here + Toke_Whitespace.burn(skoarse, i_am_here);
            ^toke;
        };

        SkoarError("Burned wrong toke").throw;
        //raise Exception("I tried to burn " + want.__name__ + ", but what I saw is " + toke.__class__.__name__)
    }

    eof {
        Toke_EOF.burn(skoarse, i_am_here);
    }

    dump {
        ("\nToker Dump" ++
        "\nhere   : " ++ i_am_here.asString ++
        "\nsaw    : " ++ i_saw.asString ++
        "\nskoarse: " ++ skoarse.copyRange(0,i_am_here)
                      ++ "_$_" ++ skoarse.copyRange(i_am_here, skoarse.size)).postln;
    }

}


// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var <>parent;        // the parent noad
    var <>j;             // the currently performing child
    var <>i;             // position in parent
    var <>n;             // number of children
    var <>toke;          // a toke, if this noad absorbed a toke
    var   children;      // a list of child noads

    var <>name;          // name of the nonterminal

    var <>performer;     // function to override when defining semantics.
    var   next_jmp;      // if this is set, we will jump to this noad instead of the next noad

    var <>noat;
    var <>is_rest;
    var <>is_beat;       // flag indicates if it's a beat.
    var <>beat;          // beat toke (note this could be a beat toke, and is_beat=false,
                         // like when setting bpm with an assignment)

    var  <inspectable;   // this toke carries information that must be inspected and processed.


    *new {
        | name, toke, parent, i=0 |

        ^super.new.init(name, toke, parent, i);
    }

    init {
        | nameArg, tokeArg, parentArg, i=0 |

        performer = {};
        parent = parentArg;

        i = i;
        j = 0;
        n = 0;

        name = nameArg;
        toke = tokeArg;

        children = List[];

        noat = nil;

        // do i use these?
        is_rest = false;
        is_beat = false;

        next_jmp = nil;

        if (toke.isKindOf(SkoarToke)) {
            inspectable = toke.class.inspectable;
        } {
            inspectable = false;
        };

    }


    // ------------------
    // shrinking the tree
    // ------------------
    replace_children {
        | x |

        children = x;
        this.recount_children;
    }

    recount_children {
        var k = 0;
        n = 0;

        "recounting children".postln;

        children.do {
            | x |
            if (x.isKindOf(SkoarNoad)) {
                x.i = k;
            };
            k = k + 1;
            n = n + 1;
        }
    }


    // ----------------
    // growing the tree
    // ----------------
    add_noad {
        | noad |

        children.add(noad);
        noad.i = n;
        n = n + 1;
    }

    add_toke {
        | name, toke |

        children.add(SkoarNoad(name, toke, this, n));
        n = n + 1;
    }

    absorb_toke {
        var x;

        if (n == 1) {

            x = children.pop;
            n = 0;

            if (x.isKindOf(SkoarNoad) && x.toke != nil) {
                toke = x.toke;
            } {
                toke = x;
            };
        };

        ^toke;
    }

    // ----------------
    // showing the tree
    // ----------------
    draw_tree {
        | tab = 1 |

        var s = " ".padLeft(tab + 1) ++ name ++ "\n";

        children.do {
            | x |
            if (x != nil) {
                s = s ++ x.draw_tree(tab + 1);
            };
        };

        ^s;
    }

    // -----------------
    // Climbing the Tree
    // -----------------
    depth_visit {
        | f |

        children.do {
            | x |
            if (x != nil) {
                x.depth_visit(f);
            };
        };

        f.(this);
    }

    next_item {

        var nxt = nil;

        if (next_jmp != nil) {
            ^next_jmp;
        };

        if (j == n) {
            if (parent == nil) {
                ^nil;
            };

            ^parent.next_item;
        };

        nxt = children[j];
        j = j + 1;
        ^nxt;
    }

    go_here_next {
        | noad |

        next_jmp = noad;
        noad.parent.j = noad.i;
    }

    // -------------------
    // performing the tree
    // -------------------
    on_enter {
        j = 0;
    }

    action {
        if (performer != nil) {
            performer.value;
        };
    }

}


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
            var e = (type: \note);
            var noad = nil;

            // collect until we get a beat
            while {
                noad = this.next;
                noad.notNil;
            } {

                noad.action;

                if (noad.is_beat == true) {
                    e[\dur] = noad.toke.val;
                    e[\midinote] = skoar.cur_noat;
                    //" Firing event:".postln;
                    //e.array.dump;
                    e.yield;
                    e = (type: \note);
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



// =====
// Skoar
// =====
Skoar {

    var   skoarse;      // the skoarse code
    var  <tree;         // root node of the tree (our start symbol, skoar)
    var  <toker;        // friendly neighbourhood toker
    var   parser;       // recursive descent predictive parser
    var   markers;      // list of markers (for gotos/repeats)
    var   inspector;    // toke inspector for decorating
    var   skoarmantics; // semantic actions

    var <>cur_noat;
    var   hand;

    *new {
        | code |
        ^super.new.init(code);
    }

    init {
        | code |

        skoarse = code;
        tree = nil;
        toker = Toker(skoarse);
        parser = SkoarParser.new(this);
        markers = List[];
        inspector = SkoarTokeInspector.new;
        skoarmantics = Skoarmantics.new;

        cur_noat = nil;
        hand = Hand.new;
    }

    parse {
        tree = parser.skoar(nil);
        try {
            toker.eof;
        } {
            | e |
            e.postln;
            toker.dump;

        }
    }

    decorate {

        var inspect = {
            | x |

            //"inspecting ".post;
            //x.dump;

            // tokens*
            if (x.toke != nil) {
                // run the function x.name, pass the token
                inspector[x.name].(x.toke);

            // nonterminals*
            } {
                // run the function, pass the noad (not the nonterminal)
                skoarmantics[x.name].(this, x);
            };
        };

        "decorating".postln;

        tree.depth_visit(inspect);
    }

    /*get_pattern_gen {
        for x in SkoarIterator():
            if (x.isKindOf(SkoarNoad)) {

                // run performance handler
                x.performer(this);

                if (x.is_beat) {
                    yield [cur_noat.lexeme, x.beat.value];
                };
            }
        };*/

    // ----
    // misc
    // ----
    noat_go {
        | noat |
        hand.update(noat);
        cur_noat = hand.finger;
    }

    // save these in a list for jumping around in
    add_marker {
        | marker_noad |
        markers.add(marker_noad);
    }

    jmp_colon {
        | noad |

        var toke = noad.toke;

        if (toke.unspent) {
            // find where we are in markers
            var n = markers.size;
            var j;


            // spend it
            toke.unspent = false;

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
                noad.go_here_next(markers[0]);
            };
        };
    }
}



Hand {

    var   direction;
    var  <finger;
    var <>octave;

    *new {
        | oct=5 |
        ^super.new.init(oct);
    }

    init {
        | oct |

        // default to up
        direction = 1;
        octave = oct;
        finger = 0;
    }

    update {
        | vector |

        var n = 0;
        var m = nil;
        var s = vector.lexeme;
        var letter = nil;
        var o = 0;
        var semis = 0;
        var target = 0;

        // prevector
        m = s.findRegexp("^~+");
        if (m.size > 0) {
            direction = 1;
            o = m[0][1].size - 1;
            "here".postln;
            o.postln;
            octave = octave + o
        };


        // postvector
        m = s.findRegexp("~+$");
        if (m.size > 0) {
            direction = -1;
            o = m[0][1].size -1;
            octave = octave - o
        };

        // letter
        m = s.findRegexp("^[^a-g]*([a-g])");
        letter = m[1][1];
        n = switch (letter)
            {"c"} {0}
            {"d"} {2}
            {"e"} {4}
            {"f"} {5}
            {"g"} {7}
            {"a"} {9}
            {"b"} {11};

        // sharps
        m = s.findRegexp("#+");
        if (m.size > 0) {
            // just one for now
            n = n + 1;
        };

        // flats
        m = s.findRegexp("[a-g](b+)");
        if (m.size > 0) {
            // just one for now
            n = n - 1;
        };

        target = octave * 12 + n;
        target.postln;
        // have we crosseed an octave boundary?
        if (direction == -1) {
            if (finger < target) {
                octave = octave - 1;
            };
        };

        if (direction == 1) {
            if (finger > target) {
                octave = octave + 1;
            };
        };

        finger = octave * 12 + n;
        finger.postln;

    }

}


+String {
	skoar {
    	var r = Skoar.new(this);
        r.parse;
        r.decorate;

        r.tree.draw_tree.postln;
        ^r;
    }

    pskoar {
        ^SkoarIterator.new(this.skoar).pfunk;
	}
}
