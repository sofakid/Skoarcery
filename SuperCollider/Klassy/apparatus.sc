
// ---------
// The Toker
// ---------

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

        if (i_saw) {
            if (i_saw.isKindOf(want.class)) {
                ^i_saw
            }
        } {
            i_am_here += Toke_Whitespace.burn(skoarse, i_am_here);
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
                if (x) {
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
            i_am_here += toke.burn;
            i_am_here += Toke_Whitespace.burn(skoarse, i_am_here);
            ^toke;
        };

        SkoarError("Burned wrong toke").throw;
        //raise Exception("I tried to burn " + want.__name__ + ", but what I saw is " + toke.__class__.__name__)
    }

    eof {
        Toke_EOF.burn(skoarse, i_am_here);
    }
}


// --------------
// The Parse Tree
// --------------

SkoarNoad {

    var <>parent;        // the parent noad
    var <>j;             // the currently performing child
    var <>i;             // position in parent
    var <>n;             // number of children
    var <>toke;          // a toke, if this noad absorbed a toke
    var   children;      // a list of child noads

    var <>name;          // name of the nonterminal

    var <>performer;     // function to override when defining semantics.
    var  >next_jmp;      // if this is set, we will jump to this noad instead of the next noad

    var <>is_beat;       // flag indicates if it's a beat

    var  <inspectable;   // this toke carries information that must be inspected and processed.

    *new {
        | name, toke, parent, i=0 |

        ^super.new.init(name, toke, parent, i);
    }

    init {
        | name, toke, parent, i=0 |

        performer = {| x | ^nil};
        parent = parent;

        i = i;
        j = 0;
        n = 0;

        name = name;
        toke = toke;

        children = List[];

        is_beat = false;

        next_jmp = nil;

        if (toke.isKindOf(SkoarToke)) {
            inspectable = toke.inspectable;
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

        children.do {
            | x |
            if (x.isKindOf(SkoarNoad)) {
                x.i = k;
            };
            k += 1;
            n += 1;
        }
    }


    // ----------------
    // growing the tree
    // ----------------
    add_noad {
        | noad |

        children.add(noad);
        noad.i = n;
        n += 1;
    }

    add_toke {
        | name, toke |

        children.add(SkoarNoad(name, toke, this, n));
        n += 1;
    }

    absorb_toke {
        var x;

        if (n == 1) {

            x = children.pop;
            n = 0;

            if (x.isKindOf(SkoarNoad) && x.toke) {
                toke = x.toke;
            } {
                toke = x;
            };
        };

        ^toke;
    }

    // -----------------
    // Climbing the Tree
    // -----------------
    depth_visit {
        | f |

        children.do {
            | x |
            if (x) {
                x.depth_visit(f);
            };
        };

        f(this);
    }

    next_item {

        if (next_jmp) {
            ^next_jmp;
        };

        if (j == n) {
            if (parent == nil) {
                // StopIteration.throw;
                ^nil;
            };

            ^parent.next_item;
        };

        n = children[j];
        j += 1;

        ^n;
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
        performer;
    }

}