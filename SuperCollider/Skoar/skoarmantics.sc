// ============
// Skoarmantics
// ============

/*

This code is applied during the decoration stage of compiling the skoar tree.

For stuff to happen during performing the tree, we set handlers here.

We also shrink the tree, drop some punctuation noads.

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
                var x, k;
                x = Skoarpion(noad);

                // we save it here, the skoarpion will be removed from the tree by branch.
                //
                // this comment may be outdated
                k = x.name;
                if (k != nil) {
                    skoar.skoarpions[k] = x;
                };
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

                if (x.isKindOf(SkoarNoad)) {
                    if (x.name == "skoarpion") {
                        noad.children = [];
                        noad.n = 0;
                    };
                } {
                    if (x.isKindOf(Toke_Voice)) {
                        noad.toke = x;
                        noad.voice = skoar.get_voice(x.val);
                    };
                };

            },

            "beat" -> {
                | skoar, noad |
                noad.skoarpuscle = noad.next_skoarpuscle;
                noad.children = [];
                noad.n = 0;
                noad.toke = nil;
                noad.performer = {
                    | m, nav |
                    noad.skoarpuscle.performer(m, nav);
                };
            },

            "listy" -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArray(noad.collect_skoarpuscles);

                noad.n = 0;
                noad.children = [];
                noad.toke = nil;

            },

            "musical_keyword_misc" -> {
                | skoar, noad |

                var toke = noad.toke;

                if (toke.isKindOf(Toke_Carrot)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };

            },

            "ottavas" -> {
                | skoar, noad |

                var toke;

                toke = noad.toke;

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
                        m.voice.octave_shift(2);
                    };
                };

                if (toke.isKindOf(Toke_QuindicesimaB)) {
                    noad.performer = {
                        | m, nav |
                        m.voice.octave_shift(-2);
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
                var toke = noad.toke;

                noad.performer = {
                    | m |
                    m.voice.dynamic(toke);
                };
            },

            "dal_goto" -> {
                | skoar, noad |

                var toke = noad.children[0];
                var al_x = noad.children[1];
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

            "marker" -> {
                | skoar, noad |

                var toke = noad.children[0];

                noad.performer = case {toke.isKindOf(Toke_Bars)} {
                    case {toke.pre_repeat == true} {{
                            | m, nav |

                            if (m.colons_burned.falseAt(noad)) {
                                m.colons_burned[noad] = true;
                                nav.(\nav_jump);
                            };

                            if (toke.post_repeat) {
                                m.colon_seen = noad;
                            };

                    }} {toke.post_repeat == true} {{
                        | m, nav |
                        m.colon_seen = noad;
                    }} {
"food".postln;
                        nil
                    }

                } {toke.isKindOf(Toke_Segno)} {{
                    | m |
                    m.segno_seen = noad;

                }} {toke.isKindOf(Toke_Fine)} {{
                    | m, nav |
                    if (m.al_fine) {
                        nav.(\nav_fine);
                    };
                }} {
                    nil
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

                x = noad.next_skoarpuscle;
                if (x != nil) {
                    noad.skoarpuscle = x;
                    clean.();
                };

            },

            // seq_ref*         : SeqRef MsgNameWithArgs listy_suffix
            //                  | SeqRef MsgName
            "seq_ref" -> {
                | skoar, noad |

                var x;
                var args;
                var msg_name;

                var clean = {
                    noad.toke = nil;
                    noad.children = [];
                    noad.n = 0;
                };

                msg_name = noad.children[1].toke.val;
msg_name.postln;
                if (noad.children.size > 2) {
"mizzle".postln;
                    args = SkoarpuscleArgs(noad.children[2].collect_skoarpuscles);
                };
"hizzle ".post; msg_name.dump; args.dump;
                noad.skoarpuscle = SkoarpuscleSeqRef(msg_name, args);
"dizzle".postln;
                clean.();

            },

            "args" -> {
                | skoar, noad |

                var clean = {
                    noad.toke = nil;
                    noad.children = [];
                    noad.n = 0;
                };

                noad.skoarpuscle = SkoarpuscleArgs(noad.collect_skoarpuscles);
                clean.();

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

                x = noad.children[0].toke.val;
                args = SkoarpuscleArray(noad.collect_skoarpuscles);
                noad.skoarpuscle = SkoarpuscleMsg(x, args);

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
                            res = x.evaluate.(m);

"foh".post; res.dump;
                            y.setter.(res, m.voice);

"fum".postln;
                        };

                    };

                } { // just a skoaroid
                    noad.performer = {
                        | m, nav |

                        x = noad.children[0];
 "FEE".postln;
                        x = x.evaluate.(m);
 "FAI FOH ".post; x.postln;

                        x.performer(m, nav);
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
                    | minstrel |
                    var result = kids[0].next_skoarpuscle;

                    if (result != nil) {
                        kids.do {
                            | y |
                            var x = y.skoarpuscle;
                            if (x.isKindOf(SkoarpuscleMsg)) {
                                result = result.skoar_msg(x, minstrel);
                            };

                        };

                        "result: ".post; result.skoarpuscle.postln;
                        result
                    } {
                        var x = noad.next_skoarpuscle;

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
                y.skoarpuscle = y.next_skoarpuscle;
                y = y.skoarpuscle;

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