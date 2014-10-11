

Hand {

    var   direction;
    var  <finger;
    var <>octave;

    *new {
        | oct=5 |
        ^super.new.init(oct);
    }

    init {
        | oct |

        // default to up
        direction = 1;
        octave = oct;
        finger = 0;
    }

    letter {
        | s |

        var n = switch (s)
            {"c"} {0}
            {"d"} {2}
            {"e"} {4}
            {"f"} {5}
            {"g"} {7}
            {"a"} {9}
            {"b"} {11};

        ^n;
    }

    choard {
        | toke |
        // TODO this sucks
        var s, c, m, n;
        var third = 3;
        var fifth = 5;

        var a = [ 0, nil ];

        var i = 0;
        var j = 0;

        // [ABCEFG])([Mm0-9]|sus|dim)*

        s = toke.lexeme;

        c = (s[0] ++ "").toLower;

        if (s.endsWith("m")) {
            third = third - 1;
        };

        n = this.letter(c);
        n = octave * 12 + n;

        finger = [n, n + third, n + fifth];
    }

    update {
        | toke |

        var sharps = toke.sharps;
        var n = 0;
        var m = sharps.sign;
        var s = toke.lexeme;
        var o = octave;

        n = this.letter(toke.val);
        if (sharps.abs > 0) {
            forBy (0, sharps, m, {
                | i |
                n = m * 0.5 + n;
            });
        };

        if (toke.low == false) {
            o = o + 1;
        };

        finger = o * 12 + n;
    }

}

