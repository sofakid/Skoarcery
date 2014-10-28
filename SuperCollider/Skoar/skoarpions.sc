// The Skoarpion is our general purpose control structure.
Skoarpion {

    var <name;
    var <n;

    var <body;
    var <stinger;

    var <args;

    *new {
        | noad |
        ^super.new.init(noad);
    }

    init {
        | noad |
        var kids = noad.children;
        var m = kids.size;
        var sig, suffix;
        var i = 0;
        var line = SkoarNoad("xient:section:line", nil);
        var section = SkoarNoad("xient:section:", nil);
        var sections = List[];

        // 0 - start
        // 1 - sig
        sig = kids[1];
        // 2 - sep
        // 3 - suffix
        suffix = kids[3];

        sig.children.do {
            | x |
            case {x.isKindOf(Toke_SymbolName)} {
                name = sig.skoarpuscle.val;
            } {x.name == \args} {
                args = x.skoarpuscle;
            };
        };

        suffix.children.do {
            | x |

            case {x.isKindOf(Toke_SkoarpionSep)} {
                section.add_noad(line);
                sections.add(section);

                section = SkoarNoad("xient:section:", nil);
                line = SkoarNoad("xient:section:line:", nil);

            } {x.isKindOf(Toke_Newline)} {
                section.add_noad(line);
                line = SkoarNoad("xient:section:line:", nil);

            } {x.isKindOf(Toke_SkoarpionEnd)} {
                section.add_noad(line);
                sections.add(section);

            } {
                line.add_noad(x);
            };

        };

        sections.do {
            | sec |
            sec.do {
                | x |
                "(*) ".post; x.asString.postln;
            };
        };

        if (sections.size == 1) {
            body = sections[0];
            stinger = nil;
        } {
            body = sections[0];
            stinger = SkoarNoad("xient:stinger:" ++ name, nil);
            stinger.children = sections[1..];
            stinger.n = stinger.children.size;
        };

        n = body.size;

    }

    iter {
        ^SkoarpionIter(this);
    }

    post_tree {
        ("---< Skoarpion " ++ name.asString ++ " >---").postln;

        if (args != nil) {
            "args: ".post; args.val.dump;
        };

        if (body != nil) {
            "body:".postln;
            body.draw_tree.post;
        };

        if (stinger != nil) {
            "stinger: ".postln;
            stinger.draw_tree.post;
        };

        "".postln;
    }
}

SkoarpionIter {

    var name;
    var <>i;
    var <>n;
    var body;

    *new {
        | skrp |
        ^super.new.init(skrp);
    }

    init {
        | skrp |
        body = skrp.body;
        name = skrp.name;
        n = skrp.n;
        i = -1;
    }

    selector {
        | f |
        i = f.value % n;
        ^body.children[i];
    }

    at {
        | j |
        ^this.selector({j});
    }

    choose {
        ^this.selector({n.rand});

    }

    wchoose {
        | weights |
        ^this.selector({weights.val.windex});

    }

    next {
        ^this.selector({1 + i});
    }

    last {
        ^this.selector({i - 1});
    }

    block {
        ^body;
    }

}

// ------------
// Skoarpuscles
// ------------
SkoarpuscleSkoarpion : Skoarpuscle {

}


SkoarpuscleSeqRef : Skoarpuscle {

    var msg_arr;
    var args;

    *new {
        | v, a |
        ^super.new.init(v, a);
    }

    init {
        | v, a |
        val = v;
        args = a;
    }

    skoar_msg {
        | msg, minstrel |
        msg_arr = msg.get_msg_arr;
        ^this;
    }

    performer {
        | m, nav |
        m.gosub(val, nav, msg_arr, args);
    }

}

