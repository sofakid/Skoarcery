
// ==========================
// The Parse Tree - SkoarNoad
// ==========================
SkoarNoad {

    var  address;         // a list code to find the noad quickly
    var <>parent;          // the parent noad
    var <>children;        // a list of child noads

    var <>evaluate;        // pass functions between skoarmantic levels here
    var <>setter;          // pass functions between skoarmantic levels here

    var <>name;            // name of the nonterminal
    var <>skoarpuscle;     // skoarpuscle types go here
    var <>toke;

    var <>performer;       // function to set when defining semantics.
    var <>one_shots;       // function to set for stuff that applies for one beat.

    var <>voice;           // what voice to use
    var <>skoap;           // what skoap are we in

    *new {
        | name, parent |
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
    decorate_pass_two {
        | v, s, parent_address, i=0 |

        if (voice == nil) {
            voice = v;
        } {
            // the voice has changed, this is what the children get
            v = voice;
        };

        address = [i] ++ parent_address;
        "____: ".post; address.post; name.postln;

        //"p#: ".post; parent_address.post; ", ".post; i.postln;

        if (skoap == nil) {
            skoap = s;
        } {
            // the skoap has changed, this is what the children get
            s = skoap;
            address = [];
            "!!!!: ".post; address.postln;
        };

        i = 0;
        children.do {
            | y |
            if (y.isKindOf(SkoarNoad)) {
                y.decorate_pass_two(v, s, address, i);
            };
            i = i + 1;
        };

        // we remove the skoarpions from the tree
        // (they are in this.skoarpuscle)
        if (name == \skoarpion) {
            children = [];
        };
    }

    // ----------------
    // growing the tree
    // ----------------
    add_noad {
        | noad |
        children.add(noad);
    }

    add_toke {
        | name, t |
        var x = SkoarNoad(t.class.name, this);
        x.toke = t;
        children.add(x);
    }

    // ----------------
    // showing the tree
    // ----------------
    draw_tree {
        | tab = 1 |
        var n = 40 - address.asString.size;
        var s = " ".padLeft(n) ++ address.asString ++ ": ";

        if (voice != nil) {
            s = s ++ voice.name ++ ":"
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
                s = s ++ " ".padLeft(tab + 1) ++ x.class.asString ++"\n";
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

        //"--- inorder: ".post; name.postln;
        f.(this);

        children.do {
            | y |
            y.inorder(f, stinger);
        };

        //"<<< inorder: ".post; name.postln;
    }

    inorder_from {
        | here, f, stinger=nil |
        var j = here.pop;
        var n = children.size - 1;



"j: ".post; j.post; " <- here.pop: ".post; here.postln;

        if (j == nil) {
            this.inorder(f, stinger);
        } {

            for (j, n, {
                | k |
                children[k].inorder_from(here, f, stinger);
            });
        };

    }

    // this finds the preceding noad
    prev_noad {
        var i = address[address.size-1];

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
                if (x.skoarpuscle != nil) {
                    results.add(x.skoarpuscle);
                };
            });

            j = j + 1;
        };
        ^results.asArray;

    }


}



