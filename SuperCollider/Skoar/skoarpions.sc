// The Skoarpion is our general purpose control structure.
Skoarpion {

    var <name;
    var <n;

    var <head;
    var <body;
    var <stinger;

    var <args;

    var <body_noad;

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
        var line = List[];
        var section = List[];
        var sections = List[];

        "kids: ".post; kids.do {
            | x |
            "   ".post;
            if (x.isKindOf(SkoarNoad)) {
                x.name.post; " ".post;
            };

            x.asString.postln;

        };

        // 0 - start
        // 1 - sig
        sig = kids[1];
        // 2 - sep
        // 3 - suffix
        suffix = kids[3];

        suffix.children.do {
            | x |

            case {x.isKindOf(Toke_SkoarpionSep)} {
                section.add(line.asArray);
                sections.add(section.asArray);
                section = List[];
                line = List[];

            } {x.isKindOf(Toke_Newline) || x.isKindOf(Toke_SkoarpionEnd)} {
                section.add(line.asArray);
                line = List[];

            } {
                line.add(x);
            };

        };

        sig.dump;
        suffix.dump;

        "---------------- barglesnaz".postln;
        sections.dump;
        sections.do {
            | sec |
            sec.do {
                | x |
                "(*) ".post; x.asString.postln;
            };
        };


        //if (stinger.n == 0) {
            stinger = nil;
        //};

        n = body.size;

        body_noad = SkoarNoad("xient:body_noad:" ++ name, nil);
        body_noad.children = body;
        body_noad.n = n;
    }

    iter {
        ^SkoarpionIter(this);
    }

}

SkoarpionIter {

    var name;
    var <>i;
    var <>n;
    var body;
    var body_noad;

    *new {
        | skrp |
        ^super.new.init(skrp);
    }

    init {
        | skrp |
        body = skrp.body;
        body_noad = skrp.body_noad;
        name = skrp.name;
        n = skrp.n;
        i = -1;
    }

    selector {
        | f |
        i = f.value % n;
        ^body[i];
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
        ^body_noad;
    }

}

// ------------
// Skoarpuscles
// ------------
SkoarpuscleSeqRef : Skoarpuscle {

    var msg_arr;
    var args;

    *new {
        | v, a |
        ^super.new.init(v, a);
    }

    init {
        | v, a |

        "dorp.post; ".post; v.dump; a.dump;
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
        "flerp".postln; msg_arr.dump; args.dump;
        m.gosub(val, nav, msg_arr, args);
    }

}

