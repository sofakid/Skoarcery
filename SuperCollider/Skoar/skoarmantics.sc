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
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion(noad));
            },

            \branch -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.next_skoarpuscle;

                noad.branch = noad;

                if (x.isKindOf(SkoarpuscleVoice)) {
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
                var x = noad.next_skoarpuscle;

                if (x.isKindOf(SkoarpuscleCarrot)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };

            },

            \ottavas -> {
                | skoar, noad |
                var skoarpuscle = noad.next_skoarpuscle;

                noad.performer = {
                    | m, nav |
                    skoarpuscle.performer(m, nav);
                };

            },

            \cthulhu -> {
                | skoar, noad |
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            \dynamic -> {
                | skoar, noad |
                var skoarpuscle = noad.next_skoarpuscle;

                noad.performer = {
                    | m, nav |
                    skoarpuscle.performer(m, nav);
                };
            },

            \dal_goto -> {
                | skoar, noad |
                var s = SkoarpuscleGoto(noad);

                noad.performer = {
                    | m, nav |
                    "blerg?".postln;
                    s.performer(m, nav);

                };
            },

            \marker -> {
                | skoar, noad |

                var skoarpuscle = noad.next_skoarpuscle;

                if (skoarpuscle != nil) {
                    noad.performer = {
                        | m, nav |
                        "zerp?".postln;
                        skoarpuscle.performer(m, nav);
                    };
                };

            },


            \pedally -> {
                | skoar, noad |
                var toke = noad.next_toke;

                noad.performer = case {toke.isKindOf(Toke_PedalUp)} {{
                    | m, nav |
                    m.koar.pedal_up;

                }} {toke.isKindOf(Toke_PedalDown)} {{
                    | m, nav |
                    m.koar.pedal_down;

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

            // deref*         : Deref MsgNameWithArgs listy_suffix
            //                | Deref MsgName
            \deref -> {
                | skoar, noad |

                var x;
                var args;
                var msg_name;

                var clean = {
                    noad.children = [];
                    noad.n = 0;
                };

                msg_name = noad.children[1].toke.val;

                if (noad.children.size > 2) {
                    args = SkoarpuscleArgs(noad.collect_skoarpuscles(2));
                };

                noad.skoarpuscle = SkoarpuscleDeref(msg_name, args);
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

                x = noad.next_toke.val;
                args = SkoarpuscleArray(noad.collect_skoarpuscles);
                noad.skoarpuscle = SkoarpuscleMsg(x, args);

                noad.children = [];
                noad.n = 0;

            },

            \stmt -> {
                | skoar, noad |
                var skoaroid = noad.children[0];
                var y = noad.children[1];

                noad.performer = if (y != nil) {

                    if (y.name == \assignment) {{
                        | m, nav |
                        var res = skoaroid.evaluate.(m);
                        y.setter.(res, m.koar);

                    }}

                } {{
                    | m, nav |
                    var skrd; // don't overwrite skoaroid
                    //"evaluating...".postln;
                    skrd = skoaroid.evaluate.(m);
                    //"performing result".postln;
                    skrd.performer(m, nav);

                }};

            },

            \skoaroid -> {
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
                var settable = nil;

                op = noad.children[0].toke.lexeme;
                settable = noad.children[1].next_skoarpuscle;

                // we prepare the destination here (noad.f), we'll setup the write in skoaroid

                noad.setter = switch (op)
                    {"+>"} {{
                        | x, voice |
                        voice.assign_incr(x, settable);
                    }}

                    {"->"} {{
                        | x, voice |
                        voice.assign_decr(x, settable);
                    }}

                    {"=>"} {{
                        | x, voice |
                        voice.assign_set(x, settable);
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