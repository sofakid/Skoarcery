SkoarFairy {

    var <name;
    var <minstrel;
    var <noat;
    var <impression;
    var magic;


    *new {
        | nom, m |
        "new SkoarFairy: ".post; nom.postln;
        ^super.new.init(nom, m);
    }

    init {
        | nom, m |
        name = nom;
        minstrel = m;
        impression = nil;
        magic = nil;
        noat = SkoarpuscleInt(0);
    }

    impress {
        | x |
        ("$:" ++ name ++ ".impression: " ++ x.asString).postln;

        if (x.isKindOf(SkoarpuscleDeref)) {
            x = x.lookup(minstrel);
        };

        impression = if (x.isKindOf(Skoarpuscle)) {
            x
        } {
            Skoarpuscle.wrap(x)
        };

        if (impression.isNoatworthy == true) {
            noat = impression;
        };

        ^impression;
    }



    charge_arcane_magic {
        | spell |
        var f = magic;

        magic = {
            var x;
            if (f.notNil) {
//                "ARCANE-alpha".postln;
                f.();
            };
//            "ARCANE-omega".postln;
            this.impress(spell.());
        };
    }

    cast_arcane_magic {
        if (magic.notNil) {
            magic.();
            magic = nil;
        };
        ^this.impression;
    }

}

SkoarpuscleFairy : Skoarpuscle {

    var msg_arr;

    *new { ^super.new.init; }

    init {
        msg_arr = #[];
    }

    flatten {
        | m |
        var x = m.fairy.impression;

        if (x.isKindOf(Skoarpuscle)) {
            x = x.flatten(m);
        };

        ^x;
    }

    on_enter {
        | m, nav |
        var x = m.fairy.impression;

        "performing fairy impression: ".post; x.dump;

        if (x.isKindOf(Skoarpuscle)) {
            x.perform(m, nav);
        };

    }


    skoar_msg {
        | msg, minstrel |
        msg_arr = msg.get_msg_arr(minstrel);

        "Fairy got msg: ".post; msg_arr.dump;

        ^this;
    }

    //isNoatworthy { ^true; }

    //asNoat {
        // need a reference to the fairy
    //}
}
