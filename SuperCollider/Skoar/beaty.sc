
SkoarpuscleBeat : Skoarpuscle {

    var <s;
    var <is_staccato;
    var <has_tie;
    var <is_grace;

    *beat_short {
        | s, n |
        var is_dotted = s.endsWith(".");
        var x = 0.0;

        if (is_dotted == true) {
            n = n - 1;
        };

        x = 2 ** (-1 * n);

        if (is_dotted == true) {
            x = x * 1.5;
        };
        ^x;
    }

    *beat_long {
        | s, n |
        var is_dotted = s.endsWith(".");
        var x = 0.0;

        if (is_dotted == true) {
            n = n - 1;
        };

        x = 2 ** (n - 1);

        if (is_dotted == true) {
            x = x * 1.5;
        };
        ^x;
    }


    init {
        | toke |
        var n;
        s = toke.lexeme;
        n = s.size;

        is_staccato = if (s.beginsWith(".")) {
                          n = n - 1;
                          true
                      } {
                          false
                      };

        case {s.contains("__")} {
            has_tie = true;
            is_grace = false;
            n = n - 2;
        } {s.contains("_")} {
            is_grace = true;
            has_tie = false;
            n = n - 1;
        };

        val = if (toke.isKindOf(Toke_Eighths)) {
            SkoarpuscleBeat.beat_short(s, n);
        } {
            SkoarpuscleBeat.beat_long(s, n);
        };

    }

    on_enter {
        // set in the decorating stage, if it's not in an assignment
    }

}

SkoarpuscleRest : SkoarpuscleBeat {

    init {
        | toke |
        var n;

        s = toke.lexeme;
        n = s.size;

        val = if (toke.isKindOf(Toke_Quavers)) {
            // size -1 for the / (we just count the o's)
            SkoarpuscleBeat.beat_short(s, n - 1)
        } {
            SkoarpuscleBeat.beat_long(s, n)
        };

    }

    on_enter {
        | m, nav |
        var e = (\dur: val, \isRest: true);

        //e.asCompileString.postln;
        e.yield;
    }

}
