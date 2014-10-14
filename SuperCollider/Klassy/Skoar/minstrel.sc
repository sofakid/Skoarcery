
SkoarJumpException : Exception {

   *new {
        | noad |

        ^super.new(noad);
    }

    noad {
        ^what;
    }

}



SkoarMinstrel {

    var skoar;
    var voice;
    var conductoar;
    var parts;
    var parts_index;
    var colons_seen;

    *new {
        | nom, v, skr |

        "new SkoarMinstrel: ".post; nom.postln;
        ^super.new.init(nom, v, skr);
    }

    init {
        | nom, v, skr |

        // the skoarlines are the first level (along the trunk)
        var lines = nil;
        var i = 0;

        skoar = skr;

        voice = v;
        conductoar = skr.conductoar;
        parts = List[];
        parts_index = Dictionary.new;
        colons_seen = Dictionary.new;

        // collect minstrel's voice and conductoar's voice
        lines = skoar.tree.children;

        lines.do {
            | line |

            var vi = line.voice;

            if ((vi == voice) || (vi == conductoar)) {
                parts_index[line] = i;
                parts.add(line);
                i = i + 1;
            };
        };

    }

    asStream {
        ^Routine({
            var n = 0;
            var j = 0;
            var src = nil;
            var dst = nil;
            var running = true;

            n = parts.size - 1;

            while {running} {

                try {
                    for ( j, n, {
                        | i |
                        var y = nil;
                        var part = nil;
                        part = parts[i];

                        part.inorder({
                            | x |
                            if (dst != nil) {
                                if (x == dst) {
                                    dst = nil;
                                    x.yield;
                                };

                            } {
                                x.yield;
                            };

                            if (x != nil) {
                                y = x.next_jmp;
                                if (y != nil) {
                                    "jumping".postln;
                                    if (colons_seen.falseAt(x)) {
                                        colons_seen[x] = true;
                                        SkoarJumpException(y).throw;
                                    };
                                };
                            };

                        });
                    });

                    running = false;
                } {
                    | e |
                    "Caught ".post;
                    if (e.isKindOf(SkoarJumpException)) {
                        "SkoarJumpException".postln;
                        dst = e.noad;
                        dst.dump;
                        j = parts_index[dst.branch];
                        "j is ".post; j.postln;
                    } {
                        "unknown exception, rethrowing.".postln;
                        e.postProtectedBacktrace;
                        e.throw;
                    };

                };
            };
        });
    }

}