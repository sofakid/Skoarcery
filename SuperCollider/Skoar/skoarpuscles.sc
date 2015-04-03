// Skoarpuscles are the closest thing we have to "types".
//
// They represent value types, as well as most things that
// can be spoken of as things, like a statement, boolean expression, etc.
//
Skoarpuscle {

    var <>val;
	var <>impressionable;
	var <>county;

    *new { | v | ^super.new.init(v); }
    init { 
		| v | 
		val = v;
		impressionable = true;
	}

    on_enter { | m, nav | }

    // override and implement .asNoat;
    isNoatworthy { ^false; }
    asNoat {SkoarError("asNoat called on " ++ this.class.asString ++ ", which is not noatworthy.").throw;}

	// override and implement .asCount;
    isCounty { ^false; }
    asCount {SkoarError("asCount called on " ++ this.class.asString ++ ", which is not county.").throw;}


    flatten { | m | ^val; }

    asString {
        var s = super.asString ++ ": " ++ val.asString;
        ^s;
    }

    skoar_msg {
        | msg, minstrel |
        var o = msg.get_msg_arr(minstrel);
        var ret = val.performMsg(o);

        ^Skoarpuscle.wrap(ret);
    }

    *wrap {
        | x |
        case {x.isNil} {
            "x nil".postln;
            ^SkoarpuscleCrap();

		} {x == false} {
            "x lies".postln;
            ^SkoarpuscleLies();

        } {x.isKindOf(Skoarpuscle)} {
            "already wrapped".postln;
            ^x;

        } {x.isKindOf(Skoarpion)} {
            "x skoarpion".postln;
            ^SkoarpuscleSkoarpion(x);

        } {x.isKindOf(Integer)} {
            debug("x int: " ++ x.asString);
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
            var a = Array.newClear(x.size);
            var i = -1;
            "x array".postln;
            x.do {
                | el |
                i = i + 1;
                a[i] = Skoarpuscle.wrap(el);
            };

            ^SkoarpuscleList(a);
        } {
            "x unknown: ".post; x.dump;
            ^SkoarpuscleUnknown(x);
        };

    }
}

SkoarpuscleUnknown : Skoarpuscle {

}

// we don't have null, null is crap.
// Is the value any good? no, it's crap.
// What did we get back? oh, crap.
SkoarpuscleCrap : Skoarpuscle {
    asString {"crap"}

    // these aren't doing nothing, they are returning this crap
    skoar_msg {}
    flatten {^nil}
}

SkoarpuscleInt : Skoarpuscle {

    isNoatworthy { ^true; }

    asNoat {
        ^SkoarNoat_Degree(val.asInteger);
    }

    isCounty { ^true; }

    asCount {
        ^val.asInteger;
    }

    flatten {
        | m |
        ^val.asInteger;
    }

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

}

SkoarpuscleFloat : Skoarpuscle {

    isNoatworthy { ^true; }

    asNoat {
        ^SkoarNoat_Degree(val.asFloat);
    }

    flatten {
        | m |
        ^val.asFloat;
    }

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }
}

SkoarpuscleFreq : Skoarpuscle {

    init {
        | lexeme |
        val = lexeme; // todo chop off Hz
    }

    isNoatworthy { ^false; } // todo

    asNoat {
        ^SkoarNoat_Freq(val.asFloat);
    }

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }
}


SkoarpuscleString : Skoarpuscle {

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }
}

SkoarpuscleSymbolName : Skoarpuscle {
}

SkoarpuscleSymbol : Skoarpuscle {

    on_enter {
        | m, nav |
        m.fairy.impress(this);
    }

    skoar_msg {
        | msg, minstrel |
        var o = msg.get_msg_arr(minstrel);
        var ret = val.performMsg(o);

        ^Skoarpuscle.wrap(ret);
    }
}


