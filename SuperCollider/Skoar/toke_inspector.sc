// ==============
// toke_inspector
// ==============
//
// Here we pick the values out of the tokens
// and set its attributes appropriately

SkoarTokeInspector {

    *new {

        var dict = IdentityDictionary[

            \Toke_Int -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleInt(toke.lexeme.asInteger);
                noad.toke = nil;
            },

            \Toke_Float -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleFloat(toke.lexeme.asFloat);
                noad.toke = nil;
            },

            \Toke_NamedNoat -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleNoat(toke.lexeme);
                noad.toke = nil;
            },

            \Toke_Choard -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleChoard(toke.lexeme);
                noad.toke = nil;
            },

            \Toke_String -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleString(toke.lexeme.asString);
                noad.toke = nil;
            },

            \Toke_Symbol -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbol(toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            \Toke_SymbolName -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbolName(toke.lexeme.asSymbol);
                noad.toke = nil;
            },

            // rests
            // } }} }}}
            \Toke_Crotchets -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // o/ oo/ ooo/
            \Toke_Quavers -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // unrests
            \Toke_Quarters -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            \Toke_Eighths -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            \Toke_Bars -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBars(noad, toke);
                noad.toke = nil;
            },

            \Toke_Volta -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleVolta(noad, toke);
                noad.toke = nil;
            },

            \Toke_Meter -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleMeter(toke);
                noad.toke = nil;
            },

            \Toke_Carrots -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleCarrots(toke);
                noad.toke = nil;
            },
            
            \Toke_Tuplet -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleTuplet(toke);
                noad.toke = nil;
            },

            \Toke_DynPiano -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            \Toke_DynForte -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            \Toke_OctaveShift -> {
                | noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_OttavaA -> {
                | noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_OttavaB  -> {
                | noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_QuindicesimaA -> {
                | noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_QuindicesimaB -> {
                | noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_BooleanOp -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleBooleanOp(toke);
                noad.toke = nil;
            },

            \Toke_Voice -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleVoice(toke);
                noad.toke = nil;
            },

            \Toke_Segno -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleSegno(noad, toke);
                noad.toke = nil;
            },

            \Toke_Rep -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleRep(toke);
                noad.toke = nil;
            },

            \Toke_Fine -> {
                | noad, toke |
                noad.skoarpuscle = SkoarpuscleFine.new;
                noad.toke = nil;
            },

            \Toke_MsgName -> {
                | noad, toke |
                toke.val = toke.lexeme.asSymbol;
            },

            \Toke_MsgNameWithArgs -> {
                | noad, toke |
                var s = toke.lexeme;
                toke.val = s[0..s.size-2].asSymbol;
            }

        ];
        ^dict;
    }

}
