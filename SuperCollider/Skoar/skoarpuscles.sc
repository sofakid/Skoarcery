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
        | msg, minstrel |
        var o = msg.get_msg_arr;
        var ret = val.performMsg(o);

        ^Skoarpuscle.wrap(ret);
    }

    *wrap {
        | x |
        case {x.isKindOf(Skoarpuscle)} {
            "already wrapped".postln;
            ^x;

        } {x.isKindOf(Skoarpion)} {
            "x skoarpion".postln;
            ^SkoarpuscleSkoarpion(x);

        } {x.isKindOf(Integer)} {
            "x int".postln;
            ^SkoarpuscleInt(x);

        } {x.isKindOf(Number)} {
            "x float".postln;
            ^SkoarpuscleFloat(x);

        } {x.isKindOf(String)} {
            "x str".postln;
            ^SkoarpuscleString(x);

        } {x.isKindOf(Symbol)} {
            "x symbol".postln;
            ^SkoarpuscleSymbol(x);

        } {x.isKindOf(Array)} {
            var a = Array.new(x.size);
            "x array".postln;
            x.do {
                | el |
                a.add(Skoarpuscle.wrap(el));
            };

            ^SkoarpuscleArray(a);

        } {
            "x unknown: ".post; x.dump;
            ^SkoarpuscleUnknown(x);
        };

    }
}

SkoarpuscleUnknown : Skoarpuscle {
}

SkoarpuscleInt : Skoarpuscle {
    flatten {
        ^val.asInteger;
    }
}

SkoarpuscleFloat : Skoarpuscle {
    flatten {
        ^val.asFloat;
    }
}

SkoarpuscleString : Skoarpuscle {
    as_noat { | m | ^nil; }
}

SkoarpuscleSymbolName : Skoarpuscle {

    performer {
    }
}

SkoarpuscleSymbol : Skoarpuscle {

    performer {
    }

    skoar_msg {
        | msg, minstrel |
        var o = msg.get_msg_arr;
        var ret = val;
        var x = this.lookup(minstrel);

        if (x == nil) {
            x = val.asClass;
        };

        if (x != nil) {
            ret = x.performMsg(o);
        } {
            ret = val.performMsg(o);
        };

        ^Skoarpuscle.wrap(ret);
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

SkoarpuscleArgs : SkoarpuscleArray {

    performer {
        | m, nav |
    }

}


SkoarpuscleMsg : Skoarpuscle {

    var <>args;

    *new {
        | v, a |
        ^super.new.init(nil).init(v, a);
    }

    init {
        | v, a |
        val = v;
        args = a;
    }

    performer {
        | m, nav |
        //val.postln;
    }

    get_msg_arr {
        var x = Array.new(args.size + 1);
        x.add(val);
        args.flatten.do {
            | y |
            x.add(y);
        };
        ^x;
    }

}
