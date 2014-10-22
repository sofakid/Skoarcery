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
                noad.val = noad.next_val;
                noad.children = [];
                noad.n = 0;
                noad.toke = nil;
                noad.performer = {
                    | m, nav |
                    noad.val.performer(m, nav);
                };
            },

            "listy" -> {
                | skoar, noad |
                noad.val = SkoarpuscleArray(noad.collect_values);

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

                x = noad.next_val;
                if (x != nil) {
                    noad.val = x;
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
                args = SkoarpuscleArray(noad.collect_values);

                noad.val = SkoarpuscleMsg(x);
                noad.val.args = args;

                clean.();

            },

            "stmt" -> {
                | skoar, noad |
                var x = nil;
                var y = nil;


                y = noad.children[1];

                // assignment
                if (y != nil) {
                    if (y.name == "assignment") {

                        noad.performer = {
                            | m, nav |
                            var res;

                            x = noad.children[0];

"fee".postln;
                            res = x.evaluate.();

"foh".post; res.dump;
                            y.setter.(res, m.voice);

"fum".postln;
                        };

                    };

                } { // just a skoaroid
                    noad.performer = {
                        | m, nav |

                        x = noad.children[0];
    ("FEE").postln;
                        x.evaluate.().performer(m, nav);
    "FUM".postln;
                    };
                };

            },

            "skoaroid" -> {
                | skoar, noad |

                var kids = List[];

                // strip out the operators
                noad.children.do {
                    | x |
                    if (x.toke.isKindOf(Toke_MsgOp) == false) {
                        kids.add(x);
                    };
                };

                noad.children = kids.asArray;
                kids = noad.children;
                noad.n = kids.size;

                // evaluate messages, returning the result
                noad.evaluate = {

                    var result = kids[0].next_val;

                    if (result != nil) {
                        kids.do {
                            | y |
                            var x = y.val;
                            if (x.isKindOf(SkoarpuscleMsg)) {
                                result = result.skoar_msg(x);
                            };

                        };

                        "result: ".post; result.val.postln;
                        result
                    } {
                        var x = noad.next_val;

                        if (x != nil) {
                            x
                        } {
                            "no evaluation.".postln; noad.dump;
                            noad
                        }
                    }
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

                // we prepare the destination here (noad.f), we'll setup the write in skoaroid

                noad.setter = switch (op)
                    {"+>"} {{
                        | x, voice |
                        voice.assign_incr(x, y);
                    }}

                    {"->"} {{
                        | x, voice |
                        voice.assign_decr(x, y);
                    }}

                    {"=>"} {{
                        | x, voice |
                        voice.assign_set(x, y);
                    }};

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