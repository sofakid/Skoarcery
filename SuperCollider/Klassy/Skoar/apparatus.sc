
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
    var <>voice;         // what voice to use

    *new {
        | name, toke, parent, i=0 |

        "new SkoarNoad: ".post;
        name.postln;

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

        voice = nil;

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

//"recounting children".postln;

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

            if (x != nil && x.isKindOf(SkoarNoad) && x.toke != nil) {
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
        | f, g=nil, x=nil |

name.postln;

        // g is a function we use to inspect before decending, and pass info to the leaves
        if (g != nil) {
            x = g.(this);
        };

        children.do {
            | y |
            if (y != nil) {
                y.depth_visit(f, g, x);
            };
        };

        f.(this, x);
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

    // ------------------
    // searching the tree
    // ------------------

    // desires - array of names of noads as string, or SkoarToke implementation classes
    // writer - a function that will do something with the matches
    match {
        | desires, writer |

        desires.do {
            | item |

            if (item.isKindOf(String)) {
                if (item == name) {
                    writer.(this);
                };

            } {
                if (toke.isKindOf(item)) {
                    writer.(this);
                };

            };
        };
    }

    collect {
        | desires |

        var results = List.new;

        this.depth_visit({
            | x |

            x.match(desires, {
                | y |
                results.add(y);
            });
        });

        ^results.asArray;
    }
}


SkoarVoice {
    var  skoar;         // global skoar
    var <skoarboard;    //

    var <name;          // name of voice as Symbol

    var   markers;      // list of markers (for gotos/repeats)
    var   codas;        // list of codas
    var <>cur_noat;
    var   hand;


    *new {
        | parent, name |
        ^super.new.init(parent, name);
    }

    init {
        | parent, name |

        skoar = parent;

        skoarboard = IdentityDictionary.new;
        skoarboard.parent = skoar.skoarboard;

        hand = Hand.new;
        cur_noat = nil;
        markers = List[];
        codas = List[];
    }

    // this is how we inherit from the default voice
    set_defaults_from {
        | skb |
        //skoarboard.parent = skb;
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

//e.postln;

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



// =====
// Skoar
// =====
Skoar {

    var   skoarse;      // the skoarse code
    var  <tree;         // root node of the tree (our start symbol, skoar)
    var  <toker;        // friendly neighbourhood toker
    var   parser;       // recursive descent predictive parser
    var   inspector;    // toke inspector for decorating
    var   skoarmantics; // semantic actions
    var  <skoarboard;   // copied into event
    var  <voices;       // dictionary of voices

    const  <default_voice = \default;

    *new {
        | code |
        ^super.new.init(code);
    }

    init {
        | code |

        var v = nil;

        skoarse = code;
        tree = nil;
        toker = Toker(skoarse);
        parser = SkoarParser.new(this);

        inspector = SkoarTokeInspector.new;
        skoarmantics = Skoarmantics.new;
        skoarboard = IdentityDictionary.new;

        voices = IdentityDictionary.new;
        v = SkoarVoice.new(this,default_voice);
        v.set_defaults_from(skoarboard);
        voices[default_voice] = v;

        this.skoarboard_defaults;
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

    skoarboard_defaults {

        // 60 bpm
        skoarboard[\tempo] = 1;

        // mp
        skoarboard[\amp] = 0.5;
    }

    put {
        | k, v |
        skoarboard[k] = v;
    }

    at {
        | k |
        ^skoarboard[k];
    }

    decorate {

        var f = {
            | x |

//"inspecting ".post; x.dump;

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
        tree.depth_visit(f);
"skoar tree decorated.".postln;

        this.decorate_voices;
    }

    // we don't know the voices until the end of decorating, so we make a second pass.
    decorate_voices {

        var f = nil;
        var g = nil;

        // inspects current noad before decending into children
        g = {
            | noad, x |

            // are we set up with a voice? return it and we'll paint the children
            if (noad.voice != nil) {
                ^noad.voice;
            };

            // did we pass in a voice?
            if (x != nil) {
                ^x;
            };

        };

        f = {
            | noad, x |
            noad.voice = x;
        };

        tree.voice = voices[Skoar.default_voice];

"configuring voices...".postln;
        tree.depth_visit(f,g);
"skoar tree decorated.".postln;
    }

    // ----
    // misc
    // ----

    get_voice {
        | k |

        var voice = nil;

        if (voices.includesKey(k)) {
            voice = voices[k];
        } {
            voice = SkoarVoice(this,k);
            voices[k] = voice;
        };

        ^voice;

    }



    cthulhu {
        | noad |

        // dump state

"^^(;,;)^^".postln;

        this.dump;

"".postln;
        SkoarError("^^(;,;)^^").throw;

    }
}

