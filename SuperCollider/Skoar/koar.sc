
SkoarKoar {
    var   skoar;        // global skoar
    var  <skoarboard;   //
    var  <stack;        // stack of vars visible to the skoar code
    var  <state_stack;  // stack of vars invisible to the skoar code

    var  <name;         // name of voice as Symbol

    *new {
        | skr, nom |
        ^super.new.init(skr, nom);
    }

    init {
        | skr, nom |

        skoar = skr;
        name = nom;

        stack = List[];
        state_stack = List[];
        skoarboard = ();
        stack.add(skoarboard);

    }

    // ---------------------
    // State and scope stuff
    // ---------------------
    put {
        | k, v |
        this.top_args[k] = v;
    }

    at {
        | k |
        var out = nil;

        stack.reverseDo {
            | skrb |
            out = skrb[k];
            if (out.notNil) {
                ^out;
            };
        };

        ^out;
    }

    state_put {
        | k, v |
        state_stack[state_stack.size - 1].put(k, v);
    }

    state_at {
        | k |
        var out = nil;

        state_stack.reverseDo {
            | skrb |
            out = skrb[k];
            if (out.notNil) {
                ^out;
            };
        };

        ^out;
    }

    // constructs the event that will be played by SC
    event {
        | minstrel |
        var e = Event.new;

        stack.do {
            | skrb |
            // native function constructs the event quickly
            e = skrb.transformEvent(e);

            // but we need to change stuff
            e.keysValuesChange {
                | key, value |

                case {value.isKindOf(SkoarpuscleSkoarpion)} {
                    // we don't need to pass skoarpions to SC
                    nil

                } {value.isKindOf(Skoarpuscle)} {
                    // we want values, not skoarpuscles
                    value.flatten(minstrel)

                } {
                    // perfect
                    value
                }
            };
        }

        ^e
    }

    set_args {
        | minstrel, arg_spec, args |
        var i = 0;
        var vars = stack[stack.size - 1];

		"ARG_SPEC: ".post; arg_spec.dump;
		"ARGS    : ".post; args.dump;

        if (arg_spec.isKindOf(SkoarpuscleArgSpec)) {
            var passed_args, n;

            passed_args = if (args.isKindOf(SkoarpuscleList)) { args.val } { [] };
            n = passed_args.size;

            // foreach arg name defined, set the value from args
            arg_spec.val.do {
                | k |
				if (k.isKindOf(SkoarpuscleSymbolName)) {
					k = k.val;
				};
                ("k: " ++ k).postln;
                vars[k] = if (i < n) {
                    passed_args[i]
                } {
                    // this defaults to passing 0 when not enough args are sent.
                    SkoarpuscleInt(0)
                };
                i = i + 1;
            };
        };
    }

    top_args {
        ^stack[stack.size - 1];
    }

    push_state {
        var state = IdentityDictionary.new;
        var projections = IdentityDictionary.new;

        state_stack = state_stack.add(state);

        state[\colons_burned] = Dictionary.new;
        state[\al_fine] = false;
        state[\projections] = projections;

        stack = stack.add(IdentityDictionary.new);

		// this is where i want this, but i don't have a fairy here.
		// fairy.push_times_seen;

    }

    pop_state {
        stack.pop;
        state_stack.pop;
		// this is where i want this, but i don't have a fairy here.
		// fairy.pop_times_seen;
    }

    do_skoarpion {
        | skoarpion, minstrel, up_nav, msg_arr, args |

        var subtree;
        var projection;
        var projections;
        var msg_name;
        var inlined;

        if (skoarpion.isKindOf(Skoarpion) == false) {
            "This isn't a skoarpion: ".post; skoarpion.postln;
            ^nil;
        };

        // default behaviour (when unmessaged)
        if (msg_arr.isNil) {
            msg_arr = [\block];
        };

        msg_name = msg_arr[0];

        inlined = (msg_name == \inline);
        if (inlined == false) {
            this.push_state;
			minstrel.fairy.push_times_seen;
        };

        // load arg values into their names
        this.set_args(minstrel, skoarpion.arg_spec, args);

        projections = this.state_at(\projections);
        if (skoarpion.name.notNil) {
            projection = projections[skoarpion.name];

            // start a new one if we haven't seen it
            if (projection.isNil) {
                projection = skoarpion.projection(name);
                projections[skoarpion.name] = projection;
            };
        } {
            projection = skoarpion.projection;
        };

        subtree = projection.performMsg(msg_arr);

        this.nav_loop(subtree, projection, minstrel, up_nav, inlined);

        if (inlined == false) {
            this.pop_state;
			minstrel.fairy.pop_times_seen;
        };
    }

    nav_loop {
        | dst, projection, minstrel, up_nav, inlined |

        var nav_result;
        var running = true;
        var subtree = dst;

        while {running} {

            // you can think of this like a try/catch for nav signals
            nav_result = block {
                | nav |

                // map dst to an address relative to the projection
                var here = projection.map_dst(dst);

                subtree.inorder_from_here(
                    here,

                    {   | x |
                        x.enter_noad(minstrel, nav); });

                // our metaphorical throws look like this,
                // you'll also find them in the navigational
                // skoarpuscles' on_enters. (segno, bars, etc..)
				//
				// that is, once nav.(\something) is called, execution is aborted and continues
				// at the top of this this block, with block returning the \something into nav_result.
                nav.(\nav_done);
            };

            // here's our metaphorical catch
            switch (nav_result)

                {\nav_done} {
                    running = false;
                }

                {\nav_fine} {
                    this.bubble_up_nav(minstrel, up_nav, \nav_fine, inlined);
                }

                {\nav_coda} {

                }

                {\nav_da_capo} {
                    this.bubble_up_nav(minstrel, up_nav, \nav_da_capo, inlined);
                }

                {\nav_segno} {
                    dst = this.state_at(\segno_seen);

                    if ((dst !? (_.skoap)) != subtree.skoap) {
                        this.bubble_up_nav(minstrel, up_nav, \nav_segno, inlined);
                    };
                }

                {\nav_colon} {
                    dst = this.state_at(\colon_seen);

                    if ((dst !? (_.skoap)) != subtree.skoap) {
                        this.bubble_up_nav(minstrel, up_nav, \nav_colon, inlined);
                    };
                };

        };
    }

    bubble_up_nav {
        | minstrel, nav, cmd, inlined |

        // the nav command will abort do_skoarpion,
        // we have to clean up here.
        if (inlined == false) {
            this.pop_state;
			minstrel.fairy.pop_times_seen;
        };

        // metaphorically rethrowing to a higher level
        nav.(cmd);
    }
}

