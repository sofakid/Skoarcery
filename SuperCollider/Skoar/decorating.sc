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

			Toke_Cat: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleCat.new;
                noad.toke = nil;
            },

            Toke_Freq: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFreq(toke.lexeme);
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
                noad.skoarpuscle = SkoarpuscleString(toke.lexeme[1..toke.lexeme.size-2]);
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

			Toke_SymbolColon: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbolColon(toke.lexeme);
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

            Toke_OttavaB: {
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
                noad.skoarpuscle = SkoarpuscleBooleanOp(noad, toke);
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
                noad.skoarpuscle = SkoarpuscleMsgNameWithArgs(s[0..s.size-2].asSymbol);
                noad.toke = nil;
            },

            Toke_MathOp: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleMathOp(toke);
                noad.toke = nil;
            },

            Toke_ListSep: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleListSep.new;
                noad.toke = nil;
            },

            Toke_ListE: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleListEnd.new;
                noad.toke = nil;
            },

			Toke_Times: {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleTimes.new;
                noad.toke = nil;
            },

			Toke_ArgSpec: {
			    | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleArgSpec(toke);
                noad.toke = nil;
            },

			Toke_HashLevel: {
			    | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleHashLevel(toke);
                noad.toke = nil;
            },

			Toke_True: {
			    | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleTrue.new;
                noad.toke = nil;
            },

			Toke_False: {
			    | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFalse.new;
                noad.toke = nil;
            },

			Toke_UGen: {  | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleUGen.new_from_toke(toke);
                noad.toke = nil;
            },

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

   i.e. from the noad's perspective, its children are processed already 

