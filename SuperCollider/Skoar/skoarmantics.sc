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

            \skoar -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion.new_from_skoar(skoar));
                noad.children = [];
            },

            \skoarpion -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion(skoar, noad));
                noad.children = [];
            },

            \conditional -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleConditional(noad);
                noad.children = [];
            },

            \boolean -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleBoolean(noad);
                noad.children = [];
            },

            \beat -> {
                | skoar, noad |
                var x = noad.next_skoarpuscle;
                noad.skoarpuscle = x;
                noad.performer = {
                    | m, nav |
                    x.performer(m, nav);
                };
                noad.children = [];
            },

            \listy -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArray(noad.collect_skoarpuscles);
                noad.children = [];
            },

            \musical_keyword_misc -> {
                | skoar, noad |
                var skoarpuscle = noad.next_skoarpuscle;

                if (skoarpuscle.isKindOf(SkoarpuscleCarrots)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };
            },

            \ottavas -> {
                | skoar, noad |
                var x = noad.next_skoarpuscle;

                noad.performer = {
                    | m, nav |
                    x.performer(m, nav);
                };
            },

            \cthulhu -> {
                | skoar, noad |
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            \dynamic -> {
                | skoar, noad |
                var x = noad.next_skoarpuscle;

                noad.performer = {
                    | m, nav |
                    x.performer(m, nav);
                };
            },

            \dal_goto -> {
                | skoar, noad |
                var x = SkoarpuscleGoto(noad);

                noad.performer = {
                    | m, nav |
                    x.performer(m, nav);
                };
            },

            \marker -> {
                | skoar, noad |

                var x = noad.next_skoarpuscle;

                if (x.notNil) {
                    noad.performer = {
                        | m, nav |
                        x.performer(m, nav);
                    };

                    if (x.isKindOf(SkoarpuscleBars)) {
                        x.noad = noad;
                        noad.children = [];
                    };
                };
            },

            \nouny -> {
                | skoar, noad |

                var x = noad.next_skoarpuscle;

                if (x.notNil) {
                    noad.skoarpuscle = x;
                    noad.children = [];
                };
            },

            // deref*         : Deref MsgNameWithArgs listy_suffix
            //                | Deref MsgName
            \deref -> {
                | skoar, noad |

                var x;
                var args;
                var msg_name;

                msg_name = noad.children[1].skoarpuscle.val;

                if (noad.children.size > 2) {
                    args = SkoarpuscleArgs(noad.collect_skoarpuscles(2));
                };

                noad.skoarpuscle = SkoarpuscleDeref(msg_name, args);
                noad.children = [];
            },

            \args -> {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArgs(noad.collect_skoarpuscles);
                noad.children = [];
            },

            \msg -> {
                | skoar, noad |
                var x = nil;
                var args = nil;

                x = noad.next_skoarpuscle;

                case {x.isKindOf(SkoarpuscleArray)} {
                    // here they are sending an array as the message
                    // this is the same as the \for message
                    "\\msg -> SkoarpuscleEach".postln;
                    noad.skoarpuscle = SkoarpuscleEach(x);

                } {x.isKindOf(SkoarpuscleMsgName)} {
                    args = SkoarpuscleArray(noad.collect_skoarpuscles(1));
                    noad.skoarpuscle = SkoarpuscleMsg(x.val, args);
                };

                noad.children = [];
            },

            \stmt -> {
                | skoar, noad |
                var skoaroid = noad.children[0];
                var y = noad.children[1];

                noad.performer = if (y.notNil) {

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
                    //"performing result".postln; skrd.asString.postln;
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

                // evaluate messages, returning the result
                noad.evaluate = {
                    | minstrel |
                    var result = kids[0].next_skoarpuscle;

                    if (result.notNil) {
                        kids.do {
                            | y |
                            var x = y.skoarpuscle;
                            if (x.isKindOf(SkoarpuscleMsg)) {
                                result = result.skoar_msg(x, minstrel);
                            };
                        };

                        //"result: ".post; result.postln;
                        result
                    } {
                        var x = noad.next_skoarpuscle;

                        if (x.notNil) {
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

        ];
        ^dict;
    }

}