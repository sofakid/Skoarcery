
// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var <>parent;        // the parent noad
    var <>j;             // the currently performing child
    var <>i;             // position in parent
    var <>n;             // number of children
    var <>toke;          // a toke, if this noad absorbed a toke
    var <>children;      // a list of child noads

    var <>setter;        // a function to perform assignment

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

        name.postln;

        children.do {
            | x |
            if (x != nil) {
                x.depth_visit(f);
            };
        };

        f.(this);
    }

    // this will follow repeats and other jmps
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

    // this finds the preceding noad
    prev_noad {
        var prv = nil;

        // are we leftmost sibling?
        if (i == 0) {
            if (parent == nil) {
                ^nil;
            };

            ^parent.prev_noad;
        };

        // return sibling to left
        prv = parent.children[i-1];
        ^prv;
    }

    // find next (first) toke, depthfirst
    next_toke {
        var x = children[0];
        if (x.toke != nil) {
            ^x.toke;
        };

        ^x.next_toke;
    }

    // this sets the destination of a jump
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
            var e = skoar.event;
            var noad = nil;

            // collect until we get a beat
            while {
                noad = this.next;
                noad.notNil;
            } {

                noad.action;

                if (noad.is_beat == true) {

                    e = skoar.event;
                    
                    e[\dur] = noad.toke.val;
                    e[\tempo] = 50/60;

                    if (noad.is_rest == true) {
                        e[\note] = \rest;
                    } {
                        e[\midinote] = skoar.cur_noat;
                    };

                    //" Firing event:".postln;
                    //e.array.dump;
                    e.yield;
                    e = skoar.event;
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
    var   skoarboard;   // copied into event

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
        skoarboard = IdentityDictionary.new;

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

        "decorating...".postln;
        tree.depth_visit(inspect);
        "skoar tree decorated.".postln;

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

    // ----
    // misc
    // ----

    // x => y
    assign_symbol {
        | x, y |

        var k = y.val;
        var v = this.evaluate(x);

        "skoarboard[".post;
        k.post;
        "] = ".post;
        v.postln;

        skoarboard[k] = v;
    }

    dynamic {
        | toke |

        if (toke.isKindOf(Toke_DynPiano) || toke.isKindOf(Toke_DynForte)) {
            skoarboard[\amp] = toke.val / 127;
        };
    }

    evaluate {
        | x |

        // just find the toke for now
        var t = x.next_toke;

        ^t.val;
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
        cur_noat.postln;
    }

    choard_listy {
        | noat |
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

    // find the start of the piece
    the_capo {
        var x = markers[0];

        if (x != nil) {
            ^x;
        }

        ^tree;
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
"YAAAAAY".postln;
                    noad.go_here_next(x);
                    break.value;
                };
            });
            SkoarError("no segno %S% found.").throw;
        };

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
                noad.go_here_next(this.the_capo);
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

    letter {
        | s |

        var n = switch (s)
            {"c"} {0}
            {"d"} {2}
            {"e"} {4}
            {"f"} {5}
            {"g"} {7}
            {"a"} {9}
            {"b"} {11};

        ^n;
    }

    choard {
        | toke |
        // TODO this sucks
        var s, c, m, n;
        var third = 3;
        var fifth = 5;

        var a = [ 0, nil ];

        var i = 0;
        var j = 0;

        // [ABCEFG])([Mm0-9]|sus|dim)*

        s = toke.lexeme;

        s.postln;
        c = (s[0] ++ "").toLower;
        c.postln;

        if (s.endsWith("m")) {
            third = third - 1;
        };

        n = this.letter(c);

        n = octave * 12 + n;

        finger = [n, n + third, n + fifth];

        "Choard: ".post;
        finger.postln;

    }

    update {
        | toke |

        var sharps = toke.sharps;
        var n = 0;
        var m = sharps.sign;
        var s = toke.lexeme;
        var o = octave;

        n = this.letter(toke.val);
        if (sharps.abs > 0) {
            forBy (0, sharps, m, {
                | i |
                n = m * 0.5 + n;
            });
        };

        if (toke.low == false) {
            o = o + 1;
        };

        finger = o * 12 + n;
        finger.postln;

    }

    update_vector {
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
            octave = octave + o;
            if (octave > 12) {
                direction = -1;
            };
        };


        // postvector
        m = s.findRegexp("~+$");
        if (m.size > 0) {
            direction = -1;
            o = m[0][1].size -1;
            octave = octave - o;
            if (octave < 0) {
                octave = 0;
                direction = 1;
            };
        };

        n = this.letter(s);

        target = octave * 12 + n;
        target.postln;
        // have we crossed an octave boundary?
        /*if (direction == -1) {
            if (finger < target) {
                octave = octave - 1;
            };
        };

        if (direction == 1) {
            if (finger > target) {
                octave = octave + 1;
            };
        };*/

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
