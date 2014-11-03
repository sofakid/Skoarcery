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
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleInt(toke.lexeme.asInteger);
                noad.toke = nil;
            },

            \Toke_Float -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFloat(toke.lexeme.asFloat);
                noad.toke = nil;
            },

            \Toke_NamedNoat -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleNoat(toke.lexeme);
                noad.toke = nil;
            },

            \Toke_Choard -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleChoard(toke.lexeme);
                noad.toke = nil;
            },

            \Toke_String -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleString(toke.lexeme.asString);
                noad.toke = nil;
            },

            \Toke_Symbol -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbol(toke.lexeme[1..].asSymbol);
                noad.toke = nil;
            },

            \Toke_SymbolName -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSymbolName(toke.lexeme.asSymbol);
                noad.toke = nil;
            },

            // rests
            // } }} }}}
            \Toke_Crotchets -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // o/ oo/ ooo/
            \Toke_Quavers -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRest(toke);
                noad.toke = nil;
            },

            // unrests
            \Toke_Quarters -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            \Toke_Eighths -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBeat(toke);
                noad.toke = nil;
            },

            \Toke_Bars -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBars(noad, toke);
                noad.toke = nil;
            },

            \Toke_Volta -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleVolta(noad, toke);
                noad.toke = nil;
            },

            \Toke_Meter -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleMeter(toke);
                noad.toke = nil;
            },

            \Toke_Carrots -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleCarrots(toke);
                noad.toke = nil;
            },
            
            \Toke_Tuplet -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleTuplet(toke);
                noad.toke = nil;
            },

            \Toke_DynPiano -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            \Toke_DynForte -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleDynamic(toke);
                noad.toke = nil;
            },

            \Toke_OctaveShift -> {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_OttavaA -> {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_OttavaB  -> {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_QuindicesimaA -> {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_QuindicesimaB -> {
                | skoar, noad, toke |

                noad.skoarpuscle = SkoarpuscleOctaveShift(toke);
                noad.toke = nil;
            },

            \Toke_BooleanOp -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleBooleanOp(toke);
                noad.toke = nil;
            },

            \Toke_Voice -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleVoice(toke);
                noad.toke = nil;
            },

            \Toke_Segno -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleSegno(noad, toke);
                noad.toke = nil;
            },

            \Toke_Rep -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleRep(toke);
                noad.toke = nil;
            },

            \Toke_Fine -> {
                | skoar, noad, toke |
                noad.skoarpuscle = SkoarpuscleFine.new;
                noad.toke = nil;
            },

            \Toke_MsgName -> {
                | skoar, noad, toke |
                toke.val = toke.lexeme.asSymbol;
            },

            \Toke_MsgNameWithArgs -> {
                | skoar, noad, toke |
                var s = toke.lexeme;
                toke.val = s[0..s.size-2].asSymbol;
            }

        ];
        ^dict;
    }

}
