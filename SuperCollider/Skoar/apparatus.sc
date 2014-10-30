
// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var <>parent;          // the parent noad
    var <>i;               // position in parent
    var <>n;               // number of children
    var <>children;        // a list of child noads

    var <>evaluate;        // pass functions between skoarmantic levels here
    var <>setter;          // pass functions between skoarmantic levels here

    var <>name;            // name of the nonterminal
    var <>label;
    var <>skoarpuscle;     // skoarpuscle types go here
    var <>toke;

    var <>performer;       // function to set when defining semantics.
    var <>one_shots;       // function to set for stuff that applies for one beat.

    var  <next_jmp;        // if this is set, we will jump to this noad instead of the next noad

    var <>voice;           // what voice to use
    var <>branch;          // what branch are we on, along the trunk (what line)

    *new {
        | name, parent, i=0 |
        ^super.new.init(name, parent, i);
    }

    init {
        | nameArg, parentArg, i=0 |

        parent = parentArg;

        i = i;
        n = 0;

        name = nameArg.asSymbol;

        children = List[];

    }

    asString {
        ^name.asString;
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
            if (y.isKindOf(SkoarNoad)) {
                y.assign_voices(v,b);
            };
        };

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
        | name, t |
        var x = SkoarNoad(t.class.name, this, n);
        x.toke = t;
        children.add(x);
        n = n + 1;
    }

    // ----------------
    // showing the tree
    // ----------------
    draw_tree {
        | tab = 1 |

        var s = if (voice != nil) {
            voice.name ++ ":"
        } {
            ""
        };

        s = s ++ " ".padLeft(tab) ++ name;

        if (skoarpuscle != nil) {
            s = s ++ ": " ++ skoarpuscle.val;
        };

        s = s ++ "\n";
        //s.post;
        children.do {
            | x |
            if (x.isKindOf(SkoarNoad)) {
                s = s ++ x.draw_tree(tab + 1);
                //x.draw_tree(tab + 1);
            } {
                s = s ++ " ".padLeft(tab + 1) ++ x.class.asString ++ "\n";
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

        //">>> depth_visit: ".post; name.postln;

        children.do {
            | y |
            y.depth_visit(f);
        };

        //"--- depth_visit: ".post; name.postln;

        // note: leaves first
        f.(this);

        //"<<< depth_visit: ".post; name.postln;
    }

    inorder {
        | f, stinger=nil |

        //">>> inorder: ".post; name.postln;
        if (stinger != nil && skoarpuscle.isKindOf(SkoarpuscleBeat)) {
            "!!! stinger: ".post; stinger.postln;
            stinger.inorder(f);
        };

        "--- inorder: ".post; name.postln;
        f.(this);

        children.do {
            | y |
            y.inorder(f, stinger);
        };

        //"<<< inorder: ".post; name.postln;
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

    // expect skoarpuscle
    next_skoarpuscle {
        var x;

        if (skoarpuscle != nil) {
            ^skoarpuscle;
        };

        x = children[0];
        if (x != nil) {
            ^x.next_skoarpuscle;
        };

        ^nil;
    }

    next_toke {
        var x;

        if (toke != nil) {
            ^toke;
        };

        x = children[0];
        if (x != nil) {
            ^x.next_toke;
        };

        ^nil;
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
                if (x.skoarpuscle != nil) {
                    results.add(x.skoarpuscle);
                };
            });

            j = j + 1;
        };
        ^results.asArray;

    }


}