*/
Skoarmantics {

    *new {

        var dict = (

            skoar: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion.new_from_skoar(skoar));
                noad.children = #[];
            },

            skoarpion: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleSkoarpion(Skoarpion(skoar, noad));
                noad.children = #[];
            },

            conditional: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleConditional(skoar, noad);
            },

			boolean_expr: {
                | skoar, noad |
                // we insert a node at the end of the boolean expression
                // so we can restore the impression
                var end_noad = SkoarNoad(\boolean_expr_end, noad);
				
				noad.on_enter = {
					| m, nav |
					m.fairy.push_boolean;
				};

				end_noad.on_enter = {
                    | m, nav |
					m.fairy.pop_boolean;
                };

                noad.add_noad(end_noad);
            },


            boolean: {
                | skoar, noad |
				// we insert a node at the end of the expression
                // so we can evaluate the result
                var end_noad = SkoarNoad(\boolean_end, noad);
				var x = SkoarpuscleBoolean(noad);
				noad.skoarpuscle = x;
				
				end_noad.on_enter = {
                    | m, nav |
					var l_value = m.fairy.l_value;
                    var y;
					var imp = m.fairy.impression;
					
					//("derp " ++ l_value.asString ++ " imp: " ++ imp.asString).postln;
					y = x.evaluate(m, nav, l_value, imp);

					m.fairy.impress(y);
					m.fairy.pop_compare;
                };

                noad.add_noad(end_noad);
            },

            beat: {
                | skoar, noad |
                noad.skoarpuscle = noad.next_skoarpuscle;
                noad.children = #[];
                
                if (noad.parent.name != \assignment) {
                    noad.on_enter = {
                        | m, nav |
                        noad.skoarpuscle.on_enter_(m, nav);
                    };
                };
            },

            loop: {
                | skoar, noad |
				var x = SkoarpuscleLoop(skoar, noad);
                noad.skoarpuscle = x; 
                noad.children = #[];
            },

			cuts: {
                | skoar, noad |
				var x = SkoarpuscleCuts(skoar, noad);
                noad.skoarpuscle = x; 
                noad.children = #[];
            },

            musical_keyword_misc: {
                | skoar, noad |
                noad.skoarpuscle = noad.next_skoarpuscle;
				noad.children = #[];
            },

            cthulhu: {
                | skoar, noad |
                noad.on_enter = {skoar.cthulhu(noad);};
            },

            dal_goto: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleGoto(noad);
            },

            marker: {
                | skoar, noad |

                var x = noad.next_skoarpuscle;
				noad.skoarpuscle = x;

                if (x.isKindOf(SkoarpuscleBars)) {
                    x.noad = noad;
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
                    args = SkoarpuscleArgs.new;
                };

                x = SkoarpuscleDeref(msg_name, args);
                noad.skoarpuscle = x;

                // !f<x,y>
                if (args.isKindOf(SkoarpuscleArgs)) {

                    var end_noad = SkoarNoad(\deref_end, noad);
                    end_noad.on_enter = {
                        | m, nav |
						x.on_exit(m, nav);
                    };

                    noad.add_noad(end_noad);
				};
            },

            listy: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleList.new;
            },

            args: {
                | skoar, noad |
                noad.skoarpuscle = SkoarpuscleArgSpec(noad);
                noad.children = #[];
            },

            msg: {
                | skoar, noad |
                var msg = nil;
                var args = nil;

                msg = noad.next_skoarpuscle;

                case {msg.isKindOf(SkoarpuscleList)} {
                    // i'm not sure what i want this to mean
					noad.children = [];	
                } {msg.isKindOf(SkoarpuscleLoop)} {
					noad.skoarpuscle = SkoarpuscleLoopMsg(msg);
					
					// this sets up the skoarpuscle to foreach, but the looping 
					// happens in the skoarpuscle.on_enter
					noad.on_enter = {
						| m, nav |
						var listy = m.fairy.impression;
						msg.foreach(listy);
						msg.on_enter(m, nav);
					};

					noad.children = [];
					
                } {msg.isKindOf(SkoarpuscleMsgNameWithArgs)} {
					var end_noad = SkoarNoad(\msg_end, noad);
					args = SkoarpuscleArgs.new;
					
					end_noad.skoarpuscle = SkoarpuscleMsg(msg.val, args);
                    noad.add_noad(end_noad);
					
                } {msg.isKindOf(SkoarpuscleMsgName)} {
					// we need the tree to have the same
					// structure as above. I don't want to
					// treat these too differently (with args or without)
					var end_noad = SkoarNoad(\msg_end, noad);
					end_noad.skoarpuscle = SkoarpuscleMsg(msg.val, nil);
                    noad.add_noad(end_noad);
				};

            },

            expr: {
                | skoar, noad |
                // we insert a node at the end of the expression
                // so we can impress the result
                var end_noad = SkoarNoad(\expr_end, noad);
				var child = noad.children[0].skoarpuscle;
				
				if (child.isKindOf(SkoarpuscleSymbolColon)) {
					var settable = child; 
					end_noad.on_enter = {
						| m, nav |
						var x = m.fairy.cast_arcane_magic;
						var p;

						//Skoar.ops.assign(m, x, settable);

						p = SkoarpusclePair(settable, x);
                        m.fairy.impress(p);
					}
				} {
					end_noad.on_enter = {
						| m, nav |
						m.fairy.cast_arcane_magic;
					}
				};

                noad.add_noad(end_noad);

				
            },

            msgable: {
                | skoar, noad |
                var noads = List[];
				var has_messages = false;
				var skoarpuscle = noad.next_skoarpuscle;
				var last = skoarpuscle;
				
				if (skoarpuscle.isKindOf(SkoarpuscleUGen)) {
					var end_noad = SkoarNoad(\msgable_end, noad);

					end_noad.on_enter = {
                        | m, nav |
						//skoarpuscle.compile_synthdef;
						m.fairy.impress(skoarpuscle);
						//skoarpuscle.dump;
                    };

                    noad.add_noad(end_noad);
				};

				last = skoarpuscle;
                // strip out the msg operators
                noad.children.do {
                    | x |
                    if (x.toke.isKindOf(Toke_MsgOp) == false) {
						x.children.do {
							| y |
							if (y.skoarpuscle.isKindOf(SkoarpuscleMsg)) {
								y.skoarpuscle.dest = last;
								last = y;
							};
						};
						
                        noads.add(x);
                    } {
						has_messages = true;
					};
                };

				if (has_messages == true) {
					noad.children = noads.asArray;
				};

            },

            assignment: {
                | skoar, noad |
                var op = nil;
                var settable = nil;

                op = noad.children[0].toke.lexeme;
                settable = noad.children[1].next_skoarpuscle;
				settable.impressionable = false;

                noad.on_enter = switch (op)
                    {"+>"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        Skoar.ops.increment(m, x, settable);
                    }}

                    {"->"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        Skoar.ops.decrement(m, x, settable);
                    }}

                    {"=>"} {{
                        | m, nav |
                        var x = m.fairy.cast_arcane_magic;
                        Skoar.ops.assign(m, x, settable);
                        
                        m.fairy.impress(x);
                        
                    }}
					
					{"*>"} {{
                        | m, nav |
                        var x = m.fairy.impression;
                        Skoar.ops.multr(m, x, settable);
                    }};
            },

            math: {
                | skoar, noad |
                var op = noad.children[0].skoarpuscle;

                noad.on_enter = {
                    | m, nav |
                    var left = m.fairy.cast_arcane_magic;

                    m.fairy.charge_arcane_magic({
                        var right = m.fairy.impression;
                        op.calculate(m, nav, left, right);
                        m.fairy.impression
                    });

                };

            },

        );
        ^dict;
    }

}