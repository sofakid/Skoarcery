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


SkoarpuscleDeref : Skoarpuscle {

    var msg_arr;
    var args;

    *new {
        | v, a |
        ^super.new.init(v, a);
    }

    init {
        | v, a |
        val = v;
        args = a;
    }

    lookup {
        | m |
        ^m.voice[val];
    }

    as_noat {
        | m |
        ^this.lookup(m).as_noat(m);
    }

    performer {
        | m, nav |
        var x = this.lookup(m);

        "deref: SYMBOL LOOKEDUP : ".post; val.post; " ".post; x.dump;

        if (x.isKindOf(SkoarpuscleSkoarpion)) {
            m.gosub(x.val, nav, msg_arr, args);
        } {
            if (x.isKindOf(Skoarpuscle)) {
                x.performer(m, nav);
            };
        };

    }

    skoar_msg {
        | msg, minstrel |
        var ret = val;
        var x = this.lookup(minstrel);

        "deref:skoar_msg: SYMBOL LOOKEDUP : ".post; val.post; " ".post; x.dump;
        msg_arr = msg.get_msg_arr;

        if (x.isKindOf(SkoarpuscleSkoarpion)) {
            ^this;
        };

        // we don't recognise that name, did they mean a SuperCollider class?
        if (x == nil) {
            x = val.asClass;
        };

        ret = if (x != nil) {
                 x.performMsg(msg_arr)
              } {
                 val.performMsg(msg_arr)
              };

        ^Skoarpuscle.wrap(ret);
    }

}


SkoarpuscleSkoarpion : Skoarpuscle {

    var msg_arr;

    skoar_msg {
        | msg, minstrel |
        msg_arr = msg.get_msg_arr;
        ^this;
    }

    performer {
        | m, nav |
        if (val.name != nil) {
            m.voice[val.name] = this;
        };

        if (msg_arr != nil) {
            m.gosub(val, nav, msg_arr, nil);
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


// -----------------------------
// musical keywords skoarpuscles
// -----------------------------

SkoarpuscleBars : Skoarpuscle {

    var <noad;
    var <pre_repeat;
    var <post_repeat;

    *new {
        | nod, toke |
        toke.dump;
        ^super.new.init_two(nod, toke);
    }

    init_two {
        | nod, toke |

        val = toke.lexeme;
        noad = nod;
        pre_repeat = val.beginsWith(":");
        post_repeat = val.endsWith(":");
    }

    performer {
        | m, nav |

        if (pre_repeat == true) {
            if (m.colons_burned.falseAt(noad)) {
                m.colons_burned[noad] = true;
                nav.(\nav_jump);
            };
        };

        if (post_repeat == true) {
            m.colon_seen = noad;
        };
    }

}

SkoarpuscleGoto : Skoarpuscle {

    var dst;
    var al_fine;

    init {
        | noad |

        var toke = noad.children[0].next_toke;
        var al_x = noad.children[1];

        dst = case {toke.isKindOf(Toke_DaCapo)} {\nav_da_capo}
                   {toke.isKindOf(Toke_DalSegno)} {\nav_segno};

        al_fine = false;
        if (al_x != nil) {
            if (al_x.toke.isKindOf(Toke_AlFine)) {
                al_fine = true;
            };
        };
    }

    performer {
        | m, nav |

        if (al_fine) {
            m.al_fine = true;
        };

        m.reset_colons;
        nav.(dst);
    }

}

SkoarpuscleVolta : Skoarpuscle {

    var <noad;

    *new {
        | nod, toke |
        ^super.new.init_two(nod, toke);
    }

    init_two {
        | nod, toke |
        val = toke.lexeme.strip("[.]").asInteger;
        noad = nod;
    }

    performer {
        | m, nav |
    }

}

SkoarpuscleMeter : Skoarpuscle {

    init {
        | toke |
        var a = toke.lexeme.split;
        val = [a[0].asInteger, a[1].asInteger];
    }
}

SkoarpuscleCarrots : Skoarpuscle {

    init {
        | toke |
        val = toke.lexeme.size;
    }
}

SkoarpuscleTuplet : Skoarpuscle {

    init {
        | toke |
        val = toke.lexeme.size;
    }
}

SkoarpuscleDynamic : Skoarpuscle {

    init {
        | toke |
        var s = toke.lexeme;

        val = switch (s)
            {"ppp"}     {1}
            {"pppiano"} {1}
            {"pp"}      {2}
            {"ppiano"}  {2}
            {"p"}       {3}
            {"piano"}   {3}
            {"mp"}      {4}
            {"mpiano"}  {4}
            {"mf"}      {5}
            {"mforte"}  {5}
            {"forte"}   {6}
            {"ff"}      {7}
            {"fforte"}  {7}
            {"fff"}     {8}
            {"ffforte"} {8};
    }

    amp {
        ^val/8;
    }

}

SkoarpuscleOctaveShift : Skoarpuscle {

    init {
        | toke |
        var f = {
            var s = toke.lexeme;
            var n = s.size - 1;

            if (s.beginsWith("o")) {
                n =  n * -1;
            };
            ^n;
        };

        val = case {toke.isKindOf(Toke_OctaveShift)} {f.()}
                   {toke.isKindOf(Toke_OttavaA)}       { 1}
                   {toke.isKindOf(Toke_OttavaB)}       {-1}
                   {toke.isKindOf(Toke_QuindicesimaA)} { 2}
                   {toke.isKindOf(Toke_QuindicesimaB)} {-2};
    }

    performer {
        | m, nav |
        m.voice.octave_shift(val);
    }

}

SkoarpuscleBooleanOp : Skoarpuscle {

    init {
        | toke |
        val = toke.lexeme;
    }

}

SkoarpuscleVoice : Skoarpuscle {

    init {
        | toke |
        var s = toke.lexeme;
        var n = s.size - 1;
        val = s[1..n].asSymbol;
    }

}

SkoarpuscleSegno : Skoarpuscle {

    var <noad;

    *new {
        | nod, toke |
        ^super.new.init_two(nod, toke);
    }

    init_two {
        | nod, toke |
        var s = toke.lexeme;
        var n = s.size;

        // ,segno`label`
        if (n > 8) {
            s[6..n-2].asSymbol;
        } {
            \segno
        };
        val = s[1..n].asSymbol;
    }

    performer {
        | m, nav |
        m.segno_seen = noad;
    }

}

SkoarpuscleRep : Skoarpuscle {

    init {
        | toke |
        val = toke.lexeme.size;
    }

}