SkoarpuscleDeref : Skoarpuscle {

    var msg_arr;
    var <args;

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
        ^m.koar[val];
    }

    flatten {
        | m |
        ^this.lookup(m);
    }

    on_enter {
        | m, nav |
		if (args.isNil) {
			this.do_deref(m, nav);
		} {
			args.on_enter(m, nav);
		};
    }

	on_exit {
		| m, nav |
		this.do_deref(m, nav);
	}

	do_deref {
		| m, nav |
		var x = this.lookup(m);

		"deref:on_enter: SYMBOL LOOKEDUP : ".post; val.post; " ".post; x.postln;
		x = Skoarpuscle.wrap(x);

		if (x.isKindOf(SkoarpuscleSkoarpion)) {
			var impression = m.fairy.impression;
			"passing args: ".post; impression.class.asString.postln;													
			m.koar.do_skoarpion(x.val, m, nav, msg_arr, impression);
		} {
			m.fairy.impress(x);
		};
	}

    skoar_msg {
        | msg, minstrel |
        var ret = val;
        var x = this.lookup(minstrel);

        "deref:skoar_msg: SYMBOL LOOKEDUP : ".post; val.post; " ".post; x.postln;
        msg_arr = msg.get_msg_arr(minstrel);

        if (x.isKindOf(SkoarpuscleSkoarpion)) {
            ^this;
        };

        // we don't recognise that name, did they mean a SuperCollider class?
        if (x.isNil) {
            x = val.asClass;
        };

        if (x.notNil) {
            if (x.isKindOf(SkoarpuscleString)) {
                x = x.val;
            };
            ret = x.performMsg(msg_arr);
        };

        ^Skoarpuscle.wrap(ret);
    }

}

SkoarpuscleMathOp : Skoarpuscle {
    var f;

    init {
        | toke |
        val = toke.lexeme;

        f = switch (val)
            {"+"}  {{
                | minstrel, a, b |
                Skoar.ops.add(minstrel, a, b);
            }}

            {"x"}  {{
                | minstrel, a, b |
                Skoar.ops.multiply(minstrel, a, b);
            }}

            {"-"}  {{
                | minstrel, a, b |
                // todo minstrel.skoar.ops.sub(minstrel, a, b);
            }};
    }

    calculate {
        | m, nav, left, right |

        // the result is impressed by the operation
        f.(m, left, right);
    }

}

SkoarpuscleLies : Skoarpuscle {
	asString {"lies"}

    skoar_msg {}
    flatten {^false}
}

SkoarpuscleBooleanOp : Skoarpuscle {

    var f;
	var <noad;

    *new {
        | n, toke |
        ^super.new.init_two(n, toke);
    }

    init_two {
        | n, toke |
        val = toke.lexeme;
		noad = n;

        // ==|!=|<=|>=|in|nin|and|or|xor
        f = switch (val)
            {"=="}  {{
                | a, b |
                a == b
            }}
            {"!="}  {{
                | a, b |
                a != b
            }}
            {"<="}  {{
                | a, b |
                a <= b
            }}
            {"<"}  {{
                | a, b |
                a < b
            }}
            {">="}  {{
                | a, b |
                a >= b
            }}
            {">"}  {{
                | a, b |
                a > b
            }}
            {"and"} {{
                | a, b |
                a and: b
            }}
            {"or"}  {{
                | a, b |
                a or: b
            }}
            {"xor"} {{
                | a, b |
                a xor: b
            }};

    }

    compare {
        | m, nav, a, b |
     
        if (a.isKindOf(Skoarpuscle)) {
            debug(a);
            a = a.flatten(m);
        };

        if (b.isKindOf(Skoarpuscle)) {
            debug(b);
            b = b.flatten(m);
        };

        debug("{? " ++ a.asString ++ " " ++ val ++ " " ++ b.asString ++ " ?}");

		a !? b !? {^f.(a, b)};
		
        ^false
    }

	on_enter {
		| m, nav |
		m.fairy.cast_arcane_magic;
		m.fairy.compare_impress(m);
	}

}

SkoarpuscleBoolean : Skoarpuscle {

    var <op;

    init {
        | noad |
        op = noad.children[0].next_skoarpuscle;
    }

	on_enter {
		| m, nav |
		m.fairy.push_compare;
	}

    evaluate {
        | m, nav, a, b |
		("slrp " ++ a.asString ++ " imp: " ++ b.asString).postln;
        ^op.compare(m, nav, a, b);
    }

}


