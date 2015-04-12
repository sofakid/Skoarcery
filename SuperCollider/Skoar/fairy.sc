SkoarFairy {

    var <name;
    var <minstrel;
    var <noat;
    var <impression;
    var magic;
    var listy_stack;
    var magic_stack;
	var i_stack;
	var i;
	var compare_stack;
	var <l_value;

	var times_seen_stack;
	var times_seen;

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
        noat = nil;
        listy_stack = [];
        magic_stack = [];
		i_stack = [];
		i = 0;
		compare_stack = [];
		l_value = nil;

		times_seen_stack = [];
		times_seen = IdentityDictionary.new;

		this.impress(0);
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
        //"popped listy: ".post; impression.postln;
    }

    next_listy {
        var listy = this.get_top_listy;

        if (listy.notNil) {
            listy = listy.add(impression);
            this.set_top_listy(listy);
        };
    }
	
	push_i {
        i_stack = i_stack.add(i);
    }

    pop_i {
        i = i_stack.pop;
    }

	incr_i {
		i = i + 1;
	}

	push_times_seen {
        times_seen_stack = times_seen_stack.add(times_seen);
		times_seen = IdentityDictionary.new;
    }

    pop_times_seen {
        times_seen = times_seen_stack.pop;
    }

	how_many_times_have_you_seen {
		| thing |

		var times = times_seen[thing];

		if (times.isNil) {
			times_seen[thing] = 1;
			^1;
		};
		
		times = times + 1;
		times_seen[thing] = times;
		^times;	
	}

	push_compare {
        compare_stack = compare_stack.add(l_value);
		l_value = nil;
    }

    pop_compare {
        l_value = compare_stack.pop;
		"popped l_value:".post; l_value.postln;
    }

	compare_impress {
		| m |
		l_value = this.impression;

		if (l_value.isKindOf(SkoarpuscleFairy))	{
			// we want the impression now
			l_value = m.fairy.impression;
		};
		
		"l_value from impression: ".post; l_value.postln;
	}

	impress_i {
		^this.impress(i);
	}

    impress {
        | x |
        ("$:" ++ name ++ ".impression: " ++ x.asString).postln;

		if (x.isKindOf(SkoarpuscleFairy)) {
            ^impression;
        };

        if (x.isKindOf(SkoarpuscleDeref)) {
            x = x.lookup(minstrel);
        };

        if (x.isKindOf(Skoarpuscle)) {
            if (x.impressionable == false) {
				^impression; 
			};
			impression = x;
        } {
            impression = Skoarpuscle.wrap(x)
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
		"Flattening the fairy: ".post; x.postln;
        ^x;
    }

    skoar_msg {
        | msg, minstrel |
        msg_arr = msg.get_msg_arr(minstrel);

        "Fairy got msg: ".post; msg_arr.dump;

		if (msg_arr[0] == \i) {
			^minstrel.fairy.impress_i;
		};

        ^this;
    }

    //isNoatworthy { ^true; }

    //asNoat {
        // need a reference to the fairy
    //}
}
