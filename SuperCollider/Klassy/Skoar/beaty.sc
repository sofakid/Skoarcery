
SkoarpuscleBeat : Skoarpuscle {

    var <s;
    var <is_rest;
    var <is_beat;
    var <is_staccato;
    var <has_tie;

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

        if (s.beginsWith(".")) {
            n = n - 1;
            is_staccato = true;
        } {
            is_staccato = false;
        };

        is_rest = false;

        if (s.contains("__")) {
            has_tie = true;
            n = n - 2;
        };

        if (toke.isKindOf(Toke_Eighths)) {
            val = SkoarpuscleBeat.beat_short(s, n);
        } {
            val = SkoarpuscleBeat.beat_long(s, n);
        };
"here".postln;

    }

    performer {
        | m, nav |

        // create an event with everything we've collected up until now
        var e = m.voice.event;
        var cur_noat = m.voice.cur_noat;

        e[\dur] = val;

        if (m.voice.cur_noat != nil) {
            if (e[\type] == \instr) {
                e[\note] = cur_noat;
            } {
                e[\midinote] = cur_noat;
            };
        };
"floop".postln;
        e.yield;
"florp".postln;
    }

}

SkoarpuscleRest : SkoarpuscleBeat {

    init {
        | toke, long |
        var n;

        s = toke.lexeme;
        n = s.size;

        toke.is_rest = true;

        if (toke.isKindOf(Toke_Quavers)) {
            // size -1 for the / (we just count the o's)
            val = SkoarpuscleBeat.beat_short(s, n - 1);
        } {
            val = SkoarpuscleBeat.beat_long(s, n);
        };

    }

    performer {
        | m, nav |

        // create an event with everything we've collected up until now
        var e = m.voice.event;
        var cur_noat = m.voice.cur_noat;

        e[\dur] = val;
        e[\note] = \rest;
"floop".postln;
        e.yield;
"florp".postln;
    }

}