SkoarpuscleConditional : Skoarpuscle {

    var ifs;

    *new {
        | skoar, noad |
        ^super.new.init_two(skoar, noad);
    }

    init_two {
        | skoar, noad |
		var i = 0;
        ifs = [];

        noad.collect(\cond_if).do {
            | x |
            var condition = Skoarpion.new_from_subtree(skoar, x.children[0]);
            var if_body;
            var else_body;

            if_body = Skoarpion.new_from_subtree(skoar, x.children[2]);

            else_body = x.children[4];
            if (else_body.notNil) {
                else_body = Skoarpion.new_from_subtree(skoar, else_body);
            };

            ifs = ifs.add([condition, if_body, else_body]);
        };
		noad.children = #[];

    }

    on_enter {
        | m, nav |

        ifs.do {
            | x |
            var condition = x[0];
            var if_body = x[1];
            var else_body = x[2];
			var impression;

			m.koar.do_skoarpion(condition,
                m, nav, [\inline], nil
            );

			impression = m.fairy.impression;
            m.koar.do_skoarpion(
                if (impression.isKindOf(SkoarpuscleLies) == false) {if_body} {else_body},
                m, nav, [\inline], nil
            );
        };
    }

}

SkoarpuscleSkoarpion : Skoarpuscle {

    var msg_arr;

    skoar_msg {
        | msg, minstrel |
        msg_arr = msg.get_msg_arr(minstrel);
        ^this;
    }

    on_enter {
        | m, nav |
        if (val.name.notNil) {
            m.koar[val.name] = this;
        };

        if (msg_arr.notNil) {
			m.fairy.impression.postln;
			msg_arr.postln;

            m.koar.do_skoarpion(val, m, nav, msg_arr, m.fairy.impression);
        };
    }

}

SkoarpuscleTimes : Skoarpuscle {

	on_enter {
        | m, nav |
		var desired_times = m.fairy.impression;
		
		if (desired_times.isCounty) {
			var times_seen = m.fairy.how_many_times_have_you_seen(this);
			desired_times = desired_times.asCount;

			("desired_times: " ++ desired_times.asString ++ "\n times seen: " ++ times_seen.asString).postln;
			m.fairy.impress( (desired_times > times_seen) );
		};
		 
    }

}

SkoarpuscleLoop : Skoarpuscle {

    var <condition;
    var <body;
    var <each;

    *new {
        | skoar, noad |
        ^super.new.init_two(skoar, noad);
    }

    init_two {
        | skoar, noad |

        noad.collect(\loop_condition).do {
            | x |
			if (x.children.size > 0) {
				condition = Skoarpion.new_from_subtree(skoar, x);
			};
        };

        noad.collect(\loop_body).do {
            | x |
            body = Skoarpion.new_from_subtree(skoar, x);
        };

        each = nil;
    }

    on_enter {
        | m, nav |
        
		m.fairy.push_i;

        block {
            | break |
            while {true} {
                var f = {
                    | element |

                    // this is how we foreach
                    if (element.isKindOf(Skoarpuscle)) {
                        m.fairy.impress(element);
                    };

                    m.koar.do_skoarpion(body, m, nav, [\inline], m.fairy.impression);
                    m.fairy.incr_i;

					if (condition.notNil) {
						m.koar.do_skoarpion(condition, m, nav, [\inline]);
                        
						if (m.fairy.impression.isKindOf(SkoarpuscleLies)) {
							break.();
						};
                    };
                };

                if (each.isNil) {
                    f.(nil);
                } {
                    debug("each: " ++ each.asString);
                    each.val.do {
                        | element |
                        debug("each:el: " ++ element.asString);
                        f.(element);
                    };
                };
				
				"zorp: condition: ".post; condition.postln;
                if (condition.isNil) {
                    break.();
                };
            };
        };

		m.fairy.pop_i;
    }

    // when we send a loop as a message, the receiver
    // goes into _each_ and _this_ becomes the new receiver.
    foreach {
        | listy |
        each = listy;
        ^this;
    }
}


SkoarpuscleLoopMsg : Skoarpuscle {
}

SkoarpuscleExprEnd : Skoarpuscle {

    on_enter {
        | m, nav |
        m.fairy.cast_arcane_magic;
    }
}

SkoarpuscleListSep : Skoarpuscle {

    on_enter {
        | m, nav |
        m.fairy.next_listy;
    }
}

SkoarpuscleListEnd : Skoarpuscle {

    on_enter {
        | m, nav |
        m.fairy.next_listy;
        m.fairy.pop;
    }
}

