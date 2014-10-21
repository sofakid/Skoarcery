// ============
// Skoarmantics
// ============

/*

This code is applied during the decoration stage of compiling the skoar tree.

For stuff to happen during performing the tree, we set handlers here.

We also shrink the tree, drop some punctuation noads;
   when you see replace_children, that's what's going on. 

absorb_toke assumes the only child is a toke, puts it in 
   noad.toke and removes the child. (and returns the toke)

We went depth first and run the code on the way back,
   so children are processed first.

*/
Skoarmantics {

    *new {

        var dict = Dictionary[

            "skoarpion" -> {
                | skoar, noad |
                var n = 0;
                var label = nil;
                var m = 0;

                n = noad.n;
                label = noad.children[1].toke.val;
                noad.label = label;
                noad.branch = nil;

                // we save it here, the skoarpion will be removed from the tree by branch.
                skoar.skoarpions[label] = Skoarpion(noad);

            },

            "skoarpion_line" -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                // drop the newline
                noad.children.pop;
                noad.n = n - 1;

            },

            "branch" -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                noad.branch = noad;

                if (x.name == "skoarpion") {
                    noad.children = [];
                    noad.n = 0;
                } {
                    x = x.toke;
                    if (x.isKindOf(Toke_Voice)) {
                        noad.toke = x;
                        noad.voice = skoar.get_voice(x.val);
                    };
                    // drop the newline
                    noad.children.pop;
                    noad.n = n - 1;
                };

            },

            "beat" -> {
                | skoar, noad |
                var t = noad.absorb_toke;

                noad.beat = t;
                noad.is_beat = true;
                noad.is_rest = t.is_rest;
            },

            "listy" -> {
                | skoar, noad |
                noad.val = SkoarValueArray(noad.collect_values);

                noad.n = 0;
                noad.children = [];
                noad.toke = nil;

            },

            "musical_keyword_misc" -> {
                | skoar, noad |

                var toke = noad.absorb_toke;

                if (toke.isKindOf(Toke_Carrot)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };

            },

            "ottavas" -> {
                | skoar, noad |

                var toke;

                toke = noad.absorb_toke;

                if (toke.isKindOf(Toke_OctaveShift)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(toke.val);
                    };
                };

                if (toke.isKindOf(Toke_OttavaA)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(1);
                    };
                };

                if (toke.isKindOf(Toke_OttavaB)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(-1);
                    };
                };

                if (toke.isKindOf(Toke_QuindicesimaA)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(1);
                    };
                };

                if (toke.isKindOf(Toke_QuindicesimaB)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(-1);
                    };
                };

            },

            "assignment" -> {
                | skoar, noad |

                var op = nil;

                // the settable
                var y = nil;
                var y_toke = nil;

                op = noad.children[0].toke.lexeme;
                y = noad.children[1];

                // we prepare the destination here (noad.setter), we'll setup the write in skoaroid

                noad.setter = switch (op)
                    {"+>"} {{
                        | x, voice |
                        var x_val = x.next_val;
                        voice.assign_incr(x_val, y);
                    }}

                    {"->"} {{
                        | x, voice |
                        var x_val = x.next_val;
                        voice.assign_decr(x_val, y);
                    }}

                    {"=>"} {{
                        | x, voice |
                        var x_val = x.next_val;
                        voice.assign_set(x_val, y);
                    }};

            },

            "skoaroid" -> {
                | skoar, noad |

                var f = nil;
                var x = nil;
                var y = nil;

                if (noad.children.size > 1) {
                    x = noad.children[0];
                    y = noad.children[1];

                    if (y.name == "assignment") {

                        x.performer = {};

                        f = y.setter;

                        noad.performer = {
                            | m |
                            f.(x, m.voice);
                        };

                    };
                };

            },

            "cthulhu" -> {
                | skoar, noad |
                noad.name = "^^(;,;)^^";
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            "dynamic" -> {
                | skoar, noad |
                var toke = noad.absorb_toke;

                noad.performer = {
                    | m |
                    m.voice.dynamic(toke);
                };
            },

            "dal_goto" -> {
                | skoar, noad |

                var toke = noad.children[0].toke;
                var al_x = noad.children[1].toke;
                var al_fine = false;

                if (al_x != nil && al_x.isKindOf(Toke_AlFine)) {
                    al_fine = true;
                };

                if (toke.isKindOf(Toke_DaCapo)) {
                    noad.performer = {
                        | m, nav |

                        if (al_fine) {
                            m.al_fine = true;
                        };

                        nav.(\nav_da_capo);
                    };
                };

                if (toke.isKindOf(Toke_DalSegno)) {
                    noad.performer = {
                        | m, nav |

                        if (al_fine) {
                            m.al_fine = true;
                        };

                        m.reset_colons;
                        nav.(\nav_segno);
                    };
                };

            },

            "al_x" -> {
                | skoar, noad |

                noad.absorb_toke;
            },

            "marker" -> {
                | skoar, noad |

                var toke = noad.absorb_toke;

                if (toke != nil) {

                    if (toke.isKindOf(Toke_Bars)) {

                        if (toke.pre_repeat) {
                            noad.performer = {
                                | m, nav |

                                if (m.colons_burned.falseAt(noad)) {
                                    m.colons_burned[noad] = true;
                                    nav.(\nav_jump);
                                };

                                if (toke.post_repeat) {
                                    m.colon_seen = noad;
                                };

                            };
                        } {
                            if (toke.post_repeat) {
                                noad.performer = {
                                    | m |
                                    m.colon_seen = noad;
                                };
                            };
                        };

                    };

                    if (toke.isKindOf(Toke_Segno)) {
                        noad.performer = {
                            | m |
                            m.segno_seen = noad;
                        };
                    };

                    if (toke.isKindOf(Toke_Fine)) {
                        noad.performer = {
                            | m, nav |
                            if (m.al_fine) {
                                nav.(\nav_fine);
                            };
                        };
                    };
                };
            },


            "pedally" -> {
                | skoar, noad |

                if (noad.toke.isKindOf(Toke_PedalUp)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.pedal_up;
                    };
                };

                if (noad.toke.isKindOf(Toke_PedalDown)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.pedal_down;
                    };
                };

            },


            "nouny" -> {
                | skoar, noad |

                var x = nil;
                var clean = {
                    noad.toke = nil;
                    noad.children = [];
                    noad.n = 0;
                };

                x = noad.children[0].toke;
                case {x.isKindOf(Toke_SkoarpionRef)} {
                    noad.performer = {
                        | m, nav |
                        m.gosub(x.val, nav);
                    };
                } {x.isKindOf(Toke_Int)} {
                    noad.val = SkoarValueInt(x.val);
                    clean.();

                } {x.isKindOf(Toke_Float)} {
                    noad.val = SkoarValueFloat(x.val);
                    clean.();

                } {x.isKindOf(Toke_NamedNoat)} {
                    noad.val = SkoarValueNoat(x);
                    clean.();

                } {x.isKindOf(Toke_Choard)} {
                    noad.val = SkoarValueChoard(x);
                    clean.();

                } {x.isKindOf(Toke_Symbol)} {
                    noad.val = SkoarValueSymbol(x.val);
                    clean.();

                } {x.isKindOf(Toke_String)} {
                    noad.val = SkoarValueString(x.val);
                    clean.();
                };

            },

            "msg" -> {
                | skoar, noad |

                var x = nil;
                var args = nil;

                var clean = {
                    noad.toke = nil;
                    noad.children = [];
                    noad.n = 0;
                };

                x = noad.children[0].toke;
                args = SkoarValueArray(noad.collect_values);

                noad.val = SkoarValueMsg(x);
                noad.val.args = args;

                clean.();

            },

            "stmt" -> {
                | skoar, noad |

                var kids = List[];
                var result;

                // strip out the operators
                noad.children.do {
                    | x |
                    if (x.toke.isKindOf(Toke_MsgOp) == false) {
                        kids.add(x);
                    };
                };

                noad.children = kids;
                noad.n = kids.size;

                noad.performer = {
                    | m, nav |
                    result = kids[0].next_val;

                    for (1, noad.n-1, {
                        | i |
                        var x = kids[i].val;
                        if (x.isKindOf(SkoarValueMsg)) {
                            result = result.skoar_msg(x);
                        };
                    });

                    result.performer(m, nav);
                };


            },

            "boolean" -> {
                | skoar, noad |
            },

            "coda" -> {
                | skoar, noad |
            }

        ];
        ^dict;
    }

}