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

    *new_from_skoar {
        | skoar |
        ^super.new.init_from_skoar(skoar);
    }

    init_from_skoar {
        | skoar |
        body = skoar.tree;
        n = body.size;
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
            case {x.skoarpuscle.isKindOf(SkoarpuscleSymbolName)} {
                name = x.skoarpuscle.val;
            } {x.name == \args} {
                args = x.skoarpuscle;
            };
        };

        "SIG: ".post; name.post; args.postln;

        suffix.children.do {
            | x |

            case {x.toke.isKindOf(Toke_SkoarpionSep)} {
                section.add_noad(line);
                sections.add(section);

                section = SkoarNoad("xient:section:", nil);
                line = SkoarNoad("xient:section:line:", nil);

            } {x.toke.isKindOf(Toke_Newline)} {
                section.add_noad(line);
                line = SkoarNoad("xient:section:line:", nil);

            } {x.toke.isKindOf(Toke_SkoarpionEnd)} {
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

    var <>i;
    var <>n;
    var body;

    *new {
        | skrp |
        ^super.new.init(skrp);
    }

    init {
        | skrp |
"here:skrp:".post; skrp.postln;
        body = skrp.body;
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
