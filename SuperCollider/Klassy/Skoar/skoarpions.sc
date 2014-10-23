Skoarpion {

    var <name;
    var <n;

    var <head;
    var <body;
    var <stinger;

    var <body_noad;

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

    block {
        ^body_noad;
    }

}

