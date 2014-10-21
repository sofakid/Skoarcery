Skoarpion {

    var <name;
    var <n;

    var <head;
    var <body;
    var <stinger;

    *new {
        | noad |
        ^super.new.init(noad);
    }

    init {
        | noad |
        var kids = noad.children;
        var m = kids.size;

        name = noad.label;

        // 0 - sep
        // 1 - label
        // 2 - sep
        // 3 - head
        head = kids[3];
        body = kids[4..m-3];
        // n-2: sep
        // n-1: stinger
        stinger = kids[m-1];

        if (stinger.n == 0) {
            stinger = nil;
        };

        n = body.size;
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

    random {
        i = n.rand;
        //(name ++ " random: " ++ i).postln;
        ^body[i];
    }

    next {
        i = 1 + i % n;
        //(name ++ " next: " ++ i).postln;
        ^body[i];
    }


}