SkoarpuscleList : Skoarpuscle {

    init {
        | x=#[] |
        val = x;
    }

    on_enter {
        | m, nav |
        m.fairy.push;
    }

    isNoatworthy {

        val.do {
            | x |
            if (x.isNoatworthy == false) {
                ^false;
            };
        };

        ^true;
    }

    asNoat {

        var n = val.size;
        var noats = Array.newClear(n);
        var i = -1;

        val.do {
            | x |
            i = i + 1;
            noats[i] = x.asNoat;
        };

        ^SkoarNoat_DegreeList(noats);
    }

    skoar_msg {
        | msg, minstrel |
        var o = msg.get_msg_arr(minstrel);
        var name = msg.val;
        var ret;

        case {name == \next} {
            ret = val.performMsg(o);
        } {name == \last} {
            ret = val.performMsg(o);
        } {
            ret = val.performMsg(o);
        }

        ^Skoarpuscle.wrap(ret);
    }

}

SkoarpuscleArgs : SkoarpuscleList {

}

// {! f<a,b,c> !! '[\a,\b,\c] is the ArgsSpec' !}
SkoarpuscleArgsSpec : Skoarpuscle {

    init {
        | noad |
        val = [];
        noad.collect_skoarpuscles.do {
            | x |
            if (x.isKindOf(SkoarpuscleSymbolName)) {
                val = val.add(x.val);
            };
        };
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

    on_enter {
        | m, nav |
        //val.postln;
    }

    get_msg_arr {
        | m |
        var x = Array.newClear(args.size + 1);
        var i = 0;

        x[0] = val;
        args.flatten(m).do {
            | y |
            i = i + 1;
            x[i] = y;
        };
        ^x;
    }
}

SkoarpuscleMsgName : Skoarpuscle {
}


// -----------------------------
// musical keywords skoarpuscles
// -----------------------------

SkoarpuscleBars : Skoarpuscle {

    var <>noad; // is set in \markers skoarmantics
    var <pre_repeat;
    var <post_repeat;

    init {
        | toke |

        val = toke.lexeme;
        pre_repeat = val.beginsWith(":");
        post_repeat = val.endsWith(":");
    }

    on_enter {
        | m, nav |

        // :|
        if (pre_repeat == true) {
            var burned = m.koar.state_at(\colons_burned);

            if (burned.falseAt(noad)) {
                burned[noad] = true;
                nav.(\nav_colon);
            };

        };

        // |:
        if (post_repeat == true) {
            m.koar.state_put(\colon_seen, noad);
        };
    }
}

SkoarpuscleFine : Skoarpuscle {

    on_enter {
        | m, nav |
        if (m.koar.state_at(\al_fine) == true) {
            debug("fine");
            nav.(\nav_fine);
        };
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

        noad = nod;

        // ,segno`label`
        if (n > 8) {
            s[6..n-2].asSymbol;
        } {
            \segno
        };
        val = s[1..n].asSymbol;
    }

    on_enter {
        | m, nav |
        m.koar.state_put(\segno_seen, noad);
    }

}

SkoarpuscleGoto : Skoarpuscle {

    var nav_cmd;
    var al_fine;

    init {
        | noad |

        var toke = noad.children[0].next_toke;
        var al_x = noad.children[1];

        nav_cmd = case {toke.isKindOf(Toke_DaCapo)} {\nav_da_capo}
                       {toke.isKindOf(Toke_DalSegno)} {\nav_segno};

        al_fine = false;
        if (al_x.notNil) {
            if (al_x.next_toke.isKindOf(Toke_AlFine)) {
                al_fine = true;
            };
        };
    }

    on_enter {
        | m, nav |
        if (al_fine == true) {
            m.koar.state_put(\al_fine, true);
        };

        m.reset_colons;
        //"goto:".post; nav_cmd.postln;
        nav.(nav_cmd);
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

    on_enter {
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

    on_enter {
        | m, nav |
        m.koar[\amp] = this.amp;
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
            n
        };

        val = case {toke.isKindOf(Toke_OctaveShift)} {f.()}
                   {toke.isKindOf(Toke_OttavaA)}       { 1}
                   {toke.isKindOf(Toke_OttavaB)}       {-1}
                   {toke.isKindOf(Toke_QuindicesimaA)} { 2}
                   {toke.isKindOf(Toke_QuindicesimaB)} {-2};
    }

    on_enter {
        | m, nav |
        var octave = m.koar[\octave] ?? 5;
        m.koar[\octave] = octave + val;
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

SkoarpuscleRep : Skoarpuscle {

    init {
        | toke |
        val = toke.lexeme.size;
    }

}
