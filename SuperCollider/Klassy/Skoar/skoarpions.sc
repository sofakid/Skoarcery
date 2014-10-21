Skoarpion {

    var <name;
    var <size;

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
        var n = kids.size;

        name = noad.label;

        // 0 - sep
        // 1 - label
        // 2 - sep
        // 3 - head
        head = kids[3];


        // n-2: sep
        // n-1: stinger
        stinger = kids[n-1];

        "SKAD:LSAKJD:SLAKJD:SALKJDS:ALKDJSA:LDKSA".postln;
        kids.do {
            | x |
            x.name.postln;
        };

        stinger.dump;
        if (stinger.name == "stinger") {
            body = kids[4..n-2];
        } {
            stinger = nil;
            body = kids[4..n-1];
        };
        
        size = body.size;
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
        n = skrp.size;
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

