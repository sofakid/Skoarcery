// ============
// Skoarmantics
// ============

/*

This code is applied during the decoration stage of compiling the skoar tree.

For stuff to happen during performance of the tree, we set handlers here.

We also shrink the tree, drop some punctuation noads.

We went depth first and run the code on the way back,
   so children are processed first.

*/
Skoarmantics {

    *new {

        var dict = IdentityDictionary[

            \skoarpion -> {
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

                noad.skoarpuscle = SkoarpuscleSkoarpion(x);
            },

            \skoarpion_line -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                // drop the newline
                noad.children.pop;
                noad.n = n - 1;

            },

            \branch -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                noad.branch = noad;

                if (x.isKindOf(Toke_Voice)) {
                    noad.voice = skoar.get_voice(x.val);
                };

            },

            \beat -> {
                | skoar, noad |
                noad.skoarpuscle = noad.next_skoarpuscle;
                noad.children = [];
                noad.n = 0;
                noad.performer = {
                    | m, nav |
                    noad.skoarpuscle.performer(m, nav);
                };
            },

            \listy -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArray(noad.collect_skoarpuscles);

                noad.n = 0;
                noad.children = [];

            },

            \musical_keyword_misc -> {
                | skoar, noad |

                var toke = noad.next_toke;

                if (toke.isKindOf(Toke_Carrot)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };

            },

            \ottavas -> {
                | skoar, noad |

                var toke;

                toke = noad.next_toke;

                noad.performer = case {toke.isKindOf(Toke_OctaveShift)} {{
                    | m, nav |
                    m.voice.octave_shift(toke.val);

                }} {toke.isKindOf(Toke_OttavaA)} {{
                    | m, nav |
                    m.voice.octave_shift(1);

                }} {toke.isKindOf(Toke_OttavaB)} {{
                    | m, nav |
                    m.voice.octave_shift(-1);

                }} {toke.isKindOf(Toke_QuindicesimaA)} {{
                    | m, nav |
                    m.voice.octave_shift(2);

                }} {toke.isKindOf(Toke_QuindicesimaB)} {{
                    | m, nav |
                    m.voice.octave_shift(-2);
                }};

            },

            \cthulhu -> {
                | skoar, noad |
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            \dynamic -> {
                | skoar, noad |
                var toke = noad.next_toke;

                noad.performer = {
                    | m |
                    m.voice.dynamic(toke);
                };
            },

            \dal_goto -> {
                | skoar, noad |

                var toke = noad.children[0];
                var al_x = noad.children[1];
                var al_fine = false;

                if (al_x != nil && al_x.isKindOf(Toke_AlFine)) {
                    al_fine = true;
                };

                noad.performer = case {toke.isKindOf(Toke_DaCapo)} {{
                    | m, nav |

                    if (al_fine) {
                        m.al_fine = true;
                    };

                    nav.(\nav_da_capo);

                }} {toke.isKindOf(Toke_DalSegno)} {{
                    | m, nav |

                    if (al_fine) {
                        m.al_fine = true;
                    };

                    m.reset_colons;
                    nav.(\nav_segno);
                }};

            },

            \marker -> {
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


            \pedally -> {
                | skoar, noad |
                var toke = noad.next_toke;

                noad.performer = case {toke.isKindOf(Toke_PedalUp)} {{
                    | m, nav |
                    m.voice.pedal_up;

                }} {toke.isKindOf(Toke_PedalDown)} {{
                    | m, nav |
                    m.voice.pedal_down;

                }};

            },


            \nouny -> {
                | skoar, noad |

                var x = nil;
                var clean = {
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
            \seq_ref -> {
                | skoar, noad |

                var x;
                var args;
                var msg_name;

                var clean = {
                    noad.children = [];
                    noad.n = 0;
                };

                msg_name = noad.children[1].val;

                if (noad.children.size > 2) {
                    args = SkoarpuscleArgs(noad.children[2].collect_skoarpuscles);
                };

                noad.skoarpuscle = SkoarpuscleSeqRef(msg_name, args);
                clean.();

            },

            \args -> {
                | skoar, noad |

                noad.skoarpuscle = SkoarpuscleArgs(noad.collect_skoarpuscles);
                noad.children = [];
                noad.n = 0;
            },

            \msg -> {
                | skoar, noad |

                var x = nil;
                var args = nil;


                x = noad.children[0].val;
                args = SkoarpuscleArray(noad.collect_skoarpuscles);
                noad.skoarpuscle = SkoarpuscleMsg(x, args);

                noad.children = [];
                noad.n = 0;

            },

            \stmt -> {
                | skoar, noad |
                var skoaroid = noad.children[0];
                var y = noad.children[1];

                noad.performer = case {y.isKindOf(SkoarNoad)} {
                    if (y.name == \assignment) {{
                        | m, nav |
                        var res = skoaroid.evaluate.(m);
                        y.setter.(res, m.voice);
                    }}

                } {{
                    | m, nav |
                    skoaroid = skoaroid.evaluate.(m);
                    skoaroid.performer(m, nav);

                }};

            },

            \skoaroid -> {
                | skoar, noad |

                var kids = List[];

                // strip out the operators
                noad.children.do {
                    | x |
                    if (x.isKindOf(Toke_MsgOp) == false) {
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

                        "result: ".post; result.postln;
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

            \assignment -> {
                | skoar, noad |

                var op = nil;

                // the settable
                var y = nil;

                op = noad.children[0].lexeme;
                y = noad.next_skoarpuscle;
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

            \boolean -> {
                | skoar, noad |
            },

            \coda -> {
                | skoar, noad |
            }

        ];
        ^dict;
    }

}