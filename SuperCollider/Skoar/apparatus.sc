// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var   address;         // a list code to find the noad quickly
    var <>parent;          // the parent noad
    var <>children;        // a list of child noads

    var <>name;            // name of the nonterminal (a \symbol)
    var <>skoarpuscle;     // skoarpuscle types go here
    var <>toke;

    var <>on_enter;
    var <>one_shots;       // function to set for stuff that appFalse for one beat.

    var <>voice;           // what voice to use
    var <>skoap;           // what skoap are we in

    *new {
        | name, parent=nil |
        ^super.new.init(name, parent);
    }

    init {
        | nameArg, parentArg |

        parent = parentArg;
        name = nameArg;

        children = List[];
        address = [];
    }

    asString {
        ^name.asString;
    }

    // here we return a copy, as whomever uses it is likely going to start
    // popping numbers off of it.
    address {
        ^Array.newFrom(address);
    }

    // -------------------
    // decorating the tree
    // -------------------
    decorate_zero {
        | v, s, parent_address, i=0 |

        if (voice.isNil) {
            voice = v;
        } {
            // the voice has changed, this is what the children get
            v = voice;
        };

        address = [];
        skoap = s;

        i = 0;
        children.do {
            | y |
            y.decorate(v, s, address, i);
            i = i + 1;
        };

    }

    decorate {
        | v, s, parent_address, i=0 |

        if (voice.isNil) {
            voice = v;
        } {
            // the voice has changed, this is what the children get
            v = voice;
        };

        address = [i] ++ parent_address;
        skoap = s;

        i = 0;
        children.do {
            | y |
            y.decorate(v, s, address, i);
            i = i + 1;
        };

    }

    // ----------------
    // growing the tree
    // ----------------
    add_noad {
        | noad |
        children = children.add(noad);
    }

    add_toke {
        | name, t |
        var x = SkoarNoad(t.class.name, this);
        x.toke = t;
        children = children.add(x);
    }

    // ----------------
    // showing the tree
    // ----------------
    draw_tree {
        | tab = 1 |
        var n = 16;
        var s;
        var sa = skoap.asString ++ ":";
        var sv;

        address.reverseDo {
            | x |
            sa = sa ++ x.asString ++ ";";
        };

        sv = if (voice.notNil) {
                voice.name.asString.padLeft(n) ++ ":"
             } {
                ":".padLeft(n+1)
             };

        s = sa.padRight(n) ++ sv ++ " ".padLeft(tab) ++ name;

        if (skoarpuscle.notNil) {
            s = s ++ ": " ++ skoarpuscle.val;
        };

        s = s ++ "\n";
        children.do {
            | x |
            s = if (x.isKindOf(SkoarNoad)) {
                    s ++ x.draw_tree(tab + 1)
                } {
                    s ++ " ".padLeft(tab + 1) ++ x.class.asString ++ "\n"
                };
        };

        ^s;
    }

    // -----------------
    // climbing the Tree
    // -----------------

    // depth-first, find the leaves, run handler, working towards trunk
    //
    // if it's crashing during the decorating stage, here's a good place to
    // start debugging
    depth_visit {
        | f |
        //var s = " " ++ if (toke.notNil) {toke.lexeme} {""};
        //debug(">>> depth_visit: " ++ name ++ s);

        children.do {
            | y |
            y.depth_visit(f);
        };

        //debug("--- depth_visit: " ++ name ++ s);

        // note: leaves first
        f.(this);

        //debug("<<< depth_visit: " ++ name ++ s);
    }

    inorder {
        | f |

        //debug(">>> inorder: " ++ name);
        
        f.(this);

        children.do {
            | y |
            y.inorder(f);
        };

        //debug("<<< inorder: " ++ name);
    }

    // debug here if it's crashing while performing the skoar
    inorder_from_here {
        | here, f |
        var j = here.pop;
        var n = children.size - 1;

        //debug("inorder_from_here: j:" ++ j ++ " " ++ name);

        if (j.isNil) {
            this.inorder(f);
        } {
            children[j].inorder_from_here(here, f);

            j = j + 1;
            if (j <= n) {
                for (j, n, {
                    | k |
                    children[k].inorder(f);
                });
            };
        };
    }

    // expect skoarpuscle
    next_skoarpuscle {
        var x;

        if (skoarpuscle.notNil) {
            ^skoarpuscle;
        };

        x = children[0];

        if (x.notNil) {
            ^x.next_skoarpuscle;
        };

        ^nil;
    }

    next_toke {
        var x;

        if (toke.notNil) {
            ^toke;
        };

        x = children[0];

        if (x.notNil) {
            ^x.next_toke;
        };

        ^nil;
    }

    // -------------------
    // performing the tree
    // -------------------
    enter_noad {
        | minstrel, nav |

        if (on_enter.notNil) {
            on_enter.(minstrel, nav);
        };

		if (skoarpuscle.notNil and: skoarpuscle.respondsTo(\on_enter)) {
			skoarpuscle.on_enter(minstrel, nav);
		};
    }

    // ------------------
    // searching the tree
    // ------------------

    // desires - array of names of noads as symbol, or SkoarToke implementation classes
    // writer - a function that will do something with the matches
    match {
        | desires, writer |

        desires.do {
            | item |

            if (item == name) {
                writer.(this);
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

    collect_skoarpuscles {
        | j=0 |

        var results = List.new;
        while {j < children.size} {

            children[j].inorder({
                | x |
                if (x.skoarpuscle.notNil) {
                    //debug("found skoarpuscle: " ++ x.skoarpuscle.asString);
                    results.add(x.skoarpuscle);
                };
            });

            j = j + 1;
        };
        ^results.asArray;

    }

}



