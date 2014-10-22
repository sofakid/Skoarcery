// ===========
// Value types
// ===========
Skoarpuscle {
    var <>val;

    *new { | v | ^super.new.init(v); }
    init { | v | val = v; }

    as_noat { | m | ^val;}

    performer {}

    flatten {^val;}

    skoar_msg {
        | msg |
        var o = msg.get_msg;
        var ret = val.performMsg(o);

        case {ret.isKindOf(Skoarpuscle)} {
            ^ret;

        } {ret.isKindOf(Integer)} {
"ret int".postln;
            ^SkoarpuscleInt(ret);

        } {ret.isKindOf(Number)} {
"ret float".postln;
            ^SkoarpuscleFloat(ret);

        } {ret.isKindOf(String)} {
"ret str".postln;
            ^SkoarpuscleString(ret);

        } {ret.isKindOf(Symbol)} {
"ret symbol".postln;
            ^SkoarpuscleSymbol(ret);

        } {ret.isKindOf(Array)} {
"ret array".postln;
            ^SkoarpuscleArray(ret);

        } {
"ret unknown: ".post; ret.dump;
            ^SkoarpuscleUnknown(ret);
        };

    }
}

SkoarpuscleUnknown : Skoarpuscle {
}

SkoarpuscleInt : Skoarpuscle {
}


SkoarpuscleFloat : Skoarpuscle {
}

SkoarpuscleSkoarpionRef : Skoarpuscle {

    var config;

    skoar_msg {
        | msg |
        config = msg.get_msg;
        ^this;
    }

    performer {
        | m, nav |
        m.gosub(val, nav, config);
        "SMEAR".postln;
    }
}


SkoarpuscleString : Skoarpuscle {
    as_noat { | m | ^nil; }
}

SkoarpuscleSymbol : Skoarpuscle {

    lookup {
        | m |
        ^m.voice.skoarboard[val];
    }

    as_noat {
        | m |
        ^this.lookup(m).as_noat(m);
    }

    performer {
        | m, nav |
        var v = this.lookup(m);

        "SYMBOL LOOKEDUP : ".post; v.dump;

        if (v.isKindOf(Skoarpuscle)) {
            v.performer(m, nav);
        };
    }

}

SkoarpuscleArray : Skoarpuscle {

    flatten {
        var out = Array.new(val.size);

        val.do {
            | x |
            out.add(if (x.respondsTo(\flatten)) {x.flatten} {x});
        };

        ^out;
    }

    performer {
        | m, nav |
        m.voice.choard_listy(val);
    }

}

// noaty stuff
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

        low = r[1][1] != "";
        val = r[2][1];

        r = s.findRegexp(sharps_regex);
        s = r[1][1];

        if (s.beginsWith("#")) {
            x = 1;
        };

        sharps = s.size * x;

    }

    flatten {^this;}

    performer {
        | m, nav |
        m.voice.noat_go(this);
    }

}


SkoarpuscleChoard : Skoarpuscle {

    flatten {^this;}

    performer {
        | m, nav |
        m.voice.choard_go(this);
    }

}


// messagy stuff
SkoarpuscleMsg : Skoarpuscle {

    var <>args;

    init {
        | t, a |

        val = t.lexeme;
        args = a;

        if (t.isKindOf(Toke_MsgNameWithArgs)) {
            val.pop;
        };

        val = val.asSymbol;
    }

    performer {
        | m, nav |
        //val.postln;
    }

    get_msg {
        var x = Array.new(args.size + 1);
        x.add(val);
        args.flatten.do {
            | y |
            x.add(y);
        };
        ^x;
    }

}

// -------------
// rhythm values
// -------------
