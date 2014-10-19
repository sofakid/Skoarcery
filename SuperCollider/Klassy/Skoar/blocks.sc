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

    var label;
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
        label = blk.label;
        n = blk.size;
        i = -1;
    }

    random {
        i = n.rand;
        (label ++ " random: " ++ i).postln;
        ^lines[i];
    }

    next {
        i = 1 + i % n;
        (label ++ " next: " ++ i).postln;
        ^lines[i];
    }


}

