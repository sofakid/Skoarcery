// ===========
// Value types
// ===========
SkoarValue {
    var <>val;

    *new { | v | ^super.new.init(v); }
    init { | v | val = v; }

    as_noat { | m | ^val;}

    performer {}

    flatten {^val;}
}


SkoarValueInt : SkoarValue {
}


SkoarValueFloat : SkoarValue {
}


SkoarValueString : SkoarValue {
    as_noat { | m | ^nil;}
}

SkoarValueSymbol : SkoarValue {

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

        if (v.isKindOf(SkoarValue)) {
            v.performer(m, nav);
        };
    }

}

SkoarValueArray : SkoarValue {

    flatten {
        var out = Array.new(val.size);

        val.do {
            | x |
            out.add(x.flatten);
        };

        "flattened array: ".post; out.postln;

        ^out;
    }

    performer {
        | m, nav |
        m.voice.choard_listy(val);
    }

}


SkoarValueNoat : SkoarValue {

    performer {
        | m, nav |
        m.voice.noat_go(val);
    }

}


SkoarValueChoard : SkoarValue {

    performer {
        | m, nav |
        m.voice.choard_go(val);
    }

}
