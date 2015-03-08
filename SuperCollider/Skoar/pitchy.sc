// -------------------
// Pitchy Skoarpuscles
// -------------------
SkoarpuscleNoat : Skoarpuscle {

    var <lexeme;
    var <low;
    var <sharps;

    init {
        | lexeme |

        var noat_regex = "^(_?)([a-g])";
        var sharps_regex = "[a-g](#*|b*)$";
        var s = lexeme;
        var r = s.findRegexp(noat_regex);
        var x = -1;
        var letter;

        low = r[1][1] != "";
        letter = r[2][1];

        r = s.findRegexp(sharps_regex);
        s = r[1][1];

        if (s.beginsWith("#")) {
            x = 1;
        };

        sharps = s.size * x;

        x = switch (letter)
            {"c"} {0}
            {"d"} {1}
            {"e"} {2}
            {"f"} {3}
            {"g"} {4}
            {"a"} {5}
            {"b"} {6};

        case {sharps > 0} {
            x = sharps * 0.1 + x;
        } {sharps < 0} {
            x = sharps * -0.1 + x - 1;
        };

        if (low == false) {
            x = x + 7;
        };

        val = x;
    }

    flatten {^val;}

    isNoatworthy { ^true; }

    asNoat {
        ^SkoarNoat_Degree(val);
    }

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

}


SkoarpuscleChoard : Skoarpuscle {

    flatten {^this;}

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

}

SkoarNoat {
    var <key;
    var <val;

    *new {
        | x |
        ^super.new.init(x);
    }

    execute {
        | minstrel |
        "sss: ".post; key.post; val.postln;
        minstrel.koar[key] = val;
    }
}

SkoarNoat_Freq : SkoarNoat {

    init {
        | x |
        key = \freq;
        val = x;
    }
}

SkoarNoat_Degree : SkoarNoat {

    init {
        | x |
        key = \degree;
        val = x;
    }
}
