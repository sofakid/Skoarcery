SkoarBlock {

    var <label;

    var <init_line;
    var <lines;
    var <size;

    *new {
        | noad |
        ^super.new.init(noad);
    }

    init {
        | noad |

        label = noad.label;

        init_line = noad.children[0];
        lines = noad.children[1..];
        size = lines.size;
    }

    iter {
        ^SkoarBlockIter(this);
    }

}

SkoarBlockIter {

    var <>i;
    var <>n;
    var lines;

    *new {
        | blk |
        ^super.new.init(blk);
    }

    init {
        | blk |
        lines = blk.lines;

        n = blk.size;
        i = -1;
    }

    random {
        ^lines[n.rand];
    }

    next {
        i = 1 + i % n;
        "iter next: ".post; i.postln;
        ^lines[i];
    }


}

