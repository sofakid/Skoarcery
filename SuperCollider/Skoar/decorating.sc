// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        ^(
            Toke_Fairy: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFairy.new;
                noad.toke = nil;
            },

            Toke_Int: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleInt(toke.lexeme.asInteger);
                noad.toke = nil;
            },

            Toke_Float: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFloat(toke.lexeme.asFloat);
                noad.toke = nil;
            },

            Toke_NamedNoat: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleNoat(toke.lexeme);
                noad.toke = nil;
            },

            Toke_Choard: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleChoard(toke.lexeme);
                noad.toke = nil;
            },

            Toke_String: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleString(toke.lexeme.asString);
                noad.toke = nil;
            },

            Toke_Symbol: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbol(toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            Toke_SymbolName: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbolName(toke.lexeme.asSymbol);
                noad.toke = nil;
            },

            // rests
            // } }} }}}
            Toke_Crotchets: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // o/ oo/ ooo/
            Toke_Quavers: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // unrests
            Toke_Quarters: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            Toke_Eighths: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            Toke_Bars: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBars(toke);
                noad.toke = nil;
            },

            Toke_Volta: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleVolta(noad, toke);
                noad.toke = nil;
            },

            Toke_Meter: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleMeter(toke);
                noad.toke = nil;
            },

            Toke_Carrots: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleCarrots(toke);
                noad.toke = nil;
            },

            Toke_Tuplet: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleTuplet(toke);
                noad.toke = nil;
            },

            Toke_DynPiano: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            Toke_DynForte: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            Toke_OctaveShift: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            Toke_OttavaA: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            Toke_OttavaB : {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            Toke_QuindicesimaA: {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            Toke_QuindicesimaB: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            Toke_BooleanOp: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBooleanOp(toke);
                noad.toke = nil;
            },

            Toke_Voice: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleVoice(toke);
                noad.toke = nil;
            },

            Toke_Segno: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSegno(noad, toke);
                noad.toke = nil;
            },

            Toke_Rep: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRep(toke);
                noad.toke = nil;
            },

            Toke_Fine: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFine.new;
                noad.toke = nil;
            },

            Toke_MsgName: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleMsgName(toke.lexeme.asSymbol);
                noad.toke = nil;
            },

            Toke_MsgNameWithArgs: {
                | skoar, noad, toke |
                var s = toke.lexeme;
                noad.skoarpuscle = SkoarpuscleMsgName(s[0..s.size-2].asSymbol);
                noad.toke = nil;
            },

            Toke_MathOp: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleMathOp(toke);
                noad.toke = nil;
            }

        );
    }

}

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

        var dict = (

            skoar: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion.new_from_skoar(skoar));
                noad.children = [];
            },

            skoarpion: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion(skoar, noad));
                noad.children = [];
            },

            conditional: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleConditional(skoar, noad);
                noad.children = [];
            },

            boolean: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleBoolean(noad);
                noad.children = [];
            },

            beat: {
                | skoar, noad |
                var x = noad.next_skoarpuscle;
                noad.skoarpuscle = x;
                noad.on_enter = {
                    | m, nav |
                    x.on_enter(m, nav);
                };
                noad.children = [];
            },

            listy: {
                | skoar, noad |

                // collecting skoarpuscles isn't enough.
                // there can be statements.

                noad.skoarpuscle = SkoarpuscleArray(noad.collect_skoarpuscles);
                noad.children = [];
            },

            loop: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleLoop(skoar, noad);
                noad.children = [];
            },

            musical_keyword_misc: {
                | skoar, noad |
                var skoarpuscle = noad.next_skoarpuscle;

                if (skoarpuscle.isKindOf(SkoarpuscleCarrots)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };
            },

            ottavas: {
                | skoar, noad |
                var x = noad.next_skoarpuscle;

                noad.on_enter = {
                    | m, nav |
                    x.on_enter(m, nav);
                };
            },

            cthulhu: {
                | skoar, noad |
                noad.on_enter = {skoar.cthulhu(noad);};
            },

            dynamic: {
                | skoar, noad |
                var x = noad.next_skoarpuscle;

                noad.on_enter = {
                    | m, nav |
                    x.on_enter(m, nav);
                };
            },

            dal_goto: {
                | skoar, noad |
                var x = SkoarpuscleGoto(noad);

                noad.on_enter = {
                    | m, nav |
                    x.on_enter(m, nav);
                };
            },

            marker: {
                | skoar, noad |

                var x = noad.next_skoarpuscle;

                if (x.notNil) {
                    noad.on_enter = {
                        | m, nav |
                        x.on_enter(m, nav);
                    };

                    if (x.isKindOf(SkoarpuscleBars)) {
                        x.noad = noad;
                        noad.children = [];
                    };
                };
            },

            nouny: {
                | skoar, noad |

                var x = noad.next_skoarpuscle;

                if (x.notNil) {
                    noad.skoarpuscle = x;
                    noad.children = [];
                };
            },

            // deref*         : Deref MsgNameWithArgs listy_suffix
            //                | Deref MsgName
            deref: {
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

            args: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArgs(noad.collect_skoarpuscles);
                noad.children = [];
            },

            msg: {
                | skoar, noad |
                var msg = nil;
                var args = nil;

                msg = noad.next_skoarpuscle;

                case {msg.isKindOf(SkoarpuscleArray)} {
                    // i'm not sure what i want this to mean

                } {msg.isKindOf(SkoarpuscleLoop)} {
                    noad.skoarpuscle = SkoarpuscleLoopMsg(msg);

                } {msg.isKindOf(SkoarpuscleMsgName)} {
                    args = SkoarpuscleArray(noad.collect_skoarpuscles(1));
                    noad.skoarpuscle = SkoarpuscleMsg(msg.val, args);
                };

                noad.children = [];
            },

            // at the end of an expression, this one
            // will exist with no children (it parsed <empty>)
            expr: {
                | skoar, noad |

                noad.on_exit = {
                    | m, nav |
                    m.fairy.impression.on_enter(m, nav);
                    m.fairy.impression.on_exit(m, nav);
                };
            },

            msgable: {
                | skoar, noad |
                var noads = List[];

                // strip out the msg operators
                noad.children.do {
                    | x |
                    if (x.toke.isKindOf(Toke_MsgOp) == false) {
                        noads.add(x);
                    };
                };

                noad.children = noads.asArray;
                noads = noad.children;

                // evaluate a chain of messages, returning the result
                noad.on_enter = {
                    | m, nav |
                    var result = noads[0].next_skoarpuscle;

                    if (result.notNil) {
                        noads.do {
                            | y |
                            var x = y.skoarpuscle;
                            case {x.isKindOf(SkoarpuscleMsg)} {
                                result = result.skoar_msg(x, m);

                            } {x.isKindOf(SkoarpuscleLoopMsg)} {
                                result = x.val.foreach(result);
                            };
                        };

                        "result: ".post; result.postln;
                        m.fairy.impress(result);
                    };

                };
            },

            assignment: {
                | skoar, noad |
                var op = nil;
                var settable = nil;

                op = noad.children[0].toke.lexeme;
                settable = noad.children[1].next_skoarpuscle;

                noad.on_enter = switch (op)
                    {"+>"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        // todo Skoar.ops.increment(m, x, settable);
                    }}

                    {"->"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        // todo Skoar.ops.decrement(m, x, settable);
                    }}

                    {"=>"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        Skoar.ops.assign(m, x, settable);
                    }};
            },

            math: {
                | skoar, noad |
                var op;
                var r_value;
                var result;

                op = noad.children[0].skoarpuscle;
                r_value = noad.children[1].next_skoarpuscle;

                noad.on_enter = {
                    | m, nav |
                    op.on_enter(m, nav, r_value);
                    result = m.fairy.impression;
                };

                noad.on_exit = {
                    | m, nav |
                    m.fairy.impress(result);
                };

            },

        );
        ^dict;
    }

}