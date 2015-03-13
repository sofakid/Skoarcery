
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

    event {
        | minstrel |
        var e = Event.new;

        stack.do {
            | skrb |

            e = skrb.transformEvent(e);

            // flatten
            e.keysValuesChange {
                | key, value |

                ("::" ++ key.asString ++ ": " ++ value.asString).postln;
                case {value.isKindOf(SkoarpuscleSkoarpion)} {
                    nil

                } {value.isKindOf(Skoarpuscle)} {
                    value.flatten(minstrel)

                } {
                    value
                }
            };

            e.postln;
        }

        ^e
    }

    set_args {
        | minstrel, args_def, args |
        var i = 0;
        var vars = stack[stack.size - 1];

        if (args_def.isKindOf(SkoarpuscleArgsSpec)) {
            var passed_args, n;

            passed_args = if (args.isNil) { [] } { args };
            n = passed_args.size;

            // foreach arg name defined, set the value from args
            args_def.val.do {
                | k |
                ("k: " ++ k).postln;
                vars[k] = if (i < n) {
                    args[i]
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
    }

    pop_state {
        stack.pop;
        state_stack.pop;
    }

    do_skoarpion {
        | skoarpion, minstrel, up_nav, msg_arr, skrp_args, stinger |

        var subtree;
        var projection;
        var projections;
        var msg_name;
        var inlined;
        var args;

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
        };

        // load arg values into their names
        this.set_args(minstrel, skoarpion.args, skrp_args);

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

        this.nav_loop(subtree, projection, minstrel, up_nav, stinger, inlined);

        if (inlined == false) {
            this.pop_state;
        };
    }

    nav_loop {
        | dst, projection, minstrel, up_nav, stinger, inlined |

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
                        x.enter_noad(minstrel, nav, stinger); },

                    stinger);

                // our metaphorical throws look like this,
                // you'll also find them in the navigational
                // skoarpuscles' on_enters. (segno, bars, etc..)
                nav.(\nav_done);
            };

            // here's our metaphorical catch
            switch (nav_result)

                {\nav_done} {
                    running = false;
                }

                {\nav_fine} {
                    this.bubble_up_nav(up_nav, \nav_fine, inlined);
                }

                {\nav_coda} {

                }

                {\nav_da_capo} {
                    this.bubble_up_nav(up_nav, \nav_da_capo, inlined);
                }

                {\nav_segno} {
                    dst = this.state_at(\segno_seen);

                    if ((dst !? (_.skoap)) != subtree.skoap) {
                        this.bubble_up_nav(up_nav, \nav_segno, inlined);
                    };
                }

                {\nav_colon} {
                    dst = this.state_at(\colon_seen);

                    if ((dst !? (_.skoap)) != subtree.skoap) {
                        this.bubble_up_nav(up_nav, \nav_colon, inlined);
                    };
                };

        };
    }

    bubble_up_nav {
        | nav, cmd, inlined |

        // the nav command will abort do_skoarpion,
        // we have to clean up here.
        if (inlined == false) {
            this.pop_state;
        };

        // metaphorically rethrowing to a higher level
        nav.(cmd);
    }
}

