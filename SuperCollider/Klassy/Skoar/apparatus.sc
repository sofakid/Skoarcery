
// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var <>parent;          // the parent noad
    var <>i;               // position in parent
    var <>n;               // number of children
    var <>toke;            // a toke, if this noad absorbed a toke
    var <>children;        // a list of child noads

    var <>setter;          // a function to perform assignment

    var <>name;            // name of the nonterminal

    var <>performer;       // function to set when defining semantics.
    var <>one_shots;       // function to set for stuff that applies for one beat.

    var  <next_jmp;        // if this is set, we will jump to this noad instead of the next noad

    var <>noat;
    var <>is_rest;
    var <>is_beat;         // flag indicates if it's a beat.
    var <>beat;            // beat toke (note this could be a beat toke, and is_beat=false,
                           // like when setting bpm with an assignment)

    var  <inspectable;     // this toke carries information that must be inspected and processed.
    var <>voice;           // what voice to use
    var <>branch;          // what branch are we on, along the trunk (what line)

    *new {
        | name, toke, parent, i=0 |
        ^super.new.init(name, toke, parent, i);
    }

    init {
        | nameArg, tokeArg, parentArg, i=0 |

        performer = {};
        parent = parentArg;

        i = i;
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

    // -------------------
    // decorating the tree
    // -------------------
    assign_voices {
        | v, b |

        if (voice == nil) {
            voice = v;
        } {
            // the voice has changed, this is what the children get
            v = voice;
        };

        if (branch == nil) {
            branch = b;
        } {
            // the branch has changed, this is what the children get
            b = branch;
        };

        children.do {
            | y |
            if (y != nil) {
                y.assign_voices(v,b);
            };
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

    // i'm unhappy with this
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

        var s = voice.name ++ ":".padRight(tab + 1) ++ name ++ "\n";

        children.do {
            | x |
            if (x != nil) {
                s = s ++ x.draw_tree(tab + 1);
            };
        };

        ^s;
    }

    // -----------------
    // climbing the Tree
    // -----------------

    // depth-first, find the leaves, run handler, working towards trunk
    depth_visit {
        | f |

        //">>> ".post; name.postln;

        children.do {
            | y |
            if (y != nil) {
                y.depth_visit(f);
            };
        };

        //"--- ".post; name.postln;

        // note: leaves first
        f.(this);

        //"<<< ".post; name.postln;
    }

    inorder {
        | f |

        f.(this);

        children.do {
            | y |
            if (y != nil) {
                y.inorder(f);
            };
        };

    }

    // this finds the preceding noad
    prev_noad {

        // are we leftmost sibling?
        if (i == 0) {
            if (parent == nil) {
                ^nil;
            };

            ^parent.prev_noad;
        };

        // return sibling to left
        ^parent.children[i-1];
    }

    // find next (first) toke, depthfirst
    next_toke {
        var x = children[0];
        if (x.toke != nil) {
            ^x.toke;
        };

        ^x.next_toke;
    }

    // -------------------
    // performing the tree
    // -------------------
    perform {
        | minstrel, nav |

        if (performer != nil) {
            performer.(minstrel, nav);
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



