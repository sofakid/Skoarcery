SkoarRecursiveTraverseAbortException : Exception {
}


SkoarMinstrel {

    var skoar;
    var voice;
    var conductoar;
    var parts;

    *new {
        | nom, v, skr |

        "new SkoarMinstrel: ".post; nom.postln;
        ^super.new.init(nom, v, skr);
    }

    init {
        | nom, v, skr |

        skoar = skr;

        voice = v;
        conductoar = skr.conductoar;
        parts = this.collect_voices;
    }

    // collect minstrel's voice and conductoar's voice
    collect_voices {

        // the skoarlines are the first level (along the trunk)
        var lines = skoar.tree.children;
        var list = List[];

        lines.do {
            | line |

            var vi = line.voice;

            if ((vi == voice) || (vi == conductoar)) {
                list.add(line);
            };
        };

        ^list;
    }

    asStream {
        ^Routine({
            var i;

            var noad;

            parts.do {
                | subtree |

                "part: ".post; subtree.name.postln;

                try {
                    subtree.inorder({
                        | x |

                        if (x.next_jmp != nil) {
                            "jumping".postln;
                            x.next_jmp.yield;
                            SkoarRecursiveTraverseAbortException.throw;
                        };

                        x.yield;
                    });
                } {
                    | e |
                    "Caught ".post;
                    if (e.isKindOf(SkoarRecursiveTraverseAbortException).not) {
                        "unknown exception, rethrowing.".postln;
                        e.throw;
                    };

                    "SkoarRecursiveTraverseAbortException".postln;
                };

            };

        });
    }

}