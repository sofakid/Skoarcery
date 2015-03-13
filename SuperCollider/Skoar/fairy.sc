SkoarFairy {

    var <name;
    var <minstrel;
    var <noat;
    var <impression;
    var magic;
    var <listy_stack;
    var <magic_stack;


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
        listy_stack = [];
        magic_stack = [];
    }

    get_top_listy {
        var n = listy_stack.size;
        if (n == 0) {
            ^nil;
        };
        ^listy_stack[n - 1];
    }

    set_top_listy {
        | x |
        var n = listy_stack.size;
        listy_stack[n - 1] = x;
    }

    push {
        magic_stack = magic_stack.add(magic);
        magic = nil;
        listy_stack = listy_stack.add([]);
        "$.push;".postln;

    }

    pop {
        magic = magic_stack.pop;
        this.impress(listy_stack.pop);
        "popped listy: ".post; impression.postln;
    }

    next_listy {
        var listy = this.get_top_listy;

        if (listy.notNil) {
            listy = listy.add(impression);
            this.set_top_listy(listy);
        };
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
            x = spell.();
            if (x.notNil) {
                this.impress(x);
            };
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
