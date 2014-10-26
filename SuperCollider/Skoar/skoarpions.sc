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

    "kids: ".post; kids.dump;

        // 0 - sep
        // 1 - label
        name = kids[1].toke.lexeme.asSymbol;
        // 2 - args
        args = kids[2].next_val;
        // 3 - sep
        // 4 - head
        head = kids[4];
        body = kids[5..m-3];
        // n-2: sep
        // n-1: stinger
        stinger = kids[m-1];

        if (stinger.n == 0) {
            stinger = nil;
        };

        n = body.size;

        body_noad = SkoarNoad("xient:body_noad:" ++ name, nil, nil);
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

// ---------------------
// Sequency Skoarpuscles
// ---------------------
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
