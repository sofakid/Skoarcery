SkoarFairy {

    var <name;
    var <minstrel;
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
        impression = nil; // todo
        this.reset_magic;
    }

    reset_magic {
        magic = {
            "magic".post;
            "magic"
        };
    }

    impress {
        | x |
        ("$:" ++ name ++ ".impression: " ++ x.asString).postln;
        impression = Skoarpuscle.wrap(x);
        ^impression;
    }

    learn_arcane_magic {
        | spell |
        var f = magic;

        magic = {
            f.();
            spell.()
        };

    }

    cast_arcane_magic {
        var x = magic.();

        this.reset_magic;

        ^Skoarpuscle.wrap(x);
    }

    impress_arcane_magic {
        ^this.impress(this.cast_arcane_magic);
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

}
